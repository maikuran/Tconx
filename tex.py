from pathlib import Path
import math
import random
import re

from PIL import Image, ImageDraw, ImageFilter


MODID = "sakalti"
SIZE = 128
SEED = 20260816

ROOT = Path(__file__).resolve().parent

RESOURCE_DIR = (
    ROOT
    / "src"
    / "main"
    / "resources"
)

TEXTURE_DIR = (
    RESOURCE_DIR
    / "assets"
    / MODID
    / "textures"
    / "fluid"
)

JAVA_DIR = (
    ROOT
    / "src"
    / "main"
    / "java"
)


def clamp(value):
    return max(0, min(255, int(value)))


def mix(a, b, amount):
    return tuple(
        clamp(
            a[i] * (1.0 - amount)
            + b[i] * amount
        )
        for i in range(3)
    )


def noise(x, y, seed):
    value = (
        math.sin(
            x * 12.9898
            + y * 78.233
            + seed * 37.719
        )
        * 43758.5453
    )

    return value - math.floor(value)


def smooth_noise(x, y, seed, scale):
    total = 0.0
    amplitude = 1.0
    frequency = 1.0
    normalization = 0.0

    for octave in range(4):
        nx = x / scale * frequency
        ny = y / scale * frequency

        ix = math.floor(nx)
        iy = math.floor(ny)

        fx = nx - ix
        fy = ny - iy

        fx = fx * fx * (3.0 - 2.0 * fx)
        fy = fy * fy * (3.0 - 2.0 * fy)

        octave_seed = seed + octave * 17

        n00 = noise(ix, iy, octave_seed)
        n10 = noise(ix + 1, iy, octave_seed)
        n01 = noise(ix, iy + 1, octave_seed)
        n11 = noise(ix + 1, iy + 1, octave_seed)

        nx0 = n00 * (1.0 - fx) + n10 * fx
        nx1 = n01 * (1.0 - fx) + n11 * fx

        value = nx0 * (1.0 - fy) + nx1 * fy

        total += value * amplitude
        normalization += amplitude

        amplitude *= 0.5
        frequency *= 2.0

    return total / normalization


def seamless_noise(x, y, seed, scale):
    px = (x % SIZE) / SIZE
    py = (y % SIZE) / SIZE

    angle_x = px * math.tau
    angle_y = py * math.tau

    sx = math.cos(angle_x) * scale
    sy = math.sin(angle_x) * scale

    tx = math.cos(angle_y) * scale
    ty = math.sin(angle_y) * scale

    return smooth_noise(
        sx + tx,
        sy + ty,
        seed,
        scale,
    )


def create_fluid_texture(
    base_color,
    seed,
    flowing=False,
):
    image = Image.new(
        "RGB",
        (SIZE, SIZE),
    )

    pixels = image.load()

    dark = mix(
        base_color,
        (0, 0, 0),
        0.30,
    )

    bright = mix(
        base_color,
        (255, 255, 255),
        0.24,
    )

    for y in range(SIZE):
        for x in range(SIZE):

            if flowing:
                n = seamless_noise(
                    x,
                    y * 0.42,
                    seed,
                    16.0,
                )
            else:
                n = seamless_noise(
                    x * 0.75,
                    y * 0.75,
                    seed,
                    18.0,
                )

            fine = seamless_noise(
                x * 1.5,
                y * 1.5,
                seed + 100,
                8.0,
            )

            value = (
                0.25
                + n * 0.75
                + (fine - 0.5) * 0.12
            )

            value = max(
                0.0,
                min(1.0, value),
            )

            if value < 0.5:
                color = mix(
                    dark,
                    base_color,
                    value * 2.0,
                )
            else:
                color = mix(
                    base_color,
                    bright,
                    (value - 0.5) * 2.0,
                )

            pixels[x, y] = color

    gloss = Image.new(
        "RGBA",
        (SIZE, SIZE),
        (0, 0, 0, 0),
    )

    draw = ImageDraw.Draw(gloss)

    random.seed(seed + 5000)

    count = 28 if flowing else 24

    for _ in range(count):
        x = random.randint(
            -20,
            SIZE + 20,
        )

        y = random.randint(
            -20,
            SIZE + 20,
        )

        length = random.randint(
            12,
            42,
        )

        if flowing:
            end = (
                x + random.randint(-2, 2),
                y + length,
            )
        else:
            end = (
                x + length,
                y + random.randint(-2, 2),
            )

        draw.line(
            [(x, y), end],
            fill=(
                255,
                255,
                255,
                random.randint(18, 70),
            ),
            width=random.randint(1, 3),
        )

    gloss = gloss.filter(
        ImageFilter.GaussianBlur(1.3)
    )

    image = Image.alpha_composite(
        image.convert("RGBA"),
        gloss,
    )

    # 最終的に必ずRGB PNGにする
    return image.convert("RGB")


def valid_fluid_name(name):
    if not name:
        return False

    if len(name) > 128:
        return False

    return re.fullmatch(
        r"[a-z0-9_.-]+",
        name,
    ) is not None


def find_fluid_names():
    """
    液体IDを固定リストなしで動的検出する。

    JSONを生成することはない。
    """

    names = set()

    # ---------------------------------------------------------
    # Javaソースから登録名を検出
    # ---------------------------------------------------------

    if JAVA_DIR.exists():
        for path in JAVA_DIR.rglob("*.java"):
            try:
                text = path.read_text(
                    encoding="utf-8"
                )
            except UnicodeDecodeError:
                continue

            patterns = (
                # FLUIDS.register("molten_xxx", ...)
                r"\bFLUIDS\s*\.\s*register\s*\(\s*"
                r'"([a-z0-9_.-]+)"',

                # register("molten_xxx", ...)
                r"\bregister\s*\(\s*"
                r'"(molten_[a-z0-9_.-]+)"',

                # create("molten_xxx", ...)
                r"\bcreate\s*\(\s*"
                r'"(molten_[a-z0-9_.-]+)"',

                # source("molten_xxx", ...)
                r"\bsource\s*\(\s*"
                r'"(molten_[a-z0-9_.-]+)"',

                # ResourceLocation("sakalti", "molten_xxx")
                rf'ResourceLocation\s*\(\s*'
                rf'"{re.escape(MODID)}"\s*,\s*'
                rf'"([a-z0-9_.-]+)"',
            )

            for pattern in patterns:
                for match in re.finditer(
                    pattern,
                    text,
                ):
                    name = match.group(1)

                    if valid_fluid_name(name):
                        names.add(name)

    # ---------------------------------------------------------
    # 既存のPNGからも検出
    # ---------------------------------------------------------

    if TEXTURE_DIR.exists():
        for path in TEXTURE_DIR.glob("*.png"):
            stem = path.stem

            for suffix in (
                "_still",
                "_flow",
                "_flowing",
            ):
                if stem.endswith(suffix):
                    stem = stem[
                        : -len(suffix)
                    ]

            if valid_fluid_name(stem):
                names.add(stem)

    # ---------------------------------------------------------
    # generated texture専用ディレクトリ以外の
    # assetsからも流体名らしいものを検出
    # ---------------------------------------------------------

    asset_dir = (
        RESOURCE_DIR
        / "assets"
        / MODID
    )

    if asset_dir.exists():
        for path in asset_dir.rglob("*.png"):
            if "fluid" not in path.parts:
                continue

            stem = path.stem

            for suffix in (
                "_still",
                "_flow",
                "_flowing",
            ):
                if stem.endswith(suffix):
                    stem = stem[
                        : -len(suffix)
                    ]

            if valid_fluid_name(stem):
                names.add(stem)

    return sorted(names)


def color_for_fluid(name):
    """
    液体名から決定論的にRGB色を作る。

    RGBAではなくRGB 3チャンネル。
    """

    value = 0

    for char in name:
        value = (
            value * 31
            + ord(char)
        ) & 0xFFFFFFFF

    rng = random.Random(
        SEED + value
    )

    return (
        rng.randint(40, 235),
        rng.randint(40, 235),
        rng.randint(40, 235),
    )


def texture_path(name, flowing):
    suffix = (
        "flow"
        if flowing
        else "still"
    )

    return (
        TEXTURE_DIR
        / f"{name}_{suffix}.png"
    )


def save_rgb_png(image, path):
    """
    PNGを必ずRGBとして保存する。
    """

    if image.mode != "RGB":
        image = image.convert("RGB")

    path.parent.mkdir(
        parents=True,
        exist_ok=True,
    )

    image.save(
        path,
        "PNG",
        optimize=True,
    )

    # 保存後にRGBであることを検証
    with Image.open(path) as check:
        if check.mode != "RGB":
            raise RuntimeError(
                f"Generated PNG is not RGB: {path}"
            )

        if check.size != (
            SIZE,
            SIZE,
        ):
            raise RuntimeError(
                f"Invalid texture size: {path}"
            )


def remove_old_generated_json():
    """
    今回の構成ではMantle用fluid_texture JSONを使わない。

    リポジトリ内に残っている旧生成JSONだけ削除する。
    """

    old_dir = (
        RESOURCE_DIR
        / "assets"
        / MODID
        / "mantle"
        / "fluid_texture"
    )

    if not old_dir.exists():
        return

    for path in old_dir.rglob("*.json"):
        path.unlink()

    # 空ディレクトリを後ろから削除
    directories = sorted(
        (
            p
            for p in old_dir.rglob("*")
            if p.is_dir()
        ),
        key=lambda p: len(p.parts),
        reverse=True,
    )

    for directory in directories:
        try:
            directory.rmdir()
        except OSError:
            pass

    try:
        old_dir.rmdir()
    except OSError:
        pass


def generate():
    print(
        "=== Sakalti Fluid PNG Generator ==="
    )

    names = find_fluid_names()

    print(
        f"Detected {len(names)} fluids."
    )

    if not names:
        raise RuntimeError(
            "No fluids detected."
        )

    TEXTURE_DIR.mkdir(
        parents=True,
        exist_ok=True,
    )

    # 旧Mantle JSONを完全に除去
    remove_old_generated_json()

    generated = 0

    for index, name in enumerate(names):

        color = color_for_fluid(name)

        seed = (
            SEED
            + index * 1000
        )

        print(
            f"[GENERATE] {name}"
        )

        still = create_fluid_texture(
            color,
            seed,
            flowing=False,
        )

        flowing = create_fluid_texture(
            color,
            seed + 1,
            flowing=True,
        )

        still_path = texture_path(
            name,
            False,
        )

        flowing_path = texture_path(
            name,
            True,
        )

        save_rgb_png(
            still,
            still_path,
        )

        save_rgb_png(
            flowing,
            flowing_path,
        )

        print(
            f"  [RGB] {still_path}"
        )

        print(
            f"  [RGB] {flowing_path}"
        )

        generated += 2

    print()
    print(
        f"Generated {generated} RGB PNG files."
    )

    print(
        f"Detected fluids: {len(names)}"
    )


if __name__ == "__main__":
    generate()

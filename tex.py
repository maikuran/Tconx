from pathlib import Path
import json
import math
import random

from PIL import Image, ImageDraw, ImageFilter


MODID = "sakalti"
SIZE = 16
SEED = 20260816

ROOT = Path(__file__).resolve().parent

TEXTURE_DIR = (
    ROOT
    / "src"
    / "main"
    / "resources"
    / "assets"
    / MODID
    / "textures"
    / "fluid"
)

FLUID_TEXTURE_JSON_DIR = (
    ROOT
    / "src"
    / "main"
    / "resources"
    / "assets"
    / MODID
    / "mantle"
    / "fluid_texture"
)

FLUID_JSON_DIR = (
    ROOT
    / "src"
    / "main"
    / "resources"
    / "data"
    / MODID
    / "fluid"
)

FLUID_TAG_DIR = (
    ROOT
    / "src"
    / "main"
    / "resources"
    / "data"
    / MODID
    / "tags"
    / "fluids"
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

        n00 = noise(
            ix,
            iy,
            seed + octave * 17,
        )

        n10 = noise(
            ix + 1,
            iy,
            seed + octave * 17,
        )

        n01 = noise(
            ix,
            iy + 1,
            seed + octave * 17,
        )

        n11 = noise(
            ix + 1,
            iy + 1,
            seed + octave * 17,
        )

        nx0 = (
            n00 * (1.0 - fx)
            + n10 * fx
        )

        nx1 = (
            n01 * (1.0 - fx)
            + n11 * fx
        )

        value = (
            nx0 * (1.0 - fy)
            + nx1 * fy
        )

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

    return image.convert("RGB")


def find_fluid_names():
    """
    リポジトリから液体IDを動的に検出する。

    固定FLUIDSリストは使用しない。
    """

    names = set()

    if FLUID_JSON_DIR.exists():
        for path in FLUID_JSON_DIR.rglob("*.json"):
            names.add(path.stem)

    if FLUID_TAG_DIR.exists():
        for path in FLUID_TAG_DIR.rglob("*.json"):
            names.add(path.stem)

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

            if stem:
                names.add(stem)

    if FLUID_TEXTURE_JSON_DIR.exists():
        for path in FLUID_TEXTURE_JSON_DIR.rglob(
            "*.json"
        ):
            names.add(path.stem)

    valid = set()

    allowed = (
        "abcdefghijklmnopqrstuvwxyz"
        "0123456789"
        "_-."
    )

    for name in names:
        if not name:
            continue

        if all(
            char in allowed
            for char in name
        ):
            valid.add(name)

    return sorted(valid)


def extract_rgb_from_color(value):
    """
    Mantleのcolor値からRGBを取得する。

    8文字:
        AARRGGBB

    6文字:
        RRGGBB

    重要:
        8文字の場合は先頭2文字を無視し、
        後ろ6文字をRGBとして使用する。
    """

    if not isinstance(value, str):
        return None

    value = value.strip()

    if value.startswith("#"):
        value = value[1:]

    value = value.upper()

    if len(value) == 8:
        rgb_hex = value[-6:]
    elif len(value) == 6:
        rgb_hex = value
    else:
        return None

    try:
        return (
            int(rgb_hex[0:2], 16),
            int(rgb_hex[2:4], 16),
            int(rgb_hex[4:6], 16),
        )
    except ValueError:
        return None


def find_existing_fluid_color(name):
    """
    既存のMantle FluidTexture JSONからcolorを読む。

    例:

    {
      "still": "...",
      "flowing": "...",
      "color": "FF00EE00"
    }

    この場合:

        FF | 00 EE 00
        AA | R  G  B

    となり、RGB=(0,238,0)を使用する。

    色を勝手にランダム生成しない。
    """

    path = fluid_texture_json_path(name)

    if not path.exists():
        return None

    try:
        with path.open(
            "r",
            encoding="utf-8",
        ) as file:
            data = json.load(file)

    except (
        OSError,
        json.JSONDecodeError,
    ):
        return None

    return extract_rgb_from_color(
        data.get("color")
    )


def find_fluid_definition_color(name):
    """
    data/<modid>/fluid/<name>.json に
    色指定が存在する場合も確認する。

    既存のJSON構造を壊さない。
    """

    path = (
        FLUID_JSON_DIR
        / f"{name}.json"
    )

    if not path.exists():
        return None

    try:
        with path.open(
            "r",
            encoding="utf-8",
        ) as file:
            data = json.load(file)

    except (
        OSError,
        json.JSONDecodeError,
    ):
        return None

    if not isinstance(data, dict):
        return None

    for key in (
        "color",
        "tint",
        "tint_color",
    ):
        color = extract_rgb_from_color(
            data.get(key)
        )

        if color is not None:
            return color

    return None


def color_for_fluid(name):
    """
    液体の色を動的に決定する。

    優先順位:

    1. 既存Mantle fluid_texture JSONのcolor
    2. data fluid JSONのcolor/tint
    3. 最後のフォールバックとして決定論的色

    既存colorがある場合、
    液体名からランダムに色を作らない。
    """

    color = find_existing_fluid_color(name)

    if color is not None:
        print(
            f"[COLOR] {name}: "
            f"existing Mantle color "
            f"#{color[0]:02X}"
            f"{color[1]:02X}"
            f"{color[2]:02X}"
        )

        return color

    color = find_fluid_definition_color(name)

    if color is not None:
        print(
            f"[COLOR] {name}: "
            f"fluid definition color "
            f"#{color[0]:02X}"
            f"{color[1]:02X}"
            f"{color[2]:02X}"
        )

        return color

    # 既存色が完全に存在しない場合のみ
    # 決定論的フォールバックを使用する。
    value = 0

    for char in name:
        value = (
            value * 31
            + ord(char)
        ) & 0xFFFFFFFF

    rng = random.Random(
        SEED + value
    )

    color = (
        rng.randint(40, 235),
        rng.randint(40, 235),
        rng.randint(40, 235),
    )

    print(
        f"[COLOR] {name}: "
        f"fallback "
        f"#{color[0]:02X}"
        f"{color[1]:02X}"
        f"{color[2]:02X}"
    )

    return color


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


def fluid_texture_json_path(name):
    return (
        FLUID_TEXTURE_JSON_DIR
        / f"{name}.json"
    )


def write_fluid_texture_json(
    name,
    color,
):
    """
    Mantle 1.11.104用FluidTexture JSON。

    still / flowing は必須。

    colorはAARRGGBB形式。
    先頭AAはFF固定。
    RGBは実際に使用する色。
    """

    color_hex = (
        "FF"
        f"{color[0]:02X}"
        f"{color[1]:02X}"
        f"{color[2]:02X}"
    )

    data = {
        "still": (
            f"{MODID}:fluid/"
            f"{name}_still"
        ),
        "flowing": (
            f"{MODID}:fluid/"
            f"{name}_flow"
        ),
        "color": color_hex,
    }

    path = fluid_texture_json_path(
        name
    )

    path.parent.mkdir(
        parents=True,
        exist_ok=True,
    )

    with path.open(
        "w",
        encoding="utf-8",
        newline="\n",
    ) as file:
        json.dump(
            data,
            file,
            indent=2,
            ensure_ascii=False,
        )

        file.write("\n")


def generate():
    print(
        "=== Sakalti Mantle Fluid Generator ==="
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

    FLUID_TEXTURE_JSON_DIR.mkdir(
        parents=True,
        exist_ok=True,
    )

    generated = 0

    for index, name in enumerate(names):

        # 既存JSONから正しいRGBを取得
        color = color_for_fluid(name)

        seed = (
            SEED
            + index * 1000
        )

        still = create_fluid_texture(
            color,
            seed,
            False,
        )

        flowing = create_fluid_texture(
            color,
            seed + 1,
            True,
        )

        still_path = texture_path(
            name,
            False,
        )

        flowing_path = texture_path(
            name,
            True,
        )

        still.save(
            still_path,
            "PNG",
            optimize=True,
        )

        flowing.save(
            flowing_path,
            "PNG",
            optimize=True,
        )

        # JSONにも同じRGBを保存
        write_fluid_texture_json(
            name,
            color,
        )

        print(
            f"[OK] {name}"
        )

        generated += 2

    print()
    print(
        f"Generated {generated} PNG textures."
    )

    print(
        f"Detected fluids: {len(names)}"
    )


if __name__ == "__main__":
    generate()

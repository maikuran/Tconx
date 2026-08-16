from pathlib import Path
import json
import math
import random

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

FLUID_TEXTURE_JSON_DIR = (
    RESOURCE_DIR
    / "assets"
    / MODID
    / "mantle"
    / "fluid_texture"
)

DATA_DIR = (
    RESOURCE_DIR
    / "data"
    / MODID
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

        total += (
            nx0 * (1.0 - fy)
            + nx1 * fy
        ) * amplitude

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


def valid_name(name):
    if not name:
        return False

    allowed = (
        "abcdefghijklmnopqrstuvwxyz"
        "0123456789"
        "_-."
    )

    return all(
        char in allowed
        for char in name
    )


def normalize_color(value):
    """
    色をRGBへ変換。

    例:
        FF00EE00 -> (0, 238, 0)
        00EE00   -> (0, 238, 0)

    8桁ARGBの場合は後ろ6文字だけを使用する。
    """

    if not isinstance(value, str):
        return None

    value = value.strip()

    if value.startswith("#"):
        value = value[1:]

    if len(value) == 8:
        value = value[-6:]

    if len(value) != 6:
        return None

    try:
        return (
            int(value[0:2], 16),
            int(value[2:4], 16),
            int(value[4:6], 16),
        )
    except ValueError:
        return None


def resource_name_from_json(path):
    """
    JSONファイル名から液体名を取得する。
    """

    name = path.stem

    if name.endswith("_still"):
        name = name[:-6]

    if name.endswith("_flow"):
        name = name[:-5]

    if name.endswith("_flowing"):
        name = name[:-8]

    return name


def collect_json_files():
    if not DATA_DIR.exists():
        return []

    return sorted(
        DATA_DIR.rglob("*.json")
    )


def find_fluid_colors():
    """
    リポジトリ内のJSONを動的に探索して
    液体のcolorを取得する。

    液体一覧をコード内に持たない。
    """

    colors = {}

    for path in collect_json_files():
        try:
            with path.open(
                "r",
                encoding="utf-8",
            ) as file:
                data = json.load(file)
        except (
            OSError,
            UnicodeDecodeError,
            json.JSONDecodeError,
        ):
            continue

        if not isinstance(data, dict):
            continue

        color = normalize_color(
            data.get("color")
        )

        if color is None:
            continue

        relative = path.relative_to(
            DATA_DIR
        )

        parts = relative.parts

        if not parts:
            continue

        name = resource_name_from_json(
            path
        )

        if not valid_name(name):
            continue

        colors[name] = color

    return colors


def find_existing_fluid_names():
    """
    既存のMantle fluid texture JSONと
    fluid texture PNGから名前を補完する。

    色そのものはここから推測しない。
    """

    names = set()

    if FLUID_TEXTURE_JSON_DIR.exists():
        for path in FLUID_TEXTURE_JSON_DIR.rglob(
            "*.json"
        ):
            name = resource_name_from_json(
                path
            )

            if valid_name(name):
                names.add(name)

    if TEXTURE_DIR.exists():
        for path in TEXTURE_DIR.glob(
            "*.png"
        ):
            name = resource_name_from_json(
                path
            )

            if valid_name(name):
                names.add(name)

    return names


def find_fluid_names():
    """
    JSONに存在するcolorを持つ液体を
    動的に検出する。

    固定リストは使用しない。
    """

    colors = find_fluid_colors()

    names = set(colors.keys())

    names.update(
        find_existing_fluid_names()
    )

    return sorted(names), colors


def fluid_texture_json_path(name):
    return (
        FLUID_TEXTURE_JSON_DIR
        / f"{name}.json"
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


def write_fluid_texture_json(
    name,
    color,
):
    """
    Mantle 1.11.104用。

    colorは元JSONから取得したRGBを
    ARGB形式へ戻して出力する。
    """

    r, g, b = color

    color_hex = (
        f"FF"
        f"{r:02X}"
        f"{g:02X}"
        f"{b:02X}"
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

    names, colors = find_fluid_names()

    print(
        f"Detected {len(names)} fluid names."
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

        if name not in colors:
            print(
                f"[SKIP] {name}: "
                "no source color found"
            )
            continue

        color = colors[name]

        seed = (
            SEED
            + index * 1000
        )

        print(
            f"[GENERATE] {name} "
            f"RGB=#{color[0]:02X}"
            f"{color[1]:02X}"
            f"{color[2]:02X}"
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

        write_fluid_texture_json(
            name,
            color,
        )

        generated += 2

    print()
    print(
        f"Generated {generated} PNG textures."
    )

    print(
        f"Colored fluids: {len(colors)}"
    )


if __name__ == "__main__":
    generate()

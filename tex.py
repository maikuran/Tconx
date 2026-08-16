from pathlib import Path
import json
import math
import random

from PIL import Image, ImageDraw, ImageFilter


MODID = "sakalti"
SIZE = 128
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
                    stem = stem[:-len(suffix)]

            if stem:
                names.add(stem)

    if FLUID_TEXTURE_JSON_DIR.exists():
        for path in FLUID_TEXTURE_JSON_DIR.rglob("*.json"):
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


def color_for_fluid(name):
    """
    液体IDから決定論的にRGBを生成する。
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


def rgb_hex(color):
    """
    RGB 3値を6文字のRRGGBBへ変換。
    RGBAの8文字にはしない。
    """

    return (
        f"{color[0]:02X}"
        f"{color[1]:02X}"
        f"{color[2]:02X}"
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


def fluid_texture_json_path(name):
    return (
        FLUID_TEXTURE_JSON_DIR
        / f"{name}.json"
    )


def write_fluid_texture_json(name):
    color = color_for_fluid(name)

    # 重要:
    # FF + RRGGBB ではなく、
    # RRGGBBの6文字だけを書く。
    color_hex = rgb_hex(color)

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

    path = fluid_texture_json_path(name)

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

        write_fluid_texture_json(
            name
        )

        print(
            f"[OK] {name} "
            f"RGB={rgb_hex(color)}"
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

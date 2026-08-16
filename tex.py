from pathlib import Path
import re
import math
import random

from PIL import Image, ImageDraw, ImageFilter


MODID = "sakalti"
SIZE = 128
SEED = 20260816

JAVA_FILE = Path(
    "src/main/java/com/sakalti/ModFluids.java"
)

TEXTURE_DIR = Path(
    "src/main/resources/assets/sakalti/textures/fluid"
)

FLUID_DIR = Path(
    "src/main/resources/assets/sakalti/fluid"
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


def detect_fluids():
    if not JAVA_FILE.exists():
        raise FileNotFoundError(
            f"ModFluids.java not found: {JAVA_FILE}"
        )

    source = JAVA_FILE.read_text(
        encoding="utf-8"
    )

    names = re.findall(
        r'MOLTEN_([A-Z0-9_]+)_TYPE\s*=\s*'
        r'registerType\("molten_([^"]+)"\)',
        source
    )

    fluids = []

    for constant_name, registry_name in names:
        fluids.append(
            registry_name
        )

    fluids = list(dict.fromkeys(fluids))

    if not fluids:
        raise RuntimeError(
            "No molten fluids detected from ModFluids.java"
        )

    return fluids


def fluid_color(index):
    rng = random.Random(
        SEED + index * 7919
    )

    return (
        rng.randint(45, 225),
        rng.randint(45, 225),
        rng.randint(45, 225),
    )


def create_texture(base_color, seed, flowing):
    rng = random.Random(seed)

    dark = mix(
        base_color,
        (0, 0, 0),
        0.30
    )

    bright = mix(
        base_color,
        (255, 255, 255),
        0.25
    )

    image = Image.new(
        "RGB",
        (SIZE, SIZE)
    )

    pixels = image.load()

    waves = []

    for _ in range(16):
        waves.append(
            (
                rng.uniform(0, SIZE),
                rng.uniform(0, SIZE),
                rng.uniform(0.025, 0.08),
                rng.uniform(0.025, 0.08),
                rng.uniform(0, math.tau)
            )
        )

    for y in range(SIZE):
        for x in range(SIZE):

            value = 0.5

            for wx, wy, sx, sy, phase in waves:
                if flowing:
                    dx = x - wx
                    dy = (y - wy) * 0.45
                else:
                    dx = (x - wx) * 0.8
                    dy = (y - wy) * 0.8

                value += (
                    math.sin(
                        dx * sx
                        + dy * sy
                        + phase
                    )
                    * 0.035
                )

            value += (
                rng.random() - 0.5
            ) * 0.08

            value = max(
                0.0,
                min(1.0, value)
            )

            if value < 0.5:
                color = mix(
                    dark,
                    base_color,
                    value * 2
                )
            else:
                color = mix(
                    base_color,
                    bright,
                    (value - 0.5) * 2
                )

            pixels[x, y] = color

    gloss = Image.new(
        "RGBA",
        (SIZE, SIZE),
        (0, 0, 0, 0)
    )

    draw = ImageDraw.Draw(gloss)

    for _ in range(28):
        x = rng.randint(
            -SIZE,
            SIZE * 2
        )

        y = rng.randint(
            -SIZE,
            SIZE * 2
        )

        length = rng.randint(
            12,
            45
        )

        if flowing:
            end = (
                x + rng.randint(-2, 2),
                y + length
            )
        else:
            end = (
                x + length,
                y + rng.randint(-2, 2)
            )

        draw.line(
            [(x, y), end],
            fill=(
                255,
                255,
                255,
                rng.randint(15, 65)
            ),
            width=rng.randint(1, 3)
        )

    gloss = gloss.filter(
        ImageFilter.GaussianBlur(1.2)
    )

    image = Image.alpha_composite(
        image.convert("RGBA"),
        gloss
    )

    return image.convert("RGB")


def write_fluid_json(
    name,
    color
):
    FLUID_DIR.mkdir(
        parents=True,
        exist_ok=True
    )

    r, g, b = color

    color_hex = (
        f"FF{r:02X}{g:02X}{b:02X}"
    )

    content = (
        "{\n"
        f'  "still": "{MODID}:fluid/{name}_still",\n'
        f'  "flowing": "{MODID}:fluid/{name}_flow",\n'
        f'  "color": "{color_hex}"\n'
        "}\n"
    )

    path = (
        FLUID_DIR
        / f"{name}.json"
    )

    path.write_text(
        content,
        encoding="utf-8"
    )


def generate():
    fluids = detect_fluids()

    TEXTURE_DIR.mkdir(
        parents=True,
        exist_ok=True
    )

    print(
        f"Detected {len(fluids)} fluids:"
    )

    for index, name in enumerate(fluids):

        print(
            f"[GENERATE] {name}"
        )

        color = fluid_color(index)

        still = create_texture(
            color,
            SEED + index * 2,
            False
        )

        flowing = create_texture(
            color,
            SEED + index * 2 + 1,
            True
        )

        still.save(
            TEXTURE_DIR
            / f"{name}_still.png",
            "PNG",
            optimize=True
        )

        flowing.save(
            TEXTURE_DIR
            / f"{name}_flow.png",
            "PNG",
            optimize=True
        )

        write_fluid_json(
            name,
            color
        )

    print(
        f"Generated {len(fluids) * 2} textures."
    )


if __name__ == "__main__":
    generate()

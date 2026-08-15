from pathlib import Path
import math
import random

from PIL import Image, ImageDraw, ImageFilter


MODID = "sakalti"
SIZE = 128
SEED = 20260816

FLUIDS = {
    "molten_hachilite": (205, 55, 35),
    "molten_kanilite": (55, 145, 220),
    "molten_igniz": (245, 70, 25),
    "molten_chirite": (115, 210, 75),
    "molten_momongaite": (220, 105, 190),
    "molten_herdyeen": (190, 165, 55),
    "molten_hiroswari": (65, 205, 190),
    "molten_marulite": (150, 75, 220),
    "molten_proxia": (245, 185, 65),
    "molten_ouswari": (80, 120, 225),
    "molten_aurostone": (245, 215, 75),
    "molten_deepsteel": (45, 55, 70),
    "molten_chiisteel": (70, 185, 210),
    "molten_ioxium": (175, 80, 240),
    "molten_dilonite": (125, 95, 70),
    "molten_tiberite": (220, 65, 105),
    "molten_ostlum": (100, 220, 125),
    "molten_emerald": (40, 210, 105),
}


OUTPUT_DIR = (
    Path("src")
    / "main"
    / "resources"
    / "assets"
    / MODID
    / "textures"
    / "fluid"
)


def clamp(value):
    return max(0, min(255, int(value)))


def mix(a, b, amount):
    return tuple(
        clamp(a[i] * (1.0 - amount) + b[i] * amount)
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

        nx0 = n00 * (1.0 - fx) + n10 * fx
        nx1 = n01 * (1.0 - fx) + n11 * fx

        value = nx0 * (1.0 - fy) + nx1 * fy

        total += value * amplitude
        normalization += amplitude

        amplitude *= 0.5
        frequency *= 2.0

    return total / normalization


def seamless_noise(x, y, seed, scale):
    """
    周期境界を持つノイズ。
    左右・上下をタイル表示しても
    境界が目立ちにくいようにする。
    """

    period = SIZE

    px = (x % period) / period
    py = (y % period) / period

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

    # =========================================================
    # Base molten-fluid pattern
    # =========================================================

    for y in range(SIZE):
        for x in range(SIZE):

            if flowing:
                # 流動テクスチャは縦方向へ伸びた模様
                nx = x
                ny = y * 0.42

                n = seamless_noise(
                    nx,
                    ny,
                    seed,
                    16.0,
                )

            else:
                # 静止液体は液面らしい横方向の揺らぎ
                nx = x * 0.75
                ny = y * 0.75

                n = seamless_noise(
                    nx,
                    ny,
                    seed,
                    18.0,
                )

            value = 0.25 + n * 0.75

            fine = seamless_noise(
                x * 1.5,
                y * 1.5,
                seed + 100,
                8.0,
            )

            value += (fine - 0.5) * 0.12

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

    # =========================================================
    # Gloss
    # =========================================================

    gloss = Image.new(
        "RGBA",
        (SIZE, SIZE),
        (0, 0, 0, 0),
    )

    draw = ImageDraw.Draw(gloss)

    random.seed(seed + 5000)

    if flowing:
        for _ in range(28):
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

            width = random.randint(
                1,
                3,
            )

            draw.line(
                [
                    (x, y),
                    (
                        x + random.randint(-2, 2),
                        y + length,
                    ),
                ],
                fill=(
                    255,
                    255,
                    255,
                    random.randint(18, 70),
                ),
                width=width,
            )

    else:
        for _ in range(24):
            x = random.randint(
                -20,
                SIZE + 20,
            )

            y = random.randint(
                -20,
                SIZE + 20,
            )

            length = random.randint(
                10,
                45,
            )

            draw.line(
                [
                    (x, y),
                    (
                        x + length,
                        y + random.randint(-2, 2),
                    ),
                ],
                fill=(
                    255,
                    255,
                    255,
                    random.randint(15, 60),
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

    # =========================================================
    # Seamless edge blending
    # =========================================================

    image = make_seamless(image)

    return image.convert("RGB")


def make_seamless(image):
    """
    左右・上下の境界をブレンドする。
    """

    image = image.convert("RGB")

    result = image.copy()

    blend_width = 16

    # ---------------------------------------------------------
    # Left / Right
    # ---------------------------------------------------------

    for x in range(blend_width):
        t = x / (blend_width - 1)

        left_x = x
        right_x = SIZE - blend_width + x

        amount = 0.5 - abs(
            0.5 - t
        )

        amount *= 2.0

        for y in range(SIZE):
            left = image.getpixel(
                (left_x, y)
            )

            right = image.getpixel(
                (right_x, y)
            )

            blended = tuple(
                clamp(
                    left[i] * (1.0 - amount)
                    + right[i] * amount
                )
                for i in range(3)
            )

            result.putpixel(
                (left_x, y),
                blended,
            )

            result.putpixel(
                (right_x, y),
                blended,
            )

    # ---------------------------------------------------------
    # Top / Bottom
    # ---------------------------------------------------------

    image = result.copy()

    for y in range(blend_width):
        t = y / (blend_width - 1)

        top_y = y
        bottom_y = SIZE - blend_width + y

        amount = 0.5 - abs(
            0.5 - t
        )

        amount *= 2.0

        for x in range(SIZE):
            top = image.getpixel(
                (x, top_y)
            )

            bottom = image.getpixel(
                (x, bottom_y)
            )

            blended = tuple(
                clamp(
                    top[i] * (1.0 - amount)
                    + bottom[i] * amount
                )
                for i in range(3)
            )

            result.putpixel(
                (x, top_y),
                blended,
            )

            result.putpixel(
                (x, bottom_y),
                blended,
            )

    return result


def output_path(name, flowing):
    suffix = (
        "flow"
        if flowing
        else "still"
    )

    return (
        OUTPUT_DIR
        / f"{name}_{suffix}.png"
    )


def generate():
    print(
        "=== Sakalti Mantle Fluid Texture Generator ==="
    )

    OUTPUT_DIR.mkdir(
        parents=True,
        exist_ok=True,
    )

    generated = 0

    for index, (name, color) in enumerate(
        FLUIDS.items()
    ):
        seed = SEED + index * 1000

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

        still_path = output_path(
            name,
            False,
        )

        flowing_path = output_path(
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

        print(
            f"  [OK] {still_path}"
        )

        print(
            f"  [OK] {flowing_path}"
        )

        generated += 2

    expected = len(FLUIDS) * 2

    print()
    print(
        f"Generated {generated} textures."
    )

    print(
        f"Expected {expected} textures."
    )

    print(
        f"Output directory: {OUTPUT_DIR}"
    )

    if generated != expected:
        raise RuntimeError(
            f"Expected {expected} textures, "
            f"but generated {generated}."
        )


if __name__ == "__main__":
    generate()

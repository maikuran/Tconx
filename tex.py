import json
import re
from pathlib import Path

from PIL import Image


MOD_FLUIDS = Path(
    "src/main/java/com/sakalti/sakalti/ModFluids.java"
)

MATERIALS_DIR = Path(
    "src/main/resources/assets/sakalti/tinkering/materials"
)

OUTPUT_DIR = Path(
    "src/main/resources/assets/sakalti/textures/fluid"
)

SIZE = 16


def find_fluids():
    """
    ModFluids.java から

        MOLTEN_XXX =
            FLUIDS.register("molten_xxx", ...)

    を動的に探す。
    """

    text = MOD_FLUIDS.read_text(encoding="utf-8")

    pattern = re.compile(
        r'public\s+static\s+final\s+RegistryObject'
        r'<[^>]+>\s+MOLTEN_[A-Z0-9_]+'
        r'\s*=\s*FLUIDS\.register\s*\(\s*'
        r'"(molten_[a-z0-9_]+)"',
        re.MULTILINE
    )

    fluids = []

    for match in pattern.finditer(text):
        fluid_name = match.group(1)

        # flowing は除外
        if fluid_name.endswith("_flowing"):
            continue

        fluids.append(fluid_name)

    return sorted(set(fluids))


def load_material_color(material_name):
    """
    tinkering/materials/<material>.json の color を読む。
    """

    path = MATERIALS_DIR / f"{material_name}.json"

    if not path.exists():
        print(f"[WARN] Material not found: {path}")
        return None

    try:
        data = json.loads(
            path.read_text(encoding="utf-8")
        )
    except Exception as exc:
        print(f"[WARN] Failed to read {path}: {exc}")
        return None

    color = data.get("color")

    if not isinstance(color, str):
        print(f"[WARN] No color in {path}")
        return None

    color = color.strip().lstrip("#")

    # TConstruct形式:
    # FFF8DAC4
    #
    # FF = alpha
    # F8 = red
    # DA = green
    # C4 = blue
    if len(color) == 8:
        color = color[2:]

    if len(color) != 6:
        print(
            f"[WARN] Invalid color in {path}: {color}"
        )
        return None

    try:
        return tuple(
            int(color[i:i + 2], 16)
            for i in (0, 2, 4)
        )
    except ValueError:
        print(
            f"[WARN] Invalid hexadecimal color in {path}: {color}"
        )
        return None


def create_still(color):
    """
    静止液体テクスチャ。
    """

    image = Image.new(
        "RGBA",
        (SIZE, SIZE),
        (*color, 255)
    )

    pixels = image.load()

    for y in range(SIZE):
        for x in range(SIZE):
            # 軽い金属感
            variation = ((x * 3 + y * 5) % 7) - 3
            factor = 1.0 + variation / 100.0

            r = max(0, min(255, int(color[0] * factor)))
            g = max(0, min(255, int(color[1] * factor)))
            b = max(0, min(255, int(color[2] * factor)))

            pixels[x, y] = (r, g, b, 255)

    return image


def create_flowing(color):
    """
    流動液体テクスチャ。
    """

    image = Image.new(
        "RGBA",
        (SIZE, SIZE)
    )

    pixels = image.load()

    for y in range(SIZE):
        for x in range(SIZE):
            shift = (y * 2) % SIZE
            xx = (x + shift) % SIZE

            variation = ((xx * 3 + y * 7) % 9) - 4
            factor = 1.0 + variation / 100.0

            r = max(0, min(255, int(color[0] * factor)))
            g = max(0, min(255, int(color[1] * factor)))
            b = max(0, min(255, int(color[2] * factor)))

            pixels[x, y] = (r, g, b, 255)

    return image


def generate():
    if not MOD_FLUIDS.exists():
        raise FileNotFoundError(
            f"ModFluids.java not found: {MOD_FLUIDS}"
        )

    fluids = find_fluids()

    print("=== Detected Fluids ===")

    if not fluids:
        print("No fluids detected.")
        return

    for fluid in fluids:
        print(f"  {fluid}")

    print()

    for fluid in fluids:
        # molten_aurostone -> aurostone
        material_name = fluid.removeprefix("molten_")

        color = load_material_color(material_name)

        if color is None:
            print(
                f"[SKIP] {fluid}: "
                f"material color unavailable"
            )
            continue

        output = OUTPUT_DIR / fluid
        output.mkdir(
            parents=True,
            exist_ok=True
        )

        still = create_still(color)
        flowing = create_flowing(color)

        still.save(
            output / "still.png"
        )

        flowing.save(
            output / "flowing.png"
        )

        print(
            f"[OK] {fluid} "
            f"#{color[0]:02X}"
            f"{color[1]:02X}"
            f"{color[2]:02X}"
        )


if __name__ == "__main__":
    generate()

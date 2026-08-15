# onc.py
from pathlib import Path
import json

MODID = "sakalti"

ROOT = Path("src/main/resources")

DEFINITION_DIR = (
    ROOT
    / "data"
    / MODID
    / "tinkering"
    / "materials"
    / "definition"
)

RENDER_DIR = (
    ROOT
    / "assets"
    / MODID
    / "tinkering"
    / "materials"
)


def normalize_color(value):
    if not isinstance(value, str):
        return "FFFFFFFF"

    value = value.strip().lstrip("#").upper()

    if len(value) == 6:
        return "FF" + value

    if len(value) == 8:
        return value

    return "FFFFFFFF"


def generate():
    if not DEFINITION_DIR.exists():
        raise SystemExit(
            f"Definition directory not found:\n{DEFINITION_DIR}"
        )

    RENDER_DIR.mkdir(parents=True, exist_ok=True)

    count = 0

    for source in sorted(DEFINITION_DIR.glob("*.json")):
        with source.open("r", encoding="utf-8") as f:
            definition = json.load(f)

        material = source.stem

        color = normalize_color(
            definition.get("color", "FFFFFFFF")
        )

        # TConstruct MaterialRenderInfo
        render = {
            "color": color
        }

        target = RENDER_DIR / f"{material}.json"

        with target.open("w", encoding="utf-8") as f:
            json.dump(
                render,
                f,
                ensure_ascii=False,
                indent=2
            )
            f.write("\n")

        print(f"[GENERATED] {target}  color={color}")
        count += 1

    print(f"Generated {count} render files.")


if __name__ == "__main__":
    generate()

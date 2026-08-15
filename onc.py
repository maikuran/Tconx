# onc.py
from pathlib import Path
import json

ROOT = Path("src/main/resources")
DEFINITION = ROOT / "data" / "sakalti" / "tinkering" / "materials" / "definition"
RENDER = ROOT / "assets" / "sakalti" / "tinkering" / "materials"

# 素材名: ARGBカラー
# definition JSON の color が #RRGGBB の場合、自動的に FF を付けます。
def get_color(data):
    color = data.get("color", "FFFFFF")

    if not isinstance(color, str):
        color = "FFFFFF"

    color = color.strip().replace("#", "").upper()

    if len(color) == 6:
        color = "FF" + color
    elif len(color) != 8:
        color = "FFFFFFFF"

    return color


def main():
    RENDER.mkdir(parents=True, exist_ok=True)

    if not DEFINITION.exists():
        raise SystemExit(f"Definition directory not found: {DEFINITION}")

    count = 0

    for definition in sorted(DEFINITION.glob("*.json")):
        material = definition.stem

        try:
            with definition.open("r", encoding="utf-8") as f:
                data = json.load(f)
        except json.JSONDecodeError as e:
            raise SystemExit(f"Invalid JSON: {definition}\n{e}")

        # definition に color があれば使用。
        # なければ白。
        color = get_color(data)

        render = {
            "color": color,
            "fallbacks": [
                "metal"
            ]
        }

        output = RENDER / f"{material}.json"

        with output.open("w", encoding="utf-8") as f:
            json.dump(render, f, ensure_ascii=False, indent=2)
            f.write("\n")

        print(f"[GENERATED] {output} -> {color}")
        count += 1

    print(f"Generated {count} material render files.")


if __name__ == "__main__":
    main()

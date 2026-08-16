from __future__ import annotations

import json
import re
from pathlib import Path


ROOT = Path(__file__).resolve().parent

JAVA_FILE = (
    ROOT
    / "src"
    / "main"
    / "java"
    / "com"
    / "sakalti"
    / "material"
    / "hachilite"
    / "ModMaterials.java"
)

OUTPUT_DIR = (
    ROOT
    / "src"
    / "main"
    / "resources"
    / "data"
    / "sakalti"
    / "recipes"
    / "material_fluid"
)


MATERIAL_PATTERN = re.compile(
    r'new\s+MaterialId\s*\(\s*"([^"]+)"\s*\)'
)


def find_materials():
    if not JAVA_FILE.exists():
        raise FileNotFoundError(
            f"ModMaterials.java が見つかりません: {JAVA_FILE}"
        )

    text = JAVA_FILE.read_text(encoding="utf-8")

    materials = MATERIAL_PATTERN.findall(text)

    # 順序を維持しつつ重複削除
    return list(dict.fromkeys(materials))


def fluid_id(material_id: str):
    namespace, name = material_id.split(":", 1)

    # Material名から molten_ を付ける
    return f"{namespace}:molten_{name}"


def create_recipe(material_id: str):
    fluid = fluid_id(material_id)

    return {
        "type": "tconstruct:material_fluid",
        "fluid": {
            "name": fluid,
            "amount": 144
        },
        "temperature": 1000,
        "output": material_id
    }


def clean_generated_files():
    if not OUTPUT_DIR.exists():
        return

    for path in OUTPUT_DIR.glob("*.json"):
        path.unlink()


def generate():
    materials = find_materials()

    if not materials:
        print("Materialが1つも見つかりませんでした。")
        return

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    clean_generated_files()

    for material_id in materials:
        namespace, name = material_id.split(":", 1)

        output = create_recipe(material_id)

        output_file = OUTPUT_DIR / f"{name}.json"

        output_file.write_text(
            json.dumps(
                output,
                indent=2,
                ensure_ascii=False
            ) + "\n",
            encoding="utf-8"
        )

        print(
            f"Generated: {output_file} "
            f"({material_id} <- {fluid_id(material_id)})"
        )

    print()
    print(f"Material数: {len(materials)}")


if __name__ == "__main__":
    generate()

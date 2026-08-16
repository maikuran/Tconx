from __future__ import annotations

import json
from pathlib import Path


ROOT = Path(__file__).resolve().parent
RESOURCES = ROOT / "src" / "main" / "resources"


def read_json(path: Path):
    try:
        with path.open("r", encoding="utf-8") as f:
            return json.load(f)
    except (OSError, json.JSONDecodeError):
        return None


def find_json_files():
    if not RESOURCES.exists():
        return []

    return list(RESOURCES.rglob("*.json"))


def find_materials():
    """
    リポジトリ内のJSONを動的に走査してMaterial候補を探す。

    固定のMaterial名は持たない。
    """

    materials = {}

    for path in find_json_files():
        data = read_json(path)

        if not isinstance(data, dict):
            continue

        # material系JSONを探す
        material_id = data.get("material")

        if isinstance(material_id, str):
            materials[material_id] = {
                "id": material_id,
                "source": path,
                "data": data,
            }

        # 複数Materialを格納する形式にも対応
        material_list = data.get("materials")

        if isinstance(material_list, list):
            for entry in material_list:
                if not isinstance(entry, dict):
                    continue

                mid = entry.get("id") or entry.get("name")

                if isinstance(mid, str):
                    materials[mid] = {
                        "id": mid,
                        "source": path,
                        "data": entry,
                    }

    return materials


def find_material_fluid(material_id: str, material_data: dict):
    """
    Material定義からFluidを探す。
    """

    candidates = [
        material_data.get("fluid"),
        material_data.get("fluid_name"),
        material_data.get("fluidName"),
    ]

    for value in candidates:
        if isinstance(value, str):
            return value

    # Material名から直接Fluid IDを推測するのではなく、
    # 既存JSON内から対応するFluidを探す。
    for path in find_json_files():
        data = read_json(path)

        if not isinstance(data, dict):
            continue

        text = json.dumps(data, ensure_ascii=False)

        if material_id not in text:
            continue

        for key in ("fluid", "fluid_name", "fluidName"):
            value = data.get(key)

            if isinstance(value, str):
                return value

    return None


def main():
    print("=== Amtri ===")
    print(f"Resources: {RESOURCES}")

    materials = find_materials()

    if not materials:
        print("Materialが見つかりませんでした。")
        return

    print(f"検出Material数: {len(materials)}")

    for material_id, info in sorted(materials.items()):
        fluid = find_material_fluid(
            material_id,
            info["data"],
        )

        print()
        print(f"Material : {material_id}")
        print(f"Source   : {info['source']}")
        print(f"Fluid    : {fluid or 'NOT FOUND'}")


if __name__ == "__main__":
    main()

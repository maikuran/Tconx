from __future__ import annotations

import json
from pathlib import Path


ROOT = Path("src/main/resources/data/sakalti/tconstruct/alloys")


def make_fluid_name(stem: str) -> str:
    return f"sakalti:molten_{stem}"


def convert_file(path: Path) -> bool:
    try:
        with path.open("r", encoding="utf-8") as f:
            data = json.load(f)
    except (json.JSONDecodeError, OSError) as e:
        print(f"[SKIP] {path}: {e}")
        return False

    stem = path.stem

    # 既に正しい形式なら変更しません
    if (
        isinstance(data, dict)
        and isinstance(data.get("result"), dict)
        and "fluid" in data["result"]
        and "amount" in data["result"]
    ):
        print(f"[OK] {path}")
        return False

    # 既存データから流体名を取得
    fluid = None
    amount = 16

    if isinstance(data, dict):
        result = data.get("result")

        if isinstance(result, dict):
            fluid = result.get("fluid")
            if isinstance(result.get("amount"), int):
                amount = result["amount"]

        if fluid is None:
            fluid = data.get("fluid")

        if isinstance(data.get("amount"), int):
            amount = data["amount"]

    # fluid が無ければ素材名から自動生成
    if not fluid:
        fluid = make_fluid_name(stem)

    output = {
        "result": {
            "fluid": fluid,
            "amount": amount
        }
    }

    with path.open("w", encoding="utf-8", newline="\n") as f:
        json.dump(output, f, ensure_ascii=False, indent=2)
        f.write("\n")

    print(f"[FIX] {path} -> {fluid} x {amount}")
    return True


def main() -> None:
    ROOT.mkdir(parents=True, exist_ok=True)

    files = sorted(ROOT.glob("*.json"))

    if not files:
        print(f"No alloy JSON files found in {ROOT}")
        return

    changed = 0

    for path in files:
        if convert_file(path):
            changed += 1

    print(f"Processed: {len(files)}")
    print(f"Changed: {changed}")


if __name__ == "__main__":
    main()

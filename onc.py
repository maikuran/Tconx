#!/usr/bin/env python3

import json
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]

DEFINITION_DIR = (
    ROOT
    / "src"
    / "main"
    / "resources"
    / "data"
    / "sakalti"
    / "tinkering"
    / "materials"
    / "definition"
)

RENDER_DIR = (
    ROOT
    / "src"
    / "main"
    / "resources"
    / "assets"
    / "sakalti"
    / "tinkering"
    / "materials"
)

DEFAULT_COLOR = "FFFFFF"


def normalize_color(value):
    if not isinstance(value, str):
        return DEFAULT_COLOR

    value = value.strip()

    if value.startswith("#"):
        value = value[1:]

    value = value.upper()

    # RRGGBB
    if re.fullmatch(r"[0-9A-F]{6}", value):
        return value

    # AARRGGBB / RRGGBBAA 等の誤入力を安全側に処理
    if re.fullmatch(r"[0-9A-F]{8}", value):
        return value[-6:]

    return DEFAULT_COLOR


def read_json(path):
    with path.open("r", encoding="utf-8") as f:
        return json.load(f)


def material_name_from_file(path):
    return path.stem


def generate_render(material_file):
    material = read_json(material_file)

    name = material_name_from_file(material_file)

    color = normalize_color(
        material.get("color", DEFAULT_COLOR)
    )

    render = {
        "color": color
    }

    return name, render


def main():
    if not DEFINITION_DIR.exists():
        raise SystemExit(
            f"Definition directory not found:\n{DEFINITION_DIR}"
        )

    RENDER_DIR.mkdir(parents=True, exist_ok=True)

    definition_files = sorted(
        DEFINITION_DIR.glob("*.json")
    )

    if not definition_files:
        raise SystemExit(
            f"No material definitions found:\n{DEFINITION_DIR}"
        )

    generated = 0

    for material_file in definition_files:
        try:
            name, render = generate_render(material_file)

            output_file = RENDER_DIR / f"{name}.json"

            with output_file.open("w", encoding="utf-8") as f:
                json.dump(
                    render,
                    f,
                    ensure_ascii=False,
                    indent=2
                )
                f.write("\n")

            print(
                f"[OK] {material_file.name} -> "
                f"{output_file.relative_to(ROOT)}"
            )

            generated += 1

        except json.JSONDecodeError as e:
            print(
                f"[ERROR] Invalid JSON: {material_file}"
            )
            print(
                f"        line={e.lineno}, "
                f"column={e.colno}: {e.msg}"
            )
            raise SystemExit(1)

        except Exception as e:
            print(
                f"[ERROR] Failed: {material_file}"
            )
            print(f"        {e}")
            raise SystemExit(1)

    print()
    print(f"Generated {generated} material render files.")


if __name__ == "__main__":
    main()

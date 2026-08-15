from pathlib import Path
import json
import re

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

RENDER_DIR.mkdir(parents=True, exist_ok=True)


def hex_to_rgb(value):
    if not isinstance(value, str):
        return None

    value = value.strip()

    if not re.fullmatch(r"#[0-9a-fA-F]{6}", value):
        return None

    return {
        "r": int(value[1:3], 16),
        "g": int(value[3:5], 16),
        "b": int(value[5:7], 16),
    }


def create_render(material_name, definition):
    color = definition.get("color", "#FFFFFF")

    rgb = hex_to_rgb(color)

    if rgb is None:
        raise ValueError(
            f"{material_name}: invalid color: {color}"
        )

    # TConstruct material render definition
    #
    # 重要:
    # definition JSON そのものをコピーしません。
    # render 用 JSON として必要な情報だけを生成します。

    return {
        "color": color
    }


def main():
    if not DEFINITION_DIR.exists():
        raise SystemExit(
            f"Definition directory not found:\n{DEFINITION_DIR}"
        )

    definition_files = sorted(
        DEFINITION_DIR.glob("*.json")
    )

    if not definition_files:
        raise SystemExit(
            f"No material definitions found:\n{DEFINITION_DIR}"
        )

    generated = 0

    for definition_path in definition_files:
        material_name = definition_path.stem

        try:
            with definition_path.open(
                "r",
                encoding="utf-8"
            ) as f:
                definition = json.load(f)
        except json.JSONDecodeError as e:
            raise SystemExit(
                f"Invalid JSON: {definition_path}\n"
                f"{e}"
            )

        render = create_render(
            material_name,
            definition
        )

        output_path = (
            RENDER_DIR
            / f"{material_name}.json"
        )

        with output_path.open(
            "w",
            encoding="utf-8"
        ) as f:
            json.dump(
                render,
                f,
                ensure_ascii=False,
                indent=2
            )
            f.write("\n")

        print(
            f"[GENERATED] "
            f"{output_path}"
        )

        generated += 1

    print(
        f"Generated {generated} material render files."
    )


if __name__ == "__main__":
    main()

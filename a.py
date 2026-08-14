import os
from pathlib import Path
from PIL import Image

MATERIAL_NAME = "hachilite"
MATERIAL_JP_NAME = "ハチライト"
MOD_ID = "sakalti"
COLOR_HEX = 0x4FC3F7  # 水色

BASE_PATH = Path(".")

# Javaパス
JAVA_MAIN_PATH = BASE_PATH / "src" / "main" / "java" / "com" / "sakalti" / MOD_ID
JAVA_MATERIAL_PATH = JAVA_MAIN_PATH / "material" / MATERIAL_NAME

# リソースパス
RESOURCES_BASE = BASE_PATH / "src" / "main" / "resources" / "assets" / MOD_ID
MATERIALS_PATH = RESOURCES_BASE / "materials" / MATERIAL_NAME
LANG_PATH = RESOURCES_BASE / "lang"
BLOCKSTATES_PATH = RESOURCES_BASE / "blockstates"
MODELS_BLOCK_PATH = RESOURCES_BASE / "models" / "block"
MODELS_ITEM_PATH = RESOURCES_BASE / "models" / "item"
TEXTURE_BLOCK_PATH = RESOURCES_BASE / "textures" / "block"
TEXTURE_ITEM_PATH = RESOURCES_BASE / "textures" / "item"
FLUIDS_PATH = RESOURCES_BASE / "fluids"

# Javaコード（省略せずに前回同様）
MOD_MAIN = f"""\
package com.sakalti.{MOD_ID};

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.sakalti.{MOD_ID}.material.{MATERIAL_NAME}.ModMaterials;
import com.sakalti.{MOD_ID}.material.{MATERIAL_NAME}.ModStats;

@Mod("{MOD_ID}")
public class ModMain {{

    public ModMain() {{
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModMaterials.registerMaterials(bus);
        ModStats.registerStats();
    }}
}}
"""

MOD_MATERIALS = f"""\
package com.sakalti.{MOD_ID}.material.{MATERIAL_NAME};

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;

public class ModMaterials {{

    public static final MaterialId {MATERIAL_NAME.upper()}_ID = new MaterialId("{MOD_ID}:{MATERIAL_NAME}");
    public static Material {MATERIAL_NAME.upper()};

    public static void registerMaterials(IEventBus bus) {{
        {MATERIAL_NAME.upper()} = MaterialManager.getInstance().register(
            new Material({MATERIAL_NAME.upper()}_ID, Material.DisplayName.withTranslationKey("material.{MATERIAL_NAME}"))
                .setCraftable(true)
                .setCastable(true)
                .setFluid(new ResourceLocation("{MOD_ID}", "molten_{MATERIAL_NAME}"))
        );
    }}
}}
"""

MOD_STATS = f"""\
package com.sakalti.{MOD_ID}.material.{MATERIAL_NAME};

import slimeknights.tconstruct.library.materials.stats.*;

public class ModStats {{

    public static HeadMaterialStats {MATERIAL_NAME.upper()}_HEAD = new HeadMaterialStats(
        800, 6.5f, 3.2f, 4); // 耐久力, 掘削速度, 攻撃力, 精度

    public static HandleMaterialStats {MATERIAL_NAME.upper()}_HANDLE = new HandleMaterialStats(
        1.1f, 20); // 攻撃速度補正, 耐久増加

    public static ExtraMaterialStats {MATERIAL_NAME.upper()}_EXTRA = new ExtraMaterialStats(50); // 追加耐久力

    public static void registerStats() {{
        // 必要に応じて実装
    }}
}}
"""

# JSONリソース

# material json
MATERIAL_JSON = f"""{{
  "name": "{MATERIAL_NAME}",
  "fluid": "{MOD_ID}:molten_{MATERIAL_NAME}",
  "color": {COLOR_HEX},
  "craftable": true,
  "castable": true,
  "hidden": false
}}
"""

# ローカライズ
LOCALE_JSON = f"""{{
  "material.{MATERIAL_NAME}": "{MATERIAL_JP_NAME}",
  "material.{MATERIAL_NAME}.head": "{MATERIAL_JP_NAME}のヘッド",
  "material.{MATERIAL_NAME}.handle": "{MATERIAL_JP_NAME}の柄",
  "material.{MATERIAL_NAME}.extra": "{MATERIAL_JP_NAME}の追加"
}}
"""

# blockstates
BLOCKSTATE_BLOCK_JSON = f"""{{
  "variants": {{
    "": {{ "model": "{MOD_ID}:block/{MATERIAL_NAME}_block" }}
  }}
}}
"""

BLOCKSTATE_ORE_JSON = f"""{{
  "variants": {{
    "": {{ "model": "{MOD_ID}:block/{MATERIAL_NAME}_ore" }}
  }}
}}
"""

# block model
BLOCK_MODEL_BLOCK_JSON = f"""{{
  "parent": "block/cube_all",
  "textures": {{
    "all": "{MOD_ID}:block/{MATERIAL_NAME}_block"
  }}
}}
"""

BLOCK_MODEL_ORE_JSON = f"""{{
  "parent": "block/cube_all",
  "textures": {{
    "all": "{MOD_ID}:block/{MATERIAL_NAME}_ore"
  }}
}}
"""

# item model
ITEM_MODEL_BLOCK_JSON = f"""{{
  "parent": "{MOD_ID}:block/{MATERIAL_NAME}_block"
}}
"""

ITEM_MODEL_INGOT_JSON = f"""{{
  "parent": "item/generated",
  "textures": {{
    "layer0": "{MOD_ID}:item/{MATERIAL_NAME}"
  }}
}}
"""

# fluid json (簡易版、実際はTCon流体登録に準ずる)
FLUID_JSON = f"""{{
  "fluid": {{
    "name": "molten_{MATERIAL_NAME}",
    "stillTexture": "{MOD_ID}:block/molten_{MATERIAL_NAME}_still",
    "flowingTexture": "{MOD_ID}:block/molten_{MATERIAL_NAME}_flow",
    "color": {COLOR_HEX},
    "temperature": 900,
    "viscosity": 6000,
    "density": 3000
  }}
}}
"""

# fluid blockstates (stillとflow用モデル指定)
FLUID_BLOCKSTATE_JSON = f"""{{
  "variants": {{
    "": {{ "model": "{MOD_ID}:block/molten_{MATERIAL_NAME}_still" }}
  }}
}}
"""

# fluid block model (still)
FLUID_BLOCK_MODEL_STILL_JSON = f"""{{
  "parent": "minecraft:block/block",
  "textures": {{
    "layer0": "{MOD_ID}:block/molten_{MATERIAL_NAME}_still"
  }}
}}
"""

# fluid block model (flow)
FLUID_BLOCK_MODEL_FLOW_JSON = f"""{{
  "parent": "minecraft:block/block",
  "textures": {{
    "layer0": "{MOD_ID}:block/molten_{MATERIAL_NAME}_flow"
  }}
}}
"""

# fluid textures (画像は液体用の2枚を16x16水色単色生成)

# ore drop JSON (鉱石のドロップ。鉄鉱石などと同様)
ORE_DROP_JSON = f"""{{
  "pools": [
    {{
      "rolls": 1,
      "entries": [
        {{
          "type": "minecraft:item",
          "name": "{MOD_ID}:{MATERIAL_NAME}_ore_item"
        }}
      ],
      "conditions": [
        {{
          "condition": "minecraft:match_tool",
          "predicate": {{
            "enchantments": [
              {{
                "enchantment": "minecraft:silk_touch",
                "levels": {{
                  "min": 1
                }}
              }}
            ]
          }}
        }}
      ]
    }},
    {{
      "rolls": 1,
      "entries": [
        {{
          "type": "minecraft:item",
          "name": "{MOD_ID}:{MATERIAL_NAME}_raw"
        }}
      ],
      "conditions": [
        {{
          "condition": "minecraft:inverted",
          "term": {{
            "condition": "minecraft:match_tool",
            "predicate": {{
              "enchantments": [
                {{
                  "enchantment": "minecraft:silk_touch",
                  "levels": {{
                    "min": 1
                  }}
                }}
              ]
            }}
          }}
        }}
      ]
    }}
  ]
}}
"""

# －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
# 書き込み・画像生成関数

def write_file(path: Path, content: str):
    path.parent.mkdir(parents=True, exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        f.write(content)
    print(f"Wrote {path}")

def generate_solid_color_image(path: Path, color_hex: int, size=16):
    r = (color_hex >> 16) & 0xFF
    g = (color_hex >> 8) & 0xFF
    b = color_hex & 0xFF
    img = Image.new("RGBA", (size, size), (r, g, b, 255))
    path.parent.mkdir(parents=True, exist_ok=True)
    img.save(path)
    print(f"Saved image: {path}")

def main():
    # Javaソース
    write_file(JAVA_MAIN_PATH / "ModMain.java", MOD_MAIN)
    write_file(JAVA_MATERIAL_PATH / "ModMaterials.java", MOD_MATERIALS)
    write_file(JAVA_MATERIAL_PATH / "ModStats.java", MOD_STATS)

    # JSON
    write_file(MATERIALS_PATH / f"{MATERIAL_NAME}.json", MATERIAL_JSON)
    write_file(LANG_PATH / "ja_jp.json", LOCALE_JSON)

    write_file(BLOCKSTATES_PATH / f"{MATERIAL_NAME}_block.json", BLOCKSTATE_BLOCK_JSON)
    write_file(BLOCKSTATES_PATH / f"{MATERIAL_NAME}_ore.json", BLOCKSTATE_ORE_JSON)

    write_file(MODELS_BLOCK_PATH / f"{MATERIAL_NAME}_block.json", BLOCK_MODEL_BLOCK_JSON)
    write_file(MODELS_BLOCK_PATH / f"{MATERIAL_NAME}_ore.json", BLOCK_MODEL_ORE_JSON)

    write_file(MODELS_ITEM_PATH / f"{MATERIAL_NAME}_block.json", ITEM_MODEL_BLOCK_JSON)
    write_file(MODELS_ITEM_PATH / f"{MATERIAL_NAME}.json", ITEM_MODEL_INGOT_JSON)

    # 液体リソース（JSON）
    write_file(RESOURCES_BASE / "fluids" / f"molten_{MATERIAL_NAME}.json", FLUID_JSON)
    write_file(BLOCKSTATES_PATH / f"molten_{MATERIAL_NAME}_still.json", FLUID_BLOCKSTATE_JSON)
    write_file(MODELS_BLOCK_PATH / f"molten_{MATERIAL_NAME}_still.json", FLUID_BLOCK_MODEL_STILL_JSON)
    write_file(MODELS_BLOCK_PATH / f"molten_{MATERIAL_NAME}_flow.json", FLUID_BLOCK_MODEL_FLOW_JSON)

    # 鉱石ドロップ JSON (data/<modid>/loot_tables/blocks/hachilite_ore.json)
    loot_path = BASE_PATH / "src" / "main" / "resources" / "data" / MOD_ID / "loot_tables" / "blocks"
    loot_path.mkdir(parents=True, exist_ok=True)
    with open(loot_path / f"{MATERIAL_NAME}_ore.json", "w", encoding="utf-8") as f:
        f.write(ORE_DROP_JSON)
    print(f"Wrote {loot_path / f'{MATERIAL_NAME}_ore.json'}")

    # テクスチャ 16x16水色単色
    generate_solid_color_image(TEXTURE_ITEM_PATH / f"{MATERIAL_NAME}.png", COLOR_HEX, 16)
    generate_solid_color_image(TEXTURE_BLOCK_PATH / f"{MATERIAL_NAME}_block.png", COLOR_HEX, 16)
    generate_solid_color_image(TEXTURE_BLOCK_PATH / f"{MATERIAL_NAME}_ore.png", COLOR_HEX, 16)
    generate_solid_color_image(TEXTURE_BLOCK_PATH / f"molten_{MATERIAL_NAME}_still.png", COLOR_HEX, 16)
    generate_solid_color_image(TEXTURE_BLOCK_PATH / f"molten_{MATERIAL_NAME}_flow.png", COLOR_HEX, 16)

if __name__ == "__main__":
    main()

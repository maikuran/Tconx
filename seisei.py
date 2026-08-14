import os
import json

base_dir = 'src/main/resources/assets/sakalti'
texture_dir = os.path.join(base_dir, 'textures', 'block')
blockstates_dir = os.path.join(base_dir, 'blockstates')
models_block_dir = os.path.join(base_dir, 'models', 'block')
models_item_dir = os.path.join(base_dir, 'models', 'item')

os.makedirs(blockstates_dir, exist_ok=True)
os.makedirs(models_block_dir, exist_ok=True)
os.makedirs(models_item_dir, exist_ok=True)

texture_files = [f for f in os.listdir(texture_dir) if f.endswith('.png')]

for tex in texture_files:
    name = tex[:-4]  # 拡張子除去

    # blockstates
    blockstate = {
        "variants": {
            "": { "model": f"sakalti:block/{name}" }
        }
    }
    with open(os.path.join(blockstates_dir, f"{name}.json"), 'w', encoding='utf-8') as f:
        json.dump(blockstate, f, indent=2, ensure_ascii=False)

    # block model
    block_model = {
        "parent": "minecraft:block/cube_all",
        "textures": {
            "all": f"sakalti:block/{name}"
        }
    }
    with open(os.path.join(models_block_dir, f"{name}.json"), 'w', encoding='utf-8') as f:
        json.dump(block_model, f, indent=2, ensure_ascii=False)

    # item model
    item_model = {
        "parent": "item/generated",
        "textures": {
            "layer0": f"sakalti:item/{name}"
        }
    }
    with open(os.path.join(models_item_dir, f"{name}.json"), 'w', encoding='utf-8') as f:
        json.dump(item_model, f, indent=2, ensure_ascii=False)

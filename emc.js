// EMC値定義スクリプト
// 使用Mod: ProjectE + KubeJS（対応アドオン不要な場合もあり）

ServerEvents.recipes(event => {
  // アイテムとEMC値のマップ
  const emcMap = {
    'sakalti:hachilite_raw': 768,
    'sakalti:hachilite_ingot': 3456,
    'sakalti:hachilite_block': 2048 * 9,
    'sakalti:hachilite_ore': 7512,

    'sakalti:kanilite_raw': 2000,
    'sakalti:kanilite_ingot': 8000,
    'sakalti:kanilite_block': 8000 * 9,
    'sakalti:kanilite_ore': 12000,

    'sakalti:igniz_raw': 4096,
    'sakalti:igniz_ingot': 16384,
    'sakalti:igniz_block': 16384 * 9,
    'sakalti:igniz_ore': 24576,

    'sakalti:chirite_raw': 3500,
    'sakalti:chirite_ingot': 14000,
    'sakalti:chirite_block': 14000 * 9,
    'sakalti:chirite_ore': 21000,

    'sakalti:momongaite_raw': 3200,
    'sakalti:momongaite_ingot': 6400,
    'sakalti:momongaite_block': 6400 * 9,
    'sakalti:momongaite_ore': 9600,

    'sakalti:herdyeen_ingot': 19000,
    'sakalti:herdyeen_block': 19000 * 9,

    'sakalti:hiroswari_ingot': 22000,
    'sakalti:hiroswari_block': 22000 * 9,

    'sakalti:marulite_ingot': 65536,
    'sakalti:marulite_block': 131072 * 9,

    'sakalti:proxia_ingot': 39800,
    'sakalti:proxia_block': 39800 * 9,

    'sakalti:ouswari_ingot': 22000,
    'sakalti:ouswari_block': 22000 * 9,

    'sakalti:aurostone_ingot': 8096,
    'sakalti:aurostone_block': 16192 * 9,

    'sakalti:deepsteel_ingot': 160000,
    'sakalti:deepsteel_block': 160000 * 9,
    'sakalti:deepchunk': 1048,

    'sakalti:chiisteel_ingot': 33120,
    'sakalti:chiisteel_block': 33120 * 9,

    'sakalti:ioxium_ingot': 22024,
    'sakalti:ioxium_block': 22024 * 9,

    'sakalti:dilonite_ingot': 34336,
    'sakalti:dilonite_block': 34336 * 9,

    'sakalti:tiberite_ingot': 6144,
    'sakalti:tiberite_block': 6144 * 9,

    'sakalti:ostlum_ingot': 28192,
    'sakalti:ostlum_block': 28192 * 9
  };

  // 一括登録
  for (const [id, emc] of Object.entries(emcMap)) {
    event.custom({
      type: 'projecte:emc',
      ingredient: { item: id },
      value: emc
    });
  }
});

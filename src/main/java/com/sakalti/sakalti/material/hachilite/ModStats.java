package com.sakalti.sakalti.material.hachilite;

import slimeknights.tconstruct.library.materials.stats.*;

public class ModStats {

    public static HeadMaterialStats HACHILITE_HEAD = new HeadMaterialStats(
        800, 6.5f, 3.2f, 4); // 耐久力, 掘削速度, 攻撃力, 精度

    public static HandleMaterialStats HACHILITE_HANDLE = new HandleMaterialStats(
        1.1f, 20); // 攻撃速度補正, 耐久増加

    public static ExtraMaterialStats HACHILITE_EXTRA = new ExtraMaterialStats(50); // 追加耐久力

    public static void registerStats() {
        // 必要に応じて実装
    }
}

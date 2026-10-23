package com.sakalti.tconx.modifier;

import com.sakalti.tconx.modifier.*;
import slimeknights.tconstruct.library.modifiers.Modifier;

public class TconxModifiers {

    public static final Modifier GLACIAL_BIND = new GlacialBindModifier();
    public static final Modifier SUPERMAGNET = new SuperMagnetModifier();
    public static final Modifier ICE_BIND = new IceBindModifier();
    public static final Modifier ICE_THORNS = new IceThornsModifier();
    public static final Modifier WEAK_BURN = new WeakBurnModifier();
    public static final Modifier LIGHT_TOUCH = new LightTouchModifier();
    public static final Modifier FIRE_WALL = new FireWallModifier();
    public static final Modifier LIFESTEAL = new LifestealModifier();
    public static final Modifier LONG_SPARK = new LongsparkModifier();
    public static final Modifier LASER_PRISM = new LaserPrismModifier();
    public static final Modifier POISONOUS_BIND = new PoisonousBindModifier();
    public static final Modifier HEAVY_STONE = new HeavyStoneModifier();
    public static final Modifier JUMPY = new JumpyModifier();
    public static final Modifier BERSERK = new BerserkModifier();
    public static final Modifier UNSTABLE = new UnstableModifier();
    public static final Modifier FIELDY = new FieldyModifier();
    public static final Modifier AURO_VISION = new AuroVisionModifier();

    public static void init() {
        // 何も登録しない、自前インスタンス保持のみ
    }
}

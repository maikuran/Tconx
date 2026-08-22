package com.sakalti.modifier;

import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;


public final class TconxModifiers {

    public static final ModifierDeferredRegister MODIFIERS =
            ModifierDeferredRegister.create("sakalti");

    public static final StaticModifier<GlacialBindModifier> GLACIAL_BIND =
            MODIFIERS.register("glacial_bind", GlacialBindModifier::new);

    public static final StaticModifier<CoralianModifier> CORALIAN =
            MODIFIERS.register("coralian", CoralianModifier::new);

    public static final StaticModifier<SuperMagnetModifier> SUPERMAGNET =
            MODIFIERS.register("supermagnet", SuperMagnetModifier::new);

    public static final StaticModifier<IceBindModifier> ICE_BIND =
            MODIFIERS.register("ice_bind", IceBindModifier::new);

    public static final StaticModifier<WeakBurnModifier> WEAK_BURN =
            MODIFIERS.register("weak_burn", WeakBurnModifier::new);

    public static final StaticModifier<LightTouchModifier> LIGHT_TOUCH =
            MODIFIERS.register("light_touch", LightTouchModifier::new);

    public static final StaticModifier<FireWallModifier> FIRE_WALL =
            MODIFIERS.register("fire_wall", FireWallModifier::new);

    public static final StaticModifier<LifestealModifier> LIFESTEAL =
            MODIFIERS.register("lifesteal", LifestealModifier::new);

    public static final StaticModifier<LongsparkModifier> LONG_SPARK =
            MODIFIERS.register("long_spark", LongsparkModifier::new);

    public static final StaticModifier<LaserPrismModifier> LASER_PRISM =
            MODIFIERS.register("laser_prism", LaserPrismModifier::new);

    public static final StaticModifier<PoisonousBindModifier> POISONOUS_BIND =
            MODIFIERS.register("poisonous_bind", PoisonousBindModifier::new);

    public static final StaticModifier<HeavyStoneModifier> HEAVY_STONE =
            MODIFIERS.register("heavy_stone", HeavyStoneModifier::new);

    public static final StaticModifier<JumpyModifier> JUMPY =
            MODIFIERS.register("jumpy", JumpyModifier::new);

    public static final StaticModifier<BerserkModifier> BERSERK =
            MODIFIERS.register("berserk", BerserkModifier::new);

    public static final StaticModifier<SeirenCurseModifier> SEIRENCURSE =
            MODIFIERS.register("seiren_curse", SeirenCurseModifier::new);

    public static final StaticModifier<UnstableModifier> UNSTABLE =
            MODIFIERS.register("unstable", UnstableModifier::new);

    public static final StaticModifier<FieldyModifier> FIELDY =
            MODIFIERS.register("fieldy", FieldyModifier::new);

    public static final StaticModifier<AuroVisionModifier> AURO_VISION =
            MODIFIERS.register("auro_vision", AuroVisionModifier::new);

    private TconxModifiers() {
    }
}

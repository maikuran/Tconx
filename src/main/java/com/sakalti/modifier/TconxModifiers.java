package com.sakalti.modifier;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public final class TconxModifiers {

    // 1.16.5 では Forge の DeferredRegister を直接使用します
    public static final DeferredRegister<Modifier> MODIFIERS =
            DeferredRegister.create(Modifier.class, "sakalti");

    public static final RegistryObject<GlacialBindModifier> GLACIAL_BIND =
            MODIFIERS.register("glacial_bind", GlacialBindModifier::new);

    public static final RegistryObject<CoralianModifier> CORALIAN =
            MODIFIERS.register("coralian", CoralianModifier::new);

    public static final RegistryObject<CelestiteResonanceModifier> GLACIAL_BIND =
            MODIFIERS.register("celestite_resonance", CelestiteResonanceModifier::new);

    public static final RegistryObject<LureMeteorModifier> CORALIAN =
            MODIFIERS.register("lure_meteor", LureMeteorModifier::new);

    public static final RegistryObject<SuperMagnetModifier> SUPERMAGNET =
            MODIFIERS.register("supermagnet", SuperMagnetModifier::new);

    public static final RegistryObject<IceBindModifier> ICE_BIND =
            MODIFIERS.register("ice_bind", IceBindModifier::new);

    public static final RegistryObject<WeakBurnModifier> WEAK_BURN =
            MODIFIERS.register("weak_burn", WeakBurnModifier::new);

    public static final RegistryObject<LightTouchModifier> LIGHT_TOUCH =
            MODIFIERS.register("light_touch", LightTouchModifier::new);

    public static final RegistryObject<FireWallModifier> FIRE_WALL =
            MODIFIERS.register("fire_wall", FireWallModifier::new);

    public static final RegistryObject<LifestealModifier> LIFESTEAL =
            MODIFIERS.register("lifesteal", LifestealModifier::new);

    public static final RegistryObject<LongsparkModifier> LONG_SPARK =
            MODIFIERS.register("long_spark", LongsparkModifier::new);

    public static final RegistryObject<LaserPrismModifier> LASER_PRISM =
            MODIFIERS.register("laser_prism", LaserPrismModifier::new);

    public static final RegistryObject<PoisonousBindModifier> POISONOUS_BIND =
            MODIFIERS.register("poisonous_bind", PoisonousBindModifier::new);

    public static final RegistryObject<HeavyStoneModifier> HEAVY_STONE =
            MODIFIERS.register("heavy_stone", HeavyStoneModifier::new);

    public static final RegistryObject<JumpyModifier> JUMPY =
            MODIFIERS.register("jumpy", JumpyModifier::new);

    public static final RegistryObject<BerserkModifier> BERSERK =
            MODIFIERS.register("berserk", BerserkModifier::new);

    public static final RegistryObject<SeirenCurseModifier> SEIRENCURSE =
            MODIFIERS.register("seiren_curse", SeirenCurseModifier::new);

    public static final RegistryObject<UnstableModifier> UNSTABLE =
            MODIFIERS.register("unstable", UnstableModifier::new);

    public static final RegistryObject<FieldyModifier> FIELDY =
            MODIFIERS.register("fieldy", FieldyModifier::new);

    public static final RegistryObject<AuroVisionModifier> AURO_VISION =
            MODIFIERS.register("auro_vision", AuroVisionModifier::new);

    private TconxModifiers() {
    }
}

package com.sakalti.sakalti.material.hachilite;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;

public class ModMaterials {

    public static final MaterialId HACHILITE_ID = new MaterialId("sakalti:hachilite");
    public static Material HACHILITE;

    public static void registerMaterials(IEventBus bus) {
        HACHILITE = MaterialManager.getInstance().register(
            new Material(HACHILITE_ID, Material.DisplayName.withTranslationKey("material.hachilite"))
                .setCraftable(true)
                .setCastable(true)
                .setFluid(new ResourceLocation("sakalti", "molten_hachilite"))
        );
    }
}

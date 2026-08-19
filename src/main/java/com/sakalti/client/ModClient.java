package com.sakalti.client;

import com.sakalti.client.model.CrimsonFlyModel;
import com.sakalti.client.renderer.CrimsonFlyRenderer;
import com.sakalti.entity.CrimsonFlyEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ModClient {

    public static final ModelLayerLocation CRIMSON_FLY_LAYER =
            new ModelLayerLocation(
                    new ResourceLocation("sakalti", "crimson_fly"),
                    "main"
            );

    @SubscribeEvent
    public static void registerLayerDefinitions(
            EntityRenderersEvent.RegisterLayerDefinitions event
    ) {
        event.registerLayerDefinition(
                CRIMSON_FLY_LAYER,
                CrimsonFlyModel::createBodyLayer
        );
    }

    @SubscribeEvent
    public static void registerRenderers(
            EntityRenderersEvent.RegisterRenderers event
    ) {
        event.registerEntityRenderer(
                CrimsonFlyEntity.CRIMSON_FLY.get(),
                CrimsonFlyRenderer::new
        );
    }
}

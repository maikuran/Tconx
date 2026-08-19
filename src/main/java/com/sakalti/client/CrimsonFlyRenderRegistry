package com.sakalti.client;

import com.sakalti.client.renderer.CrimsonFlyRenderer;
import com.sakalti.entity.CrimsonFlyEntity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class CrimsonFlyRenderRegistry {

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

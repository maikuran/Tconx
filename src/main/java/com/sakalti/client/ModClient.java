package com.sakalti.client;

import com.sakalti.client.renderer.CrimsonFlyRenderer;
import com.sakalti.entity.CrimsonFlyEntity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = CrimsonFlyEntity.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ModClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // 1.16.5 でのエンティティレンダラー登録方法
        RenderingRegistry.registerEntityRenderingHandler(
                CrimsonFlyEntity.CRIMSON_FLY.get(),
                CrimsonFlyRenderer::new
        );
    }
}

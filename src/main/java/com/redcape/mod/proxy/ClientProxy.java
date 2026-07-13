package com.redcape.mod.proxy;

import com.redcape.mod.client.LayerRedCape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.Map;

public class ClientProxy extends CommonProxy {

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        Map<String, RenderPlayer> skinMap = renderManager.getSkinMap();

        for (RenderPlayer renderPlayer : skinMap.values()) {
            renderPlayer.addLayer(new LayerRedCape(renderPlayer));
        }
    }
}

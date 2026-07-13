package com.redcape.mod;

import com.redcape.mod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RedCapeMod.MODID, name = RedCapeMod.NAME, version = RedCapeMod.VERSION)
public class RedCapeMod {

    public static final String MODID = "redcape";
    public static final String NAME = "Red Cape Mod";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "com.redcape.mod.proxy.ClientProxy", serverSide = "com.redcape.mod.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}

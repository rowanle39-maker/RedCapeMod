package com.redcape.mod.client;

import com.redcape.mod.RedCapeMod;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class LayerRedCape implements LayerRenderer<AbstractClientPlayer> {

    private static final ResourceLocation RED_CAPE_TEXTURE =
            new ResourceLocation(RedCapeMod.MODID, "textures/models/cape.png");

    private final RenderPlayer renderPlayer;

    public LayerRedCape(RenderPlayer renderPlayerIn) {
        this.renderPlayer = renderPlayerIn;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks,
                               float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        if (player.isInvisible() || !player.isWearing(EnumPlayerModelParts.CAPE)) {
            return;
        }

        ItemStack chestSlot = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (chestSlot.getItem() == Items.ELYTRA) {
            return;
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderPlayer.bindTexture(RED_CAPE_TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 0.125F);

        double dx = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * (double) partialTicks
                - (player.prevPosX + (player.posX - player.prevPosX) * (double) partialTicks);
        double dy = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * (double) partialTicks
                - (player.prevPosY + (player.posY - player.prevPosY) * (double) partialTicks);
        double dz = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * (double) partialTicks
                - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double) partialTicks);

        float yaw = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
        double sinYaw = MathHelper.sin(yaw * 0.017453292F);
        double cosYaw = -MathHelper.cos(yaw * 0.017453292F);

        float capeSway = (float) dy * 10.0F;
        capeSway = MathHelper.clamp(capeSway, -6.0F, 32.0F);

        float sideLean = (float) (dx * sinYaw + dz * cosYaw) * 100.0F;
        float forwardLean = (float) (dx * cosYaw - dz * sinYaw) * 100.0F;

        if (sideLean < 0.0F) {
            sideLean = 0.0F;
        }

        float cameraYaw = MathHelper.lerp(partialTicks, player.prevCameraYaw, player.cameraYaw);
        capeSway += MathHelper.sin(MathHelper.lerp(partialTicks, player.prevDistanceWalkedModified, player.distanceWalkedModified) * 6.0F) * 32.0F * cameraYaw;

        if (player.isSneaking()) {
            capeSway += 25.0F;
        }

        GlStateManager.rotate(6.0F + sideLean / 2.0F + capeSway, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(forwardLean / 2.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-forwardLean / 2.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableRescaleNormal();

        this.renderPlayer.getMainModel().renderCape(0.0625F);

        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

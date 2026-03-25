package com.ommods.openmodularturrets.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ommods.openmodularturrets.client.model.RailGunTurretModel;
import com.ommods.openmodularturrets.content.block.turrets.RailGunTurretBlockEntity;
import com.ommods.openmodularturrets.util.TurretAimHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class RailGunTurretBlockEntityRenderer implements BlockEntityRenderer<RailGunTurretBlockEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/block/rail_gun_turret.png");
    private final RailGunTurretModel model;
    public RailGunTurretBlockEntityRenderer(BlockEntityRendererProvider.Context context){ this.model = new RailGunTurretModel(context.bakeLayer(RailGunTurretModel.LAYER_LOCATION)); }
    @Override public void render(RailGunTurretBlockEntity be,float pt,PoseStack ps,MultiBufferSource bs,int light,int overlay){ if (be.isConcealed()) { return; } ps.pushPose(); ps.translate(0.5D,1.5D,0.5D); ps.scale(1.0F,-1.0F,-1.0F); model.resetPose(); model.setBaseRotation(be.getBaseFacing()); model.setRotationForTarget(TurretAimHelper.getRotationXY(be.getRenderYaw(),be.getRenderPitch(),be.getBaseFacing()), TurretAimHelper.getRotationXZ(be.getRenderYaw(),be.getRenderPitch(),be.getBaseFacing())); VertexConsumer vc=bs.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)); model.renderToBuffer(ps,vc,light,overlay,-1); ps.popPose(); }
}

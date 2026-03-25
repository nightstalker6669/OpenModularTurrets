package com.ommods.openmodularturrets.client;

import com.ommods.openmodularturrets.OMTBlockEntities;
import com.ommods.openmodularturrets.client.model.LaserTurretModel;
import com.ommods.openmodularturrets.client.model.MachineGunTurretModel;
import com.ommods.openmodularturrets.client.model.RocketTurretModel;
import com.ommods.openmodularturrets.client.model.DisposableItemTurretModel;
import com.ommods.openmodularturrets.client.model.GrenadeTurretModel;
import com.ommods.openmodularturrets.client.model.IncendiaryTurretModel;
import com.ommods.openmodularturrets.client.model.PotatoCannonTurretModel;
import com.ommods.openmodularturrets.client.model.RailGunTurretModel;
import com.ommods.openmodularturrets.client.model.PlasmaTurretModel;
import com.ommods.openmodularturrets.client.model.ArcTurretModel;
import com.ommods.openmodularturrets.client.model.TeleporterTurretModel;
import com.ommods.openmodularturrets.client.model.RelativisticTurretModel;
import com.ommods.openmodularturrets.client.model.CrossbowTurretModel;
import com.ommods.openmodularturrets.client.render.LaserTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.MachineGunTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.RocketTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.DisposableItemTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.GrenadeTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.IncendiaryTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.PotatoCannonTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.RailGunTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.PlasmaTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.ArcTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.TeleporterTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.RelativisticTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.render.CrossbowTurretBlockEntityRenderer;
import com.ommods.openmodularturrets.client.OMTMenuScreenRegistrar;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class OMTClient {
    private OMTClient() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(OMTBlockEntities.MACHINE_GUN_TURRET.get(), MachineGunTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.LASER_TURRET.get(), LaserTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.ROCKET_TURRET.get(), RocketTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.DISPOSABLE_ITEM_TURRET.get(), DisposableItemTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.GRENADE_TURRET.get(), GrenadeTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.INCENDIARY_TURRET.get(), IncendiaryTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.POTATO_CANNON_TURRET.get(), PotatoCannonTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.RAIL_GUN_TURRET.get(), RailGunTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.PLASMA_TURRET.get(), PlasmaTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.ARC_TURRET.get(), ArcTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.TELEPORTER_TURRET.get(), TeleporterTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.RELATIVISTIC_TURRET.get(), RelativisticTurretBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(OMTBlockEntities.CROSSBOW_TURRET.get(), CrossbowTurretBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MachineGunTurretModel.LAYER_LOCATION, MachineGunTurretModel::createBodyLayer);
        event.registerLayerDefinition(LaserTurretModel.LAYER_LOCATION, LaserTurretModel::createBodyLayer);
        event.registerLayerDefinition(RocketTurretModel.LAYER_LOCATION, RocketTurretModel::createBodyLayer);
        event.registerLayerDefinition(DisposableItemTurretModel.LAYER_LOCATION, DisposableItemTurretModel::createBodyLayer);
        event.registerLayerDefinition(GrenadeTurretModel.LAYER_LOCATION, GrenadeTurretModel::createBodyLayer);
        event.registerLayerDefinition(IncendiaryTurretModel.LAYER_LOCATION, IncendiaryTurretModel::createBodyLayer);
        event.registerLayerDefinition(PotatoCannonTurretModel.LAYER_LOCATION, PotatoCannonTurretModel::createBodyLayer);
        event.registerLayerDefinition(RailGunTurretModel.LAYER_LOCATION, RailGunTurretModel::createBodyLayer);
        event.registerLayerDefinition(PlasmaTurretModel.LAYER_LOCATION, PlasmaTurretModel::createBodyLayer);
        event.registerLayerDefinition(ArcTurretModel.LAYER_LOCATION, ArcTurretModel::createBodyLayer);
        event.registerLayerDefinition(TeleporterTurretModel.LAYER_LOCATION, TeleporterTurretModel::createBodyLayer);
        event.registerLayerDefinition(RelativisticTurretModel.LAYER_LOCATION, RelativisticTurretModel::createBodyLayer);
        event.registerLayerDefinition(CrossbowTurretModel.LAYER_LOCATION, CrossbowTurretModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        OMTMenuScreenRegistrar.register(event);
    }
}

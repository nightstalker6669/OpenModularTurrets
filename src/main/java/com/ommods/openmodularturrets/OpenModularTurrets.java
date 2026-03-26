package com.ommods.openmodularturrets;

import com.ommods.openmodularturrets.data.OMTDataGenerators;
import com.ommods.openmodularturrets.config.OMTConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

@Mod(MOD_ID)
public class OpenModularTurrets {
    private static final Logger LOGGER = LogManager.getLogger();

    public OpenModularTurrets(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Initializing OpenModularTurrets");
        modEventBus.addListener(OMTDataGenerators::gatherData);
        modContainer.registerConfig(ModConfig.Type.COMMON, OMTConfig.COMMON_SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, OMTConfig.SERVER_SPEC);
        OMTBlocks.register(modEventBus);
        OMTBlockEntities.register(modEventBus);
        OMTMenus.register(modEventBus);
        OMTItems.register(modEventBus);
        OMTCreativeModeTab.register(modEventBus);
    }
}

package com.ommods.openmodularturrets;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

@Mod(MOD_ID)
public class OpenModularTurrets {
    private static final Logger LOGGER = LogManager.getLogger();

    public OpenModularTurrets(IEventBus modEventBus) {
        LOGGER.info("Initializing OpenModularTurrets");
        OMTBlocks.register(modEventBus);
        OMTBlockEntities.register(modEventBus);
        OMTMenus.register(modEventBus);
        OMTItems.register(modEventBus);
        OMTCreativeModeTab.register(modEventBus);
    }
}

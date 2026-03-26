package com.ommods.openmodularturrets.data;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class OMTDataGenerators {
    private OMTDataGenerators() {
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.getVanillaPack(event.includeServer()).addProvider(output -> new OMTRecipeProvider(output, event.getLookupProvider()));
    }
}

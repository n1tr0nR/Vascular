package dev.rbn.vascular;

import dev.rbn.vascular.datagen.ModAdvancements;
import dev.rbn.vascular.datagen.ModModels;
import dev.rbn.vascular.datagen.ModRecipe;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class VascularDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModAdvancements::new);
		pack.addProvider(ModModels::new);
		pack.addProvider(ModRecipe::new);
	}
}

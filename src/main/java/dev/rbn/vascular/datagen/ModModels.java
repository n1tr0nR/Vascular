package dev.rbn.vascular.datagen;

import dev.rbn.vascular.init.ModBlocks;
import dev.rbn.vascular.init.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.util.DyeColor;

public class ModModels extends FabricModelProvider {
    public ModModels(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerSimpleCubeAll(ModBlocks.BLOOD_BLOCK);
        generator.registerSimpleCubeAll(ModBlocks.GUT_BLOCK);
        generator.registerSimpleCubeAll(ModBlocks.GOLDEN_BLOOD_BLOCK);
        generator.registerSimpleCubeAll(ModBlocks.GOLDEN_GUT_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(ModItems.BLOOD, Models.GENERATED);
        generator.register(ModItems.ICHOR, Models.GENERATED);
        generator.register(ModItems.ROT, Models.GENERATED);

        generator.register(ModItems.BLOOD_KNIFE, Models.HANDHELD);

        generator.register(ModItems.SYRINGE, Models.GENERATED);
        generator.registerSubModel(ModItems.SYRINGE, "_blood", Models.GENERATED);
        generator.registerSubModel(ModItems.SYRINGE, "_ichor", Models.GENERATED);
        generator.registerSubModel(ModItems.SYRINGE, "_rot", Models.GENERATED);

        generator.register(ModItems.BLOOD_BAG, Models.GENERATED);
        generator.registerSubModel(ModItems.BLOOD_BAG, "_ichor", Models.GENERATED);
        generator.registerSubModel(ModItems.BLOOD_BAG, "_rot", Models.GENERATED);

        generator.register(ModItems.CASSETTE, Models.GENERATED);

        generator.register(ModItems.PATIENT_CARD, Models.GENERATED);
        generator.registerSubModel(ModItems.PATIENT_CARD, "_bound", Models.GENERATED);
    }
}

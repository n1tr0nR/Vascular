package dev.rbn.vascular.datagen;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.init.ModBlocks;
import dev.rbn.vascular.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipe extends FabricRecipeProvider {
    public ModRecipe(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = wrapperLookup.getOrThrow(RegistryKeys.ITEM);

                createShaped(RecipeCategory.TOOLS, ModItems.SYRINGE, 1)
                        .pattern("n")
                        .pattern("i")
                        .pattern("i")
                        .input('n', Items.IRON_NUGGET)
                        .input('i', Items.IRON_INGOT)
                        .criterion("has_iron_nugget", conditionsFromItem(Items.IRON_NUGGET))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.TOOLS, ModItems.PATIENT_CARD, 1)
                        .pattern("ni")
                        .pattern("in")
                        .input('n', Items.IRON_NUGGET)
                        .input('i', Items.IRON_INGOT)
                        .criterion("has_iron_nugget", conditionsFromItem(Items.IRON_NUGGET))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLOOD_BLOCK, 1)
                        .pattern("xx")
                        .pattern("xx")
                        .input('x', ModItems.BLOOD)
                        .criterion("has_blood", conditionsFromItem(ModItems.BLOOD))
                        .offerTo(recipeExporter);
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLDEN_BLOOD_BLOCK, 1)
                        .pattern("xx")
                        .pattern("xx")
                        .input('x', ModItems.ICHOR)
                        .criterion("has_ichor", conditionsFromItem(ModItems.ICHOR))
                        .offerTo(recipeExporter);
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GUT_BLOCK, 4)
                        .pattern("xx")
                        .pattern("xx")
                        .input('x', ModBlocks.BLOOD_BLOCK)
                        .criterion("has_blood_block", conditionsFromItem(ModBlocks.BLOOD_BLOCK))
                        .offerTo(recipeExporter);
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLDEN_GUT_BLOCK, 4)
                        .pattern("xx")
                        .pattern("xx")
                        .input('x', ModBlocks.GOLDEN_BLOOD_BLOCK)
                        .criterion("has_ichor_block", conditionsFromItem(ModBlocks.GOLDEN_BLOOD_BLOCK))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MONITOR, 1)
                        .pattern("ill")
                        .pattern("ill")
                        .pattern("rnn")
                        .input('i', Items.IRON_INGOT)
                        .input('l', Items.REDSTONE_LAMP)
                        .input('r', Items.REDSTONE)
                        .input('n', Items.NOTE_BLOCK)
                        .criterion("has_patient_card", conditionsFromItem(ModItems.PATIENT_CARD))
                        .offerTo(recipeExporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Vascular";
    }
}

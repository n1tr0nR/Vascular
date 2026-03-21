package dev.rbn.vascular.datagen;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.init.VascularBloodTypes;
import dev.rbn.vascular.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancements extends FabricAdvancementProvider {
    public ModAdvancements(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registries, Consumer<AdvancementEntry> consumer) {
        RegistryEntryLookup<Item> items = registries.getOrThrow(RegistryKeys.ITEM);
        RegistryEntryLookup<EntityType<?>> entity = registries.getOrThrow(RegistryKeys.ENTITY_TYPE);

        AdvancementEntry THE_WORLD_LOOKS_WHITE = Advancement.Builder.create()
                .display(
                        ModItems.BLOOD,
                        Text.translatable("advancements.vascular.the_world_looks_white.title"),
                        Text.translatable("advancements.vascular.the_world_looks_white.description").formatted(Formatting.RED),
                        Vascular.id("gui/advancements/backgrounds/guts"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        true
                )
                .criterion("tick", TickCriterion.Conditions.createTick())
                .build(Vascular.id("the_world_looks_white"));
        consumer.accept(THE_WORLD_LOOKS_WHITE);

        AdvancementEntry THE_WORLD_LOOKS_RED = Advancement.Builder.create()
                .parent(THE_WORLD_LOOKS_WHITE)
                .display(
                        ModItems.BLOOD,
                        Text.translatable("advancements.vascular.the_world_looks_red.title"),
                        Text.translatable("advancements.vascular.the_world_looks_red.description").formatted(Formatting.RED),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("has_item", InventoryChangedCriterion.Conditions.items(ModItems.BLOOD))
                .build(Vascular.id("the_world_looks_red"));
        consumer.accept(THE_WORLD_LOOKS_RED);

        AdvancementEntry DANSE_MACABRE = Advancement.Builder.create()
                .parent(THE_WORLD_LOOKS_RED)
                .display(
                        ModItems.ICHOR,
                        Text.translatable("advancements.vascular.danse_macabre.title"),
                        Text.translatable("advancements.vascular.danse_macabre.description").formatted(Formatting.GOLD),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("has_item", InventoryChangedCriterion.Conditions.items(ModItems.ICHOR))
                .build(Vascular.id("danse_macabre"));
        consumer.accept(DANSE_MACABRE);

        AdvancementEntry ORDER = Advancement.Builder.create()
                .parent(DANSE_MACABRE)
                .display(
                        ModItems.ROT,
                        Text.translatable("advancements.vascular.order.title"),
                        Text.translatable("advancements.vascular.order.description").formatted(Formatting.DARK_PURPLE),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("has_item", InventoryChangedCriterion.Conditions.items(ModItems.ROT))
                .build(Vascular.id("order"));
        consumer.accept(ORDER);

        ItemStack syringe = ModItems.SYRINGE.getDefaultStack();
        syringe.set(DataComponentTypes.ITEM_MODEL, Vascular.id("syringe_blood"));
        syringe = syringe.copy();
        AdvancementEntry INTO_THE_FIRE = Advancement.Builder.create()
                .parent(THE_WORLD_LOOKS_WHITE)
                .display(
                        syringe,
                        Text.translatable("advancements.vascular.into_the_fire.title"),
                        Text.translatable("advancements.vascular.into_the_fire.description").formatted(Formatting.RED),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("used_syringe", Criteria.PLAYER_INTERACTED_WITH_ENTITY.create(
                        new PlayerInteractedWithEntityCriterion.Conditions(
                                Optional.empty(),
                                Optional.of(
                                        ItemPredicate.Builder.create()
                                                .items(items, ModItems.SYRINGE)
                                                .components(
                                                        ComponentsPredicate.EMPTY
                                                ).build()
                                ),
                                Optional.of(
                                        EntityPredicate.asLootContextPredicate(
                                                EntityPredicate.Builder.create()
                                                        .type(entity, EntityType.PLAYER)
                                                        .build()
                                        )
                                )
                        )
                ))
                .build(Vascular.id("into_the_fire"));
        consumer.accept(INTO_THE_FIRE);

        AdvancementEntry THE_FIRE_IS_GONE = Advancement.Builder.create()
                .parent(INTO_THE_FIRE)
                .display(
                        syringe,
                        Text.translatable("advancements.vascular.the_fire_is_gone.title"),
                        Text.translatable("advancements.vascular.the_fire_is_gone.description").formatted(Formatting.RED),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        true
                )
                .criterion("used_syringe", Criteria.PLAYER_KILLED_ENTITY.create(
                        OnKilledCriterion.Conditions.createPlayerKilledEntity(
                                Optional.empty(),
                                Optional.of(DamageSourcePredicate.Builder.create()
                                        .tag(TagPredicate.expected(Vascular.SYRINGE)).build()
                                )
                        ).conditions()
                ))
                .build(Vascular.id("the_fire_is_gone"));
        consumer.accept(THE_FIRE_IS_GONE);

        AdvancementEntry REQUIEM = Advancement.Builder.create()
                .parent(INTO_THE_FIRE)
                .display(
                        ModItems.BLOOD_BAG,
                        Text.translatable("advancements.vascular.requiem.title"),
                        Text.translatable("advancements.vascular.requiem.description").formatted(Formatting.RED),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("drunk_bag", Criteria.CONSUME_ITEM.create(
                        new ConsumeItemCriterion.Conditions(
                                Optional.empty(),
                                Optional.of(
                                        ItemPredicate.Builder.create()
                                                .items(items, ModItems.BLOOD_BAG)
                                                .build()
                                )
                        )
                ))
                .build(Vascular.id("requiem"));
        consumer.accept(REQUIEM);

        AdvancementEntry DIVINE_INTERVENTION = Advancement.Builder.create()
                .parent(REQUIEM)
                .display(
                        VascularBloodTypes.ICHOR.createBagItem(),
                        Text.translatable("advancements.vascular.divine_intervention.title"),
                        Text.translatable("advancements.vascular.divine_intervention.description").formatted(Formatting.RED),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("in_code", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(Vascular.id("divine_intervention"));
        consumer.accept(DIVINE_INTERVENTION);
    }
}


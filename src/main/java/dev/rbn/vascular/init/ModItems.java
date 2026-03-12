package dev.rbn.vascular.init;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.VascularBloodTypes;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.content.item.BloodBagItem;
import dev.rbn.vascular.content.item.PatientCardItem;
import dev.rbn.vascular.content.item.SyringeItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface ModItems {
    Item BLOOD = register("blood", Item::new, new Item.Settings());
    Item ICHOR = register("ichor", Item::new, new Item.Settings());
    Item ROT = register("rot", Item::new, new Item.Settings());

    Item SYRINGE = register("syringe", SyringeItem::new, new Item.Settings().maxCount(1));

    Item PATIENT_CARD = register("patient_card", PatientCardItem::new, new Item.Settings().maxCount(1));

    Item BLOOD_BAG = register("blood_bag", BloodBagItem::new, new Item.Settings().maxCount(1).food(
            new FoodComponent(0, 0, true),
            new ConsumableComponent(32, UseAction.DRINK,
                    ModSounds.BLOOD_DRINK, false, List.of())));

    static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Vascular.id(name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    static void init(){
        Vascular.GROUP = Registry.register(Registries.ITEM_GROUP, Vascular.id(Vascular.MOD_ID),
                FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + Vascular.MOD_ID)).icon(BLOOD::getDefaultStack).entries((displayContext, entries) -> {
                    entries.add(BLOOD);
                    entries.add(ICHOR);
                    entries.add(ROT);
                    entries.add(SYRINGE);
                    entries.add(PATIENT_CARD);

                    entries.add(ModBlocks.BLOOD_BLOCK);
                    entries.add(ModBlocks.GUT_BLOCK);
                    entries.add(ModBlocks.GOLDEN_BLOOD_BLOCK);
                    entries.add(ModBlocks.GOLDEN_GUT_BLOCK);

                    for (Map.Entry<Identifier, BloodType> entry : VascularBloodTypes.BLOOD_TYPES.entrySet()){
                        entries.add(entry.getValue().createSyringeItem());
                    }

                    for (Map.Entry<Identifier, BloodType> entry : VascularBloodTypes.BLOOD_TYPES.entrySet()){
                        entries.add(entry.getValue().createBagItem());
                    }

                }).build());
    }
}

package dev.rbn.vascular.init;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.content.block.MonitorBlock;
import dev.rbn.vascular.content.blockEntity.MonitorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MultifaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public interface ModBlocks {
    Block BLOOD_BLOCK = registerBlockItem("blood_block", AbstractBlock.Settings.copy(Blocks.SCULK)
            .sounds(BlockSoundGroup.HONEY), Block::new);
    Block GUT_BLOCK = registerBlockItem("gut_block", AbstractBlock.Settings.copy(Blocks.SCULK)
            .sounds(BlockSoundGroup.HONEY), Block::new);
    Block GOLDEN_BLOOD_BLOCK = registerBlockItem("golden_blood_block", AbstractBlock.Settings.copy(Blocks.SCULK)
            .sounds(BlockSoundGroup.HONEY), Block::new);
    Block GOLDEN_GUT_BLOCK = registerBlockItem("golden_gut_block", AbstractBlock.Settings.copy(Blocks.SCULK)
            .sounds(BlockSoundGroup.HONEY), Block::new);

    Block MONITOR = registerBlockItem("monitor", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque(), MonitorBlock::new);

    private static Block register(String name, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, Block> factory){
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Vascular.id(name));
        return Registry.register(Registries.BLOCK, key, factory.apply(settings.registryKey(key)));
    }

    private static Block registerBlockItem(String name, AbstractBlock.Settings settings, Function<AbstractBlock.Settings, Block> factory){
        Block block = register(name, settings, factory);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Vascular.id(name));
        Registry.register(Registries.ITEM, key, new BlockItem(block, new Item.Settings().registryKey(key).useBlockPrefixedTranslationKey()));
        return block;
    }

    BlockEntityType<MonitorBlockEntity> MONITOR_BE =
            register("monitor", MonitorBlockEntity::new, MONITOR);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Vascular.id(name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    static void init(){}
}

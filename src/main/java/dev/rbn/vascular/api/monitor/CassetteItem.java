package dev.rbn.vascular.api.monitor;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public interface CassetteItem {
    Identifier getTexture(World world, ItemStack stack);
}

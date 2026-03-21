package dev.rbn.vascular.content.item;

import dev.rbn.vascular.content.data.BloodBagComponent;
import dev.rbn.vascular.init.ModDataComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class ObliterationRayItem extends Item {
    public ObliterationRayItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xf9422c;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return 13;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    public void appendTooltip(ItemStack stack, List<Text> list) {
        list.add(Text.literal("Shoots out powerful beams of").formatted(Formatting.DARK_GRAY));
        list.add(Text.literal("light to break blocks.").formatted(Formatting.DARK_GRAY));
        list.add(Text.literal("Blood: 100/100").withColor(0xf9422c));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }
}

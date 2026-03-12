package dev.rbn.vascular.content.item;

import dev.rbn.vascular.content.data.BloodBagComponent;
import dev.rbn.vascular.init.ModDataComponents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class BloodBagItem extends Item {
    public BloodBagItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        BloodBagComponent component = stack.get(ModDataComponents.BLOOD_BAG);
        if (component != null) {
            return component.type().getBloodColor();
        }
        return 0xd60007;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        BloodBagComponent component = stack.get(ModDataComponents.BLOOD_BAG);
        if (component != null){
            float perc = component.amount() / 8F;
            return (int) (perc * 13);
        }
        return 0;
    }

    public void appendTooltip(ItemStack stack, List<Text> list) {
        BloodBagComponent component = stack.get(ModDataComponents.BLOOD_BAG);
        if (component != null){
            String bloodType = component.type().getTranslationKey();
            list.add(Text.translatable("item.syringe.tooltip.blood").formatted(Formatting.DARK_GRAY).append(": ").append(Text.translatable(bloodType).formatted(
                    Formatting.GRAY
            )));
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        BloodBagComponent component = stack.get(ModDataComponents.BLOOD_BAG);
        if (component != null) {
            return component.amount() * 8;
        }
        return 32;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity player){
            Criteria.CONSUME_ITEM.trigger(player, stack);
        }
        BloodBagComponent component = stack.get(ModDataComponents.BLOOD_BAG);
        if (user instanceof PlayerEntity player && component != null){
            component.type().onConsumeBlood(component.type(), player, world, component);
        }

        stack.decrement(1);
        return super.finishUsing(stack, world, user);
    }
}

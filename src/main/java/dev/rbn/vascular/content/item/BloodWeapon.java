package dev.rbn.vascular.content.item;

import dev.rbn.vascular.client.VascularClient;
import dev.rbn.vascular.content.entity.BloodWeaponEntity;
import dev.rbn.vascular.init.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BloodWeapon extends Item {
    public BloodWeapon(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player){
            int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
            if (i < 10){
                return false;
            } else {
                world.playSound(user, user.getBlockPos(), ModSounds.THROW_BLOOD.value(), SoundCategory.PLAYERS, 1.0F, 0.9F + user.getRandom().nextFloat() * 0.2F);
                BloodWeaponEntity entity = new BloodWeaponEntity(world, user, stack);
                entity.setPos(user.getX(), user.getEyeY() - 0.1, player.getZ());
                float power = Math.min(i / 20.0F, 7F);
                entity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, power * 2.5F, 1.0F);
                world.spawnEntity(entity);
                entity.setYaw(user.getYaw());
                entity.setPitch(user.getPitch());

                stack.decrementUnlessCreative(1, user);
                if (world.isClient()){
                    VascularClient.bloodTaking.lastBloodThrown = entity;
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
    }
}

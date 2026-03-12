package dev.rbn.vascular.content.item;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.BloodTypeEntityRegistry;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.api.blood_types.set.HumanBloodType;
import dev.rbn.vascular.content.data.BloodBagComponent;
import dev.rbn.vascular.content.data.SyringeComponent;
import dev.rbn.vascular.init.ModDataComponents;
import dev.rbn.vascular.init.ModEffects;
import dev.rbn.vascular.init.ModItems;
import dev.rbn.vascular.init.ModSounds;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.List;

public class SyringeItem extends Item {
    public SyringeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getEntityWorld().isClient()) {
            return ActionResult.PASS;
        }
        DamageSource source = user.getEntityWorld().getDamageSources().create(Vascular.BLED_OUT);

        if (entity instanceof PlayerEntity target){
            SyringeComponent component = stack.get(ModDataComponents.DNA);
            boolean canTakeBlood = !target.hasStatusEffect(ModEffects.BLEEDING) && component == null;
            boolean canDonateBlood = false;

            if (canTakeBlood){
                BloodType targetType = BloodTypeEntityRegistry.getPlayerBloodType(target.getUuid());
                stack.set(ModDataComponents.DNA, new SyringeComponent(target.getUuidAsString(), target.getName(), targetType));
                stack.set(DataComponentTypes.ITEM_MODEL, targetType.getSyringeModel());
                user.setStackInHand(hand, stack.copy());
                if (target.getEntityWorld() instanceof ServerWorld serverWorld){
                    target.damage(serverWorld, source, 2);
                }
                target.getEntityWorld().playSound(target, target.getBlockPos(), ModSounds.SYRINGE_USE.value(), SoundCategory.PLAYERS,
                        1.0F, 0.9F + target.getRandom().nextFloat() * 0.2F);
                if (user instanceof ServerPlayerEntity serverPlayerEntity && target.getHealth() <= 0.0F){
                    Criteria.PLAYER_KILLED_ENTITY.trigger(serverPlayerEntity, target, source);
                }
                return ActionResult.CONSUME;
            }
            /*if (canDonateBlood){
                BloodType targetType = BloodType.getBloodType(target);
                if (targetType.canRecieveFrom(component.type())){
                    stack.remove(ModDataComponents.DNA);
                    stack.set(DataComponentTypes.ITEM_MODEL, Vascular.id("syringe"));
                    user.setStackInHand(hand, stack.copy());
                    target.removeStatusEffect(ModEffects.BLEEDING);
                    if (target.getEntityWorld() instanceof ServerWorld serverWorld){
                        target.damage(serverWorld, source, 2);
                    }
                    return ActionResult.CONSUME;
                }
            }*/
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    public void appendTooltip(ItemStack stack, List<Text> list) {
        SyringeComponent component = stack.get(ModDataComponents.DNA);
        if (component != null){
            String bloodType = component.type().getTranslationKey();
            if (component.type() instanceof HumanBloodType human && !(component.uuid().isEmpty())){
                bloodType = human.getBloodTypeTranslationKey(component.playerName().getString());
            }
            list.add(Text.translatable("item.syringe.tooltip.blood").formatted(Formatting.DARK_GRAY).append(": ").append(Text.translatable(bloodType).formatted(
                    Formatting.GRAY
            )));
            if (!component.uuid().isEmpty()){
                list.add(Text.translatable("item.syringe.tooltip.target").formatted(Formatting.DARK_GRAY).append(": ").append(component.playerName().copy().formatted(Formatting.GRAY)));
            }
        }
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        SyringeComponent component = stack.get(ModDataComponents.DNA);
        if (slotStack.getItem().equals(Items.IRON_INGOT) && slotStack.getCount() == 1 && component != null){
            ItemStack bloodBag = ModItems.BLOOD_BAG.getDefaultStack();
            bloodBag.set(ModDataComponents.BLOOD_BAG, new BloodBagComponent(component.type(), 1));
            if (component.type().getBagModel() != null){
                bloodBag.set(DataComponentTypes.ITEM_MODEL, component.type().getBagModel());
            }

            slot.setStack(bloodBag);
            stack.remove(ModDataComponents.DNA);
            stack.set(DataComponentTypes.ITEM_MODEL, Vascular.id("syringe"));
            if (player.getEntityWorld().isClient()){
                player.playSound(ModSounds.BAG_CREATE.value(), 1.0F, 0.9F + player.getRandom().nextFloat() * 0.2F);
            }
            return true;
        } else if (slotStack.getItem() instanceof BloodBagItem && component != null){
            BloodBagComponent component1 = slotStack.get(ModDataComponents.BLOOD_BAG);
            if (component1 != null){
                if (component1.canInsert(component.type())){
                    BloodBagComponent newComp = new BloodBagComponent(component1.type(), component1.amount() + 1);
                    ItemStack newStack = slotStack.copy();
                    newStack.set(ModDataComponents.BLOOD_BAG, newComp);
                    slot.setStack(newStack);
                    stack.remove(ModDataComponents.DNA);

                    stack.set(DataComponentTypes.ITEM_MODEL, Vascular.id("syringe"));

                    if (player.getEntityWorld().isClient()){
                        player.playSound(ModSounds.BAG_CREATE.value(), 1.0F, 0.9F + player.getRandom().nextFloat() * 0.2F);
                    }
                    return true;
                }
            }
        } else if (slotStack.getItem() instanceof BloodBagItem){
            BloodBagComponent component1 = slotStack.get(ModDataComponents.BLOOD_BAG);
            if (component1 != null){
                int afterTake = component1.amount() - 1;
                SyringeComponent newComp = new SyringeComponent("", Text.empty(), component1.type());
                BloodBagComponent newBagComp = new BloodBagComponent(component1.type(), afterTake);
                stack.set(ModDataComponents.DNA, newComp);
                stack.set(DataComponentTypes.ITEM_MODEL, component1.type().getSyringeModel());
                if (afterTake <= 0){
                    slot.setStack(Items.IRON_INGOT.getDefaultStack());
                } else {
                    slotStack.set(ModDataComponents.BLOOD_BAG, newBagComp);
                    slot.setStack(slotStack.copy());
                }
                if (player.getEntityWorld().isClient()){
                    player.playSound(ModSounds.BAG_CREATE.value(), 1.0F, 1.5F + player.getRandom().nextFloat() * 0.2F);
                }
                return true;
            }
        }
        return super.onStackClicked(stack, slot, clickType, player);
    }
}

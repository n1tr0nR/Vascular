package dev.rbn.vascular.api.blood_types.set;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.content.data.BloodBagComponent;
import dev.rbn.vascular.init.ModEffects;
import dev.rbn.vascular.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IchorBloodType extends BloodType {
    public IchorBloodType() {
        super(Identifier.of(Vascular.MOD_ID, "ichor"));
    }

    @Override
    public @Nullable Identifier getBagModel() {
        return Vascular.id("blood_bag_ichor");
    }

    @Override
    public Identifier getSyringeModel() {
        return Vascular.id("syringe_ichor");
    }

    @Override
    public int getBloodColor() {
        return 0xf9d24d;
    }

    @Override
    public void onConsumeBlood(BloodType type, PlayerEntity player, World world, BloodBagComponent component) {
        player.removeStatusEffect(ModEffects.BLEEDING);
        Vascular.grantAdvancement(player, Vascular.id("divine_intervention"), "in_code");
    }

    @Override
    public Item bloodItem() {
        return ModItems.ICHOR;
    }
}

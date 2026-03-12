package dev.rbn.vascular.api.blood_types.set;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class RotBloodType extends BloodType {
    public RotBloodType() {
        super(Identifier.of(Vascular.MOD_ID, "rot"));
    }

    @Override
    public @Nullable Identifier getBagModel() {
        return Vascular.id("blood_bag_rot");
    }

    @Override
    public Identifier getSyringeModel() {
        return Vascular.id("syringe_rot");
    }

    @Override
    public int getBloodColor() {
        return 0x705e5e;
    }

    @Override
    public Item bloodItem() {
        return ModItems.ROT;
    }
}

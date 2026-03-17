package dev.rbn.vascular.api.blood_types.set;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.blood_types.BloodType;
import net.minecraft.util.Identifier;

public class PyroBloodType extends BloodType {
    public PyroBloodType() {
        super(Identifier.of(Vascular.MOD_ID, "pyro"));
    }
}

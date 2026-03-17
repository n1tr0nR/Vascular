package dev.rbn.vascular.api;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.api.blood_types.set.HumanBloodType;
import dev.rbn.vascular.api.blood_types.set.IchorBloodType;
import dev.rbn.vascular.api.blood_types.set.PyroBloodType;
import dev.rbn.vascular.api.blood_types.set.RotBloodType;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class VascularBloodTypes {
    public static final HashMap<Identifier, BloodType> BLOOD_TYPES = new HashMap<>();

    public static final BloodType HUMAN = registerBloodType(Vascular.id("human"), new HumanBloodType());
    public static final BloodType ROT = registerBloodType(Vascular.id("rot"), new RotBloodType());
    public static final BloodType ICHOR = registerBloodType(Vascular.id("ichor"), new IchorBloodType());
    public static final BloodType PYRO = registerBloodType(Vascular.id("pyro"), new PyroBloodType());

    public static BloodType registerBloodType(Identifier id, BloodType type){
        BLOOD_TYPES.put(id, type);
        return type;
    }

    public static BloodType get(Identifier id) {
        BloodType type = BLOOD_TYPES.get(id);
        if (type == null) {
            throw new IllegalArgumentException("Unknown blood type: " + id);
        }
        return type;
    }
}
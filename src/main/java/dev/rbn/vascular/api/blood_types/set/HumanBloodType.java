package dev.rbn.vascular.api.blood_types.set;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.blood_types.BloodType;
import net.minecraft.util.Identifier;

public class HumanBloodType extends BloodType {
    public HumanBloodType() {
        super(Identifier.of(Vascular.MOD_ID, "human"));
    }

    public String getBloodTypeTranslationKey(String playerName){
        int hash = playerName.hashCode();
        int number = Math.floorMod(hash, 8) + 1;

        return switch (number){
            case 1 -> getTranslationKey() + ".ap";
            case 2 -> getTranslationKey() + ".an";
            case 3 -> getTranslationKey() + ".bp";
            case 4 -> getTranslationKey() + ".bn";
            case 5 -> getTranslationKey() + ".abp";
            case 6 -> getTranslationKey() + ".abn";
            case 7 -> getTranslationKey() + ".op";
            default -> getTranslationKey() + ".on";
        };
    }
}

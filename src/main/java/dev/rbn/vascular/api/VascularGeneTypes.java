package dev.rbn.vascular.api;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.api.genes.Gene;
import dev.rbn.vascular.api.genes.set.BloodlustGene;
import dev.rbn.vascular.api.genes.set.HumanGene;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class VascularGeneTypes {
    public static final HashMap<Identifier, Gene> GENE_TYPES = new HashMap<>();

    public static final Gene HUMAN = registerGeneType(Vascular.id("human"), new HumanGene());
    public static final Gene BLOODLUST = registerGeneType(Vascular.id("bloodlust"), new BloodlustGene());

    public static Gene registerGeneType(Identifier id, Gene type){
        GENE_TYPES.put(id, type);
        return type;
    }

    public static Gene get(Identifier id) {
        Gene type = GENE_TYPES.get(id);
        if (type == null) {
            throw new IllegalArgumentException("Unknown gene: " + id);
        }
        return type;
    }
}
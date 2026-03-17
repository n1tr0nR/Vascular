package dev.rbn.vascular.api.blood_types;

import com.mojang.serialization.Codec;
import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.GeneTypeEntityRegistry;
import dev.rbn.vascular.api.VascularBloodTypes;
import dev.rbn.vascular.api.VascularGeneTypes;
import dev.rbn.vascular.content.data.BloodBagComponent;
import dev.rbn.vascular.content.data.SyringeComponent;
import dev.rbn.vascular.init.ModDataComponents;
import dev.rbn.vascular.init.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class BloodType {
    private final Identifier id;

    protected BloodType(Identifier id) {
        this.id = id;
    }

    public void onConsumeBlood(BloodType type, PlayerEntity player, World world, BloodBagComponent component){
        GeneTypeEntityRegistry.getPlayerGene(player.getUuid()).onDrinkBlood(type, player, world, component);
    }

    public Identifier getId() {
        return id;
    }

    public String getTranslationKey(){
        return "blood." + getId().getNamespace() + "." + getId().getPath();
    }

    public ItemStack createSyringeItem(){
        ItemStack defStack = ModItems.SYRINGE.getDefaultStack();
        defStack.set(ModDataComponents.DNA, new SyringeComponent("", Text.empty(), this, VascularGeneTypes.HUMAN));
        defStack.set(DataComponentTypes.ITEM_MODEL, getSyringeModel());
        return defStack;
    }

    public ItemStack createBagItem(){
        ItemStack defStack = ModItems.BLOOD_BAG.getDefaultStack();
        defStack.set(ModDataComponents.BLOOD_BAG, new BloodBagComponent(this, VascularGeneTypes.HUMAN, 8));
        if (getBagModel() != null){
            defStack.set(DataComponentTypes.ITEM_MODEL, getBagModel());
        }
        return defStack;
    }

    public static final Codec<BloodType> CODEC = Identifier.CODEC.xmap(
            VascularBloodTypes::get,
            BloodType::getId
    );

    public Identifier getSyringeModel(){
        return Vascular.id("syringe_blood");
    }

    public @Nullable Identifier getBagModel(){
        return null;
    }

    public int getBloodColor(){
        return 0xd60007;
    }

    public Item bloodItem(){
        return ModItems.BLOOD;
    }
}

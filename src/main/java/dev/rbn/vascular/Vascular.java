package dev.rbn.vascular;

import dev.rbn.vascular.api.BloodTypeEntityRegistry;
import dev.rbn.vascular.api.GeneTypeEntityRegistry;
import dev.rbn.vascular.api.VascularBloodTypes;
import dev.rbn.vascular.api.VascularGeneTypes;
import dev.rbn.vascular.content.item.BloodBagItem;
import dev.rbn.vascular.content.item.SyringeItem;
import dev.rbn.vascular.init.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Vascular implements ModInitializer {
	public static final String MOD_ID = "vascular";
	public static final Logger LOGGER = LoggerFactory.getLogger("Vascular");

	public static ItemGroup GROUP;

	public static final RegistryKey<DamageType> BLED_OUT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("bled_out"));
	public static final TagKey<DamageType> SYRINGE = TagKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(MOD_ID, "syringe"));

	@Override
	public void onInitialize() {
		LOGGER.info("THE WORLD IS YOUR CANVAS. SO TAKE UP YOUR BRUSH. AND PAINT. THE WORLD. RED.");

		ModItems.init();
		ModBlocks.init();
		ModDataComponents.init();
		ModEffects.init();
		ModSounds.init();

		GeneTypeEntityRegistry.addPlayerToGene(UUID.fromString("49c1c458-0503-48fb-a5bb-2c4eff2044b0"), VascularGeneTypes.BLOODLUST);

		BloodTypeEntityRegistry.addPlayerToBloodType(UUID.fromString("89d12ebd-6459-4f22-b319-3f45a013fcf6"), VascularBloodTypes.ROT);
		BloodTypeEntityRegistry.addPlayerToBloodType(UUID.fromString("a9bcfe9b-bb80-463d-848e-11e0b03f2b6e"), VascularBloodTypes.ICHOR);

		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
			if (itemStack.getItem() instanceof SyringeItem syringeItem){
				syringeItem.appendTooltip(itemStack, list);
			}
			if (itemStack.getItem() instanceof BloodBagItem bloodBagItem){
				bloodBagItem.appendTooltip(itemStack, list);
			}
		});
	}

	public static void grantAdvancement(PlayerEntity player, Identifier identifier, String criterion){
		if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
		MinecraftServer server = serverPlayer.getEntityWorld().getServer();
		if (server != null){
			AdvancementEntry advancement = server.getAdvancementLoader().get(identifier);
			serverPlayer.getAdvancementTracker().grantCriterion(advancement, criterion);
		}
	}

	public static Identifier id(String path){
		return Identifier.of(MOD_ID, path);
	}
}
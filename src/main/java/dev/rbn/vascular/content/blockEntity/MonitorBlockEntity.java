package dev.rbn.vascular.content.blockEntity;

import dev.rbn.vascular.api.monitor.VhsItem;
import dev.rbn.vascular.client.sound.MonitorSoundInstance;
import dev.rbn.vascular.client.sound.MonitorStaticSoundInstance;
import dev.rbn.vascular.content.block.MonitorBlock;
import dev.rbn.vascular.init.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MonitorBlockEntity extends BlockEntity {
    private ItemStack currentVhs = Items.DIRT.getDefaultStack();

    @Environment(EnvType.CLIENT)
    private MonitorStaticSoundInstance staticSound;
    @Environment(EnvType.CLIENT)
    private MonitorSoundInstance humSound;

    public MonitorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.MONITOR_BE, pos, state);
    }

    public ItemStack getCurrentVhs() {
        return currentVhs;
    }

    public void setCurrentVhs(ItemStack currentVhs) {
        this.currentVhs = currentVhs;
        this.markDirty();
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.currentVhs = view.read("vhs", ItemStack.CODEC).orElse(Items.DIRT.getDefaultStack());
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.put("vhs", ItemStack.CODEC, this.currentVhs);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void clientTick(World world, BlockPos pos, BlockState state){
        if (world.isClient()){
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.player == null) return;

            if (state.get(MonitorBlock.POWERED)){
                if (staticSound == null || !client.getSoundManager().isPlaying(staticSound) && this.getCurrentVhs().isOf(Items.DIRT)) {

                    staticSound = new MonitorStaticSoundInstance(
                            pos
                    );

                    client.getSoundManager().play(staticSound);
                }
                if (!this.getCurrentVhs().isOf(Items.DIRT) && client.getSoundManager().isPlaying(staticSound)){
                    client.getSoundManager().stop(staticSound);
                    staticSound = null;
                }

                if (humSound == null || !client.getSoundManager().isPlaying(humSound) && !this.getCurrentVhs().isOf(Items.DIRT)) {
                    if (this.getCurrentVhs().getItem() instanceof VhsItem item && item.sound(this.getCurrentVhs()) != null){
                        humSound = new MonitorSoundInstance(pos, item.sound(this.getCurrentVhs()), item.loop());
                        client.getSoundManager().play(humSound);
                    }
                }
                if (this.getCurrentVhs().isOf(Items.DIRT) && client.getSoundManager().isPlaying(humSound)){
                    client.getSoundManager().stop(humSound);
                    humSound = null;
                }
            } else {
                if (client.getSoundManager().isPlaying(humSound)){
                    client.getSoundManager().stop(humSound);
                    humSound = null;
                }
                if (client.getSoundManager().isPlaying(staticSound)){
                    client.getSoundManager().stop(staticSound);
                    staticSound = null;
                }
            }
        }
    }

    @Override
    public void markRemoved() {
        if (this.getWorld().isClient()){
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.getSoundManager().isPlaying(staticSound)){
                client.getSoundManager().stop(staticSound);
                staticSound = null;
            }
        }
        super.markRemoved();
    }
}

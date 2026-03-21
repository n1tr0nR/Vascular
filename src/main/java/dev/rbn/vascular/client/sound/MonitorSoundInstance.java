package dev.rbn.vascular.client.sound;

import dev.rbn.vascular.content.block.MonitorBlock;
import dev.rbn.vascular.init.ModBlocks;
import dev.rbn.vascular.init.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MonitorSoundInstance extends MovingSoundInstance {
    private final BlockPos pos;
    private final MinecraftClient client;

    public MonitorSoundInstance(BlockPos pos, SoundEvent event, boolean loop) {
        super(event, SoundCategory.BLOCKS, SoundInstance.createRandom());

        this.pos = pos;
        this.client = MinecraftClient.getInstance();

        this.x = pos.getX() + 0.5;
        this.y = pos.getY() + 0.5;
        this.z = pos.getZ() + 0.5;

        this.repeat = loop;
        this.repeatDelay = 0;

        this.volume = 0.1f;
        this.pitch = 1.0f;
    }

    @Override
    public void tick() {
        if (client.player == null) {
            this.setDone();
            return;
        }

        if (client.world != null){
            if (!client.world.getBlockState(pos).isOf(ModBlocks.MONITOR)){
                if (client.getSoundManager().isPlaying(this)){
                    client.getSoundManager().stop(this);
                }
            }

            BlockState entity = client.world.getBlockState(pos);
            if (entity.contains(MonitorBlock.POWERED)){
                if (!entity.get(MonitorBlock.POWERED)) {
                    this.setDone();
                    return;
                }
            }
        }

        Vec3d playerPos = client.player.getEntityPos();
        Vec3d soundPos = Vec3d.ofCenter(pos);

        double distance = playerPos.distanceTo(soundPos);

        if (distance > 10) {
            this.volume = 0F;
            return;
        }

        float fade = 1.0f - (float)(distance / 10);
        fade = MathHelper.clamp(fade * fade, 0.0f, 1.0f);

        this.volume = fade * 0.5F;
    }
}

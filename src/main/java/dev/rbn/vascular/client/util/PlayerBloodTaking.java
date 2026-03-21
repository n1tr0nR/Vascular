package dev.rbn.vascular.client.util;

import dev.rbn.vascular.content.entity.BloodWeaponEntity;
import dev.rbn.vascular.init.ModSounds;
import net.minecraft.client.MinecraftClient;

public class PlayerBloodTaking {
    private final MinecraftClient client;

    private int bloodTook = 0;
    private int ticksTakingBlood = 0;
    private boolean takingBlood = false;

    public BloodWeaponEntity lastBloodThrown = null;

    public PlayerBloodTaking(MinecraftClient client) {
        this.client = client;
    }

    public void startTakingBlood(){
        takingBlood = true;
    }

    public void tick(){
        if (takingBlood && client.player != null){
            ticksTakingBlood++;
            if (ticksTakingBlood % 5 == 0 && bloodTook < 10){
                bloodTook++;
                client.player.playSound(ModSounds.SYRINGE_USE.value(), 0.1F, 0.8F + client.player.getRandom().nextFloat() * 0.2F);
            }
        } else {
            bloodTook = 0;
            ticksTakingBlood = 0;
        }
    }

    public void stopTakingBlood(){
        takingBlood = false;
    }

    public int getBloodTook() {
        return bloodTook;
    }

    public void setBloodTook(int bloodTook) {
        this.bloodTook = bloodTook;
    }

    public boolean isTakingBlood() {
        return takingBlood;
    }

    public void setTakingBlood(boolean takingBlood) {
        this.takingBlood = takingBlood;
    }

    public int getTicksTakingBlood() {
        return ticksTakingBlood;
    }

    public void setTicksTakingBlood(int ticksTakingBlood) {
        this.ticksTakingBlood = ticksTakingBlood;
    }
}

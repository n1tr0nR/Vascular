package dev.rbn.vascular.client.render.entity.state;

import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.item.ItemStack;

public class BloodWeaponEntityRenderState extends ProjectileEntityRenderState {
    public ItemRenderState itemState = new ItemRenderState();
    public ItemStack stack;

    public BloodWeaponEntityRenderState(ItemStack stack){
        this.stack = stack;
    }
}

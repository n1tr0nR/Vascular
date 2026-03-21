package dev.rbn.vascular.content.entity;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.client.VascularClient;
import dev.rbn.vascular.init.ModDataComponents;
import dev.rbn.vascular.init.ModEntities;
import dev.rbn.vascular.init.ModItems;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

import java.awt.*;

public class BloodWeaponEntity extends PersistentProjectileEntity {
    private static final TrackedData<Boolean> EXPLODE = DataTracker.registerData(BloodWeaponEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private ItemStack stack;

    public BloodWeaponEntity(EntityType<BloodWeaponEntity> entityEntityType, World world) {
        super(entityEntityType, world);
        this.stack = ItemStack.EMPTY;
    }

    public BloodWeaponEntity(World world, LivingEntity owner, ItemStack stack){
        super(ModEntities.BLOOD_WEAPON, owner, world, stack, (ItemStack)null);
        this.dataTracker.set(EXPLODE, stack.contains(ModDataComponents.EXPLODE));
        this.stack = stack.copy();
    }

    public void setItemStack(ItemStack stack){
        this.setStack(stack);
        this.stack = stack.copy();
    }

    public BloodWeaponEntity(World world, double x, double y, double z, ItemStack stack){
        super(ModEntities.BLOOD_WEAPON, x, y, z, world, stack, stack);
        this.dataTracker.set(EXPLODE, stack.contains(ModDataComponents.EXPLODE));
        this.stack = stack.copy();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(EXPLODE, false);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getEntityWorld().isClient()){
            if (VascularClient.bloodTaking.lastBloodThrown.getId() == this.getId()){
                VascularClient.bloodTaking.lastBloodThrown = this;
            }
        }

        if (this.getEntityWorld() instanceof ServerWorld serverWorld){
            DustParticleEffect dustParticleEffect = new DustParticleEffect(0xff0d00, 1);
            serverWorld.spawnParticles(
                    dustParticleEffect, this.getX(), this.getY() + 0.25F, this.getZ(), 5, 0.1F, 0.1F, 0.1F, 0.1F
            );
        }

        this.setNoGravity(true);

        if (this.age > 100){
            explode();
        }
    }

    public boolean shouldBlowUp(){
        return this.dataTracker.get(EXPLODE);
    }

    public void explode(){
        discard();
    }

    public void shatter(){
        discard();
    }

    @Override
    public void readData(ReadView view) {
        super.readData(view);

        this.stack = view.read("stackB", ItemStack.CODEC).orElse(ModItems.BLOOD.getDefaultStack());
    }

    @Override
    public void writeData(WriteView view) {
        super.writeData(view);

        view.put("stackB", ItemStack.CODEC, this.stack);
    }

    @Override
    protected @Nullable EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        if (shouldBlowUp()){
            explode();
        } else {
            shatter();
        }
        return super.getEntityCollision(currentPosition, nextPosition);
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        /*if (shouldBlowUp()){
            explode();
        } else {
            shatter();
        }*/
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (shouldBlowUp()){
            explode();
        } else {
            shatter();
        }
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return ModItems.BLOOD.getDefaultStack();
    }

    public ItemStack getStack() {
        return stack;
    }
}
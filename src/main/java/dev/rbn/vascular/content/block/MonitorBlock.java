package dev.rbn.vascular.content.block;

import com.mojang.serialization.MapCodec;
import dev.rbn.vascular.api.monitor.VhsItem;
import dev.rbn.vascular.content.blockEntity.MonitorBlockEntity;
import dev.rbn.vascular.init.ModBlocks;
import dev.rbn.vascular.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jspecify.annotations.Nullable;

public class MonitorBlock extends BlockWithEntity {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = Properties.POWERED;

    public MonitorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (!world.isReceivingRedstonePower(pos) && state.get(POWERED)){
            //Powered Off
            world.playSound(
                    null, pos, ModSounds.MONITOR_POWER_OFF.value(), SoundCategory.BLOCKS, 0.5F, 0.9F + world.getRandom().nextFloat() * 0.2F
            );
        }
        world.setBlockState(pos, state.with(POWERED, world.isReceivingRedstonePower(pos)));
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof MonitorBlockEntity monitorBlockEntity && state.get(POWERED)){
            if (stack.getItem() instanceof VhsItem && monitorBlockEntity.getCurrentVhs().isOf(Items.DIRT)){
                monitorBlockEntity.setCurrentVhs(stack.copy());
                world.playSoundAtBlockCenterClient(
                        pos, ModSounds.VHS_ADD.value(), SoundCategory.BLOCKS, 1.0F, 1.0F, true
                );
                return ActionResult.SUCCESS;
            } else if (!monitorBlockEntity.getCurrentVhs().isOf(Items.DIRT)){
                monitorBlockEntity.setCurrentVhs(Items.DIRT.getDefaultStack());
                world.playSoundAtBlockCenterClient(
                        pos, ModSounds.VHS_REMOVE.value(), SoundCategory.BLOCKS, 1.0F, 1.0F, true
                );
                return ActionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(MonitorBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MonitorBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient()){
            return validateTicker(type, ModBlocks.MONITOR_BE, (world1, pos, state1, blockEntity) -> blockEntity.clientTick(world1, pos, state1));
        }
        return super.getTicker(world, state, type);
    }
}

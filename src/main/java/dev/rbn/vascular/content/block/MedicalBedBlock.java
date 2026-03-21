package dev.rbn.vascular.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class MedicalBedBlock extends HorizontalFacingBlock {
    public static final EnumProperty<BedPart> PART = Properties.BED_PART;

    public MedicalBedBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(PART, BedPart.FOOT));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos pos = ctx.getBlockPos();
        BlockPos pos2 = pos.offset(direction);
        World world = ctx.getWorld();

        if (!world.getBlockState(pos2).canReplace(ctx) || !world.getWorldBorder().contains(pos2)) {
            return null;
        }

        return this.getDefaultState()
                .with(FACING, direction)
                .with(PART, BedPart.FOOT);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient()) {
            BlockPos otherPos = pos.offset(state.get(FACING));

            world.setBlockState(otherPos,
                    state.with(PART, BedPart.HEAD),
                    3);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView,
                                                BlockPos pos, Direction direction, BlockPos neighborPos,
                                                BlockState neighborState, Random random) {
        Direction facing = state.get(FACING);

        Direction otherDir = (state.get(PART) == BedPart.FOOT) ? facing : facing.getOpposite();

        if (direction == otherDir) {
            if (!neighborState.isOf(this)) {
                return Blocks.AIR.getDefaultState();
            }
        }

        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            Direction facing = state.get(FACING);
            BedPart part = state.get(PART);

            BlockPos otherPos = (part == BedPart.FOOT)
                    ? pos.offset(facing)
                    : pos.offset(facing.getOpposite());

            BlockState otherState = world.getBlockState(otherPos);

            if (otherState.isOf(this)) {
                world.setBlockState(otherPos, Blocks.AIR.getDefaultState(), 35);
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return null;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createColumnShape(16, 0, 11);
    }
}

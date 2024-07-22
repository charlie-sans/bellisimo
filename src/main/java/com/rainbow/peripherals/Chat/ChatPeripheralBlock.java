package com.rainbow.peripherals.Chat;

import com.mojang.serialization.MapCodec;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ChatPeripheralBlock extends FacingBlock {

    public static final MapCodec<ChatPeripheralBlock> CODEC = createCodec(ChatPeripheralBlock::new);
	public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public ChatPeripheralBlock(Settings settings) {
        super(settings);
		setDefaultState(getDefaultState().with(Properties.FACING, Direction.NORTH));
		setDefaultState(getDefaultState().with(OPEN, false));	
    }

    @Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.FACING);
		builder.add(OPEN);
	}
 
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
		Direction dir = state.get(Properties.FACING);
		switch(dir) {
			case SOUTH:
				return VoxelShapes.cuboid(0.125f, 0.125f, 0.0f, 0.875f, 0.875f, 0.1875f);
			case NORTH:
				return VoxelShapes.cuboid(0.125f, 0.125f, 0.8125f, 0.875f, 0.875f, 1.0f);
			case WEST:
				return VoxelShapes.cuboid(0.8125f, 0.125f, 0.125f, 1.0f, 0.875f, 0.875f);
			case EAST:
				return VoxelShapes.cuboid(0.0f, 0.125f, 0.125f, 0.1875f, 0.875f, 0.875f);
			case UP:
				return VoxelShapes.cuboid(0.125f, 0.0f, 0.125f, 0.875f, 0.1875f, 0.875f);
			case DOWN:
				return VoxelShapes.cuboid(0.125f, 0.8125f, 0.125f, 0.875f, 1.0f, 0.875f);
			default:
				return VoxelShapes.fullCube();
		}
	}
 
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
		Direction side = Direction.UP;
		HitResult hit = ctx.getPlayer().raycast(20, 0, false); // 20 is distance used by the DebugHud for "looking at block", false means ignore fluids
		if (hit.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) hit;
			side = blockHit.getSide();
		}
        return super.getPlacementState(ctx).with(Properties.FACING, side);
    }

	@Override
	public MapCodec<ChatPeripheralBlock> getCodec() {
		return CODEC;
	}
}

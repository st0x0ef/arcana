package org.exodusstudio.arcana.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.exodusstudio.arcana.common.registry.BlockRegistry;

public class LillieBlockItem extends BlockItem {
    public LillieBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        // If used on stone, transform it instead of placing the block
        if (state.is(Blocks.LILY_PAD)) {
            if (!level.isClientSide()) {
                level.setBlock(pos, BlockRegistry.LILY_PAD_BLOCK.get().defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.LILY_PAD_PLACE,
                        SoundSource.BLOCKS, 1.0F, 1.0F);

                // Consume one item
                if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                    context.getItemInHand().shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }

        // For other blocks, use normal placement
        return super.useOn(context);
    }
}

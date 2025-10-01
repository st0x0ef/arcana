package org.exodusstudio.arcana.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.exodusstudio.arcana.client.menu.ResearchTableMenu;
import org.exodusstudio.arcana.common.registry.BlockEntityRegistry;
import org.jetbrains.annotations.Nullable;

public class ResearchTableEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide())
            {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public ResearchTableEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.RESEARCH_TABLE_BE.get(), pos, blockState);
    }




    @Override
    public Component getDisplayName() {
        return Component.translatable("arcana.block_entity.research_table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ResearchTableMenu(containerId, inventory, this);
    }
}

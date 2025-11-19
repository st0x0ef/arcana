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
import net.neoforged.neoforge.transfer.IndexModifier;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.exodusstudio.arcana.client.menu.ResearchTableMenu;
import org.exodusstudio.arcana.common.registry.BlockEntityRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResearchTableEntity extends BlockEntity implements MenuProvider {
    public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(1) {
        @Override
        protected int getCapacity(int index, ItemResource resource) {
            return 1; // Limit to 1 item per slot, matching the original behavior
        }


        private void onContentsChanged(int index) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public final IndexModifier<ItemResource> modifier = (slot, resource, amount) -> {
        try (var tx = Transaction.openRoot()) {
            // Clear existing
            var existing = inventory.getResource(slot);
            if (!existing.isEmpty()) {
                inventory.extract(slot, existing, inventory.getAmountAsInt(slot), tx);
            }

            // Insert new
            if (!resource.isEmpty() && amount > 0) {
                inventory.insert(slot, resource, amount, tx);
            }

            tx.commit();
        }
    };

    public ResearchTableEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.RESEARCH_TABLE_BE.get(), pos, blockState);
    }

    


    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("arcana.block_entity.research_table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ResearchTableMenu(containerId, inventory, this);
    }
}

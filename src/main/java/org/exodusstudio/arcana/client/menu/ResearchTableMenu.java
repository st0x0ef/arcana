package org.exodusstudio.arcana.client.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.exodusstudio.arcana.common.block.entity.ResearchTableEntity;
import org.exodusstudio.arcana.common.registry.BlockRegistry;
import org.exodusstudio.arcana.common.registry.MenuRegistry;

public class ResearchTableMenu extends AbstractContainerMenu {
    public final ResearchTableEntity researchTableEntity;
    private final Level level;
    public ResearchTableMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ResearchTableMenu(int containerId, Inventory inv, BlockEntity blockEntity)
    {
        super(MenuRegistry.RESEARCH_TABLE_MENU.get(), containerId);
        this.researchTableEntity = (ResearchTableEntity) blockEntity;
        this.level = inv.player.level();


        this.addSlot(new ResourceHandlerSlot(
                this.researchTableEntity.inventory,
                this.researchTableEntity.modifier,
                0,
                80, 35
        ));
    }



    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, researchTableEntity.getBlockPos()),
                player, BlockRegistry.RESEARCH_TABLE.get());
    }
}

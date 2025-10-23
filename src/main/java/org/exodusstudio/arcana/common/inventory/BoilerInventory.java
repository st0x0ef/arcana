package org.exodusstudio.arcana.common.inventory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoilerInventory implements ResourceHandler<ItemResource> {
    private static final int maxStackSize = 5;
    private static final int maxSlots = 5;

    private final ItemStack[] stacks = new ItemStack[maxSlots];

    public BoilerInventory(){
        for (int i = 0; i < maxSlots; i++){
            stacks[i] = ItemStack.EMPTY;
        }
    }

    public ItemStack getStackInSlot(int slot){
        return stacks[slot];
    }

    public int getSlots(){
        return maxSlots;
    }

    public void setStackInSlot(int slot, ItemStack stack){
        stacks[slot] = stack;
    }

    public int getMaxStackSize(){ return maxStackSize;}

    public boolean isEmpty(){
        for (ItemStack stack : stacks){
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    public boolean isFull(){
        for (ItemStack stack : stacks){
            if (stack.isEmpty() || stack.getCount() < maxStackSize){
                return false;
            }
        }
        return true;
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public ItemResource getResource(int i) {
        return null;
    }

    @Override
    public long getAmountAsLong(int i) {
        return 0;
    }

    @Override
    public long getCapacityAsLong(int i, ItemResource itemResource) {
        return 0;
    }

    @Override
    public boolean isValid(int i, ItemResource itemResource) {
        return false;
    }

    @Override
    public int insert(int slot, ItemResource resource, int amount, TransactionContext context) {
        System.out.println("HelpHelpHElpHElpHElPHELPHELPHELPHELPHELP");
        if (amount <= 0 || slot < 0 || slot >= maxSlots) return 0;

        Item item = resource.getItem();
        ItemStack current = stacks[slot];

        amount = 1;

        if (!current.isEmpty() && current.is(item)){
            if (current.getCount() >= maxStackSize) {
                System.out.println("Slot " + slot + " full, rejecting insert.");
                return 0;
            }

            current.setCount(Math.min(current.getCount() + 1, maxStackSize));
            return 1;

        }

        if (current.isEmpty()){
            ItemStack newStack = new ItemStack(item, 1);
            stacks[slot] = newStack;
            return 1;
        }

        return 0;
    }

    @Override
    public int extract(int i, ItemResource itemResource, int i1, TransactionContext transactionContext) {
        return 0;
    }

    private static final Codec<List<ItemStack>> STACK_LIST_CODEC = Codec.list(ItemStack.CODEC);

    public CompoundTag saveToTag(){
        CompoundTag compound = new CompoundTag();

        List<ItemStack> list = new ArrayList<>(Arrays.asList(stacks));

        DataResult<Tag> result = STACK_LIST_CODEC.encodeStart(NbtOps.INSTANCE, list);

        result.resultOrPartial(err -> {
            System.err.println("Failed to save itemStacks: " + err);
        }).ifPresent(tag -> compound.put("Items", (Tag) tag));

        return compound;

    }

    public void loadFromTag(CompoundTag compound) {
        if (!compound.contains("Items")) {
            // clear or keep current state
            for (int i = 0; i < stacks.length; i++) stacks[i] = ItemStack.EMPTY;
            return;
        }

        Tag tag = compound.get("Items");

        DataResult<List<ItemStack>> result = STACK_LIST_CODEC.parse(NbtOps.INSTANCE, tag);

        result.resultOrPartial(err -> {
            System.err.println("Failed to load item stacks: " + err);
        }).ifPresent(list -> {
            for (int i = 0; i < stacks.length; i++) {
                if (i < list.size()) stacks[i] = list.get(i);
                else stacks[i] = ItemStack.EMPTY;
            }
        });
    }

}

package org.exodusstudio.arcana.common.block.entity;

import com.sun.jdi.connect.spi.TransportService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import org.exodusstudio.arcana.common.block.BoilerBlock;
import org.exodusstudio.arcana.common.boiler_recipe.BoilerRecipe;
import org.exodusstudio.arcana.common.boiler_recipe.BoilerRecipes;
import org.exodusstudio.arcana.common.capabilities.ModCapabilities;
import org.exodusstudio.arcana.common.fluid_storage.BoilerFluidStorage;
import org.exodusstudio.arcana.common.inventory.BoilerInventory;
import org.exodusstudio.arcana.common.particle.ParticleRegistry;
import org.exodusstudio.arcana.common.registry.BlockEntityRegistry;

import java.util.*;

public class BoilerBlockEntity extends BlockEntity {


    private final BoilerInventory inventory = new BoilerInventory();
    public final BoilerFluidStorage fluidStorage = new BoilerFluidStorage(this);

    public BoilerBlockEntity( BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.BOILER_BE.get(), pos, blockState);
    }


    private int heatTime = 0;

    public BoilerInventory getInventory(){
        return inventory;
    }
    public BoilerFluidStorage getFluidStorage() {return fluidStorage;}

    public static void spawnParticles(BlockPos pos, Level level){
        ((ServerLevel) level).sendParticles(ParticleRegistry.EVAPORATION_PARTICLE.get(),
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                4, 0.1, 0, 0.1, 0.2);
    }

    public int getFluidAmount(){
        return (int) this.fluidStorage.getAmountAsLong(0);
    }

    public int tryInsertFluid(FluidResource resource, int amount){
        int inserted = this.fluidStorage.insert(0, resource, amount, null);
        if (inserted > 0) onFluidChanged();
        return inserted;
    }

    public int tryExtractFluid(int amount){
        FluidResource current = this.fluidStorage.getResource(0);
        if (current == null || current.isEmpty()) return 0;

        int extracted = this.fluidStorage.extract(0, current, amount, null);
        if (extracted > 0) onFluidChanged();
        return extracted;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BoilerBlockEntity be) {
/*
        BoilerRecipe recipe = be.findMatchingRecipe();
*/
        if (be.getFluidAmount() > 0) {
            BlockState below = level.getBlockState(pos.below());
            boolean heatSource = below.is(Blocks.FIRE) || below.is(Blocks.CAMPFIRE) || below.is(Blocks.LAVA);

            if (heatSource) {
                be.heatTime++;
                if (be.heatTime > 100 && !state.getValue(BoilerBlock.BOILING)) {
                    level.setBlock(pos, state.setValue(BoilerBlock.BOILING, true), 3);


                }
                if (state.getValue(BoilerBlock.BOILING)){
                    boolean flag = be.heatTime % 1200 == 0;
                    boolean pflag = be.heatTime % 20 == 0;
                    if (flag){
                        be.tryExtractFluid(1000);
                        be.heatTime = 0;
                    }
                    if (pflag){
                        spawnParticles(pos, level);
                    }
/*
                    if (recipe != null) {
                        be.craftRecipe(recipe, pos, level);
                    }
*/
                }

            } else {
                be.heatTime = 0;
                if (state.getValue(BoilerBlock.BOILING)) {
                    level.setBlock(pos, state.setValue(BoilerBlock.BOILING, false), 3);
                }
            }
        } else {
            be.heatTime = 0;
            if (state.getValue(BoilerBlock.BOILING)) {
                level.setBlock(pos, state.setValue(BoilerBlock.BOILING, false), 3);
            }
        }
    }
/*
    //This returns the total ammount of items inside the inventory
    public int getTotalItemCount(){
        int total = 0;
        for (int i = 0; i < inventory.getSlots(); i++){
            ItemStack s = inventory.getStackInSlot(i);
            if (!s.isEmpty()) total += s.getCount();
        }
        return total;
    }

    //Counts the ammount of a specific Item inside the inventory
    public int getItemCount(Item item){
        int total = 0;
        for (int i = 0; i < inventory.getSlots(); i++){
            ItemStack s = inventory.getStackInSlot(i);
            if (!s.isEmpty() && s.is(item)) total += s.getCount();
        }
        return total;
    }
*//*
    public BoilerRecipe findMatchingRecipe(){
        for (BoilerRecipe recipe : BoilerRecipes.getRecipes()){
            if (hasIngredients(recipe.inputs())){
                return recipe;
            }
        }
        return null;
    }*/
/*
    public void craftRecipe(BoilerRecipe recipe, BlockPos pos, Level level){
        if (!hasIngredients(recipe.inputs())) return;

        consumeIngredients(recipe.inputs());

        ItemStack output = recipe.output();
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, output);
        level.addFreshEntity(entity);

    }
*/
    /*
    public boolean hasIngredients(Map<Item, Integer> required){
        for (var entry : required.entrySet()){
            Item item = entry.getKey();
            int needed = entry.getValue();
            if (getItemCount(item) < needed) return false;
        }
        return true;
    }*/
/*
    public boolean consumeIngredients(Map<Item, Integer> required){
        if (!hasIngredients(required)) return false;

        for (var entry : required.entrySet()){
            Item item = entry.getKey();
            int toRemove = entry.getValue();

            for (int slot = 0; slot < inventory.getSlots() && toRemove > 0; slot++) {
                ItemStack s = inventory.getStackInSlot(slot);
                if (!s.isEmpty() && s.is(item)) {
                    ItemStack removed = inventory.extractItem(slot, Math.min(toRemove, s.getCount()), false);
                    toRemove -= removed.getCount();
                }
            }
            if (toRemove > 0) return false;
        }
        setChanged();
        return true;
    }
*/


/*
    public void addWater(int ammount){
        this.waterAmmount = Math.min(this.waterAmmount + ammount, 3);
        updateWaterLevel();
        setChanged();
    }

    public void removeWater(int ammount){
        this.waterAmmount = Math.max(0, this.waterAmmount - ammount);
        updateWaterLevel();
        setChanged();
    }
*/
    private void updateWaterLevel() {
        if (level != null){
            int amt = getFluidAmount();
            int levelInt = 0;
            int cap = BoilerFluidStorage.CAPACITY;
            if (amt <= 0) levelInt = 0;
            else if (amt <= cap / 3) levelInt = 1;
            else if (amt <= (2 * cap) / 3) levelInt = 2;
            else levelInt = 3;

            BlockState state = level.getBlockState(worldPosition);
            if (state.getBlock() instanceof BoilerBlock && state.getValue(BoilerBlock.WATER_LEVEL) != levelInt){
                level.setBlock(worldPosition, state.setValue(BoilerBlock.WATER_LEVEL, levelInt), 3);
            }
        }
    }


    public void onFluidChanged(){
        setChanged();
        updateWaterLevel();
        if (level != null){
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }


    public int getWaterAmmount(){
        return getFluidAmount();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        fluidStorage.saveTo(output);

        output.putInt("HeatTime", heatTime);

        for (int i = 0; i < inventory.getSlots(); i++){
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()){
                ResourceLocation id1 = BuiltInRegistries.ITEM.getKey(stack.getItem());
                output.putString("Item" + i, id1.toString());
                output.putInt("Count" + i, stack.getCount());
            }
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        fluidStorage.loadFrom(input);
        this.heatTime = input.getInt("HeatTime").orElse(0);

        for (int i = 0; i < inventory.getSlots(); i++){
            String id = input.getStringOr("Item" + i, "");
            int cnt = input.getIntOr("Count" + i, 0);

            if (!id.isEmpty() && cnt > 0){
                ResourceLocation rl = ResourceLocation.tryParse(id);
                if (rl != null){
                    Item item = BuiltInRegistries.ITEM.getValue(rl);
                    if (item != Items.AIR && item != null){
                        inventory.setStackInSlot(i, new ItemStack(item, cnt));
                        continue;
                    }
                }
            }
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
        updateWaterLevel();
    }
}

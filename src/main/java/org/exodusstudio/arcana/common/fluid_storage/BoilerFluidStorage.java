package org.exodusstudio.arcana.common.fluid_storage;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.exodusstudio.arcana.common.block.entity.BoilerBlockEntity;
import org.exodusstudio.arcana.common.registry.BlockEntityRegistry;

import java.util.Optional;

public class BoilerFluidStorage implements ResourceHandler<FluidResource> {

    private final BoilerBlockEntity blockEntity;

    public FluidResource stored = FluidResource.EMPTY;
    public int amount = 0;
    public static final int CAPACITY = 3000;


    public BoilerFluidStorage(BoilerBlockEntity blockEntity){
        this.blockEntity = blockEntity;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public FluidResource getResource(int i) {
        return stored;
    }

    @Override
    public long getAmountAsLong(int i) {
        return amount;
    }

    @Override
    public long getCapacityAsLong(int i, FluidResource resource) {
        return CAPACITY;
    }

    @Override
    public boolean isValid(int i, FluidResource resource) {
        return resource != null && !resource.isEmpty();
    }

    @Override
    public int insert(int i, FluidResource resource, int toInsert, TransactionContext context) {
        if (i != 0 || resource.isEmpty() || toInsert <= 0) return 0;

        if (!stored.isEmpty() && !stored.equals(resource)){
            return 0;
        }

        int space = CAPACITY - amount;
        if (space <=0) return 0;

        int accepted = Math.min(space, toInsert);
        amount += accepted;
        stored = resource;
        onContentsChanged();
        return accepted;
    }

    @Override
    public int extract(int i, FluidResource resource, int toExtract, TransactionContext context) {
        if (i != 0 || resource.isEmpty() || toExtract <= 0) return 0;
        if (stored.isEmpty() || !stored.equals(resource)) return 0;

        int extracted = Math.min(toExtract, amount);
        amount -= extracted;
        if (amount == 0){
            stored = FluidResource.EMPTY;
        }
        onContentsChanged();
        return extracted;
    }


    public void saveTo(ValueOutput tag){
        if (!stored.isEmpty()){
            tag.putString("FluidId", BuiltInRegistries.FLUID.getKey(stored.getFluid()).toString());
            tag.putInt("FluidAmt", amount);
        }
    }

    public void loadFrom(ValueInput input) {

        String id = input.getStringOr("FluidId", "");
        int amount = input.getIntOr("FluidAmt", 0);

        if (id.isEmpty() || amount <= 0) {
            this.stored = FluidResource.EMPTY;
            this.amount = 0;
            return;
        }


        ResourceLocation rl = ResourceLocation.tryParse(id);
        if (rl == null) {
            this.stored = FluidResource.EMPTY;
            this.amount = 0;
            return;
        }


        var optFluid = BuiltInRegistries.FLUID.getOptional(rl);
        if (optFluid.isEmpty()) {
            this.stored = FluidResource.EMPTY;
            this.amount = 0;
            return;
        }


        FluidStack fs = new FluidStack(optFluid.get(), amount);
        this.stored = FluidResource.of(fs);
        this.amount = amount;
    }

    protected void onContentsChanged(){
        if (blockEntity != null){
            blockEntity.onFluidChanged();
        }
    }

}

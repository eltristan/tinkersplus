package com.tristan.tinkersplus.fluids;

import com.tristan.tinkersplus.TinkersPlus;
import com.tristan.tinkersplus.blocks.TinkersPlusBlocks;
import com.tristan.tinkersplus.items.TinkersPlusItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TinkersPlusFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, TinkersPlus.MOD_ID);

    // Registro del fluido Molten Fiery
    public static final RegistryObject<FlowingFluid> STILL_MOLTEN_FIERY = FLUIDS.register("molten_fiery",
            () -> new ForgeFlowingFluid.Source(TinkersPlusFluids.MOLTEN_FIERY_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MOLTEN_FIERY = FLUIDS.register("molten_fiery_flowing",
            () -> new ForgeFlowingFluid.Flowing(TinkersPlusFluids.MOLTEN_FIERY_PROPERTIES));

    public static final ForgeFlowingFluid.Properties MOLTEN_FIERY_PROPERTIES = new ForgeFlowingFluid.Properties(
            TinkersPlusFluidTypes.MOLTEN_FIERY_TYPE, STILL_MOLTEN_FIERY, FLOWING_MOLTEN_FIERY)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .block(TinkersPlusBlocks.MOLTEN_FIERY_BLOCK) // Bloque asociado al fluido
            .bucket(TinkersPlusItems.MOLTEN_FIERY_BUCKET) // Cubo asociado al fluido
            .tickRate(30) // Tasa de flujo del fluido
            .explosionResistance(100f); // Resistencia a explosiones

    /**
     * Método para registrar todos los fluidos en el bus de eventos.
     *
     * @param eventBus El bus de eventos donde registrar los fluidos.
     */
    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}


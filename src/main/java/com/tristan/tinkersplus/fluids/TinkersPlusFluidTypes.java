package com.tristan.tinkersplus.fluids;

import com.tristan.tinkersplus.TinkersPlus;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TinkersPlusFluidTypes {
    // Registro de tipos de fluidos
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, TinkersPlus.MOD_ID);

    // Texturas para el fluido molten_fiery
    public static final ResourceLocation MOLTEN_FIERY_STILL_RL = new ResourceLocation(TinkersPlus.MOD_ID, "fluid/molten/fiery_still");
    public static final ResourceLocation MOLTEN_FIERY_FLOWING_RL = new ResourceLocation(TinkersPlus.MOD_ID, "fluid/molten/fiery_flow");

    // Registro del tipo de fluido molten_fiery
    public static final RegistryObject<FluidType> MOLTEN_FIERY_TYPE = register("molten_fiery",
            FluidType.Properties.create()
                    .lightLevel(15) // Nivel de luz emitido
                    .density(2500) // Menor densidad que la lava (Lava: 3000)
                    .viscosity(4000) // Menor viscosidad que la lava (Lava: 6000)
                    .temperature(1300) // Temperatura en Kelvin
                    .sound(SoundAction.get("place"), SoundEvents.BUCKET_EMPTY_LAVA)
                    .sound(SoundAction.get("pickup"), SoundEvents.BUCKET_FILL_LAVA));

    // Método para registrar un tipo de fluido
    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(
                MOLTEN_FIERY_STILL_RL, // Textura estática
                MOLTEN_FIERY_FLOWING_RL, // Textura fluyendo
                0xDA7600, // Color del fluido (naranja oscuro)
                new Vector3f(218f / 255f, 118f / 255f, 0f), // Color de la niebla en RGB
                properties
        ));
    }

    /**
     * Método para registrar todos los tipos de fluidos en el bus de eventos.
     *
     * @param eventBus El bus de eventos donde registrar los tipos de fluidos.
     */
    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}

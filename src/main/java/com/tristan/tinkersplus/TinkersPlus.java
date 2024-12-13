package com.tristan.tinkersplus;

import com.mojang.logging.LogUtils;
import com.tristan.tinkersplus.blocks.TinkersPlusBlocks;
import com.tristan.tinkersplus.items.TinkersPlusItems;
import com.tristan.tinkersplus.fluids.TinkersPlusFluids;
import com.tristan.tinkersplus.fluids.TinkersPlusFluidTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(TinkersPlus.MOD_ID)
public class TinkersPlus {
    // Mod ID
    public static final String MOD_ID = "tinkersplus";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Mapa para colores dinámicos de fluidos
    private static final Map<Fluid, Integer> FLUID_COLORS = new HashMap<>();

    public TinkersPlus() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Registro de ítems, bloques, fluidos y tipos de fluidos
        TinkersPlusBlocks.register(modEventBus);
        TinkersPlusItems.register(modEventBus);
        TinkersPlusFluids.register(modEventBus);
        TinkersPlusFluidTypes.register(modEventBus);

        // Configuración y eventos comunes
        modEventBus.addListener(this::commonSetup);

        // Configuración y eventos específicos del cliente
        modEventBus.addListener(this::clientSetup);

        // Registro de eventos generales de Forge
        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("TinkersPlus mod initialized!");
    }

    /**
     * Configuración general del mod.
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Registro de colores para los fluidos
            FLUID_COLORS.put(TinkersPlusFluids.STILL_MOLTEN_FIERY.get(), 0xDA7600); // Naranja oscuro
        });

        LOGGER.info("Common setup completed.");
    }

    /**
     * Configuración específica del cliente.
     */
    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Configuración de renderizado para los fluidos (opacos)
            configureFluidRenderLayers();
            registerFluidColors();
        });

        LOGGER.info("Client setup completed.");
    }

    /**
     * Configura las capas de renderizado para los fluidos.
     */
    private void configureFluidRenderLayers() {
        // Configuración para fluidos opacos (RenderType.solid)
        ItemBlockRenderTypes.setRenderLayer(TinkersPlusFluids.STILL_MOLTEN_FIERY.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(TinkersPlusFluids.FLOWING_MOLTEN_FIERY.get(), RenderType.solid());
    }

    /**
     * Registra los colores dinámicos de los fluidos en cubos.
     */
    private void registerFluidColors() {
        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        itemColors.register(
                (stack, tintIndex) -> {
                    if (tintIndex == 1) { // TintIndex 1 para los colores de fluidos
                        Fluid fluid = getFluidFromBucket(stack);
                        return FLUID_COLORS.getOrDefault(fluid, 0xFFFFFF); // Blanco por defecto
                    }
                    return 0xFFFFFF;
                },
                TinkersPlusItems.MOLTEN_FIERY_BUCKET.get()
        );
    }

    /**
     * Obtiene el fluido contenido en un cubo.
     *
     * @param stack El cubo del fluido.
     * @return El fluido contenido o null si no hay.
     */
    private Fluid getFluidFromBucket(ItemStack stack) {
        if (stack.getItem() instanceof BucketItem bucket) {
            return bucket.getFluid();
        }
        return null;
    }
}

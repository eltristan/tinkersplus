package com.tristan.tinkersplus.items;

import com.tristan.tinkersplus.TinkersPlus;
import com.tristan.tinkersplus.fluids.TinkersPlusFluids;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class TinkersPlusItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TinkersPlus.MOD_ID);

    // Registro del cubo de molten_fiery
    public static final RegistryObject<Item> MOLTEN_FIERY_BUCKET = ITEMS.register("molten_fiery_bucket",
            () -> new BucketItem(() -> TinkersPlusFluids.STILL_MOLTEN_FIERY.get(),
                    new Item.Properties()
                            .craftRemainder(Items.BUCKET) // Devuelve un cubo vacío tras ser usado
                            .stacksTo(1))); // Solo permite un cubo por pila

    // Mapa de colores para los fluidos
    private static final Map<Fluid, Integer> FLUID_COLORS = new HashMap<>();

    /**
     * Método para registrar todos los ítems en el bus de eventos.
     *
     * @param eventBus El bus de eventos donde registrar los ítems.
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    /**
     * Método para configurar el tintado dinámico del cubo.
     *
     * @param event Evento de configuración del cliente.
     */
    public static void setupItemColors(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Registrar tintado dinámico para ítems
            ItemColors itemColors = Minecraft.getInstance().getItemColors();

            // Mapear colores para fluidos
            FLUID_COLORS.put(TinkersPlusFluids.STILL_MOLTEN_FIERY.get(), 0xDA7600); // Naranja oscuro para molten_fiery

            // Configurar tintado dinámico
            itemColors.register((stack, tintIndex) -> {
                if (tintIndex == 1) { // TintIndex 1 es la superposición (overlay)
                    Fluid fluid = getFluidFromBucket(stack);
                    return FLUID_COLORS.getOrDefault(fluid, 0xFFFFFF); // Blanco por defecto si no se encuentra
                }
                return 0xFFFFFF; // Blanco para la textura base
            }, MOLTEN_FIERY_BUCKET.get());
        });
    }

    /**
     * Obtiene el fluido de un cubo.
     *
     * @param stack El ItemStack del cubo.
     * @return El fluido contenido en el cubo, o null si no contiene ningún fluido.
     */
    private static Fluid getFluidFromBucket(ItemStack stack) {
        if (stack.getItem() instanceof BucketItem bucket) {
            return bucket.getFluid();
        }
        return null;
    }
}

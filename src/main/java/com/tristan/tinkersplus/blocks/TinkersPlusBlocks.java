package com.tristan.tinkersplus.blocks;

import com.tristan.tinkersplus.TinkersPlus;
import com.tristan.tinkersplus.fluids.TinkersPlusFluids;
import com.tristan.tinkersplus.items.TinkersPlusItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TinkersPlusBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TinkersPlus.MOD_ID);

    // Registro del bloque MOLTEN_FIERY_BLOCK
    public static final RegistryObject<LiquidBlock> MOLTEN_FIERY_BLOCK = BLOCKS.register("molten_fiery_block",
            () -> new LiquidBlock(() -> TinkersPlusFluids.STILL_MOLTEN_FIERY.get(),
                    BlockBehaviour.Properties.copy(Blocks.LAVA)
                            .lightLevel(state -> 15) // Nivel de luz máximo
                            .strength(100.0f) // Resistencia a explosiones
                            .noCollission() // Sin colisión para imitar comportamiento de líquidos
            ) {
                @Override
                public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
                    super.entityInside(state, world, pos, entity);

                    // Aplica daño y quema solo a entidades vivientes
                    if (!world.isClientSide && entity instanceof LivingEntity livingEntity) {
                        int lavaBaseDamage = 5; // Daño fijo (Lava: 4 + 1)
                        int lavaBaseBurnTime = 6; // Tiempo de quemado fijo (Lava: 5 + 1)

                        // Aplica daño
                        livingEntity.hurt(DamageSource.LAVA, lavaBaseDamage);
                        // Prende fuego a la entidad
                        livingEntity.setSecondsOnFire(lavaBaseBurnTime);
                    }
                }
            });

    /**
     * Plantilla para registrar un bloque.
     *
     * @param name  Nombre del bloque.
     * @param block Proveedor del bloque.
     * @param tab   Pestaña del inventario creativo.
     * @param <T>   Tipo de bloque.
     * @return El objeto registrado.
     */
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    /**
     * Plantilla para registrar un ítem de bloque.
     *
     * @param name  Nombre del ítem.
     * @param block Objeto del bloque.
     * @param tab   Pestaña del inventario creativo.
     * @param <T>   Tipo de bloque.
     * @return El objeto registrado.
     */
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return TinkersPlusItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    /**
     * Método para registrar todos los bloques en el bus de eventos.
     *
     * @param eventBus El bus de eventos donde registrar los bloques.
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

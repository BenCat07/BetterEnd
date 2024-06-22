package org.betterx.datagen.betterend.recipes;

import org.betterx.betterend.registry.EndEnchantments;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.provider.WoverEnchantmentProvider;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class EndEnchantmentProvider extends WoverEnchantmentProvider {
    public EndEnchantmentProvider(ModCore modCore) {
        super(modCore, "BetterEnd - Enchantments");
    }

    @Override
    protected void bootstrap(BootstrapContext<Enchantment> context) {
        final HolderGetter<Item> items = context.lookup(Registries.ITEM);
        EndEnchantments.END_VEIL.register(context, Enchantment
                .enchantment(
                        Enchantment.definition(
                                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                8, 1,
                                Enchantment.constantCost(5),
                                Enchantment.constantCost(41),
                                4,
                                EquipmentSlotGroup.HEAD
                        )
                )
                .withEffect(EndEnchantments.END_VEIL_STATE)
        );

    }
}

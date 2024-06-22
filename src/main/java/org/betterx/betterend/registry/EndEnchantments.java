package org.betterx.betterend.registry;

import org.betterx.betterend.BetterEnd;
import org.betterx.wover.enchantment.api.EnchantmentKey;
import org.betterx.wover.enchantment.api.EnchantmentManager;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.Unit;

public class EndEnchantments {
    public final static EnchantmentKey END_VEIL = EnchantmentManager.createKey(BetterEnd.C.mk("end_veil"));


    public static final DataComponentType<Unit> END_VEIL_STATE = EnchantmentManager.registerDataComponent(
            BetterEnd.C.mk("end_veil"),
            (builder) -> builder.persistent(Unit.CODEC)
    );

    public static void register() {
    }
}

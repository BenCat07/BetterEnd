package org.betterx.betterend.item.material;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndBlocks;
import org.betterx.betterend.registry.EndItems;
import org.betterx.wover.item.api.armor.CustomArmorMaterial;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class EndArmorMaterial {
    public static final Holder<ArmorMaterial> THALLASIUM = CustomArmorMaterial
            .start(BetterEnd.C.mk("thallasium"))
            .defense(1, 4, 5, 2, 12)
            .enchantmentValue(17)
            .equipSound(SoundEvents.ARMOR_EQUIP_IRON)
            .toughness(0.0f)
            .knockbackResistance(0.0f)
            .repairIngredientSupplier(() -> Ingredient.of(EndBlocks.THALLASIUM.ingot))
            .buildAndRegister();

    public static final Holder<ArmorMaterial> TERMINITE = CustomArmorMaterial
            .start(BetterEnd.C.mk("terminite"))
            .defense(3, 6, 7, 3, 14)
            .enchantmentValue(26)
            .equipSound(SoundEvents.ARMOR_EQUIP_IRON)
            .toughness(1.0f)
            .knockbackResistance(0.05f)
            .repairIngredientSupplier(() -> Ingredient.of(EndBlocks.TERMINITE.ingot))
            .buildAndRegister();

    public static final Holder<ArmorMaterial> AETERNIUM = CustomArmorMaterial
            .start(BetterEnd.C.mk("aeternium"))
            .defense(4, 7, 9, 4, 18)
            .enchantmentValue(40)
            .equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
            .toughness(3.5f)
            .knockbackResistance(0.2f)
            .repairIngredientSupplier(() -> Ingredient.of(EndItems.AETERNIUM_INGOT))
            .buildAndRegister();

    public static final Holder<ArmorMaterial> CRYSTALITE = CustomArmorMaterial
            .start(BetterEnd.C.mk("crystalite"))
            .defense(3, 6, 8, 3, 24)
            .enchantmentValue(30)
            .equipSound(SoundEvents.ARMOR_EQUIP_DIAMOND)
            .toughness(1.2f)
            .knockbackResistance(0.1f)
            .repairIngredientSupplier(() -> Ingredient.of(EndBlocks.TERMINITE.ingot))
            .buildAndRegister();
}

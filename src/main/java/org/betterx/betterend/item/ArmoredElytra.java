package org.betterx.betterend.item;

import org.betterx.bclib.items.BaseArmorItem;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.interfaces.BetterEndElytra;
import org.betterx.betterend.interfaces.MultiModelItem;
import org.betterx.betterend.registry.EndItems;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ArmoredElytra extends BaseArmorItem implements MultiModelItem, BetterEndElytra {
    private final ResourceLocation wingTexture;
    private final Item repairItem;
    private final double movementFactor;
    private final float toughness;
    private final int defense;

    public ArmoredElytra(
            String name,
            Holder<ArmorMaterial> material,
            Item repairItem,
            int durability,
            double movementFactor,
            boolean fireproof
    ) {
        super(
                material,
                Type.CHESTPLATE,
                fireproof ? EndItems
                        .makeEndItemSettings()
                        .durability(durability)
                        .rarity(Rarity.EPIC)
                        .fireResistant() : EndItems.makeEndItemSettings().durability(durability).rarity(Rarity.EPIC)
        );
        this.wingTexture = BetterEnd.C.mk("textures/entity/" + name + ".png");
        this.repairItem = repairItem;
        this.movementFactor = movementFactor;
        this.defense = (int) ((double) material.getDefenseForType(Type.CHESTPLATE) / 1.15);
        this.toughness = material.getToughness() / 1.15F;
        addAttributeModifier(
                Attributes.ARMOR,
                new AttributeModifier(
                        ARMOR_MODIFIER_UUID_PER_SLOT[2],
                        "Armor modifier",
                        defense,
                        AttributeModifier.Operation.ADDITION
                )
        );
        addAttributeModifier(
                Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(
                        ARMOR_MODIFIER_UUID_PER_SLOT[2],
                        "Armor toughness",
                        toughness,
                        AttributeModifier.Operation.ADDITION
                )
        );
    }

    @Override
    public double getMovementFactor() {
        return movementFactor;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ResourceLocation getModelTexture() {
        return wingTexture;
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return super.isValidRepairItem(itemStack, itemStack2) || itemStack2.getItem() == repairItem;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerModelPredicate() {
        ItemProperties.register(
                this,
                ResourceLocation.withDefaultNamespace("broken"),
                (itemStack, clientLevel, livingEntity, id) -> ElytraItem.isFlyEnabled(itemStack) ? 0.0F : 1.0F
        );
    }
}

package org.betterx.betterend.advancements;

import org.betterx.betterend.BetterEnd;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class BECriteria {
    public static ResourceLocation PORTAL_ON_ID = BetterEnd.C.mk("portal_on");
    public static ResourceLocation PORTAL_TRAVEL_ID = BetterEnd.C.mk("portal_travel");
    public static ResourceLocation INFUSION_FINISHED_ID = BetterEnd.C.mk("infusion_finished");


    public static PlayerTrigger PORTAL_ON;
    public static PlayerTrigger PORTAL_TRAVEL;
    public static PlayerTrigger INFUSION_FINISHED;

    public static Criterion<PlayerTrigger.TriggerInstance> PORTAL_ON_TRIGGER;
    public static Criterion<PlayerTrigger.TriggerInstance> PORTAL_TRAVEL_TRIGGER;
    public static Criterion<PlayerTrigger.TriggerInstance> INFUSION_FINISHED_TRIGGER;


    public static void register() {
        PORTAL_ON = register(PORTAL_ON_ID, new PlayerTrigger());
        PORTAL_TRAVEL = register(PORTAL_TRAVEL_ID, new PlayerTrigger());
        INFUSION_FINISHED = register(INFUSION_FINISHED_ID, new PlayerTrigger());

        PORTAL_ON_TRIGGER = PORTAL_ON.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
        PORTAL_TRAVEL_TRIGGER = PORTAL_TRAVEL.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
        INFUSION_FINISHED_TRIGGER = INFUSION_FINISHED.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
    }

    public static <T extends CriterionTrigger<?>> T register(ResourceLocation id, T criterionTrigger) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, id, criterionTrigger);
    }
}

package org.betterx.betterend.integration.emi;

import org.betterx.betterend.recipe.builders.AlloyingRecipe;

import net.minecraft.resources.ResourceLocation;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;

import java.util.List;

public class EMIAlloyingRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIAlloyingRecipe(AlloyingRecipe recipe) {
        this.id = recipe.getId();
        this.input = List.of(
                EmiIngredient.of(recipe.getIngredients().get(0)),
                EmiIngredient.of(recipe.getIngredients().get(1))
        );
        this.output = List.of(EmiStack.of(recipe.getResultItem()));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIPlugin.END_SMELTER_CATEGORY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 76;
    }

    @Override
    public int getDisplayHeight() {
        return 18;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // Add an arrow texture to indicate processing
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 46, 1);

        // Adds an input slot on the left
        widgets.addSlot(input.get(0), 0, 0);
        widgets.addSlot(input.get(1), 20, 0);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgets.addSlot(output.get(0), 58, 0).recipeContext(this);
    }
}

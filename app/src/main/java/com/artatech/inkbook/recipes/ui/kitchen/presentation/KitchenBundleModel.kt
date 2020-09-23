package com.artatech.inkbook.recipes.ui.kitchen.presentation

import com.artatech.inkbook.recipes.api.response.models.recipe.KitchenResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.TastyResponse
import java.io.Serializable

class KitchenBundleModel(
    val kitchens: List<KitchenResponse>,
    val tastes: List<TastyResponse>): Serializable
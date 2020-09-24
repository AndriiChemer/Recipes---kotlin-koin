package com.artatech.inkbook.recipes.ui.kitchen.presentation

import com.artatech.inkbook.recipes.api.response.models.recipe.CountryResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.TastyResponse
import java.io.Serializable

class KitchenBundleModel(
    val countries: List<CountryResponse>,
    val tastes: List<TastyResponse>): Serializable
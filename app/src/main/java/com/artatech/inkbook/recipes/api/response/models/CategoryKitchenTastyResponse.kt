package com.artatech.inkbook.recipes.api.response.models

import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.api.response.models.recipe.CountryResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.TastyResponse

class CategoryKitchenTastyResponse(
    val categories: List<CategoryModel>,
    val kitchens: List<CountryResponse>,
    val tastes: List<TastyResponse>)
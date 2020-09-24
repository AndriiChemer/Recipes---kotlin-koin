package com.artatech.inkbook.recipes.api.response.models.recipe

import com.artatech.inkbook.recipes.ui.kitchen.presentation.KitchenModel
import java.io.Serializable

class TastyResponse(
    val id: Int,
    val name: String
): Serializable, KitchenModel {
    override fun getNameForTitle() = name
}
package com.artatech.inkbook.recipes.app.di.screens

import com.artatech.inkbook.recipes.core.utils.ExpandOrCollapse
import com.artatech.inkbook.recipes.ui.recipedetail.presentation.RecipeDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val recipeDetailsModule = module {
    viewModel { RecipeDetailViewModel() }
    factory { ExpandOrCollapse() }
}
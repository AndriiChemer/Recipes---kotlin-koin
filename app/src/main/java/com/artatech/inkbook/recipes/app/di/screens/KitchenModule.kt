package com.artatech.inkbook.recipes.app.di.screens

import com.artatech.inkbook.recipes.ui.kitchen.presentation.KitchenFragment
import com.artatech.inkbook.recipes.ui.kitchen.presentation.KitchenViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val kitchenModule = module {
    viewModel { KitchenViewModel() }
    factory { KitchenFragment.newInstance() }
}
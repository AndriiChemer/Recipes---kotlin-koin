package com.artatech.inkbook.recipes.app.di

import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.SubcategoryAdapter
import com.artatech.inkbook.recipes.ui.subcategory.presentation.SubcategoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val subcategoryModule = module {
    factory { SubcategoryAdapter() }
    viewModel { SubcategoryViewModel() }
}
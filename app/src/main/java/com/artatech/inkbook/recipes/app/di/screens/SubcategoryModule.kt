package com.artatech.inkbook.recipes.app.di.screens

import com.artatech.inkbook.recipes.ui.subcategory.presentation.SubcategoryFragment
import com.artatech.inkbook.recipes.depricated.SubcategoryAdapter
import com.artatech.inkbook.recipes.ui.subcategory.presentation.SubcategoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val subcategoryModule = module {
    factory { SubcategoryAdapter() }
    factory { SubcategoryFragment.newInstance() }
    viewModel { SubcategoryViewModel() }
}
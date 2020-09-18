package com.artatech.inkbook.recipes.ui.subcategory.presentation

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.api.response.models.category.RecipeCategoryModel
import com.artatech.inkbook.recipes.api.response.models.category.SubcategoryModel
import com.artatech.inkbook.recipes.core.utils.SingleLiveEvent
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model.EmptySubcategoryItem
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model.RecipeCategoryItem
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model.SubcategoryItem

class SubcategoryViewModel: ViewModel() {

    val categoryData = MutableLiveData<CategoryIntentModel>()
    val subcategoryData = MutableLiveData<List<SubcategoryItem>>()
    val recipeCategoryData = MutableLiveData<List<EmptySubcategoryItem>>()
    val toolbarTitle = MutableLiveData<String>()
    val error = SingleLiveEvent<Throwable>()

    fun onViewCreated(key: String, bundle: Bundle?) {
        if (bundle != null) {
            val categoryModel = bundle.getSerializable(key) as CategoryModel
            toolbarTitle.value = categoryModel.name
            prepareSubcategory(categoryModel.subcategories)
        } else {
            error.value = Throwable("Argument with $key key should not be null!")
        }
    }

    fun onSubcategoryClicked(categoryId: Int, subcategoryId: Int, recipeCategoryId: Int?, title: String) {
        val categoryIntent = CategoryIntentModel(categoryId, subcategoryId, recipeCategoryId, title)
        categoryData.value = categoryIntent
    }

    private fun prepareSubcategory(subcategories: List<SubcategoryModel>) {
        val subcategoryAdapterModels = mutableListOf<SubcategoryItem>()
        val emptySubcategoryAdapterModels = mutableListOf<EmptySubcategoryItem>()

        subcategories.forEach { subcategory -> run {
            if (subcategory.recipeCategories.isNotEmpty()) {
                val list = convertListRecipeCategory(subcategory.recipeCategories)
                val subcategoryItem = SubcategoryItem(subcategory.id, subcategory.name, subcategory.categoryId, list,false)
                subcategoryAdapterModels.add(subcategoryItem)
            } else {
                val subcategoryItem = EmptySubcategoryItem(subcategory.id, subcategory.name, subcategory.categoryId)
                emptySubcategoryAdapterModels.add(subcategoryItem)
            }
        } }

        subcategoryData.value = subcategoryAdapterModels
        recipeCategoryData.value = emptySubcategoryAdapterModels
    }

    private fun convertListRecipeCategory(recipeCategories: List<RecipeCategoryModel>): MutableList<RecipeCategoryItem> {
        val recipeCategoryRecyclerList = mutableListOf<RecipeCategoryItem>()

        recipeCategories.forEach { recipeCategory -> run {
            val recipeCategoryItem = RecipeCategoryItem(recipeCategory.id, recipeCategory.name, recipeCategory.subcategoryId)
            recipeCategoryRecyclerList.add(recipeCategoryItem)
        } }

        return recipeCategoryRecyclerList
    }
}
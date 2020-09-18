package com.artatech.inkbook.recipes.ui.subcategory.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.api.response.models.category.RecipeCategoryModel
import com.artatech.inkbook.recipes.api.response.models.category.SubcategoryModel
import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem
import com.artatech.inkbook.recipes.core.utils.SingleLiveEvent
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.EmptySubcategoryItem
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.RecipeCategoryItem
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.SubcategoryItem
import java.util.*

class SubcategoryViewModel: ViewModel() {

    private lateinit var categoryModel: CategoryModel

    val categoryData = MutableLiveData<CategoryIntentModel>()
    val subcategoryData = MutableLiveData<List<ListRecyclerItem>>()
    val toolbarTitle = MutableLiveData<String>()
    val error = SingleLiveEvent<Throwable>()

    fun onViewCreated(key: String, bundle: Bundle?) {
        if (bundle != null) {
            categoryModel = bundle.getSerializable(key) as CategoryModel
            toolbarTitle.value = categoryModel.name
            subcategoryData.value = prepareSubcategory(categoryModel.subcategories)
        } else {
            error.value = Throwable("Argument with $key key should not be null!")
        }
    }

    fun onSubcategoryClicked(subcategoryId: Int, recipeCategoryId: Int?, title: String) {
        val categoryIntent = CategoryIntentModel(categoryModel.id, subcategoryId, recipeCategoryId, title)
        categoryData.value = categoryIntent
    }

    private fun prepareSubcategory(subcategories: List<SubcategoryModel>): List<ListRecyclerItem> {
        val items = mutableListOf<ListRecyclerItem>()

        subcategories.forEach { subcategory -> run {
            if (subcategory.recipeCategories.isNotEmpty()) {
                val color = getRandomColor()
                val list = convertListRecipeCategory(subcategory.recipeCategories, color)
                val subcategoryItem = SubcategoryItem(subcategory.id, subcategory.name, subcategory.categoryId, list, color,false)
                items.add(subcategoryItem)
            } else {
                val color = getRandomColor()
                val subcategoryItem = EmptySubcategoryItem(subcategory.id, subcategory.name, subcategory.categoryId, color)
                items.add(subcategoryItem)
            }
        } }

        return items
    }

    private fun convertListRecipeCategory(recipeCategories: List<RecipeCategoryModel>, color: Int): MutableList<RecipeCategoryItem> {
        val recipeCategoryRecyclerList = mutableListOf<RecipeCategoryItem>()

        recipeCategories.forEach { recipeCategory -> run {
            val recipeCategoryItem = RecipeCategoryItem(recipeCategory.id, recipeCategory.name, recipeCategory.subcategoryId, color)
            recipeCategoryRecyclerList.add(recipeCategoryItem)
        } }

        return recipeCategoryRecyclerList
    }

    private fun getRandomColor(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
}
package com.artatech.inkbook.recipes.api

import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.GeneralResponse
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.api.response.models.recipe.RecipeResponse

const val RECIPE_PER_PAGE = 7
class Repository(private val api: RecipesApi) {
    private val TAG = Repository::class.java.simpleName

    //Suspend is used to await the result from Deferred
    suspend fun getCategoryAndSubcategory(): List<CategoryModel> {
        val response: GeneralResponse<List<CategoryModel>> = api.getCategoriesAndSubcategories()
        return response.body
    }

    suspend fun getRecipesByCategoryId(categoryRequest: CategoryRequest): PaginationRecipesResponse {
        return api.getRecipesByCategoryId(categoryRequest).body
    }

    private fun <T : Any> handleError(response: GeneralResponse<T>) {
        when(response.statusCode) {
            200 -> true
        }

    }
}
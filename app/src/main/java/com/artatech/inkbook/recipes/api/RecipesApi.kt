package com.artatech.inkbook.recipes.api

import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.api.response.GeneralResponse
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import com.artatech.inkbook.recipes.api.response.models.CategoryKitchenTastyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecipesApi {
    @GET("categories/get/categories/")
    suspend fun getCategoriesAndSubcategories(): GeneralResponse<List<CategoryModel>>

    @POST("recipes/getby/category/subcategory")
    suspend fun getRecipesByCategoryId(@Body categoryRequest: CategoryRequest): GeneralResponse<PaginationRecipesResponse>

    @GET("categories/get/get_categories_kitchens_tastes/")
    suspend fun getCategoriesKitchensTasties(): GeneralResponse<CategoryKitchenTastyResponse>
}
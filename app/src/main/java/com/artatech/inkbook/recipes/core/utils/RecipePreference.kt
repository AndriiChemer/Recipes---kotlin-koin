package com.artatech.inkbook.recipes.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object RecipePreference {
    private const val NAME = "RecipePreference"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences
    private lateinit var gson: Gson

    /**
     * Keys
     * */
    private const val RECIPES_FAVORITE = "RECIPES_FAVORITE"

    fun init(context: Context) {
        gson = Gson()
        preferences = context.getSharedPreferences(
            NAME,
            MODE
        )
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    /**
     * get set Methods
     * */

    var recipeFavoriteMap: HashMap<Int, FullRecipeResponse>?
        get() {
            val json = preferences.getString(RECIPES_FAVORITE, "")

            if (json == "") {
                return HashMap()
            }

            val type: Type = object : TypeToken<HashMap<Int, FullRecipeResponse>>() {}.type
            return gson.fromJson(json, type)
        }
        set(value) = preferences.edit {
            it.clear()
            val json = gson.toJson(value)
            it.putString(RECIPES_FAVORITE, json)
        }

    fun addRecipeToFavorite(item: FullRecipeResponse) {
        val favoriteMap: HashMap<Int, FullRecipeResponse>? = recipeFavoriteMap

        if (!favoriteMap?.keys?.contains(item.recipe.id)!!) {
            favoriteMap[item.recipe.id] = item
            recipeFavoriteMap = favoriteMap
        }
    }
}
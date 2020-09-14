package com.artatech.inkbook.recipes.ui.recipeshortdetail.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.IngredientResponse
import com.artatech.inkbook.recipes.core.utils.RecipePreference
import com.artatech.inkbook.recipes.ui.recipedetail.presentation.RecipeDetailActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_recipe_short_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Type

class RecipeShortDetailActivity : AppCompatActivity() {

    private val viewModel: RecipeShortDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installAnimation()
        setContentView(R.layout.activity_recipe_short_detail)

        val recipe = getFromExtras()
        val isFavorite = RecipePreference.isFavorite(recipe)

        showRecipeDetail(recipe, isFavorite)
        prepareListener(recipe, isFavorite)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showRecipeDetail(recipe: FullRecipeResponse, isFavorite: Boolean) {
        Glide.with(this)
            .load(recipe.recipe.imageUrl)
            .into(imageView)

        titleRecipe.text = recipe.recipe.name

        if (recipe.recipe.cookTime != null) {
            cookTimeContainer.visibility = View.VISIBLE
            cookTimeValue.text = recipe.recipe.cookTime
        }

        if (recipe.kitchens.isNotEmpty()) {
            kitchenContainer.visibility = View.VISIBLE
            kitchenValue.text = recipe.kitchens[0].name
        }

        val ingredients = recipe.ingredients
        ingredients.forEach { ingredient ->
            buildIngredientList(ingredient)
        }

        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_star_gold)
        }
    }

    private fun prepareListener(recipe: FullRecipeResponse, isFavorite: Boolean) {
        var isFavoriteClick = isFavorite
        buttonBack.setOnClickListener { finish() }
        buttonStart.setOnClickListener { RecipeDetailActivity.star(this, recipe) }
        favoriteButton.setOnClickListener {

            if (isFavoriteClick) {
                isFavoriteClick = false
                viewModel.removeFromFavorite(recipe)
                favoriteButton.setImageResource(R.drawable.ic_star_border_gold)
            } else {
                isFavoriteClick = true
                viewModel.addToFavorite(recipe)
                favoriteButton.setImageResource(R.drawable.ic_star_gold)
            }
        }
    }

    private fun installAnimation() {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.enterTransition = Explode()
        window.exitTransition = Explode()
    }

    private fun buildIngredientList(ingredient: IngredientResponse) {
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL


        val paramsTextView = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        // TextView name of ingredient
        val name = TextView(this)
        name.text = ingredient.name
        name.layoutParams = paramsTextView

        // TextView value of ingredient
        val value = TextView(this)
        value.text = if (ingredient.value == "") ingredient.description.removeSuffix(")") else ingredient.value
        value.layoutParams = paramsTextView

        // TextView space between name and value of ingredient
        val param = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        val space = TextView(this)
        space.layoutParams = param
        space.setLines(1)

        //Added to row
        linearLayout.addView(name)
        linearLayout.addView(space)
        linearLayout.addView(value)

        //Add row to column
        ingredientContainer.addView(linearLayout)
    }

    private fun getFromExtras(): FullRecipeResponse {
        val gson = Gson()
        val jsonObject = intent.extras?.getString(RECIPE_ARG)
        if (jsonObject != null) {
            val type: Type = object : TypeToken<FullRecipeResponse>() {}.type
            return gson.fromJson(jsonObject, type)
        } else {
            throw NullPointerException("param $RECIPE_ARG should not be null!")
        }
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }

    companion object {
        private const val RECIPE_ARG = "RECIPE_ARG"
        private const val TRANSACTION_NAME = "RecipeShortDetailActivity"

        fun startWithAnimation(activity: Activity, fullRecipeResponse: FullRecipeResponse, itemView: View) {
            val gson = Gson()
            val intent = Intent(activity, RecipeShortDetailActivity::class.java)
            intent.putExtra(RECIPE_ARG, gson.toJson(fullRecipeResponse))

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, itemView,
                TRANSACTION_NAME
            )
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }
    }
}
package com.artatech.inkbook.recipes.ui.recipeshortdetail.presentation

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
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_recipe_short_detail1.*
import java.lang.reflect.Type

class RecipeShortDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installAnimation()
        setContentView(R.layout.activity_recipe_short_detail1)

        val recipe = getFromExtras()
        showRecipeDetail(recipe)
    }

    private fun installAnimation() {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.enterTransition = Explode()
        window.exitTransition = Explode()
    }

    private fun showRecipeDetail(recipe: FullRecipeResponse) {
        Glide.with(this)
            .load(recipe.recipe.imageUrl)
            .into(imageView)

        titleRecipe.text = recipe.recipe.name

        val ingredients = recipe.ingredients
        ingredients.forEach { ingredient ->
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            val paramsTextView = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)


            val name = TextView(this)
            name.text = ingredient.name
            name.layoutParams = paramsTextView

            val param = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
            )


            val space = TextView(this)
            space.layoutParams = param


            val value = TextView(this)
            value.text = ingredient.value
            value.layoutParams = paramsTextView

            linearLayout.addView(name)
            linearLayout.addView(space)
            linearLayout.addView(value)

            ingredientContainer.addView(linearLayout)
        }
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
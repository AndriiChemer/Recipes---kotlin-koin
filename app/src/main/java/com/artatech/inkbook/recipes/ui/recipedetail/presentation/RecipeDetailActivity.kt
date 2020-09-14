package com.artatech.inkbook.recipes.ui.recipedetail.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.EnergyResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.KitchenResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.RecipeResponse
import com.artatech.inkbook.recipes.core.utils.ExpandOrCollapse
import com.artatech.inkbook.recipes.core.utils.RecipePreference
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlinx.android.synthetic.main.activity_recipe_detail.buttonBack
import kotlinx.android.synthetic.main.activity_recipe_detail.favoriteButton
import kotlinx.android.synthetic.main.activity_recipe_detail.imageView
import kotlinx.android.synthetic.main.energy_value_item.view.*
import kotlinx.android.synthetic.main.energy_value_item.view.kcal_value
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Type

class RecipeDetailActivity : AppCompatActivity() {

    private val viewModel: RecipeDetailViewModel by viewModel()
    private val expandCollapseViewUtils: ExpandOrCollapse by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        showDetails()
    }

    private fun showDetails() {
        val recipe = getFromExtras()
        val isFavorite = RecipePreference.isFavorite(recipe)

        recipeTitle.text = recipe.recipe.name

        prepareFavoriteButton(isFavorite)
        showImage(recipe.recipe.imageUrl)
        showKitchen(recipe.kitchens)
        showCookTime(recipe.recipe)
        showPortionCount(recipe.recipe)
        showKсalsTable(recipe.energies)
        prepareListeners(isFavorite, recipe)
    }

    private fun prepareFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            favoriteButton.background = ContextCompat.getDrawable(this, R.drawable.custom_favorite_button)
        }
    }

    private fun showImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }

    private fun showKсalsTable(energies: List<EnergyResponse>) {
        val energyResponse = energies[0]
        addEnergyViewToContainer(energyResponse, energyTable)

        energies.subList(1, energies.size).forEach {
            addEnergyViewToContainer(it, collapsableEnergyTable)
        }

    }

    private fun addEnergyViewToContainer(energy: EnergyResponse, container: LinearLayout) {
        val view = LayoutInflater.from(this).inflate(R.layout.energy_value_item, null, false)

        view.energyTitle.text = energy.name
        view.kcal_value.text = energy.kcal
        view.squirrels_value.text = energy.squirrels
        view.grease_value.text = energy.grease
        view.carbohydrates_value.text = energy.carbohydrates
        container.addView(view)
    }

    private fun showKitchen(kitchens: List<KitchenResponse>) {
        val isKitchenVisible = kitchens.isNotEmpty()
        if (isKitchenVisible) {
            kitchen.text = kitchens[0].name
        } else {
            kitchenIcon.visibility = View.GONE
            kitchen.visibility = View.GONE
            kitchenSpace.visibility = View.GONE
            addSpaceView()
        }
    }

    private fun showCookTime(recipe: RecipeResponse) {
        val isCookTimeVisible = recipe.cookTime != null
        if (isCookTimeVisible) {
            cookTime.text = recipe.cookTime
        } else {
            cookTimeIcon.visibility = View.GONE
            cookTime.visibility = View.GONE
            cookTimeSpace.visibility = View.GONE
            addSpaceView()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showPortionCount(recipe: RecipeResponse) {
        val isPortionCountVisible = recipe.portionCount != null
        if (isPortionCountVisible) {
            portionCount.text = "${recipe.portionCount} ${getPortionSuffixWord(recipe.portionCount!!)}"
        } else {
            portionCountIcon.visibility = View.GONE
            portionCount.visibility = View.GONE
        }
    }

    private fun prepareListeners(
        isFavorite: Boolean,
        recipe: FullRecipeResponse
    ) {
        var isExpanded = false
        var isFavoriteClick = isFavorite

        collapseExpandButton.setOnClickListener {
            if (isExpanded) {
                isExpanded = false
                expandCollapseViewUtils.collapse(collapsableEnergyTable)
                collapseExpandImage.rotation = 0f
            } else {
                isExpanded = true
                expandCollapseViewUtils.expand(collapsableEnergyTable)
                collapseExpandImage.rotation = -180f
            }
        }

        collapseExpandImage.setOnClickListener {
            if (isExpanded) {
                isExpanded = false
                expandCollapseViewUtils.collapse(collapsableEnergyTable)
                collapseExpandImage.rotation = 0f
            } else {
                isExpanded = true
                expandCollapseViewUtils.expand(collapsableEnergyTable)
                collapseExpandImage.rotation = -180f
            }
        }

        buttonBack.setOnClickListener {
            finish()
        }

        favoriteButton.setOnClickListener {
            if (isFavoriteClick) {
                isFavoriteClick = false
                viewModel.removeFromFavorite(recipe)
                favoriteButton.background = ContextCompat.getDrawable(this, R.drawable.custom_favorite_border_button)
            } else {
                isFavoriteClick = true
                viewModel.addToFavorite(recipe)
                favoriteButton.background = ContextCompat.getDrawable(this, R.drawable.custom_favorite_button)
            }
        }
    }

    private fun addSpaceView() {
        val param = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            5,
            1.0f
        )
        val space = LinearLayout(this)
        space.layoutParams = param

        shortDescriptionContainer.addView(space)
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

    private fun getPortionSuffixWord(portionCount: String): String {
        val valueWithoutEmptyChars = portionCount.replace("\\s".toRegex(), "")
        return when(valueWithoutEmptyChars.toInt()) {
            1 -> "порция."
            2, 3, 4 -> "порции."
            else -> "порций."
        }
    }

    companion object {
        private const val RECIPE_ARG = "RECIPE_ARG"

        fun star(context: Context, fullRecipeResponse: FullRecipeResponse) {
            val gson = Gson()
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra(RECIPE_ARG, gson.toJson(fullRecipeResponse))

            context.startActivity(intent)
        }
    }
}
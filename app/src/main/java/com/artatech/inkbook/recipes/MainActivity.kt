package com.artatech.inkbook.recipes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.artatech.inkbook.recipes.api.response.models.CategoryKitchenTastyResponse
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.api.response.models.recipe.KitchenResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.TastyResponse
import com.artatech.inkbook.recipes.core.extentions.addFragment
import com.artatech.inkbook.recipes.core.extentions.replaceFragment
import com.artatech.inkbook.recipes.core.ui.custom.bottomnavview.CustomBottomNavigation
import com.artatech.inkbook.recipes.ui.FragmentNavigationListener
import com.artatech.inkbook.recipes.ui.category.presentation.CategoriesFragment
import com.artatech.inkbook.recipes.ui.kitchen.presentation.KitchenFragment
import com.artatech.inkbook.recipes.ui.recipesfavoritelist.presentation.RecipesFavoriteFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import java.lang.reflect.Type

class MainActivity: AppCompatActivity(), FragmentNavigationListener {

    private val kitchenFragment: KitchenFragment by inject()
    private val categoriesFragment: CategoriesFragment by inject()
    private val favoriteRecipeFragment: RecipesFavoriteFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setContentView(R.layout.activity_main)

        val model = getFromExtras()//intent.getFromExtras<CategoryKitchenTastyResponse>(MODEL_KEY)
        val tastyList = model.tastes
        val kitchenList = model.kitchens
        val categoriesList = model.categories


        setArgumentsForKitchenFragment(kitchenList, tastyList)
        prepareBottomNavigationListener()
        launchCategoriesFragment(categoriesList)
    }

    private fun setArgumentsForKitchenFragment(
        kitchenList: List<KitchenResponse>,
        tastyList: List<TastyResponse>
    ) {
        kitchenFragment.setArguments(kitchenList, tastyList)
    }

    private fun launchCategoriesFragment(categoriesList: List<CategoryModel>) {
        categoriesFragment.setArguments(categoriesList)

        addFragment(categoriesFragment, R.id.fragmentContainer)
    }

    private fun replaceFragment(fragment: Fragment) {
        replaceFragment(fragment, R.id.fragmentContainer)
    }

    private fun prepareBottomNavigationListener() {

        bottomNavMenu.setOnNavigationItemListener(object : CustomBottomNavigation.ItemClickListener {
            override fun onItemClick(item: MenuItem) {
                when(item.itemId) {
                    R.id.action_kitchen -> replaceFragment(kitchenFragment)
                    R.id.action_categories -> replaceFragment(categoriesFragment)
                    R.id.action_favorite -> replaceFragment(favoriteRecipeFragment)
                }
            }
        })
    }

    private fun getFromExtras(): CategoryKitchenTastyResponse {
        val gson = Gson()
        val jsonObject = intent.extras?.getString(MODEL_KEY)
        if (jsonObject != null) {
            val type: Type = object : TypeToken<CategoryKitchenTastyResponse>() {}.type
            return gson.fromJson(jsonObject, type)
        } else {
            throw NullPointerException("param $MODEL_KEY should not be null!")
        }
    }

    companion object {
        private const val MODEL_KEY = "MODEL_KEY"
        const val TAG_MENU_FRAGMENT = "TAG_MENU_FRAGMENT"

        fun start(context: Context, categoryKitchenTastyResponse: CategoryKitchenTastyResponse) {
            val gson = Gson()

            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            intent.putExtra(MODEL_KEY, gson.toJson(categoryKitchenTastyResponse))

            context.startActivity(intent)
        }
    }

    override fun onNavigate(fragment: Fragment) {
        addFragment(fragment, R.id.fragmentContainer, TAG_MENU_FRAGMENT, true,
            R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
    }
}
package com.artatech.inkbook.recipes

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.artatech.inkbook.recipes.ui.category.presentation.CategoriesActivity
import com.artatech.inkbook.recipes.ui.recipedetail.presentation.RecipeDetailActivity
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesActivity
import com.artatech.inkbook.recipes.ui.subcategory.presentation.CategoryIntentModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openCategoriesButton.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }

        openRecipeListButton.setOnClickListener {
            RecipesActivity.start(this, CategoryIntentModel(43, 164, 173, "Солянка"))
        }

        customBottomBar.inflateMenu(R.menu.bottom_menu)
        customBottomBar.selectedItemId = R.id.action_categories

        customBottomBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_kitchen -> {
                    customBottomBar.click(1)
                    lin.x = customBottomBar.mFirstCurveControlPoint1.x.toFloat()
                    fab_share.visibility = View.VISIBLE
                    fab_search.visibility = View.GONE
                    fab_favorite.visibility = View.GONE
                    drawAnimation(fab_share)
                    true
                }
                R.id.action_categories -> {
                    customBottomBar.click(2)
                    lin.x = customBottomBar.mFirstCurveControlPoint1.x.toFloat()
                    fab_share.visibility = View.GONE
                    fab_search.visibility = View.VISIBLE
                    fab_favorite.visibility = View.GONE
                    drawAnimation(fab_search)
                    true
                }
                R.id.action_favorite -> {
                    customBottomBar.click(3)
                    lin.x = customBottomBar.mFirstCurveControlPoint1.x.toFloat()
                    fab_share.visibility = View.GONE
                    fab_search.visibility = View.GONE
                    fab_favorite.visibility = View.VISIBLE
                    drawAnimation(fab_favorite)
                    true
                }
                else -> true
            }
        }
    }

    private fun drawAnimation(fabShare: ImageView?) {

    }
}

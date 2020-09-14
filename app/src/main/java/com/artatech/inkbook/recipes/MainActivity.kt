package com.artatech.inkbook.recipes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artatech.inkbook.recipes.ui.category.presentation.CategoriesActivity
import com.artatech.inkbook.recipes.ui.recipedetail.presentation.RecipeDetailActivity
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesActivity
import com.artatech.inkbook.recipes.ui.subcategory.presentation.CategoryIntentModel
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

        openRecipeDetailButton.setOnClickListener {

        }
    }
}

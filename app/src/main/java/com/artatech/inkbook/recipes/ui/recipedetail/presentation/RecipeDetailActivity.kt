package com.artatech.inkbook.recipes.ui.recipedetail.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.artatech.inkbook.recipes.R
import kotlinx.android.synthetic.main.activity_recipe_detail.*

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

//        val view = CircleHalfView(this)
//        container.addView(view)

        showDetails()
    }

    private fun showDetails() {
        //TODO load image
        //TODO show name
        showKitchen()
        showCookTime()
        showPortionCount()
        showKсalsTable()
    }

    private fun showKсalsTable() {

    }

    private fun showKitchen() {
        val isKitchenVisible = false
        if (isKitchenVisible) {
            kitchen.text = "Кухня"
        } else {
            kitchenIcon.visibility = View.GONE
            kitchen.visibility = View.GONE
            kitchenSpace.visibility = View.GONE
            addSpaceView()
        }
    }

    private fun showCookTime() {
        val isCookTimeVisible = false
        if (isCookTimeVisible) {
            cookTime.text = "30 минут"
        } else {
            cookTimeIcon.visibility = View.GONE
            cookTime.visibility = View.GONE
            cookTimeSpace.visibility = View.GONE
            addSpaceView()
        }
    }

    private fun showPortionCount() {
        val isPortionCountVisible = false
        if (isPortionCountVisible) {
            portionCount.text = "5 порций"
        } else {
            portionCountIcon.visibility = View.GONE
            portionCount.visibility = View.GONE
        }
    }

    private fun addSpaceView() {
        val param = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        val space = View(this)
        space.layoutParams = param

        shortDescriptionContainer.addView(space)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            context.startActivity(intent)
        }
    }
}
package com.artatech.inkbook.recipes.ui.recipedetail.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.recipe.CookStepResponse
import com.artatech.inkbook.recipes.core.extentions.loadImage
import com.artatech.inkbook.recipes.core.ui.adapter.BaseViewHolder
import com.artatech.inkbook.recipes.core.ui.adapter.ClickableAdapter
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cook_step_item.view.*

const val COOK_STEP_VIEW_TYPE = 0

class CookStepAdapter: ClickableAdapter<CookStepResponse, CookStepAdapter.CookStepViewHolder, CookStepAdapter.Listener>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookStepViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cook_step_item, parent, false)

        return CookStepViewHolder(itemView, listener as Listener)

    }

    class CookStepViewHolder(itemView: View, val listener: Listener): BaseViewHolder<CookStepResponse>(itemView) {

        override fun bind(item: CookStepResponse, position: Int) {
            itemView.image.loadImage(item.imageUrl)
            itemView.description.text = item.description

            itemView.setOnClickListener {
                listener.onCookStepClick(item)
            }
        }

    }

    interface Listener {
        fun onCookStepClick(cookStep: CookStepResponse)
    }
}
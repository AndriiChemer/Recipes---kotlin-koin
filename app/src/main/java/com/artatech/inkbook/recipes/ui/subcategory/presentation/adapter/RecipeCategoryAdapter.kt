package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.core.ui.adapter.BaseViewHolder
import com.artatech.inkbook.recipes.core.ui.adapter.ClickableAdapter
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model.RecipeCategoryItem
import kotlinx.android.synthetic.main.item_recipe_category.view.*

class RecipeCategoryAdapter: ClickableAdapter<RecipeCategoryItem, RecipeCategoryAdapter.RecipeCategoryViewHolder, RecipeCategoryAdapter.Listener>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeCategoryViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_recipe_category, parent, false)

        return RecipeCategoryViewHolder(itemView, listener as Listener)
    }

    class RecipeCategoryViewHolder(itemView: View, val listener: Listener): BaseViewHolder<RecipeCategoryItem>(itemView) {
        override fun bind(item: RecipeCategoryItem, position: Int) {
            itemView.titleRecipeCategory.text = item.name
            itemView.setOnClickListener {
                listener.onRecipeCategoryClicked(item.id, item.name)
            }
        }

    }

    interface Listener {
        fun onRecipeCategoryClicked(recipeCategoryId: Int, title: String)
    }
}
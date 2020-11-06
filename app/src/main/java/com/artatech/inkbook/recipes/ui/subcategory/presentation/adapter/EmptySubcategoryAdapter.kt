package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.core.ui.adapter.BaseViewHolder
import com.artatech.inkbook.recipes.core.ui.adapter.ClickableAdapter
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.model.EmptySubcategoryItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.item_empty_subcategory.view.*

class EmptySubcategoryAdapter: ClickableAdapter
       <EmptySubcategoryItem,
        EmptySubcategoryAdapter.SubcategoryWithoutRecCatViewHolder,
               EmptySubcategoryAdapter.Listener>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubcategoryWithoutRecCatViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_empty_subcategory, parent, false)

        return SubcategoryWithoutRecCatViewHolder(itemView, listener as Listener)
    }

    class SubcategoryWithoutRecCatViewHolder(itemView: View, val listener: Listener): BaseViewHolder<EmptySubcategoryItem>(itemView) {
        override fun bind(item: EmptySubcategoryItem, position: Int) {
            itemView.title.text = item.name

            Log.d("ANDRII","imageName: ${item.imageName}")

            if (item.getImageUrl() != null) {
                Glide.with(itemView.context)
                    .load(item.getImageUrl())
                    .thumbnail(0.5f)
                    .transform(CenterCrop(), RoundedCorners(40))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.imageBackground)
            } else {
                val sdk = android.os.Build.VERSION.SDK_INT
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    itemView.imageBackground.setBackgroundDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.subcategory_rounded_background))
                } else {
                    itemView.imageBackground.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.subcategory_rounded_background)
                }
            }

            itemView.setOnClickListener {
                listener.onSubcategoryClicked(item.categoryId, item.id, item.name)
            }
        }

    }

    interface Listener {
        fun onSubcategoryClicked(categoryId: Int, subcategoryId: Int, title: String)
    }
}
package com.artatech.inkbook.recipes.ui.recipeslist.presentation
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.RecipeResponse
import com.artatech.inkbook.recipes.core.extentions.loadRoundedImage
import com.artatech.inkbook.recipes.core.ui.adapter.BaseAdapter
import com.artatech.inkbook.recipes.core.ui.adapter.BaseViewHolder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.recipe_item.view.*

const val RECIPE_VIEW_TYPE = 0

class RecipesAdapter: BaseAdapter<FullRecipeResponse, RecipesAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)

        return RecipeViewHolder(itemView)
    }

    class RecipeViewHolder(itemView: View): BaseViewHolder<FullRecipeResponse>(itemView) {

        override fun bind(item: FullRecipeResponse, position: Int) {

            val listener = object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean) = false

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                    resource?.let {
//                        BlurBuilder.blur(itemView.context, (it as BitmapDrawable).bitmap, itemView.bottomBackground, false, false, true, true)
//                    }

                    return false
                }
            }

            itemView.favoriteIcon.setOnClickListener {
                //TODO handle favorite icon
            }

            itemView.darkTransparentBackground.setOnClickListener {
                //TODO open detail (or short detail)
            }

            itemView.imageView.loadRoundedImage(item.recipe.imageUrl, 60, listener)
            itemView.title.text = item.recipe.name
        }
    }
}
package com.artatech.inkbook.recipes.ui.recipeslist.presentation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.core.extentions.loadImageRounded
import com.artatech.inkbook.recipes.core.ui.adapter.BaseViewHolder
import com.artatech.inkbook.recipes.core.ui.adapter.ClickableAdapter
import kotlinx.android.synthetic.main.recipe_item.view.*

const val RECIPE_VIEW_TYPE = 0

class RecipesAdapter: ClickableAdapter<FullRecipeResponse, RecipesAdapter.RecipeViewHolder, RecipesAdapter.RecipeListener>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)

        val recipeListener = listener as RecipeListener

        return RecipeViewHolder(itemView, recipeListener)
    }

    class RecipeViewHolder(itemView: View, private var recipeListener: RecipeListener): BaseViewHolder<FullRecipeResponse>(itemView) {

        override fun bind(item: FullRecipeResponse, position: Int) {
            var isFavorite = recipeListener.isRecipeInFavorite(item.recipe.id)

            //TODO think about it
//            val listener = object : RequestListener<Drawable> {
//                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean) = false
//
//                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                    resource?.let {
//                        BlurBuilder.blur(itemView.context, (it as BitmapDrawable).bitmap, itemView.bottomBackground, false, false, true, true)
//                    }
//
//                    return false
//                }
//            }

            itemView.favoriteIcon.let {
                if (isFavorite) {
                    it.setImageResource(R.drawable.ic_star)
                } else {
                    it.setImageResource(R.drawable.ic_star_border)
                }
            }

            itemView.favoriteIcon.setOnClickListener {
                isFavorite = if (isFavorite) {
                    itemView.favoriteIcon.setImageResource(R.drawable.ic_star_border)
                    false
                } else {
                    itemView.favoriteIcon.setImageResource(R.drawable.ic_star)
                    true
                }
                recipeListener.onFavoriteClick(item)

            }

            itemView.darkTransparentBackground.setOnClickListener {
                recipeListener.onFavoriteClick(item)
            }

            itemView.darkTransparentBackground.setOnClickListener {
                recipeListener.onClick(item, itemView)
            }

//            itemView.imageView.loadRoundedImage(item.recipe.imageUrl, 60, listener)
            itemView.imageView.loadImageRounded(item.recipe.imageUrl)
            itemView.title.text = item.recipe.name
        }
    }

    interface RecipeListener {
        fun onClick(item: FullRecipeResponse, itemView: View)
        fun onFavoriteClick(item: FullRecipeResponse)
        fun isRecipeInFavorite(id: Int): Boolean
    }
}
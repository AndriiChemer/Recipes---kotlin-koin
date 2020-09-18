package com.artatech.inkbook.recipes.ui.recipeslist.presentation

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.renderscript.Allocation
import androidx.renderscript.RenderScript
import androidx.renderscript.ScriptIntrinsicBlur
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.core.extentions.loadImageRounded
import com.artatech.inkbook.recipes.core.ui.adapter.BaseViewHolder
import com.artatech.inkbook.recipes.core.ui.adapter.ClickableAdapter
import com.artatech.inkbook.recipes.core.ui.bluer.BlurBuilder
import com.bumptech.glide.Glide
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
                recipeListener.onFavoriteClick(position, item)

            }

            itemView.darkTransparentBackground.setOnClickListener {
                recipeListener.onFavoriteClick(position, item)
            }

            itemView.darkTransparentBackground.setOnClickListener {
                recipeListener.onClick(item, itemView)
            }

            itemView.imageView.loadImageRounded(item.recipe.imageUrl)
            itemView.title.text = item.recipe.name

            applyBlur(itemView.context, itemView.imageView, itemView.bluerView)
        }

        private fun applyBlur(context: Context, image: View, layout: View) {
            image.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    image.viewTreeObserver.removeOnPreDrawListener(this)
                    image.buildDrawingCache()

                    val bmp = image.drawingCache

                    val overlay = Bitmap.createBitmap(layout.measuredWidth, layout.measuredHeight, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(overlay)

                    canvas.translate((-layout.left).toFloat(), (-layout.top).toFloat())
                    canvas.drawBitmap(bmp, 0f, 0f, null)

                    val rs = RenderScript.create(context)

                    val overlayAlloc = Allocation.createFromBitmap(rs, overlay)
                    val blur: ScriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, overlayAlloc.element)
                    blur.setInput(overlayAlloc)
                    blur.setRadius(25f)
                    blur.forEach(overlayAlloc)
                    overlayAlloc.copyTo(overlay)

                    layout.background = BitmapDrawable(context.resources, overlay)

                    rs.destroy()

                    return true
                }
            })
        }

        private fun createRoundedRectBitmap(
            bitmap: Bitmap,
            topLeftCorner: Float, topRightCorner: Float,
            bottomRightCorner: Float, bottomLeftCorner: Float
        ): Bitmap? {
            val output = Bitmap.createBitmap(
                bitmap.width, bitmap.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(output)
            val color = Color.WHITE
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)
            val path = Path()
            val radii = floatArrayOf(
                topLeftCorner, bottomLeftCorner,
                topRightCorner, topRightCorner,
                bottomRightCorner, bottomRightCorner,
                bottomLeftCorner, bottomLeftCorner
            )
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            path.addRoundRect(rectF, radii, Path.Direction.CW)
            canvas.drawPath(path, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return output
        }

        private fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap? {
            val output = Bitmap.createBitmap(
                bitmap.width, bitmap
                    .height, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(output)
            val color = -0xbdbdbe
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)
            val roundPx = pixels.toFloat()
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return output
        }
    }


    interface RecipeListener {
        fun onClick(item: FullRecipeResponse, itemView: View)
        fun onFavoriteClick(position: Int, item: FullRecipeResponse)
        fun isRecipeInFavorite(id: Int): Boolean
    }
}
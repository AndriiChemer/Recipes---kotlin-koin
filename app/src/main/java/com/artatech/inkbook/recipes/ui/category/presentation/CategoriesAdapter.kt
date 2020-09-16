package com.artatech.inkbook.recipes.ui.category.presentation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.app.di.RECIPE_IMAGE_WITHOUT_SUFFIX
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.category_item.view.*
import java.util.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private lateinit var onCategoryClicked: (category: CategoryModel) -> Unit
    private lateinit var context: Context
    private val categories by lazy { mutableListOf<CategoryModel>() }

    fun setCategories(context:Context, categories: List<CategoryModel>) {
        this.context = context

        if (categories.isNotEmpty()) {
            this.categories.clear()
        }

        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    fun setClickListener(onCategoryClicked: (category: CategoryModel) -> Unit) {
        this.onCategoryClicked = onCategoryClicked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.category_item, parent, false)

        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, onCategoryClicked)
    }

    override fun getItemCount() = categories.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(category: CategoryModel, onCategoryClicked: (category: CategoryModel) -> Unit) {

            Log.d("ANDRII", "categoryImage: ${category.imageName}")
            loadCategoryImage(category.imageName)

            itemView.categoryTitle.text = category.name

            val subcategoriesCount = category.subcategories.size
            if (subcategoriesCount > 0) {
                itemView.subcategoriesCount.text = subcategoriesCount.toString()
            } else {
                itemView.subcategoriesCount.visibility = View.INVISIBLE
            }

            itemView.container.setOnClickListener {
                onCategoryClicked.invoke(category)
            }
        }

        private fun loadCategoryImage(imageName: String?) {
            if (imageName != null) {
                itemView.image.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                val backgroundImage = RECIPE_IMAGE_WITHOUT_SUFFIX + imageName
                Glide.with(itemView.context)
                    .load(backgroundImage)
                    .thumbnail(0.5f)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.image)
            } else {
                val color = getRandomColor()
                itemView.image.setBackgroundColor(color)
            }
        }

        private fun getRandomColor(): Int {
            val random = Random()
            return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        }
    }
}

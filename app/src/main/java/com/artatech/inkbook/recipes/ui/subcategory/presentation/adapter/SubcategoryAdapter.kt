package com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.core.ui.adapter.ClickableAdapter
import com.artatech.inkbook.recipes.core.ui.adapter.ClickableViewHolder
import com.artatech.inkbook.recipes.core.ui.adapter.ListRecyclerItem
import kotlinx.android.synthetic.main.subcategory_empty.view.*
import java.lang.Math.round
import kotlin.math.roundToInt


val RECIPE_CATEGORY_VIEW_TYPE = 2
val SUBCATEGORY_VIEW_TYPE = 1
val EMPTY_SUBCATEGORY_VIEW_TYPE = 0


val NO_ACTIVE_SECTION_POSITION = -1
val ANIMATION_DURATION = 250

class SubcategoryAdapter:
    ClickableAdapter<ListRecyclerItem, ClickableViewHolder<ListRecyclerItem, SubcategoryAdapter.SubcategoryListener>, SubcategoryAdapter.SubcategoryListener>() {


    private var positionActiveSectionHeader: Int =
        NO_ACTIVE_SECTION_POSITION
    private var activeSectionHeader: SubcategoryItem? = null
    private lateinit var recyclerView: RecyclerView

    override fun setItems(items: List<ListRecyclerItem>, listener: SubcategoryListener) {
        super.setItems(items, listener)
        setActiveSection(null, NO_ACTIVE_SECTION_POSITION)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableViewHolder<ListRecyclerItem, SubcategoryListener> {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            SUBCATEGORY_VIEW_TYPE -> {
                val view = layoutInflater.inflate(R.layout.subcategory_item, parent, false)
                SubcategoryViewHolder(view)
            }
            EMPTY_SUBCATEGORY_VIEW_TYPE -> {
                val view = layoutInflater.inflate(R.layout.subcategory_empty, parent, false)
                EmptySubcategoryHolder(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.recipe_category_item, parent, false)
                RecipeCategoryViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ClickableViewHolder<ListRecyclerItem, SubcategoryListener>, position: Int) {
        val item = itemAt(position)
        holder.listener = listener

        holder.bind(item, holder.adapterPosition)
    }









    //TODO Part of ViewHolder ===================================================================
    /**
     * Subcategory with empty list of RecipeCategory
     */
    class EmptySubcategoryHolder(itemView: View):
        ClickableViewHolder<ListRecyclerItem, SubcategoryListener>(itemView) {

        override fun bind(item: ListRecyclerItem, position: Int) {
            if (item is EmptySubcategoryItem) {

                itemView.subcategoryTitle.text = item.name
                itemView.setBackgroundColor(item.color)
                itemView.container.setOnClickListener {
                    listener?.onSubcategoryClick(item.id, item.name)
                }
            }
        }
    }

    /**
     * Subcategory with list of RecipeCategory
     */
    inner class SubcategoryViewHolder(itemView: View): ClickableViewHolder<ListRecyclerItem, SubcategoryListener>(itemView) {

        private val container = itemView.findViewById<ViewGroup>(R.id.container)
        private val arrowCircleImage = itemView.findViewById<ImageView>(R.id.arrowCircleImage)
        private val subcategoryTitle = itemView.findViewById<TextView>(R.id.subcategoryTitle)

        override fun bind(item: ListRecyclerItem, position: Int) {
            if (item is SubcategoryItem) {
                setArrow(item)

                subcategoryTitle.text = item.name
                itemView.setBackgroundColor(item.color)
                container.setOnClickListener {
                    toggleSectionItemsVisibility(item, position)
                }
            }
        }

        private fun setArrow(item: SubcategoryItem) {
            if (item.isExpanded) {
                arrowCircleImage.setImageResource(R.drawable.arrow_circle_up);
            } else {
                arrowCircleImage.setImageResource(R.drawable.arrow_circle_down);
            }
        }
    }

    /**
     * RecipeCategory
     */
    inner class RecipeCategoryViewHolder(itemView: View): ClickableViewHolder<ListRecyclerItem, SubcategoryListener>(itemView) {

        private var container = itemView.findViewById<ViewGroup>(R.id.container)
        private var subcategoryTitle = itemView.findViewById<TextView>(R.id.subcategoryTitle)

        override fun bind(item: ListRecyclerItem, position: Int) {
            if (item is RecipeCategoryItem) {
                setShadowEffect(item)

                subcategoryTitle.text = item.name
                itemView.setBackgroundColor(item.color)
                container.setOnClickListener {
                    listener?.onRecipeCategoryClick(item.subcategoryId, item.id, item.name)
                }

            }
        }

        private fun setShadowEffect(recipeCategoryItem: RecipeCategoryItem) {
            val paddingLeft = container.paddingLeft
            val paddingRight = container.paddingRight
            val paddingTop = container.paddingTop
            val paddingBottom = container.paddingBottom

            if (isLastElementInSection(recipeCategoryItem)) {
                container.setBackgroundResource(R.drawable.shadow_bottom);
//                divider.setVisibility(View.GONE);
            } else {
                container.setBackgroundResource(R.drawable.shadow_side);
//                divider.setVisibility(View.VISIBLE);
            }

            container.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }

        private fun isLastElementInSection(recipeCategoryItem: RecipeCategoryItem): Boolean {
            val nextItemFromBenefitIndex = items.indexOf(recipeCategoryItem) + 1

            if (nextItemFromBenefitIndex == items.size) {
                return true
            }

            return items[nextItemFromBenefitIndex].getViewType() == SUBCATEGORY_VIEW_TYPE
        }
    }






    interface SubcategoryListener {
        fun scrollTo(y: Int, delay: Int)
        fun onRecipeCategoryClick(subcategoryId: Int, recipeCategoryId: Int, title: String)
        fun onSubcategoryClick(subcategoryId: Int, title: String)
    }








    //TODO Part of toogle ==========================================================================
    private fun setActiveSection(subcategoryItem: SubcategoryItem?, newPosition: Int) {
        activeSectionHeader = subcategoryItem
        positionActiveSectionHeader = newPosition
    }

    fun toggleSectionItemsVisibility(subcategory: SubcategoryItem, position: Int) {
        val paddingOffset = 4
        if (!subcategory.isExpanded && activeSectionHeader == null) {
            setActiveSection(subcategory, position)
            addItemsAndNotifyHeader(positionActiveSectionHeader, subcategory.recipeCategories)
            listener?.scrollTo((recyclerView.y + getChildTopPosition(positionActiveSectionHeader) + paddingOffset).roundToInt(),
                ANIMATION_DURATION
            )

        } else if (!subcategory.isExpanded && activeSectionHeader != null) {
            removeItemsAndNotifyAndSetExpanded(activeSectionHeader!!, positionActiveSectionHeader, activeSectionHeader!!.recipeCategories)
            val offset = getOffset(position)
            setActiveSection(subcategory, getHeaderPosition(position))
            addItemsAndNotifyHeader(positionActiveSectionHeader, subcategory.recipeCategories)
            listener?.scrollTo(round(recyclerView.getY() + getChildTopPosition(positionActiveSectionHeader) + offset + paddingOffset),
                ANIMATION_DURATION
            )

        } else if (subcategory.isExpanded && activeSectionHeader!! == subcategory) {
            setActiveSection(null,
                NO_ACTIVE_SECTION_POSITION
            )
            removeItemsAndNotifyHeader(position, subcategory.recipeCategories)
            listener?.scrollTo(round(recyclerView.getY() + getChildTopPosition(0)), 0)
        }

        subcategory.isExpanded = !subcategory.isExpanded
    }

    private fun addItemsAndNotifyHeader(positionActiveSectionHeader: Int, subcategoryList: List<ListRecyclerItem>) {
        addItems(positionActiveSectionHeader + 1, subcategoryList)
        notifyItemChanged(positionActiveSectionHeader)
    }

    private fun getChildTopPosition(positionActiveSectionHeader: Int): Float {
        return recyclerView.getChildAt(positionActiveSectionHeader).y
    }

    private fun removeItemsAndNotifyAndSetExpanded(sectionHeader: SubcategoryItem, positionActiveSectionHeader: Int, subcategoryItem: List<ListRecyclerItem>) {
        sectionHeader.isExpanded = false
        removeItemsAndNotifyHeader(positionActiveSectionHeader, subcategoryItem)
    }

    private fun removeItemsAndNotifyHeader(positionActiveSectionHeader: Int, subcategoryItem: List<ListRecyclerItem>) {
        removeItems(positionActiveSectionHeader + 1, subcategoryItem)
        notifyItemChanged(positionActiveSectionHeader)
    }

    private fun getOffset(position: Int): Int {
        val diffBetweenExpandedAndClickedItem = getHeaderPosition(position) - positionActiveSectionHeader - 1
        val childItemsCount = activeSectionHeader?.recipeCategories!!.size
        val heightDiffBetweenItemAndHeader = getHeightDiffBetweenItemAndHeader();
        if (diffBetweenExpandedAndClickedItem <= 0 || heightDiffBetweenItemAndHeader <= 0) {
            return 0
        }

        return if (childItemsCount >= diffBetweenExpandedAndClickedItem) {
            heightDiffBetweenItemAndHeader * diffBetweenExpandedAndClickedItem
        } else {
            heightDiffBetweenItemAndHeader * childItemsCount
        }

    }

    private fun getHeaderPosition(position: Int): Int {
        if (position > positionActiveSectionHeader) {
            return position - activeSectionHeader!!.recipeCategories.size
        }
        return position
    }

    private fun getHeightDiffBetweenItemAndHeader(): Int {
        val height = recyclerView.getChildAt(positionActiveSectionHeader).height
        val heightWithOneItem = recyclerView.getChildAt(positionActiveSectionHeader + 1).height

        return height - heightWithOneItem
    }

}
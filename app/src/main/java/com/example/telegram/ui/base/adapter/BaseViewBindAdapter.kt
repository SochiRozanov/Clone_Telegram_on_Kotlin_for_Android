package com.example.telegram.ui.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telegram.ui.base.adapter.model.RecyclerRowPadding
import com.example.telegram.ui.base.adapter.viewholder.factory.ViewHolderFactory
import com.example.telegram.ui.base.adapter.viewholder.type.RowBindingFactory
import com.example.telegram.ui.base.adapter.viewholder.type.ViewType

class BaseViewBindAdapter private constructor() : RecyclerView.Adapter<CommonViewHolder>() {

    lateinit var dataSource: AdapterDataSource
    var interactionListener: AdapterInteractionListener? = null
    var spanCount = 1
    var paddings = RecyclerRowPadding()
    var screenWidth: Int? = null
    private var defaultPadding: RecyclerRowPadding? = null
    private val viewTypes: MutableList<ViewType> = mutableListOf()

    companion object {
        fun newInstance(): BaseViewBindAdapter {
            return BaseViewBindAdapter()
        }
    }

    interface AdapterInteractionListener {
        fun onItemClick(position: Int, view: View)
    }

    interface AdapterDataSource {
        fun getItemCount(): Int
        fun getItemByPosition(position: Int): Any
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val viewBinding = RowBindingFactory.create(parent, viewType)

        screenWidth?.let {
            val layoutParams = viewBinding.root.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.width = (it / 2) - layoutParams.leftMargin - layoutParams.rightMargin
            viewBinding.root.layoutParams = layoutParams
        }

        return ViewHolderFactory.create(viewType, viewBinding)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        if (defaultPadding == null) {
            defaultPadding = holder.getPadding()
        }

        val model = dataSource.getItemByPosition(position)
        holder.initializeView(model)
        holder.onViewClick = {
            interactionListener?.onItemClick(position, it)
        }
        setBottomPadding(position, holder)
        setTopPadding(position, holder)
        setStartPadding(position, holder)
        setEndPadding(position, holder)
    }

    override fun getItemCount(): Int = dataSource.getItemCount()

    override fun getItemViewType(position: Int): Int {
        return getRowViewType(position).id
    }

    fun remove(viewType: ViewType) {
        viewTypes.remove(viewType)
    }

    fun clearAndRegister(viewType: ViewType) {
        viewTypes.clear()
        viewTypes.add(viewType)
    }

    fun register(viewType: ViewType) {
        viewTypes.add(viewType)
    }

    fun unregister(viewType: ViewType) {
        viewTypes.remove(viewType)
    }

    private fun getRowViewType(position: Int): ViewType {
        val model = dataSource.getItemByPosition(position)
        return getViewType(model.javaClass)
    }

    private fun getViewType(type: Class<out Any>): ViewType {
        val cellInfo = viewTypes.firstOrNull { it.model == type }
        if (cellInfo != null) {
            return cellInfo
        } else {
            throw NoSuchElementException()
        }
    }

    private fun setBottomPadding(position: Int, holder: CommonViewHolder) {
        if (position >= getLastItemsCount() && paddings.bottom != null) {
            holder.setPadding(bottom = paddings.bottom)
        } else {
            holder.setPadding(bottom = defaultPadding?.bottom)
        }
    }

    private fun setTopPadding(position: Int, holder: CommonViewHolder) {
        if (position <= spanCount - 1 && paddings.top != null) {
            holder.setPadding(top = paddings.top)
        } else {
            holder.setPadding(top = defaultPadding?.top)
        }
    }

    private fun setStartPadding(position: Int, holder: CommonViewHolder) {
        if (position <= spanCount - 1 && paddings.start != null) {
            holder.setPadding(paddings.start)
        } else {
            holder.setPadding(defaultPadding?.start)
        }
    }

    private fun setEndPadding(position: Int, holder: CommonViewHolder) {
        if (position >= getLastItemsCount() && paddings.end != null) {
            holder.setPadding(end = paddings.end)
        } else {
            holder.setPadding(end = defaultPadding?.end)
        }
    }

    private fun getLastItemsCount(): Int {
        val isRowFull = itemCount % spanCount == 0
        return if (!isRowFull) {
            itemCount - itemCount % spanCount
        } else {
            itemCount - spanCount
        }
    }
}
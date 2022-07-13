package com.example.telegram.ui.screens.stories.adapter

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.telegram.ui.base.adapter.MainRowView
import com.example.telegram.ui.screens.main_list.adapter.MainListAdapter
// папка с адаптерами и вьюхолдерами, если интересно гугли про recycleview=))
class StoriesRecyclerAdapter: RecyclerView.Adapter<MainListAdapter.RowViewHolder>() {

    var screenWidth:Int? = null

    interface InteractionListener {
        fun onBindViewHolder(holder: MainListAdapter.RowViewHolder, position: Int)
        fun getItemCount(): Int
    }

    lateinit var interactionListener: InteractionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListAdapter.RowViewHolder {
        val view =
            MainRowView(parent.context)

        screenWidth?.let {
            val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.width = it
            view.layoutParams = layoutParams
        }

        return MainListAdapter.RowViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: MainListAdapter.RowViewHolder, position: Int) {
        interactionListener.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return interactionListener.getItemCount()
    }

    class RowViewHolder(val mainRow: View) :
        RecyclerView.ViewHolder(mainRow)

}
package com.example.telegram.ui.base.adapter

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telegram.databinding.MainRowLayoutBinding
import com.example.telegram.extension.getPixelFromDp
import com.example.telegram.ui.base.adapter.BaseViewBindAdapter
import com.example.telegram.ui.base.adapter.model.RecyclerRowPadding
import com.example.telegram.ui.base.adapter.viewholder.type.MainRowListType
import com.example.telegram.ui.base.adapter.viewholder.type.ViewType

class MainRowView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        MainRowLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    private var listType = MainRowListType.RECYCLER_VIEW

    private var listener: ClickListener? = null

    fun setRecyclerView(viewType: ViewType, orientation: Int = LinearLayoutManager.HORIZONTAL) {
        binding.recyclerView.visibility = View.VISIBLE
        binding.recyclerView.bindAdapter.register(viewType)
        binding.recyclerView.orientation = orientation
        binding.recyclerView.setPaddings(RecyclerRowPadding(start = 18, end = 18))
    }

    fun <T : Any> setLiveData(
        lifecycleOwner: LifecycleOwner,
        liveData: MutableLiveData<Collection<T>>,
        withAnimation: Boolean = true
    ) {
        liveData.observe(lifecycleOwner) {
            binding.root.visibility = when (it.isEmpty()) {
                true -> View.GONE
                else -> View.VISIBLE
            }
            when (listType) {
                MainRowListType.RECYCLER_VIEW -> {
                    binding.recyclerView.bindAdapter.notifyDataSetChanged()
                    if (withAnimation) {
                        binding.recyclerView.scheduleLayoutAnimation()
                    }
                }

                MainRowListType.PAGER_VIEW -> {
                    // TODO("you can use it for viewpager")
                }
            }
        }
        binding.recyclerView.bindAdapter.dataSource =
            object : BaseViewBindAdapter.AdapterDataSource {
                override fun getItemCount(): Int {
                    return liveData.value?.size ?: 0
                }

                override fun getItemByPosition(position: Int): T {
                    return liveData.value!!.toList()[position]
                }
            }
    }

    fun <T: Any> setData(data: List<T>?, withAnimation: Boolean = true) {
        binding.root.visibility = when (data?.isEmpty()) {
            true -> View.GONE
            else -> View.VISIBLE
        }

        when (listType) {
            MainRowListType.RECYCLER_VIEW -> {
                binding.recyclerView.bindAdapter.notifyDataSetChanged()
                if (withAnimation) {
                    binding.recyclerView.scheduleLayoutAnimation()
                }
            }

            MainRowListType.PAGER_VIEW -> {
                // TODO("you can use it for viewpager")
            }
        }

        binding.recyclerView.bindAdapter.dataSource =
            object : BaseViewBindAdapter.AdapterDataSource {
                override fun getItemCount(): Int {
                    return data?.size ?: 0
                }

                override fun getItemByPosition(position: Int): T {
                    return data?.get(position)!!
                }
            }
    }


    fun setOnItemClickListeners(onClick: (Pair<Int, View>) -> Unit) {
        when (listType) {
            MainRowListType.RECYCLER_VIEW -> binding.recyclerView.bindAdapter.interactionListener =
                object : BaseViewBindAdapter.AdapterInteractionListener {
                    override fun onItemClick(position: Int, view: View) {
                        onClick(Pair(position, view))
                    }
                }
            MainRowListType.PAGER_VIEW -> {
                // TODO("you can use it for viewpager")
            }
        }
    }

    interface ClickListener {
        fun onClick(position: Int, view: View)
    }
}
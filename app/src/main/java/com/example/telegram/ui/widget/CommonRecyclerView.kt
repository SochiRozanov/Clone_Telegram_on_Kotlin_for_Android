package com.example.telegram.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.telegram.ui.base.adapter.BaseViewBindAdapter
import com.example.telegram.ui.base.adapter.model.RecyclerRowPadding

//ничего интересного, назначаем отступы и т.п. по сути настраиваем xml
open class CommonRecyclerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RecyclerView(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    val bindAdapter = BaseViewBindAdapter.newInstance()

    var spanCount = 1
        set(value) {
            field = value
            bindAdapter.spanCount = value
            setupLayoutManager()
        }

    var orientation = VERTICAL
        set(value) {
            field = value
            setupLayoutManager()
        }

    init {
        this.adapter = bindAdapter
        this.overScrollMode = OVER_SCROLL_NEVER
    }

    fun setPaddings(recyclerRowPadding: RecyclerRowPadding) {
        bindAdapter.paddings = recyclerRowPadding
    }

    private fun setupLayoutManager() {
        val layoutManager =
            when (spanCount) {
                1 -> {
                    LinearLayoutManager(context, orientation, false)
                }
                else -> {
                    GridLayoutManager(context, spanCount, orientation, false)
                }
            }
        this.layoutManager = layoutManager
    }
}
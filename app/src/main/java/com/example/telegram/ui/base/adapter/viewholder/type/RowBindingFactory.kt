package com.example.telegram.ui.base.adapter.viewholder.type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.telegram.databinding.MainListItemBinding
import com.example.telegram.databinding.StoriesItemBinding

class RowBindingFactory {
    companion object {

        fun create(parent: ViewGroup, viewTypeId: Int): ViewBinding {

            return when (ViewType.fromValue(viewTypeId)) {
                ViewType.ROW_MAIN_LIST -> MainListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewType.ROW_STORIES -> StoriesItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
        }
    }
}
package com.example.telegram.ui.base.adapter.viewholder.factory

import androidx.viewbinding.ViewBinding
import com.example.telegram.databinding.MainListItemBinding
import com.example.telegram.databinding.StoriesItemBinding
import com.example.telegram.ui.base.adapter.CommonViewHolder
import com.example.telegram.ui.base.adapter.viewholder.type.ViewType
import com.example.telegram.ui.screens.main_list.adapter.MainListViewHolder
import com.example.telegram.ui.screens.stories.adapter.StoriesViewHolder

class ViewHolderFactory {
    companion object {
        fun create(viewTypeId: Int, viewBinding: ViewBinding): CommonViewHolder {
            return when (ViewType.fromValue(viewTypeId)) {
                ViewType.ROW_MAIN_LIST -> MainListViewHolder(viewBinding as MainListItemBinding)
                ViewType.ROW_STORIES -> StoriesViewHolder(viewBinding as StoriesItemBinding)
            }
        }
    }
}
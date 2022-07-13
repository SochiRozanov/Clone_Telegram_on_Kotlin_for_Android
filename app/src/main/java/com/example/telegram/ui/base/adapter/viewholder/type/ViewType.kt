package com.example.telegram.ui.base.adapter.viewholder.type

import androidx.annotation.LayoutRes
import com.example.telegram.R
import com.example.telegram.models.CommonModel
import com.example.telegram.ui.screens.stories.model.StoriesModel

enum class ViewType(@LayoutRes val id: Int, val model: Class<out Any>) {
    ROW_MAIN_LIST(R.layout.main_list_item, CommonModel::class.java),
    ROW_STORIES(R.layout.stories_item, StoriesModel::class.java);

    companion object {
        fun fromValue(id: Int): ViewType {
            for (type in values()) {
                if (type.id == id) {
                    return type
                }
            }

            throw NoSuchElementException()
        }
    }
}
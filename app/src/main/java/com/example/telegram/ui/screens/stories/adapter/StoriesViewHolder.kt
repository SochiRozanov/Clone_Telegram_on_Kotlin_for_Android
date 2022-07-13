package com.example.telegram.ui.screens.stories.adapter

import com.example.telegram.databinding.StoriesItemBinding
import com.example.telegram.ui.base.adapter.CommonViewHolder
import com.example.telegram.extension.setImage
import com.example.telegram.ui.screens.stories.model.StoriesModel
// папка с адаптерами и вьюхолдерами, если интересно гугли про recycleview=))

class StoriesViewHolder(private val binding: StoriesItemBinding) : CommonViewHolder(binding.root) {

    override fun initializeView(model: Any) {
        if (model is StoriesModel) {
            binding.imageView1.setImage(model.previewImage)
            binding.root.setOnClickListener {
                onViewClick(it)
            }
        }
    }
}
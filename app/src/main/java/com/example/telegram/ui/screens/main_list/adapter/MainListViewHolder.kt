package com.example.telegram.ui.screens.main_list.adapter

import com.example.telegram.databinding.MainListItemBinding
import com.example.telegram.extension.setImage
import com.example.telegram.models.CommonModel
import com.example.telegram.ui.base.adapter.CommonViewHolder

class MainListViewHolder(private val binding: MainListItemBinding) : CommonViewHolder(binding.root) {

    override fun initializeView(model: Any) {
        if (model is CommonModel) {
            binding.mainListItemName.text = model.fullname
            binding.mainListLastMessage.text = model.lastMessage
            binding.mainListItemPhoto.setImage(model.photoUrl)
            binding.root.setOnClickListener {
                onViewClick(it)
            }
        }
    }
}
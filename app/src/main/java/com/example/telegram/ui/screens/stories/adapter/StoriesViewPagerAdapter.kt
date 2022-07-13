package com.example.telegram.ui.screens.stories.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.telegram.ui.screens.stories.StoriesItemFragment
import com.example.telegram.ui.screens.stories.model.StoriesModel
// папка с адаптерами и вьюхолдерами, если интересно гугли про recycleview=))

class StoriesViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var stories = mutableListOf<StoriesModel>()

    override fun getItemCount() = stories.size

    override fun createFragment(position: Int) : StoriesItemFragment {
        return StoriesItemFragment.create(stories[position])
    }

    fun addItems(list: List<StoriesModel>) {
        stories.clear()
        stories.addAll(list)
        notifyDataSetChanged()
    }
}
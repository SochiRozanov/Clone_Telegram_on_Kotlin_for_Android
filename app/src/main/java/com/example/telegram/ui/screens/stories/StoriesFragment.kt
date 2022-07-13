package com.example.telegram.ui.screens.stories

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.telegram.R
import com.example.telegram.databinding.FragmentStoriesBinding
import com.example.telegram.extension.replace
import com.example.telegram.ui.screens.main_list.MainListFragment
import com.example.telegram.ui.screens.stories.adapter.StoriesViewPagerAdapter
import com.example.telegram.ui.stories.adapter.StoriesViewPagerTransformer
import com.example.telegram.ui.screens.stories.model.StoriesModel

const val EXTRA_POSITION = "EXTRA_POSITION"
const val EXTRA_ITEMS = "EXTRA_ITEMS"
//Здесь функции с говорящими названиями, разберётесь)
class StoriesFragment : DialogFragment() {

    private lateinit var binding: FragmentStoriesBinding
    private var itemPosition: Int? = null
    private var items: List<StoriesModel>? = null
    private lateinit var adapter: StoriesViewPagerAdapter

    companion object {
        fun create(itemPosition: Int, items: List<StoriesModel>) =
            StoriesFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_POSITION, itemPosition)
                    putParcelableArray(EXTRA_ITEMS, items.toTypedArray())
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemPosition = it.getInt(EXTRA_POSITION)
            items = it.getParcelableArray(EXTRA_ITEMS)?.toList() as? List<StoriesModel>

        }
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
            //TODO("replace deprecated stuff")
            dialog.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoriesBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupData()
    }

    private fun setupViewPager() {
        adapter = StoriesViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.setPageTransformer(StoriesViewPagerTransformer())
        binding.viewPager.offscreenPageLimit = 1
    }

    fun showNextStory() {
        if (binding.viewPager.currentItem >= (items?.size)?.minus(1) ?: 0)
            replace(MainListFragment())
        binding.viewPager.currentItem++
    }

    fun showPreviousStory() {
        if (binding.viewPager.currentItem == 0) {
            replace(MainListFragment())
        }
        binding.viewPager.currentItem--
    }

    override fun dismiss() {
        replace(MainListFragment())
    }
    private fun setupData() {
        adapter.addItems(items ?: return)
        binding.viewPager.setCurrentItem(itemPosition ?: 0, false)
    }
}

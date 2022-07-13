package com.example.telegram.ui.screens.main_list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telegram.R
import com.example.telegram.database.*
import com.example.telegram.models.CommonModel
import com.example.telegram.ui.base.adapter.MainRowView
import com.example.telegram.ui.base.adapter.viewholder.type.MainRowType
import com.example.telegram.extension.replace
import com.example.telegram.ui.screens.groups.GroupChatFragment
import com.example.telegram.ui.screens.main_list.adapter.MainListAdapter
import com.example.telegram.ui.screens.single_chat.SingleChatFragment
import com.example.telegram.ui.screens.stories.StoriesFragment
import com.example.telegram.ui.screens.stories.model.MediaType
import com.example.telegram.ui.screens.stories.model.StoriesModel
import com.example.telegram.utilits.*
import kotlinx.android.synthetic.main.fragment_main_list.*

/* Главный фрагмент, содержит все чаты, группы и каналы с которыми взаимодействует пользователь*/

class MainListFragment : Fragment(R.layout.fragment_main_list) {

    private val viewModel: MainListViewModel by lazy {
        MainListViewModel()
    }

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Telegram"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initStoriesRecyclerView()
        viewModel.mainListSetup()
    }

    private fun initStoriesRecyclerView() {
        storiesRecyclerView.adapter = MainListAdapter().apply {

            this.screenWidth = requireActivity().windowManager.defaultDisplay.width

            this.interactionListener = object : MainListAdapter.InteractionListener {
                override fun onBindViewHolder(holder: MainListAdapter.RowViewHolder, position: Int) {

                    val mainRowView = holder.mainRow as MainRowView
                    val rowType = MainRowType.fromValue("stories")

                    mainRowView.setRecyclerView(rowType.viewType ?: return)

                    when ("stories") {
                        MainRowType.STORIES.value -> {
                            mainRowView.setData(items)
                            mainRowView.setOnItemClickListeners {
                                replace(StoriesFragment.create(position, items))
                            }
                        }
                    }
                }
                override fun getItemCount() = 1
            }
        }
    }

    private fun initRecyclerView() {
        main_list_recycle_view.adapter = MainListAdapter().apply {

            this.screenWidth = requireActivity().windowManager.defaultDisplay.width

            this.interactionListener = object : MainListAdapter.InteractionListener {
                override fun onBindViewHolder(holder: MainListAdapter.RowViewHolder, position: Int) {

                    val mainRowView = holder.mainRow as MainRowView
                    val rowType = MainRowType.fromValue("main_chats")

                    mainRowView.setRecyclerView(rowType.viewType ?: return, LinearLayoutManager.VERTICAL)

                    when ("main_chats") {
                        MainRowType.MAIN_LIST.value -> {
                            mainRowView.setLiveData(viewLifecycleOwner, viewModel.model)
                            mainRowView.setOnItemClickListeners {
                                val item = viewModel.model.value?.toList()?.get(position)
                                when (item?.type) {
                                    TYPE_CHAT -> replace(SingleChatFragment(item))
                                    TYPE_GROUP -> replace(GroupChatFragment(item))
                                }
                            }
                        }
                    }
                }
                override fun getItemCount() = 1
            }
        }
    }
}

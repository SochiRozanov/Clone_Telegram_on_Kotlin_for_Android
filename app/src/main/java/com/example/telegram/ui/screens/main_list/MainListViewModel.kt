package com.example.telegram.ui.screens.main_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.telegram.database.CURRENT_UID
import com.example.telegram.database.NODE_GROUPS
import com.example.telegram.database.NODE_MAIN_LIST
import com.example.telegram.database.NODE_MESSAGES
import com.example.telegram.database.NODE_USERS
import com.example.telegram.database.REF_DATABASE_ROOT
import com.example.telegram.database.getCommonModel
import com.example.telegram.models.CommonModel
import com.example.telegram.ui.screens.stories.model.MediaType
import com.example.telegram.ui.screens.stories.model.StoriesModel
import com.example.telegram.utilits.AppValueEventListener
import com.example.telegram.utilits.TYPE_CHAT
import com.example.telegram.utilits.TYPE_GROUP

val imagePath = "https://www.meme-arsenal.com/memes/9cf074bd64431d00001dc41a25fa55ae.jpg"
val previewPath = "https://www.meme-arsenal.com/memes/9cf074bd64431d00001dc41a25fa55ae.jpg"

val items: List<StoriesModel> = listOf(
    StoriesModel(
        title = "Title 1",
        description = "Description 1",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 2",
        description = "Description 2",
        imagePath = imagePath,
        previewImage = previewPath,
        video = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        MediaType.VIDEO
    ),
    StoriesModel(
        title = "Title 3",
        description = "Description 3",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 4",
        description = "Description 4",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 4",
        description = "Description 4",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 4",
        description = "Description 4",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 4",
        description = "Description 4",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 4",
        description = "Description 4",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 4",
        description = "Description 4",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 4",
        description = "Description 4",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 4",
        description = "Description 4",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    ),
    StoriesModel(
        title = "Title 4",
        description = "Description 4",
        imagePath = imagePath,
        previewImage = previewPath,
        video = null,
        MediaType.IMAGE
    )
)

class MainListViewModel : ViewModel() {

    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()

    private val list = mutableSetOf<CommonModel>()
    val model = MutableLiveData<Collection<CommonModel>>()

    fun mainListSetup() {
        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            mListItems = dataSnapshot.children.map { it.getCommonModel() }

            mListItems.forEach { model ->
                when (model.type) {
                    TYPE_CHAT -> showChat(model)
                    TYPE_GROUP -> showGroup(model)
                }
            }
        })
    }

    private fun showGroup(model: CommonModel) {
        REF_DATABASE_ROOT.child(NODE_GROUPS).child(model.id)
            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->
                val newModel = dataSnapshot1.getCommonModel()

                // 3 запрос
                REF_DATABASE_ROOT.child(NODE_GROUPS).child(model.id).child(NODE_MESSAGES)
                    .limitToLast(1)
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->
                        val tempList = dataSnapshot2.children.map { it.getCommonModel() }

                        if (tempList.isEmpty()) {
                            newModel.lastMessage = "Чат очищен"
                        } else {
                            newModel.lastMessage = tempList[0].text
                        }
                        newModel.type = TYPE_GROUP
                        list.add(newModel)
                        this.model.postValue(list.toList())
                    })
            })
    }

    private fun showChat(model: CommonModel) {
        mRefUsers.child(model.id)
            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->
                val newModel = dataSnapshot1.getCommonModel()

                mRefMessages.child(model.id).limitToLast(1)
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->
                        val tempList = dataSnapshot2.children.map { it.getCommonModel() }

                        if (tempList.isEmpty()) {
                            newModel.lastMessage = "Чат очищен"
                        } else {
                            newModel.lastMessage = tempList[0].text
                        }


                        if (newModel.fullname.isEmpty()) {
                            newModel.fullname = newModel.phone
                        }

                        newModel.type = TYPE_CHAT
                        list.add(newModel)
                        this.model.postValue(list.toList())
                    })
            })
    }
}
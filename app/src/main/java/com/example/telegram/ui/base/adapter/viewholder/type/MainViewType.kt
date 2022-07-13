package com.example.telegram.ui.base.adapter.viewholder.type

enum class MainRowType(
    val value: String,
    val title: String,
    val viewType: ViewType?
) {
    UNDEFINED("", "", null),
    MAIN_LIST("main_chats", "Чаты", ViewType.ROW_MAIN_LIST),
    STORIES("stories", "Истории", ViewType.ROW_STORIES);

    companion object {
        fun fromValue(value:String): MainRowType {
            for (type in values()) {
                if (type.value == value) {
                    return type
                }
            }
            return UNDEFINED
        }
    }
}
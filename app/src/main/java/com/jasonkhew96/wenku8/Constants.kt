package com.jasonkhew96.wenku8

object Constants {
    val sortMap: LinkedHashMap<String, String> = linkedMapOf(
        "allvisit" to "总排行榜",
        "monthvisit" to "月排行榜",
        "weekvisit" to "周排行榜",
        "dayvisit" to "日排行榜",

        "allvote" to "总推荐榜",
        "monthvote" to "月推荐榜",
        "weekvote" to "周推荐榜",
        "dayvote" to "日推荐榜",

        "postdate" to "最新入库",
        "goodnum" to "总收藏榜",
        "lastupdate" to "最近更新",
        "size" to "字数排行",
        "anime" to "动画化",
    )

    const val ITEMS_PER_PAGE = 10

    const val BASE_URL = "https://www.wenku8.net"

    const val WENKU8_DATABASE = "wenku8_database"
    const val NOVEL_TABLE = "novel_table"
    const val TOP_LIST_TABLE = "top_list_table"
    const val NOVEL_DETAILS_TABLE = "novel_details_table"
    const val NOVEL_VOLUME_TABLE = "novel_volume_table"
    const val NOVEL_CHAPTER_TABLE = "novel_chapter_table"
    const val NOVEL_CHAPTER_CONTENT_TABLE = "novel_chapter_content_table"
    const val NOVEL_CHAPTER_CONTENT_IMAGE_TABLE = "novel_chapter_content_image_table"
}


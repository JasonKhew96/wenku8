package com.jasonkhew96.wenku8.data.remote

import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Wenku8Service {

//    enum class ClassType {
//        ALL,
//        DENGEKI_BUNKO,
//        FUJIMI_FANTASIA_BUNKO,
//        KADOKAWA_BUNKO,
//        MF_BUNKO_J,
//        FAMITSU_BUNKO,
//        GA_BUNKO,
//        HJ_BUNKO,
//        ICHIJINSHA,
//        SHUEISHA,
//        SHOKAKUKAN,
//        KODANSHA,
//        SHOUJO_BUNKO,
//        OTHERS_BUNKO,
//        GAME_SCRIPT,
//    }
//
//    enum class ViewType {
//        UPDATE,
//        HOT,
//        FINISH,
//        ANIME,
//    }
//
//    enum class SearchType(val value: String) {
//        ARTICLE_NAME("articlename"),
//        AUTHOR("author"),
//    }

    @GET("modules/article/articlelist.php")
    suspend fun getArticleList(
        @Query("page") page: Int,
        @Query("class") classType: String,
        @Query("initial") initial: String,
        @Query("fullflag") fullFlag: Int
    ): ResponseBody

    @GET("modules/article/toplist.php")
    suspend fun getTopList(
        @Query("page") page: Int,
        @Query("sort") sort: String,
    ): ResponseBody

    @GET("modules/article/tags.php")
    suspend fun getTags(
        @Query("page") page: Int, @Query("t") tag: String, @Query("v") viewType: String
    ): ResponseBody

    @GET("book/{aid}.htm")
    suspend fun getBook(
        @Path("aid") aid: Int
    ): ResponseBody

    @GET("novel/{id}/{aid}/index.htm")
    suspend fun getNovelVolume(
        @Path("id") id: Int, @Path("aid") aid: Int
    ): ResponseBody

    @GET("novel/{id}/{aid}/{cid}.htm")
    suspend fun getNovelChapterContent(
        @Path("id") id: Int, @Path("aid") aid: Int, @Path("cid") cid: Int
    ): ResponseBody

    @GET("modules/article/search.php")
    suspend fun getSearchResult(
        @Query("searchtype") searchType: String,
        @Query("searchkey") searchKey: String,
        @Query("page") page: Int
    ): ResponseBody

    @FormUrlEncoded
    @POST("login.php?do=submit")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("usecookie") useCookie: Int = 315360000, // 0, 86400, 2592000, 315360000
        @Field("action") action: String = "login",
        @Field("submit") submit: Boolean = true,
    ): ResponseBody

    @GET("logout.php")
    suspend fun logout(): ResponseBody

    @GET("userdetail.php")
    suspend fun getUserDetail(): ResponseBody
}

package com.jasonkhew96.wenku8.utils

import android.util.Log
import com.jasonkhew96.wenku8.model.Novel
import com.jasonkhew96.wenku8.model.NovelChapter
import com.jasonkhew96.wenku8.model.NovelChapterContent
import com.jasonkhew96.wenku8.model.NovelDetails
import com.jasonkhew96.wenku8.model.NovelVolume
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import java.nio.charset.Charset

class Parser {
    companion object {
        fun parseTopList(response: ResponseBody): List<Novel> {
            val regexLinkAid = Regex("""^https?://www\.wenku8\.net/book/(\d+)\.htm${'$'}""")
            val regexAuthorClassType = Regex("""^作者:(.*?)/分类:(.*?)$""")
            val regexLastUpdateCharCount = Regex("""^更新:(.*?)/字数:(.*?)/(.*?)$""")
            val regexTags = Regex("""^Tags:(.*?)$""")
            val regexDescription = Regex("""^简介:(.*?)$""", RegexOption.MULTILINE)

            val jsoup = Jsoup.parse(String(response.bytes(), Charset.forName("GBK")))
            val topList = mutableListOf<Novel>()
            jsoup.select("#content > table > tbody > tr > td > div > div:nth-child(2)")
                .forEach { entry ->
                    val aid = regexLinkAid.find(
                        entry.select("b > a").attr("href")
                    )!!.groupValues[1].toInt()
                    val title = entry?.child(0)?.text()
                    val match01 = regexAuthorClassType.find(
                        entry?.child(1)?.text().toString()
                    )
                    val author = match01?.groupValues?.get(1)
                    val classType = match01?.groupValues?.get(2)
                    val match02 = regexLastUpdateCharCount.find(
                        entry?.child(2)?.text().toString()
                    )
                    val lastUpdate = match02?.groupValues?.get(1)
                    val charCount = match02?.groupValues?.get(2)
                    val status = match02?.groupValues?.get(3)
                    val match03 = regexTags.find(
                        entry?.child(3)?.text().toString()
                    )
                    val tags = match03?.groupValues?.get(1)
                    val match04 = regexDescription.find(
                        entry?.child(4)?.text().toString()
                    )
                    val description = match04?.groupValues?.get(1)

                    val novel = Novel(
                        aid = aid,
                        title = title ?: "",
                        author = author ?: "",
                        classType = classType ?: "",
                        lastUpdate = lastUpdate ?: "",
                        charCount = charCount ?: "",
                        status = status ?: "",
                        tags = tags ?: "",
                        description = description ?: "",
                    )
                    topList.add(novel)
                }
            return topList
        }

        fun parseNovelDetails(aid: Int, response: ResponseBody): NovelDetails {
            val jsoup = Jsoup.parse(String(response.bytes(), Charset.forName("GBK")))
            val title =
                jsoup.select("#content > div:nth-child(1) > table:nth-child(1) > tbody > tr:nth-child(1) > td > table > tbody > tr > td:nth-child(1) > span > b")
                    .text()
            val test01 =
                jsoup.selectFirst("#content > div:nth-child(1) > table:nth-child(1) > tbody > tr:nth-child(2)")
            val classType = test01?.child(0)?.text()?.split("：")?.get(1)
            val author = test01?.child(1)?.text()?.split("：")?.get(1)
            val status = test01?.child(2)?.text()?.split("：")?.get(1)
            val lastUpdate = test01?.child(3)?.text()?.split("：")?.get(1)
            val charCount = test01?.child(4)?.text()?.split("：")?.get(1)
            val tags =
                jsoup.select("#content > div:nth-child(1) > table:nth-child(4) > tbody > tr > td:nth-child(2) > span:nth-child(1) > b")
                    .text().split("：")[1]
            val test02 =
                jsoup.selectFirst("#content > div:nth-child(1) > table:nth-child(4) > tbody > tr > td:nth-child(2) > span:nth-child(3) > b")
                    ?.text()
            val re = Regex("""^作品热度：(.)级，当前热度上升指数为：(.)级$""")
            val hotStatus = test02?.let { re.find(it)?.groupValues?.get(1) }
            val hotIndexStatus = test02?.let { re.find(it)?.groupValues?.get(2) }
            val lastUpdatedChapter =
                jsoup.selectFirst("#content > div:nth-child(1) > table:nth-child(4) > tbody > tr > td:nth-child(2) > span:nth-child(8) > a")
                    ?.text()
            val introduction =
                jsoup.selectFirst("#content > div:nth-child(1) > table:nth-child(4) > tbody > tr > td:nth-child(2) > span:nth-child(13)")
                    ?.text()
            val animeStatus =
                jsoup.selectFirst("#content > div:nth-child(1) > table:nth-child(4) > tbody > tr > td:nth-child(1) > span > b")
                    ?.text()
            return NovelDetails(
                aid = aid,
                title = title,
                classType = classType ?: "",
                author = author ?: "",
                status = status ?: "",
                lastUpdate = lastUpdate ?: "",
                charCount = charCount ?: "",
                tags = tags,
                hotStatus = hotStatus ?: "",
                hotIndexStatus = hotIndexStatus ?: "",
                lastUpdatedChapter = lastUpdatedChapter ?: "",
                introduction = introduction ?: "",
                animeStatus = animeStatus ?: ""
            )
        }

        fun parseNovelVolumes(aid: Int, response: ResponseBody): List<NovelVolume> {
            val volumes = mutableListOf<NovelVolume>()
            val jsoup = Jsoup.parse(String(response.bytes(), Charset.forName("GBK")))

            var volumeTitle = ""
            var chapters: MutableList<NovelChapter> = mutableListOf()

            jsoup.select("table > tbody > tr").forEach tr@{ tr ->
                tr.select("td").forEach td@{ td ->
                    when (td.className()) {
                        "vcss" -> {
                            // volume
                            if (volumeTitle == "") {
                                volumeTitle = td.text()
                            } else if (volumeTitle != td.text()) {
                                volumes.add(
                                    NovelVolume(
                                        id = 0, aid = aid, title = volumeTitle, chapters = chapters
                                    )
                                )
                                chapters = mutableListOf()
                                volumeTitle = td.text()
                            }
                        }
                        "ccss" -> {
                            // chapter
                            if (td.text() == "") {
                                return@td
                            }
                            val a = td.selectFirst("a")
                            val href = a?.attr("href")
                            val re = Regex("""(\d+).htm""")
                            val cid = href?.let { re.find(it)?.groupValues?.get(1)?.toInt() } ?: 0
                            chapters.add(NovelChapter(aid, cid, td.text()))
                        }
                    }
                }
            }
            if (chapters.isNotEmpty()) {
                volumes.add(NovelVolume(aid = aid, title = volumeTitle, chapters = chapters))
            }
            return volumes
        }

        fun parseNovelChapterContent(
            aid: Int, cid: Int, response: ResponseBody
        ): NovelChapterContent {
            val jsoup = Jsoup.parse(String(response.bytes(), Charset.forName("GBK")))
            val title = jsoup.selectFirst("#title")?.text()
            val content =
                jsoup.selectFirst("#content")?.html()?.replace("<ul id=\"contentdp\">", "")
                    ?.replace("</ul>", "")?.replace("<br>", "\n")?.replace("&nbsp;", "")

            val images = mutableListOf<String>()
            jsoup.select(".imagecontent").forEach {
                images.add(it.attr("src").replace("http://", "https://"))
            }

            if (images.isNotEmpty()) {
                return NovelChapterContent(aid, cid, title ?: "", "", images)
            }
            return NovelChapterContent(aid, cid, title ?: "", content ?: "", listOf())
        }
    }
}

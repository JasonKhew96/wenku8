package com.jasonkhew96.wenku8

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


class Wenku8CookieJar : CookieJar {
    private var cookies: List<Cookie>? = null
    private val sPref = Wenku8Application.applicationContext()
        .getSharedPreferences("cookie", Context.MODE_PRIVATE)

    init {

    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        if (url.host == "www.wenku8.net") {
            return this.cookies ?: emptyList()
        }
        return emptyList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (url.host == "www.wenku8.net") {
            if (this.cookies != cookies) {
                this.cookies = cookies
            }
        }
    }
}

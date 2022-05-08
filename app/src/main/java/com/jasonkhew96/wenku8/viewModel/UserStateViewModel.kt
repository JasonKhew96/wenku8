package com.jasonkhew96.wenku8.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jasonkhew96.wenku8.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.nio.charset.Charset
import javax.inject.Inject

@HiltViewModel
class UserStateViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var errorMsg by mutableStateOf("")
    var isLoggedIn by mutableStateOf(false)
    var isBusy by mutableStateOf(false)

    var userId by mutableStateOf("")
    var username by mutableStateOf("")

    init {
        checkLogin()
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            isBusy = true
            val test = repository.login(username, password)
            val jsoup = Jsoup.parse(String(test.bytes(), Charset.forName("GBK")))
            if (jsoup.title().contains("错误", true)) {
                isLoggedIn = false
                errorMsg = jsoup.select(".blockcontent").text()
            } else {
                isLoggedIn = true
                errorMsg = ""
            }
            isBusy = false
        }
    }

    fun checkLogin() {
        viewModelScope.launch {
            isBusy = true
            val userDetail = repository.getUserDetail()
            val jsoup = Jsoup.parse(String(userDetail.bytes(), Charset.forName("GBK")))
            val tbody = jsoup.select("table.grid > tbody")
            userId = tbody.select("tr:nth-child(1) > td:nth-child(2)").text()
            username = tbody.select("tr:nth-child(3) > td:nth-child(2)").text()
            isLoggedIn = userId.isNotEmpty()
            isBusy = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            isBusy = true
            repository.logout()
            isLoggedIn = false
            isBusy = false
        }
    }

}

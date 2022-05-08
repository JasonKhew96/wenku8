package com.jasonkhew96.wenku8.navigation

sealed class Wenku8Screens(val route: String) {
    object SplashScreen : Wenku8Screens("splash")
    object LoginScreen : Wenku8Screens("login")
    object HomeScreen : Wenku8Screens("home")
    object SettingsScreen : Wenku8Screens("settings")
    object AboutScreen : Wenku8Screens("about")
    object DetailsScreen : Wenku8Screens("details")
    object ChapterScreen : Wenku8Screens("chapter")
}

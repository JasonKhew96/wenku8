package com.jasonkhew96.wenku8.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jasonkhew96.wenku8.screens.about.AboutScreen
import com.jasonkhew96.wenku8.screens.chapter.ChapterContentScreen
import com.jasonkhew96.wenku8.screens.details.DetailsScreen
import com.jasonkhew96.wenku8.screens.home.HomeScreen
import com.jasonkhew96.wenku8.screens.login.LoginScreen
import com.jasonkhew96.wenku8.screens.splash.SplashScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Wenku8Screens.SplashScreen.route) {
        composable(route = Wenku8Screens.SplashScreen.route) {
            SplashScreen(toHomeScreen = {
                navController.navigate(Wenku8Screens.HomeScreen.route) {
                    popUpTo(Wenku8Screens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }, toLoginScreen = {
                navController.navigate(Wenku8Screens.LoginScreen.route)
            })
        }
        composable(route = Wenku8Screens.LoginScreen.route) {
            LoginScreen()
        }
        composable(route = Wenku8Screens.HomeScreen.route) {
            HomeScreen(onNovelClick = { aid ->
                navController.navigate(Wenku8Screens.DetailsScreen.route + "/${aid}")
            })
        }
        composable(route = Wenku8Screens.AboutScreen.route) {
            AboutScreen()
        }
        composable(
            route = Wenku8Screens.DetailsScreen.route + "/{aid}",
            arguments = listOf(navArgument("aid") { type = NavType.IntType })
        ) { entry ->
            val aid = entry.arguments?.getInt("aid")
            if (aid != null) {
                DetailsScreen(onChapterClick = { cid ->
                    navController.navigate(Wenku8Screens.ChapterScreen.route + "/${aid}/${cid}")
                }, aid = aid)
            }
        }
        composable(
            route = Wenku8Screens.ChapterScreen.route + "/{aid}/{cid}", arguments = listOf(
                navArgument("aid") { type = NavType.IntType },
                navArgument("cid") { type = NavType.IntType })
        ) { entry ->
            val aid = entry.arguments?.getInt("aid")
            val cid = entry.arguments?.getInt("cid")
            if (aid != null && cid != null) {
                ChapterContentScreen(aid = aid, cid = cid)
            }
        }
    }
}

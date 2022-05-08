package com.jasonkhew96.wenku8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jasonkhew96.wenku8.navigation.Navigation
import com.jasonkhew96.wenku8.ui.theme.wenku8Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            wenku8Theme {
                Navigation()
            }
        }
    }
}

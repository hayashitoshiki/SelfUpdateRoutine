package com.myapp.presentation.ui.diary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.myapp.presentation.utils.theme.ComposeSampleTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * 振り返り画面 Activity
 */
@AndroidEntryPoint
class DiaryActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeSampleTheme {
                ComposeBaseApp()
            }
        }
    }
}

/**
 * ベース画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun ComposeBaseApp() {
    ComposeSampleTheme {
        val navController = rememberNavController()
        Scaffold(backgroundColor = Color(0xfff5f5f5)) {
            AppNavHost(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        DiaryActivity()
    }
}

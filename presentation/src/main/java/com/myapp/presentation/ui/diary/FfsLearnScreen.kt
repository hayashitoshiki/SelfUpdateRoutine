package com.myapp.presentation.ui.diary

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.myapp.presentation.R
import com.myapp.presentation.ui.Screens
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * 振り返り_教訓画面
 *
 */
@Composable
fun FfsLearnScreen(
    navController: NavHostController,
    viewModel: FfsLearnViewModel
) {

    // label
    val section = LocalContext.current.getString(R.string.section1_3)
    val title = LocalContext.current.getString(R.string.title_item_learn)

    // state
    val state = viewModel.state.value

    // event
    val onChangeText: (String) -> Unit = { viewModel.setEvent(DiaryBaseContract.Event.OnChangeText(it)) }
    val onClickOkButton: () -> Unit = { viewModel.setEvent(DiaryBaseContract.Event.OnClickNextButton) }

    // effect
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is DiaryBaseContract.Effect.NextNavigation -> {
                    navController.navigate(Screens.FfsStatementScreen.route) {
                        popUpTo(Screens.FfsLeanScreen.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
            .collect()
    }

    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            DiaryTextInputContent(
                section, title, state, onChangeText, onClickOkButton
            )
        }
    }
}

/**
 * プレビュー表示
 *
 */
@Preview(
    showBackground = true, name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun PreviewFfsLearnScreen() {
    val navController = rememberNavController()
    val viewModel: FfsLearnViewModel = hiltViewModel()

    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        FfsLearnScreen(navController, viewModel)
    }
}
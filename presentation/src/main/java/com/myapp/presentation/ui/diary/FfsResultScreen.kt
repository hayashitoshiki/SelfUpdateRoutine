package com.myapp.presentation.ui.diary

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.myapp.presentation.R
import com.myapp.presentation.ui.Screens
import com.myapp.presentation.utils.base.LayoutTag
import com.myapp.presentation.utils.theme.TextColor
import com.myapp.presentation.utils.theme.buttonRoundedCornerShape
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * 振り返り_天気比喩振り返り確認画面
 */
@Composable
fun FfsResultScreen(
    navController: NavHostController,
    viewModel: FfsResultViewModel
) {

    // state
    val state = viewModel.state.value

    // event
    val onClickOkButton: () -> Unit = { viewModel.setEvent(FfsResultContract.Event.OnClickNextButton) }

    // effect
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is FfsResultContract.Effect.NextNavigation -> {
                    navController.navigate(Screens.FfsAssessmentScreen.route) {
                        popUpTo(Screens.FfsResultScreen.route) {
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
            FfsResultContent(state, onClickOkButton)
        }
    }
}

/**
 * 画面要素定義
 */
sealed class FfsResultContentTag(value: String) : LayoutTag(value) {
    // Button
    object BtnOk : DiaryTextInputContentTag("btn_ok")

    // Text
    object TxtFindSection : DiaryTextInputContentTag("txt_section")
    object TxtFindTitle : DiaryTextInputContentTag("txt_title")
    object TxtFindValue : DiaryTextInputContentTag("txt_title")
    object TxtFactSection : DiaryTextInputContentTag("txt_section")
    object TxtFactTitle : DiaryTextInputContentTag("txt_title")
    object TxtFactValue : DiaryTextInputContentTag("txt_title")
    object TxtLearnSection : DiaryTextInputContentTag("txt_section")
    object TxtLearnTitle : DiaryTextInputContentTag("txt_title")
    object TxtLearnValue : DiaryTextInputContentTag("txt_title")
    object TxtStatementSection : DiaryTextInputContentTag("txt_section")
    object TxtStatementTitle : DiaryTextInputContentTag("txt_title")
    object TxtStatementValue : DiaryTextInputContentTag("txt_title")
}

/**
 * 画面描画
 *
 */
@Composable
fun FfsResultContent(
    state: FfsResultContract.State,
    onClickOkButton: () -> Unit
) {
    val title = LocalContext.current.getString(R.string.title_answer_check)
    val factSection = LocalContext.current.getString(R.string.section1_1)
    val factTitle = LocalContext.current.getString(R.string.question_fact)
    val findSection = LocalContext.current.getString(R.string.section1_2)
    val findTitle = LocalContext.current.getString(R.string.question_find)
    val learnSection = LocalContext.current.getString(R.string.section1_3)
    val learnTitle = LocalContext.current.getString(R.string.question_learn)
    val statementSection = LocalContext.current.getString(R.string.section1_4)
    val statementTitle = LocalContext.current.getString(R.string.question_statement)
    Column {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = title,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor.DarkPrimary,
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp)
                    .align(Alignment.Center)
                    .testTag(DiaryTextInputContentTag.TxtSection.value),
            )
        }

        FfsValues(
            factSection, FfsResultContentTag.TxtFactSection, factTitle, FfsResultContentTag.TxtFactTitle, state.fact,
            FfsResultContentTag.TxtFactValue
        )
        FfsValues(
            findSection, FfsResultContentTag.TxtFindSection, findTitle, FfsResultContentTag.TxtFindTitle, state.find,
            FfsResultContentTag.TxtFindValue
        )
        FfsValues(
            learnSection, FfsResultContentTag.TxtLearnSection, learnTitle, FfsResultContentTag.TxtLearnTitle, state.learn,
            FfsResultContentTag.TxtLearnValue
        )
        FfsValues(
            statementSection, FfsResultContentTag.TxtStatementSection, statementTitle, FfsResultContentTag.TxtStatementTitle,
            state.statement, FfsResultContentTag.TxtStatementValue
        )

        Box(Modifier.fillMaxWidth()) {
            Button(
                onClick = { onClickOkButton() },
                shape = buttonRoundedCornerShape,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.Center)
                    .testTag(FfsResultContentTag.BtnOk.value),
            ) {
                Text(stringResource(id = R.string.btn_next))
            }
        }
    }
}

/**
 * サブタイトル
 *
 * @param title サブタイトルテキスト
 */
@Composable
private fun FfsValues(
    section: String,
    sectionTag: LayoutTag,
    title: String,
    titleTag: LayoutTag,
    value: String,
    valueTag: LayoutTag
) {
    Row(modifier = Modifier.padding(top = 8.dp, start = 16.dp)) {
        Text(
            text = section, fontWeight = FontWeight.Bold, modifier = Modifier.testTag(sectionTag.value)
        )
        Text(
            text = title, fontWeight = FontWeight.Bold, color = TextColor.DarkPrimary, modifier = Modifier
                .padding(start = 4.dp)
                .testTag(titleTag.value)
        )
    }
    Text(
        text = value, modifier = Modifier
            .padding(top = 4.dp, start = 16.dp)
            .testTag(valueTag.value)
    )
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
fun PreviewFfsResultScreen() {
    val navController = rememberNavController()
    val viewModel: FfsResultViewModel = hiltViewModel()

    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        FfsResultScreen(navController, viewModel)
    }
}
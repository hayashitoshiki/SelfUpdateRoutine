package com.myapp.presentation.ui.diary

import android.app.Activity
import android.content.Intent
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
import com.myapp.domain.model.value.HeartScore
import com.myapp.presentation.R
import com.myapp.presentation.ui.MainActivity
import com.myapp.presentation.utils.base.LayoutTag
import com.myapp.presentation.utils.expansion.text
import com.myapp.presentation.utils.theme.TextColor
import com.myapp.presentation.utils.theme.buttonRoundedCornerShape
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * 振り返り_天気比喩振り返り確認画面
 */
@Composable
fun WeatherResultScreen(
    viewModel: WeatherResultViewModel
) {

    // state
    val state = viewModel.state.value

    // event
    val onClickOkButton: () -> Unit = { viewModel.setEvent(WeatherResultContract.Event.OnClickSaveButton) }

    // effect
    val context = LocalContext.current
    val intent = Intent(context, MainActivity::class.java)
    val activity = (context as? Activity)
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is WeatherResultContract.Effect.SaveResult -> {
                    context.startActivity(intent)
                    activity?.finish()
                }
            }
        }
            .collect()
    }

    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            WeatherResultContent(state, onClickOkButton)
        }
    }
}

/**
 * 画面要素定義
 */
sealed class WeatherResultContentTag(value: String) : LayoutTag(value) {
    // Button
    object BtnOk : DiaryTextInputContentTag("btn_ok")

    // Text
    object TxtTitle : DiaryTextInputContentTag("txt_section")
    object TxtAssessmentSection : DiaryTextInputContentTag("txt_section")
    object TxtAssessmentTitle : DiaryTextInputContentTag("txt_title")
    object TxtAssessmentValue : DiaryTextInputContentTag("txt_title")
    object TxtReasonSection : DiaryTextInputContentTag("txt_section")
    object TxtReasonTitle : DiaryTextInputContentTag("txt_title")
    object TxtReasonValue : DiaryTextInputContentTag("txt_title")
    object TxtImproveSection : DiaryTextInputContentTag("txt_section")
    object TxtImproveTitle : DiaryTextInputContentTag("txt_title")
    object TxtImproveValue : DiaryTextInputContentTag("txt_title")
}

/**
 * 画面描画
 *
 */
@Composable
fun WeatherResultContent(
    state: WeatherResultContract.State,
    onClickOkButton: () -> Unit
) {
    val title = LocalContext.current.getString(R.string.title_answer_check)
    val assessmentSection = LocalContext.current.getString(R.string.section2_1)
    val assessmentTitle = LocalContext.current.getString(R.string.question_assessment)
    val reasonSection = LocalContext.current.getString(R.string.section2_2)
    val reasonTitle = LocalContext.current.getString(R.string.question_reason)
    val improveSection = LocalContext.current.getString(R.string.section2_3)
    val improveTitle = LocalContext.current.getString(R.string.question_improve)

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
                    .testTag(WeatherResultContentTag.TxtTitle.value),
            )
        }

        WeatherValues(
            assessmentSection, WeatherResultContentTag.TxtAssessmentSection, assessmentTitle,
            WeatherResultContentTag.TxtAssessmentTitle, LocalContext.current.getString(HeartScore(state.assessment).text), WeatherResultContentTag.TxtAssessmentValue
        )
        WeatherValues(
            reasonSection, WeatherResultContentTag.TxtReasonSection, reasonTitle, WeatherResultContentTag.TxtReasonTitle,
            state.reason, WeatherResultContentTag.TxtReasonValue
        )
        WeatherValues(
            improveSection, WeatherResultContentTag.TxtImproveSection, improveTitle, WeatherResultContentTag.TxtImproveTitle,
            state.improve, WeatherResultContentTag.TxtImproveValue
        )

        Box(Modifier.fillMaxWidth()) {
            Button(
                onClick = { onClickOkButton() },
                shape = buttonRoundedCornerShape,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.Center)
                    .testTag(WeatherResultContentTag.BtnOk.value),
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
private fun WeatherValues(
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
fun PreviewWeatherResultScreen() {
    val viewModel: WeatherResultViewModel = hiltViewModel()

    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        WeatherResultScreen(viewModel)
    }
}

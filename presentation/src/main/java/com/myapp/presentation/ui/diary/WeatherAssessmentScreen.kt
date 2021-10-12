package com.myapp.presentation.ui.diary

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.myapp.domain.model.value.HeartScore
import com.myapp.presentation.R
import com.myapp.presentation.utils.base.LayoutTag
import com.myapp.presentation.utils.expansion.img
import com.myapp.presentation.utils.theme.TextColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * 振り返り_点数画面
 */
@Composable
fun WeatherAssessmentScreen(
    navController: NavHostController,
    viewModel: WeatherAssessmentViewModel
) {

    // label
    val section = LocalContext.current.getString(R.string.section2_1)
    val title = LocalContext.current.getString(R.string.title_item_assessment)

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
                    navController.navigate(Screens.WEATHER_REASON_SCREEN.route) {
                        popUpTo(Screens.WEATHER_ASSESSMENT_SCREEN.route) {
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
            DiarySliderInputContent(
                section, title, state, onChangeText, onClickOkButton
            )
        }
    }
}

/**
 * 画面要素定義
 */
sealed class DiarySliderInputContentTag(value: String) : LayoutTag(value) {
    // Button
    object BtnOk : DiaryTextInputContentTag("btn_ok")

    // Text
    object TxtSection : DiaryTextInputContentTag("txt_section")
    object TxtTitle : DiaryTextInputContentTag("txt_title")

    // Image
    object ImgWeather : DiaryTextInputContentTag("edt_input")

    // Slider
    object SliderInput : DiaryTextInputContentTag("edt_input")
}


/**
 * 画面描画
 *
 */
@Composable
fun DiarySliderInputContent(
    section: String,
    title: String,
    state: DiaryBaseContract.State,
    onChangeSlider: (String) -> Unit,
    onClickOkButton: () -> Unit
) {
    val heartScope = (state.inputText.toFloat() * 100).toInt()
    val img = HeartScore(heartScope).img
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val (txtSection, txtTitle, imgWeather, edtInput, btnOk) = createRefs()
        Text(
            text = section,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor.DarkPrimary,
            modifier = Modifier
                .constrainAs(txtSection) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(txtTitle.top)
                }
                .testTag(DiarySliderInputContentTag.TxtSection.value),
        )
        Text(
            text = title,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor.DarkPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(txtTitle) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(imgWeather.top)
                }
                .testTag(DiarySliderInputContentTag.TxtTitle.value),
        )
        Image(
            painter = painterResource(img), contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                .constrainAs(imgWeather) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .testTag(DiarySliderInputContentTag.ImgWeather.value),
        )

        Slider(value = state.inputText.toFloat(), onValueChange = { onChangeSlider(it.toString()) },
            modifier = Modifier
                .constrainAs(edtInput) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(imgWeather.bottom, margin = 32.dp)
                }
                .testTag(DiarySliderInputContentTag.SliderInput.value))

        Button(
            onClick = { onClickOkButton() },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .constrainAs(btnOk) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(edtInput.bottom, margin = 32.dp)
                }
                .testTag(DiarySliderInputContentTag.BtnOk.value),
        ) {
            Text(stringResource(id = R.string.btn_next))
        }
    }
}

@Preview(
    showBackground = true, name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun FirstScreenDemo() {
    val navController = rememberNavController()
    val viewModel = WeatherAssessmentViewModel()
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            WeatherAssessmentScreen(navController, viewModel)
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
fun PreviewWeatherAssessmentScreen() {
    val navController = rememberNavController()
    val viewModel: WeatherAssessmentViewModel = hiltViewModel()

    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        WeatherAssessmentScreen(navController, viewModel)
    }
}
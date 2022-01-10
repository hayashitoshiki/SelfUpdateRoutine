package com.myapp.presentation.ui.diary

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.myapp.presentation.R
import com.myapp.presentation.utils.base.LayoutTag
import com.myapp.presentation.utils.theme.TextColor
import com.myapp.presentation.utils.theme.buttonRoundedCornerShape

/**
 * 画面要素定義
 */
sealed class DiaryTextInputContentTag(value: String) : LayoutTag(value) {
    // Button
    object BtnOk : DiaryTextInputContentTag("btn_ok")

    // Text
    object TxtSection : DiaryTextInputContentTag("txt_section")
    object TxtTitle : DiaryTextInputContentTag("txt_title")
    object TxtHint : DiaryTextInputContentTag("txt_hint")

    // EditText
    object EdtInput : DiaryTextInputContentTag("edt_input")
}

/**
 * 画面描画
 *
 */
@Composable
fun DiaryTextInputContent(
    section: String,
    title: String,
    state: DiaryBaseContract.State,
    onChangeText: (String) -> Unit,
    onClickOkButton: () -> Unit
) {
    // 背景タップ設定
    val focusManager = LocalFocusManager.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        val (txtSection, txtTitle, edtInput, hint, btnOk) = createRefs()
        Text(
            text = section,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor.DarkPrimary,
            modifier = Modifier
                .constrainAs(txtSection) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(txtTitle.top, margin = 16.dp)
                }
                .testTag(DiaryTextInputContentTag.TxtSection.value),
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
                    bottom.linkTo(edtInput.top, margin = 16.dp)
                }
                .testTag(DiaryTextInputContentTag.TxtTitle.value),
        )
        OutlinedTextField(
            value = state.inputText,
            onValueChange = { onChangeText(it) },
            isError = state.hintVisibility,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
                .constrainAs(edtInput) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(btnOk.top, margin = 32.dp)
                }
                .testTag(DiaryTextInputContentTag.EdtInput.value))
        if (state.hintVisibility) {
            Text(
                text = state.hintText,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.constrainAs(hint) {
                    top.linkTo(edtInput.bottom)
                    start.linkTo(edtInput.start)
                }
                    .testTag(DiaryTextInputContentTag.TxtHint.value)
            )
        }
        Button(
            onClick = { onClickOkButton() },
            shape = buttonRoundedCornerShape,
            enabled = state.isButtonEnable,
            modifier = Modifier
                .constrainAs(btnOk) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .testTag(DiaryTextInputContentTag.BtnOk.value),
        ) {
            Text(stringResource(id = R.string.btn_next))
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
fun PreviewDiaryTextInputScreen() {
    val viewModel = FfsFindViewModel()
    val section = LocalContext.current.getString(R.string.section1_1)
    val title = LocalContext.current.getString(R.string.title_item_fact)
    val state = viewModel.state.value
    val onChangeText: (String) -> Unit = {
        viewModel.setEvent(DiaryBaseContract.Event.OnChangeText(it))
    }
    val onClickOkButton: () -> Unit = {
        viewModel.setEvent(DiaryBaseContract.Event.OnClickNextButton)
    }
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        DiaryTextInputContent(
            section, title, state, onChangeText, onClickOkButton
        )
    }
}

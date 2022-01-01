package com.myapp.presentation.utils.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.myapp.presentation.utils.theme.PrimaryColor
import com.myapp.presentation.utils.theme.PrimaryColorDisable
import com.myapp.presentation.utils.theme.TextColor

/**
 * リスト表示用のメインテキスト定義
 *
 * @param modifier レイアウト
 * @param text テキスト
 */
@Composable
fun ListMainDarkText(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = TextColor.DarkPrimary,
        modifier = modifier
    )
}


/**
 * リスト表示用のサブテキスト定義
 *
 * @param modifier レイアウト
 * @param text テキスト
 */
@Composable
fun ListSubDarkText(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = TextColor.DarkSecondary,
        modifier = modifier
    )
}

/**
 * メインテキスト定義
 *
 * @param modifier レイアウト
 * @param text テキスト
 * @param enable 活性・非活性
 */
@Composable
fun MainLightText(modifier: Modifier = Modifier, text: String, enable: Boolean = true) {

    val color = if (enable) TextColor.LightPrimary else TextColor.LightSecondary
    Text(
        text = text,
        fontSize = 12.sp,
        color = color,
        modifier = modifier
    )
}

/**
 * ボタンテキスト定義
 *
 * @param modifier レイアウト
 * @param text テキスト
 * @param enable 活性・非活性
 */
@Composable
fun ButtonPrimaryText(modifier: Modifier = Modifier, text: String, enable: Boolean = true) {

    val color = if (enable) PrimaryColor else PrimaryColorDisable
    Text(
        text = text,
        fontSize = 12.sp,
        color = color,
        modifier = modifier
    )
}
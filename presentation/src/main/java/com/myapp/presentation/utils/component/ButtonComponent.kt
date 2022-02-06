package com.myapp.presentation.utils.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.myapp.presentation.utils.theme.ButtonDisableColor
import com.myapp.presentation.utils.theme.PrimaryColor
import com.myapp.presentation.utils.theme.PrimaryColorDisable
import com.myapp.presentation.utils.theme.buttonRoundedCornerShape

/**
 * メイン配色ボタン
 *
 * @param modifier レイアウト
 * @param onClick クリックアクション
 * @param enabled 活性非活性
 * @param text ボタン文言
 */
@Composable
fun PrimaryColorButton(
    onClick:() -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PrimaryColor,
            disabledContentColor = PrimaryColorDisable
        ),
        modifier = modifier.clip(CircleShape)
    ) {
        MainLightText(
            text = text,
            enable = enabled
        )
    }
}

/**
 * 白色ボタン
 *
 * @param modifier レイアウト
 * @param onClick クリックアクション
 * @param enabled 活性非活性
 * @param text ボタン文言
 */
@Composable
fun WhiteColorButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        shape = buttonRoundedCornerShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            disabledContentColor = ButtonDisableColor
        ),
        modifier = modifier.clip(CircleShape)
    ) {
        ButtonPrimaryText(
            text = text,
            enable = enabled
        )
    }
}


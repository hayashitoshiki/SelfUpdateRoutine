package com.myapp.presentation.utils.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.myapp.presentation.utils.theme.PrimaryColor
import com.myapp.presentation.utils.theme.PrimaryColorDisable

/**
 * リスト表示用のメインテキスト定義
 *
 * @param modifier レイアウト
 * @param text テキスト
 */
@Composable
fun PrimaryColorButton(onClick:() -> Unit, modifier: Modifier = Modifier, text: String, enable: Boolean = true) {
    Button(
        onClick = { onClick() },
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PrimaryColor,
            disabledContentColor = PrimaryColorDisable
        ),
        modifier = modifier.clip(CircleShape)
    ) {
        MainLightText(
            text = text,
            enable = enable
        )
    }
}

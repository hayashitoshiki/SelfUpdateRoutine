package com.myapp.presentation.utils.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


/**
 * 確認用ダイアログ
 *
 * @param title タイトル
 * @param message 本文
 * @param onClickPositiveAction 許可アクション
 * @param onClickNegativeAction 拒否アクション
 */
@Composable
fun ShowDeleteConfirmDialog(
    title: String = "",
    message: String,
    onClickPositiveAction: () -> Unit,
    onClickNegativeAction: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = Modifier.background(Color.White)
        ) {
            Column(modifier= Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    fontSize = 20.sp
                )
                Text(
                    text = message,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Row(modifier = Modifier.padding(top = 16.dp)) {
                    Button(onClick = { onClickNegativeAction() }) {
                        Text(text = "いいえ")
                    }
                    Button(onClick = { onClickPositiveAction() }) {
                        Text(text = "はい")
                    }
                }
            }
        }
    }
}
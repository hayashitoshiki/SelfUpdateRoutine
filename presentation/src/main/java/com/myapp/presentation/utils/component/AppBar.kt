package com.myapp.presentation.utils.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.myapp.presentation.utils.theme.backgroundColor

/**
 * タブトップ画面用AppBar
 *
 * @param title タイトル
 * @param onButtonClicked クリックイベント（画面遷移）
 */
@Composable
fun MenuButtonAppBar(title: String = "", onButtonClicked: () -> Unit) {
    BaseAppBar(
        title = title,
        onButtonClicked = onButtonClicked,
        icon = Icons.Filled.Menu
    )
}

/**
 * タブ２番目以降画面用Appbar
 *
 * @param title タイトル
 * @param onButtonClicked クリックイベント（戻る処理）
 */
@Composable
fun BackButtonAppBar(title: String = "", onButtonClicked: () -> Unit) {
    BaseAppBar(
        title = title,
        onButtonClicked = onButtonClicked,
        icon = Icons.Filled.ArrowBack
    )
}

/**
 * Base AppBar
 *
 * @param title タイトル
 * @param onButtonClicked クリックイベント（戻る処理）
 * @param icon 表示する画像
 */
@Composable
private fun BaseAppBar(title: String = "", onButtonClicked: () -> Unit, icon: ImageVector) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() } ) {
                Icon(icon, contentDescription = "")
            }
        },
        backgroundColor = backgroundColor,
        elevation = 0.dp,
    )
}
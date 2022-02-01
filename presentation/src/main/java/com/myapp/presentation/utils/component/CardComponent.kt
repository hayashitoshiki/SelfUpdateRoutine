package com.myapp.presentation.utils.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import com.myapp.presentation.utils.theme.PrimaryColor
import com.myapp.presentation.utils.theme.PrimaryDarkColor

/**
 * 設定値入力用カード
 *
 * @param title タイトル
 * @param content
 */
@Composable
fun SettingCard(title: Int, content: @Composable () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            ListMainDarkText(text = stringResource(id = title))
            content()
        }
    }
}

/**
 * 入力値確認用BottomCard
 *
 * @param modifier
 * @param content
 */
@Composable
fun BottomResultCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PrimaryColor,
                        PrimaryDarkColor
                    )
                )
            )
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        content()
    }
}

/**
 * カード表示用アイコン
 *
 * @param resId アイコンのResourceID
 * @param modifier
 */
@Composable
fun CardIcon(resId: Int, modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.size(64.dp),
        painter = painterResource(id = resId),
        contentDescription = null
    )
}

@ExperimentalMaterialApi
@Composable
fun CommonCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit= {},
    content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        elevation = 8.dp,
        onClick = {onClick()},
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            content()
        }
    }
}
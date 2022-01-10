package com.myapp.presentation.utils.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.presentation.R
import com.myapp.presentation.ui.diary.Screens
import com.myapp.presentation.utils.theme.*

/**
 * Drawableメニューリスト定義
 */
private val drawableScreens = listOf(
    Screens.DrawerScreens.HOME_SCREEN,
    Screens.DrawerScreens.ACCOUNT_SCREEN,
    Screens.DrawerScreens.SETTING_SCREEN
)

/**
 * Drawerメニュー定義
 *
 * @param modifier
 * @param onDestinationClicked クリックイベント(routeIdを返却)
 */
@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(R.drawable.ic_launcher_foreground),
//            contentDescription = "App icon"
//        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Red300,
                            Red400,
                            Red500
                        )
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = modifier.padding(15.dp)) {
                Text(
                    text = "Arvind Meshram",
                    style = MaterialTheme.typography.h5,
                    color = TextColor.DarkPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "meshramaravind@gmail.com",
                    style = MaterialTheme.typography.body2,
                    color = TextColor.DarkPrimary
                )
            }

            Image(
                painter = painterResource(R.drawable.ic_baseline_person_pin_24),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

        }

        drawableScreens.forEach { screen ->
            Spacer(Modifier.height(14.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onDestinationClicked(screen.route) })
                    .height(40.dp)
                    .background(color = Color.Transparent)

            ) {
                Image(
                    painter = painterResource(id = screen.icon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                        .padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = stringResource(id = screen.resourceId),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

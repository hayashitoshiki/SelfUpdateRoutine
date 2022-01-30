package com.myapp.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myapp.presentation.utils.component.ListMainDarkText
import com.myapp.presentation.utils.component.ListSubDarkText

/**
 * レポート詳細リスト表示用コンテンツ
 *
 * @param ReportDetailList 表示するレポート詳細項目
 */
@Composable
fun ReportDetailListContent(ReportDetailList: List<ReportDetail>) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent
    ) {
        LazyColumn {
            ReportDetailList.forEach { reportDetail ->
                item {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        elevation = 4.dp,
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            ListSubDarkText(text = reportDetail.date)
                            ListMainDarkText(
                                text = reportDetail.value,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


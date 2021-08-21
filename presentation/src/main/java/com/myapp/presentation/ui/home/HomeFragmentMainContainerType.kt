package com.myapp.presentation.ui.home


/**
 * ホーム画面 メインコンテナの表示View制御
 *
 * @param T
 */
sealed class HomeFragmentMainContainerType<out T> {

    /**
     * レポートみ入力　View
     */
    object NotReport : HomeFragmentMainContainerType<Nothing>()

    /**
     * レポートView
     *
     * @param Report
     * @property data
     */
    data class Report<Report>(val data: Report) : HomeFragmentMainContainerType<Report>()

    /**
     * ビジョンView
     *
     * @param Report
     * @property data
     */
    data class Vision<Report>(val data: Report) : HomeFragmentMainContainerType<Report>()
}
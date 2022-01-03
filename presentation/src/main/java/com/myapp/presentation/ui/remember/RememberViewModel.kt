package com.myapp.presentation.ui.remember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.domain.model.entity.Report
import com.myapp.presentation.utils.base.BaseAacViewModel
import com.myapp.presentation.utils.base.BaseViewModel
import com.myapp.presentation.utils.base.StringResource
import com.myapp.presentation.utils.expansion.img
import com.myapp.presentation.utils.expansion.text
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * 過去の振り返り詳細画面　UIロジック
 *
 */
class RememberViewModel @AssistedInject constructor(@Assisted private val report: Report) :
    BaseViewModel<RememberContract.State, RememberContract.Effect, RememberContract.Event>() {

    override fun initState(): RememberContract.State {
        return RememberContract.State()
    }

    override fun handleEvents(event: RememberContract.Event){}

    init {
        setState {
            copy(
                factComment = report.ffsReport.factComment, findComment = report.ffsReport.findComment,
                learnComment = report.ffsReport.learnComment, statementComment = report.ffsReport.statementComment,
                heartScoreComment = report.weatherReport.heartScore,
                reasonComment = report.weatherReport.reasonComment, improveComment = report.weatherReport.improveComment,
                heartScoreImg = report.weatherReport.heartScore.img
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(report: Report): RememberViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            factory: Factory,
            report: Report
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(report) as T
            }
        }
    }

}

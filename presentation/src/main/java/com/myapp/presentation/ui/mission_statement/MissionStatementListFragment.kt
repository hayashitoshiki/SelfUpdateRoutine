package com.myapp.presentation.ui.mission_statement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import com.myapp.presentation.utils.component.ListMainDarkText
import com.myapp.presentation.utils.component.ListSubDarkText
import com.myapp.presentation.utils.component.PrimaryColorButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * ミッションステートメント一覧画面
 *
 */
@AndroidEntryPoint
class MissionStatementListFragment : Fragment() {

    private val viewModel: MissionStatementListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.state.value
                LaunchedEffect(true) {
                    viewModel.effect.onEach { effect ->
                        when (effect) {
                            is MissionStatementListContract.Effect.NavigateMissionStatementSetting -> {
                                val action = MissionStatementListFragmentDirections.actionNavConstitutionToNavConstitutionSetting(effect.value)
                                findNavController().navigate(action)
                            }
                        }
                    }.collect()
                }
                MissionStatementListScreenContent(viewModel, state)
            }
        }
    }
}


@Composable
private fun MissionStatementListScreenContent(
    viewModel: MissionStatementListViewModel,
    state: MissionStatementListContract.State
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (state.missionStatement == null) {
                ListSubDarkText(
                    text = stringResource(id = R.string.txt_no_mission_statement),
                    modifier = Modifier.padding(top = 64.dp)
                )
                PrimaryColorButton(
                    onClick = { viewModel.setEvent(MissionStatementListContract.Event.OnClickChangeButton) },
                    text = stringResource(id = R.string.btn_edit),
                    modifier = Modifier.padding(top = 64.dp)
                )
            } else {
                if (state.funeralList.isNotEmpty()) {
                    MissionStatementCard(title = stringResource(id = R.string.title_sub_dai)) {
                        state.funeralList.forEach { ListMainDarkText(text = "・$it") }
                    }
                }
                if (state.purposeLife.isNotEmpty()) {
                    MissionStatementCard(title = stringResource(id = R.string.title_sub_mission_statement)) {
                        ListMainDarkText(text = state.purposeLife)
                    }
                }
                if (state.constitutionList.isNotEmpty()) {
                    MissionStatementCard(title = stringResource(id = R.string.title_sub_constitution)) {
                        state.constitutionList.forEach { ListMainDarkText(text = "・$it") }
                    }
                }
                PrimaryColorButton(
                    onClick = { viewModel.setEvent(MissionStatementListContract.Event.OnClickChangeButton) },
                    text = stringResource(id = R.string.btn_edit)
                )
            }
        }
    }
}

@Composable
private fun MissionStatementCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.padding(16.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            ListSubDarkText(
                text = title, 
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
        }
    }
}
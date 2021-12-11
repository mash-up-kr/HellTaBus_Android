/*
 * Created by Leo on 2021. 12. 11 ..
 */
package com.mashup.healthyup.features.summary

import com.mashup.healthyup.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ExerciseSummaryViewModel @Inject constructor() : BaseViewModel() {

    sealed class Action {
        object OnShareClicked : Action()
        object OnImageSaveClicked : Action()
        object OnCloseClicked : Action()
    }

    private val _action = Channel<Action>(Channel.BUFFERED)
    val actionFlow: Flow<Action>
        get() = _action.receiveAsFlow()

    fun onShareClick() {
        val action = Action.OnShareClicked
        _action.trySend(action)
    }

    fun onImageSaveClick() {
        val action = Action.OnImageSaveClicked
        _action.trySend(action)
    }

    fun onCloseClick() {
        val action = Action.OnCloseClicked
        _action.trySend(action)
    }
}

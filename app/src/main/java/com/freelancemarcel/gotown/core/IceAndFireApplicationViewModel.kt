package com.freelancemarcel.gotown.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.freelancemarcel.gotown.core.models.Error
import com.freelancemarcel.gotown.houses.data.HouseDataSource
import com.freelancemarcel.gotown.houses.data.HouseRepository
import com.freelancemarcel.gotown.houses.models.House
import com.freelancemarcel.gotown.houses.models.HouseListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

class IceAndFireApplicationViewModel(private val houseRepository: HouseRepository) : ViewModel() {
    data class State(
        val error: Error? = null,
        val isAScreenLoading: Boolean = false,
        val selectedHouse: House? = null,
    )

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return IceAndFireApplicationViewModel(houseRepository = (application as IceAndFireApplication).houseRepository) as T
            }
        }
    }

    val houses: Flow<PagingData<HouseListItem>> = Pager(PagingConfig(pageSize = 20)) {
        HouseDataSource(houseRepository)
    }.flow

    private val _stateChanges by lazy { MutableStateFlow(State()) }
    private val _stateFlow by lazy { _stateChanges.asStateFlow() }

    fun getHouse(houseUrl:String){
        updateState { it.copy(isAScreenLoading = true, selectedHouse = null) }
        viewModelScope.launch(Dispatchers.IO){
            houseRepository.getHouse(houseUrl).fold(::onError){house ->
                updateState {state->  state.copy(isAScreenLoading = false, selectedHouse = house) }
            }
        }
    }

    fun <V> getValue(stateProperty: KProperty1<State, V>): V {
        return stateProperty.get(_stateChanges.value)
    }

    @Composable
    fun <V> getValueAsState(stateProperty: KProperty1<State, V>): androidx.compose.runtime.State<V> {
        return getValueChanges(stateProperty).collectAsState(initial = getValue(stateProperty))
    }

    fun onError(error: Error?) {
        updateState { it.copy(error = error,isAScreenLoading = false) }
    }

    private fun <V> getValueChanges(stateProperty: KProperty1<State, V>): Flow<V> {
        return _stateFlow.map { state -> stateProperty.get(state) }
    }

    private fun updateState(updateCallback: (State) -> State) {
        _stateChanges.update { state -> updateCallback(state) }
    }
}
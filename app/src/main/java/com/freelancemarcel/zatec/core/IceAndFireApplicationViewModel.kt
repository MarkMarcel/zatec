package com.freelancemarcel.zatec.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.freelancemarcel.zatec.houses.data.HouseDataSource
import com.freelancemarcel.zatec.houses.data.HouseRepository
import com.freelancemarcel.zatec.houses.models.HouseListItem
import kotlinx.coroutines.flow.Flow

class IceAndFireApplicationViewModel(private val houseRepository: HouseRepository) : ViewModel() {
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
}
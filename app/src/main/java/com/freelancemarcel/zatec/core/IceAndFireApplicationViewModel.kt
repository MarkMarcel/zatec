package com.freelancemarcel.zatec.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.freelancemarcel.zatec.houses.data.HouseDataSource
import com.freelancemarcel.zatec.houses.data.HouseRepository

class IceAndFireApplicationViewModel(private val houseRepository: HouseRepository) : ViewModel() {
    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return IceAndFireApplicationViewModel(houseRepository = (application as IceAndFireApplication).houseRepository) as T
            }
        }
    }
}
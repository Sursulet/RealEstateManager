package com.openclassrooms.realestatemanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    realEstateDao: RealEstateDao
) : ViewModel() {

    private val realEstatesEventChannel = Channel<RealEstatesEvent>()
    val realEstatesEvent = realEstatesEventChannel.receiveAsFlow()

    val searchQuery = MutableStateFlow("")
    private val realEstatesFlow = searchQuery.flatMapLatest { realEstateDao.sortedBy(it) }
    val realEstates = realEstatesFlow.asLiveData()

    fun onAddNewRealEstateClick() = viewModelScope.launch {
        realEstatesEventChannel.send(RealEstatesEvent.NavigateToAddRealEstateScreen)
    }

    fun onEditRealEstateClick() = viewModelScope.launch {
        realEstatesEventChannel.send(RealEstatesEvent.NavigateToEditRealEstateScreen)
    }

    sealed class RealEstatesEvent {
        object NavigateToAddRealEstateScreen : RealEstatesEvent()
        object NavigateToEditRealEstateScreen : RealEstatesEvent()
    }
}
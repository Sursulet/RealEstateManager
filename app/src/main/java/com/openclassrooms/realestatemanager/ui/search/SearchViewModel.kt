package com.openclassrooms.realestatemanager.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.repositories.SearchQueryRepository
import com.openclassrooms.realestatemanager.utils.SearchQuery
import com.openclassrooms.realestatemanager.utils.Utils.calculatePeriod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchQueryRepository: SearchQueryRepository
) : ViewModel() {

    private val _searchEvents = MutableStateFlow<SearchRealEstateEvent>(SearchRealEstateEvent.Empty)
    val searchEvents = _searchEvents.asStateFlow()

    var type = ""
    var zone = ""
    var minPrice = ""
    var maxPrice = ""
    var nbTime = ""
    var unitTime = ""
    var status = false
    var minSurface = ""
    var maxSurface = ""
    var nearest = ""
    var nbPhotos = ""

    fun clear() {
        onSearchClick()
    }

    fun onSearchClick() {
        if (minPrice.isBlank() && maxPrice.isNotBlank()) {
            showInvalidInputMessage(MESSAGE.MIN_PRICE)
            return
        }

        if (minPrice.isNotBlank() && maxPrice.isBlank()) {
            showInvalidInputMessage(MESSAGE.MAX_PRICE)
            return
        }

        if (nbTime.isBlank() && unitTime.isNotBlank()) {
            showInvalidInputMessage(MESSAGE.NUMBER_TIME)
            return
        }

        if (unitTime.isBlank() && nbTime.isNotBlank()) {
            showInvalidInputMessage(MESSAGE.UNIT_TIME)
            return
        }

        if (minSurface.isBlank() && maxSurface.isNotBlank()) {
            showInvalidInputMessage(MESSAGE.MIN_SURFACE)
            return
        }

        if (minSurface.isNotBlank() && maxSurface.isBlank()) {
            showInvalidInputMessage(MESSAGE.MAX_SURFACE)
            return
        }

        val miniPrice = minPrice.toFloatOrNull() ?: 0f
        val maxiPrice = maxPrice.toFloatOrNull() ?: Float.MAX_VALUE
        val miniSurface = minSurface.toIntOrNull() ?: 0
        val maxiSurface = maxSurface.toIntOrNull() ?: Int.MAX_VALUE

        val period = if (unitTime.isBlank() && nbTime.isBlank()) null
        else calculatePeriod(unitTime.toLong(), nbTime)

        val release = /*if (status.not()) period else*/ LocalDate.parse("1970-01-01")

        val number = nbPhotos.filter { it.isDigit() }.toIntOrNull() ?: 1

        val newSearchQuery = SearchQuery(
            type = type,
            zone = zone,
            minPrice = miniPrice,
            maxPrice = maxiPrice,
            release = release,
            status = status,
            minSurface = miniSurface,
            maxSurface = maxiSurface,
            nearest = nearest,
            nbPhotos = number
        )

        search(newSearchQuery)
    }

    fun search(searchQuery: SearchQuery) = viewModelScope.launch {
        searchQueryRepository.setCurrent(searchQuery)
    }

    private fun showInvalidInputMessage(msg: MESSAGE) = viewModelScope.launch {
        _searchEvents.value = SearchRealEstateEvent.ShowInvalidInputMessage(msg)
    }

    sealed class SearchRealEstateEvent {
        object Empty : SearchRealEstateEvent()
        data class ShowInvalidInputMessage(val error: MESSAGE) : SearchRealEstateEvent()
    }

    enum class MESSAGE(val msg: String) {
        MIN_PRICE("Minimum price is Empty, Enter a number"),
        MAX_PRICE("Maximum price is Empty, Enter a number"),
        NUMBER_TIME("Number is Empty, Enter a number for the period"),
        UNIT_TIME("Unit time is empty, Choose one"),
        MIN_SURFACE("Minimum surface is Empty, Enter a number"),
        MAX_SURFACE("Maximum surface is Empty, Enter a number")
    }
}
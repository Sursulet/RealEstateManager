package com.openclassrooms.realestatemanager.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import com.openclassrooms.realestatemanager.ui.list.RealEstateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val realEstateRepository: RealEstateRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var searchMinPriceQuery = MutableStateFlow("")
    var searchMaxPriceQuery = MutableStateFlow("")

    private val searchFlow = combine(
        searchMinPriceQuery,
        searchMaxPriceQuery
    ) { minPrice, maxPrice ->
        //Wrapper(minPrice.toFloat(), maxPrice.toFloat())
    }
        //.flatMapLatest { (a, b) -> realEstateRepository.search(a, b) }

    /*
    val search = liveData {

        searchFlow.collect { list ->
            emit(list.map {
                RealEstateUiModel(
                    id = it.id,
                    url = "it.url",
                    type = it.type,
                    city = it.city,
                    price = "$" + it.price.toString(),
                    style = "#FF4081"
                )
            })
        }
    }

     */

    fun onSearchClick() {
        if (searchMinPriceQuery.value.isBlank() &&
            searchMaxPriceQuery.value.isBlank()
        ) {
            //TODO : Message "There is no selected search criteria"
            return
        }
    }

    fun onRealEstateSelected(realEstateId: Int) {
        sharedRepository.setRealEstateId(realEstateId)
    }

    data class Wrapper(
        val minPrice: Float,
        val maxPrice: Float
    )
}
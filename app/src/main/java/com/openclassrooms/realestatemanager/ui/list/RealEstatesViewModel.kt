package com.openclassrooms.realestatemanager.ui.list

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.IoDispatcher
import com.openclassrooms.realestatemanager.repositories.*
import com.openclassrooms.realestatemanager.utils.Constants.NO_REAL_ESTATE_ID
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstatesViewModel @Inject constructor(
    twoPaneRepository: TwoPaneRepository,
    private val currentIdRepository: CurrentIdRepository,
    realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository,
    searchQueryRepository: SearchQueryRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val twoPane = twoPaneRepository.twoPane
    private val searchQuery = searchQueryRepository.searchQuery

    private val _selectedId = MutableStateFlow(NO_REAL_ESTATE_ID)
    private val selectedId = _selectedId.asStateFlow()

    private val realEstatesFlow = searchQuery.flatMapLatest { searchQuery ->
        if (searchQuery == null) {
            realEstateRepository.getRealEstates()
        } else {
            realEstateRepository.search(
                searchQuery.type,
                searchQuery.zone,
                searchQuery.minPrice,
                searchQuery.maxPrice,
                searchQuery.release,
                searchQuery.status,
                searchQuery.minSurface,
                searchQuery.maxSurface,
                searchQuery.nearest,
                searchQuery.nbPhotos
            )
        }
    }

    val uiModels = MutableStateFlow<List<RealEstateUiModel>>(listOf())

    init {
        viewModelScope.launch(ioDispatcher) {
            combine(twoPane, selectedId, realEstatesFlow) { twoPane, id, list ->
                list.map {
                    Log.d(TAG, "REAL: $it")
                    val photo = photoRepository.getPhoto(it.id).filterNotNull().first()
                    val color = if (twoPane && id == it.id) R.color.colorAccent else Color.WHITE
                    RealEstateUiModel(
                        id = it.id,
                        bitmap = photo.bitmap,
                        type = it.type,
                        city = it.address.city,
                        price = "$" + it.price.toString(),
                        backgroundColorRes = color,
                        style = "color"
                    )
                }
            }.collect {
                Log.d(TAG, "LIST: $it")
                uiModels.value = it
            }
        }

    }

    fun onRealEstateSelected(realEstateId: Long) {
        _selectedId.value = realEstateId
        currentIdRepository.setRealEstateId(realEstateId)
    }
}
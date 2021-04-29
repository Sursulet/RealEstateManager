package com.openclassrooms.realestatemanager.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.CurrentSearchParametersRepository
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import com.openclassrooms.realestatemanager.utils.Utils.calculatePeriod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Float.POSITIVE_INFINITY
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    val photoRepository: PhotoRepository,
    private val currentSearchParametersRepository: CurrentSearchParametersRepository
) : ViewModel() {

    private val _current = MutableLiveData<List<RealEstate>>()
    val current: LiveData<List<RealEstate>>
        get() = _current

    private val _searchEvents = MutableSharedFlow<SearchRealEstateEvent>()
    val searchEvents = _searchEvents.asSharedFlow()

    var type = ""
    var zone = ""
    var minPrice = ""
    var maxPrice = ""
    var unit = ""
    var time = ""
    var status = false
    var minSurface = ""
    var maxSurface = ""
    var nearest = ""
    var nbPhotos = ""

    fun onSearchClick() {
        //vendu et mis sur le marché sont différent
        //vendu fera appel à la variable sale
        //mis sur le marché fera appel à la variable created
        //release
        val miniPrice = if (minPrice.isBlank()) 0f else minPrice.toFloat()
        val maxiPrice = if (maxPrice.isBlank()) POSITIVE_INFINITY else maxPrice.toFloat()
        val miniSurface = if (minSurface.isBlank()) 0 else minSurface.toInt()
        val maxiSurface =
            if (maxSurface.isBlank()) POSITIVE_INFINITY.toInt() else maxSurface.toInt()

        if (unit.isBlank() && time.isNotBlank()) {
            Log.d(TAG, "onSearchClick: ERROR NUMBER")
            showInvalidInputMessage("Number Period is Empty, Enter a number for the period")
            return
        }
        if (unit.isNotBlank() && time.isBlank()) {
            showInvalidInputMessage("Unit Period is empty, Choose one")
            return
        }

        Log.d(TAG, "STATUS: $status")

        val period = if(unit.isBlank() && time.isBlank()) LocalDate.parse("1970-01-01") else  calculatePeriod(unit.toLong(), time)
        val release = if(status.not()) period else null
        val sold = if(status) period else null
        //if(status) release = time else sold = time


        val xxxm = if (nbPhotos.isBlank()) 0 else nbPhotos.filter { it.isDigit() }.toInt()
        val today = LocalDateTime.now()
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val df = today.format(format)
        val todayL = today.toEpochSecond(ZoneOffset.UTC) * 1000 + today.get(ChronoField.MILLI_OF_SECOND)
        val date = today.minusDays(2).toEpochSecond(ZoneOffset.UTC)

        val op = LocalDate.now()

        Log.d(
            TAG, "type = $type \n " +
                    "time = $time \n" +
                    "price = $miniPrice && $maxiPrice \n " +
                    "surface = $miniSurface && $maxiSurface \n" +
                    "release = $release || sold = $sold \n " +
                    "nbPhotos = ${xxxm} \n " +
                    "period = $period // df = $df " +
                    "today = $today // $todayL , date = $date"
        )
        //1619311324
        //1619312244230
        //1619239331888
        //1619239331877
        //1619272800000
        //1619276400000
        //2021-04-25T02:09:20.440

        search(type, zone, miniPrice, maxiPrice, release, status, miniSurface, maxiSurface, nearest, xxxm)
    }

    fun search(
        type: String,
        zone: String,
        minPrice: Float,
        maxPrice: Float,
        release: LocalDate?,
        status: Boolean,
        minSurface: Int,
        maxSurface: Int,
        nearest: String,
        nbPhotos: Int
    ) = viewModelScope.launch {
        //currentSearchParametersRepository.setParams(CurrentSearchParametersRepository.Params(minSurface))
        //_current.value = photoRepository.search(nbPhotos).first()
        //_current.value = realEstateRepository.searchPhoto().first()
        //_current.value = realEstateRepository.searchDate(release).first()
        val value = realEstateRepository.searchDate(release).first()
        currentSearchParametersRepository.setCurrent(value)
        /*
        _current.value = realEstateRepository.search(
            type,
            zone,
            minPrice,
            maxPrice,
            release,
            status,
            minSurface,
            maxSurface,
            nearest,
            nbPhotos
        ).first()

         */
    }

    private fun showInvalidInputMessage(msg: String) = viewModelScope.launch {
        _searchEvents.emit(SearchRealEstateEvent.ShowInvalidInputMessage(msg))
    }

    sealed class SearchRealEstateEvent {
        data class ShowInvalidInputMessage(val msg: String) : SearchRealEstateEvent()
        data class BackWithResult(val result: List<RealEstate>) : SearchRealEstateEvent()
    }
}
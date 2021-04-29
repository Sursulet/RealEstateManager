package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentSearchParametersRepository @Inject constructor() {
    //private val current = MutableStateFlow<Params?>(null)
    private val _current = MutableStateFlow<List<RealEstate>?>(null)
    val current = _current.asStateFlow()
    fun setCurrent(value: List<RealEstate>) { _current.value = value }
    /*
    fun setParams(params: Params) { current.value = params }
    fun getSearchParamsFlow(): Flow<Params> = current.filterNotNull()
    data class Params (val photoCount: Int)

     */
}
package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.utils.SearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchQueryRepository @Inject constructor() {
    private val _searchQuery = MutableStateFlow<SearchQuery?>(null)
    val searchQuery: Flow<SearchQuery?> = _searchQuery.asStateFlow()

    fun setCurrent(query: SearchQuery) {
        _searchQuery.value = query
    }
}


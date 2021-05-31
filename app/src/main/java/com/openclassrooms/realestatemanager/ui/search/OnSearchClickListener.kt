package com.openclassrooms.realestatemanager.ui.search

import com.openclassrooms.realestatemanager.utils.SearchQuery

interface OnSearchClickListener {
    fun onSearchClick(searchQuery: SearchQuery)
}
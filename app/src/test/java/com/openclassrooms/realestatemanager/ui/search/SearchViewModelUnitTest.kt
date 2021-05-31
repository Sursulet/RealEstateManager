package com.openclassrooms.realestatemanager.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.openclassrooms.realestatemanager.repositories.SearchQueryRepository
import com.openclassrooms.realestatemanager.utilities.MainCoroutineRule
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SearchViewModel

    private val searchQueryRepository = mockkClass(SearchQueryRepository::class)

    @Before
    fun setUp() {
        viewModel = SearchViewModel(searchQueryRepository)
    }

    @Test
    fun `insert minPrice with maxPrice empty` () {
        viewModel.minPrice = "12"
        viewModel.onSearchClick()
        val error = SearchViewModel.SearchRealEstateEvent.ShowInvalidInputMessage(error = SearchViewModel.MESSAGE.MAX_PRICE)
        assertThat(viewModel.searchEvents.value).isEqualTo(error)
    }

    @Test
    fun `insert maxPrice with minPrice empty` () {
        viewModel.maxPrice = "122"
        viewModel.onSearchClick()
        val error = SearchViewModel.SearchRealEstateEvent.ShowInvalidInputMessage(error = SearchViewModel.MESSAGE.MIN_PRICE)
        assertThat(viewModel.searchEvents.value).isEqualTo(error)
    }

    @Test
    fun `insert minSurface with maxSurface empty` () {
        viewModel.minSurface = "12"
        viewModel.onSearchClick()
        val error = SearchViewModel.SearchRealEstateEvent.ShowInvalidInputMessage(error = SearchViewModel.MESSAGE.MAX_SURFACE)
        assertThat(viewModel.searchEvents.value).isEqualTo(error)
    }

    @Test
    fun `insert maxSurface with minSurface empty` () {
        viewModel.maxSurface = "12"
        viewModel.onSearchClick()
        val error = SearchViewModel.SearchRealEstateEvent.ShowInvalidInputMessage(error = SearchViewModel.MESSAGE.MIN_SURFACE)
        assertThat(viewModel.searchEvents.value).isEqualTo(error)
    }

    @Test
    fun `insert numberTime with unitTime empty` () {
        viewModel.nbTime = "12"
        viewModel.onSearchClick()
        val error = SearchViewModel.SearchRealEstateEvent.ShowInvalidInputMessage(error = SearchViewModel.MESSAGE.UNIT_TIME)
        assertThat(viewModel.searchEvents.value).isEqualTo(error)
    }

    @Test
    fun `insert unitTime with numberTime empty` () {
        viewModel.unitTime = "12"
        viewModel.onSearchClick()
        val error = SearchViewModel.SearchRealEstateEvent.ShowInvalidInputMessage(error = SearchViewModel.MESSAGE.NUMBER_TIME)
        assertThat(viewModel.searchEvents.value).isEqualTo(error)
    }
}
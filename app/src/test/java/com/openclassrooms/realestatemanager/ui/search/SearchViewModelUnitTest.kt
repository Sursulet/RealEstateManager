package com.openclassrooms.realestatemanager.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utilities.MainCoroutineRule
import com.openclassrooms.realestatemanager.utilities.getValue
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SearchViewModelUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SearchViewModel

    private val realEstateRepository = mockkClass(RealEstateRepository::class)

    @Before
    fun setUp() {
    }

    @Test
    fun test() {
        val value = getValue(viewModel.current)
    }
}
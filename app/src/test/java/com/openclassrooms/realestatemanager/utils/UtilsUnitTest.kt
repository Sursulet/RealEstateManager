package com.openclassrooms.realestatemanager.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UtilsUnitTest {

    @Test
    fun convertDollarToEuro() {
        val result = Utils.convertDollarToEuro(5)
        assertThat(result).isEqualTo(4)
    }

    @Test
    fun convertEuroToDollar() {
        val result = Utils.convertEuroToDollar(5)
        assertThat(result).isEqualTo(6)
    }

    @Test
    fun getTodayDate() {
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val result = Utils.getTodayDate()
        assertThat(result).isEqualTo(date)
    }

    @Test
    fun formattedAddress() { //TODO ERREUR
        val address = "16 Rue Auguste Perret 75013 Paris"
        val result = Utils.formattedAddress(address)
        assertThat(result).isEqualTo(" \n $address")
    }

}
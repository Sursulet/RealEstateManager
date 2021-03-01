package com.openclassrooms.realestatemanager.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilsTest {

    @Test
    fun isInternetAvailable() {
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi enable")
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = Utils.isInternetAvailable(context)
        Truth.assertThat(result).isTrue()
    }
}
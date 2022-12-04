package com.dev_mohmmadelefranji.b_store_app

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented Test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under Test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.dev_mohmmadelefranji.b_store_app", appContext.packageName)
    }
}
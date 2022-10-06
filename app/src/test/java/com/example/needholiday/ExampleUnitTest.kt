package com.example.needholiday

import androidx.test.core.app.ApplicationProvider
import com.example.needholiday.repository.SharedPref
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(
    RobolectricTestRunner::class)
class ExampleUnitTest {
    @Mock
    lateinit var sp: SharedPref

     private var sp1: SharedPref? = null

    @Before
    fun initMock() {
        MockitoAnnotations.openMocks(this)
    }
    @Before
    fun init(){
        sp1 = SharedPref(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testSharedPref() {
        Mockito.`when`(sp.language).thenReturn("uk")
        assertEquals(sp.language, "uk")
    }

    @Test
    fun testDefaultSharedPrefValue() {
        assertEquals(sp1?.language, "en")
    }
}
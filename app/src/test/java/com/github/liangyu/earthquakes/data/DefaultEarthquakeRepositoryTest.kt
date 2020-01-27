package com.github.liangyu.earthquakes.data

import com.github.liangyu.earthquakes.data.networking.GeoNameEarthquakeService
import com.github.liangyu.earthquakes.data.networking.response.EarthquakeResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultEarthquakeRepositoryTest {

    private val geoNameEarthquakeService: GeoNameEarthquakeService = mockk()
    private val coroutinesDispatcher = Dispatchers.Unconfined
    private lateinit var sut: DefaultEarthquakeRepository

    @Before
    fun setup() {
        sut = DefaultEarthquakeRepository(geoNameEarthquakeService, coroutinesDispatcher)
    }

    @Test
    fun `getEarthquakes empty cache with empty remote response`() = runBlockingTest {
        //Arrange
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { emptyEarthquakeResponse }
        //Act
        val result = sut.getEarthquakes(false)
        //Assert
        result.succeeded shouldEqual result.succeeded
        (result as Result.Success).data shouldEqual emptyList()
    }

    @Test
    fun `getEarthquakes empty cache with remote response`() = runBlockingTest {
        //Arrange
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { earthquakeResponse }
        //Act
        val result = sut.getEarthquakes(false)
        //Assert
        result.succeeded shouldEqual result.succeeded
        (result as Result.Success).data shouldEqual earthquakeResponse.earthquakeEntities
    }

    @Test
    fun `getEarthquakes should return cache first`() = runBlockingTest {
        //Arrange
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { earthquakeResponse }
        sut.getEarthquakes(false)
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { newEarthquakeResponse }
        //Act
        val newResult = sut.getEarthquakes(false)
        newResult.succeeded shouldEqual newResult.succeeded
        (newResult as Result.Success).data shouldEqual earthquakeResponse.earthquakeEntities
    }

    @Test
    fun `getEarthquakes(true) force update should return remote data`() = runBlockingTest {
        //Arrange
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { earthquakeResponse }
        sut.getEarthquakes(false)
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { newEarthquakeResponse }
        //Act
        val newResult = sut.getEarthquakes(true)
        newResult.succeeded shouldEqual newResult.succeeded
        (newResult as Result.Success).data shouldEqual newEarthquakeResponse.earthquakeEntities
    }

    @Test
    fun `getEarthquake empty cache with empty remote response`() = runBlockingTest {
        //Arrange
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { emptyEarthquakeResponse }
        //Act
        val result = sut.getEarthquake("eq1",false)
        //Assert
        result.succeeded shouldEqual false
    }

    @Test
    fun `getEarthquake empty cache with remote response`() = runBlockingTest {
        //Arrange
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { earthquakeResponse }
        //Act
        val result = sut.getEarthquake("eq1",false)
        //Assert
        result.succeeded shouldEqual result.succeeded
        (result as Result.Success).data shouldEqual eq1
    }

    @Test
    fun `getEarthquake should return cache first`() = runBlockingTest {
        //Arrange
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { earthquakeResponse }
        sut.getEarthquakes(false)
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { newEarthquakeResponse }
        //Act
        val newResult = sut.getEarthquake("eq1",false)
        newResult.succeeded shouldEqual newResult.succeeded
        (newResult as Result.Success).data shouldEqual eq1
    }

    @Test
    fun `getEarthquake(true) force update should return remote data`() = runBlockingTest {
        //Arrange
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { earthquakeResponse }
        sut.getEarthquakes(false)
        coEvery { geoNameEarthquakeService.earthquakeAsync() } returns GlobalScope.async { newEarthquakeResponse }
        sut.getEarthquakes(true)
        //Act
        val newResult = sut.getEarthquake("new eq",true)
        newResult.succeeded shouldEqual newResult.succeeded
        (newResult as Result.Success).data shouldEqual newEq
    }

    //------------------------- Helper ----------------------------------------------------
    private val eq1 = EarthquakeEntity("eq1", "datetime 1", 10.0, -15.5, 10.2, 8.6, "us")
    private val eq2 = EarthquakeEntity("eq2", "datetime 2", 12.0, -11.5, 80.2, 7.6, "us")
    private val eq3 = EarthquakeEntity("eq3", "datetime 3", 13.0, -15.8, 90.2, 2.6, "us")
    private val newEq = EarthquakeEntity("new eq", "datetime 4", 10.0, -15.5, 10.2, 9.6, "us")
    private val earthquakeResponse = EarthquakeResponse(listOf(eq1, eq2, eq3))
    private val emptyEarthquakeResponse = EarthquakeResponse(emptyList())
    private val newEarthquakeResponse = EarthquakeResponse(listOf(newEq))
}

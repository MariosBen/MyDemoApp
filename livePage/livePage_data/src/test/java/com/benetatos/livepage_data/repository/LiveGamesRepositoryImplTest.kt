package com.benetatos.livepage_data.repository

import com.benetatos.livepage_data.remote.LiveGamesApi
import com.benetatos.livepage_data.remote.malformedLiveGamesResponse
import com.benetatos.livepage_data.remote.repository.LiveGamesRepositoryImpl
import com.benetatos.livepage_data.remote.validLiveGamesResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LiveGamesRepositoryImplTest {

    private lateinit var repository: LiveGamesRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: LiveGamesApi


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder().writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS).connectTimeout(1, TimeUnit.SECONDS).build()

        api = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).baseUrl(mockWebServer.url("/")).build()
            .create(LiveGamesApi::class.java)

        repository = LiveGamesRepositoryImpl(
            dao = mockk(relaxed = true), api
        )
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun `get games , valid response , return failure`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(validLiveGamesResponse)
        )

        val result = repository.getLiveGames()

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `get games , invalid response , return results`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(403).setBody(validLiveGamesResponse)
        )

        val result = repository.getLiveGames()

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `get games , malformed response , return failure`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setBody(malformedLiveGamesResponse)
        )

        val result = repository.getLiveGames()

        assertThat(result.isSuccess).isTrue()
    }
}
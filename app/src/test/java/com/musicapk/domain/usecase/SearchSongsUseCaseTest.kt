package com.musicapk.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.musicapk.domain.model.Song
import com.musicapk.domain.repository.MusicRepository
import com.musicapk.domain.util.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for SearchSongsUseCase
 * 
 * This demonstrates:
 * - Testing use cases
 * - Testing business logic
 * - Flow testing with Turbine
 */
class SearchSongsUseCaseTest {
    
    private lateinit var musicRepository: MusicRepository
    private lateinit var useCase: SearchSongsUseCase
    
    private val testSong = Song(
        id = "1",
        title = "Test Song",
        artist = "Test Artist",
        album = "Test Album",
        duration = 180000L,
        artworkUri = null,
        audioUri = "test://audio",
        dateAdded = System.currentTimeMillis(),
        isFavorite = false
    )
    
    @Before
    fun setup() {
        musicRepository = mockk()
        useCase = SearchSongsUseCase(musicRepository)
    }
    
    @Test
    fun `invoke with blank query should return empty list`() = runTest {
        // Given
        val query = "   "
        
        // When/Then
        useCase(query).test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEmpty()
            awaitComplete()
        }
        
        // Should not call repository
        verify(exactly = 0) { musicRepository.searchSongs(any()) }
    }
    
    @Test
    fun `invoke with valid query should call repository`() = runTest {
        // Given
        val query = "test"
        val searchResults = listOf(testSong)
        every { musicRepository.searchSongs(query) } returns flowOf(
            Result.Success(searchResults)
        )
        
        // When
        useCase(query).test {
            val result = awaitItem()
            
            // Then
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEqualTo(searchResults)
            awaitComplete()
        }
        
        verify { musicRepository.searchSongs(query) }
    }
    
    @Test
    fun `invoke with empty string should return empty list`() = runTest {
        // Given
        val query = ""
        
        // When/Then
        useCase(query).test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEmpty()
            awaitComplete()
        }
    }
}



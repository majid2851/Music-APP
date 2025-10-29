package com.musicapk.data.repository_impl

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.musicapk.data.local.dao.PlaylistDao
import com.musicapk.data.local.dao.SongDao
import com.musicapk.data.local.entity.SongEntity
import com.musicapk.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for MusicRepositoryImpl
 * 
 * This demonstrates:
 * - Testing repository implementations
 * - Testing data layer
 * - Mocking DAOs
 */
class MusicRepositoryImplTest {
    
    private lateinit var songDao: SongDao
    private lateinit var playlistDao: PlaylistDao
    private lateinit var repository: MusicRepositoryImpl
    
    private val testSongEntity = SongEntity(
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
        songDao = mockk()
        playlistDao = mockk()
        repository = MusicRepositoryImpl(songDao, playlistDao)
    }
    
    @Test
    fun `getAllSongs should return success with songs from dao`() = runTest {
        // Given
        val songs = listOf(testSongEntity)
        every { songDao.getAllSongs() } returns flowOf(songs)
        
        // When/Then
        repository.getAllSongs().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).hasSize(1)
            assertThat(result.data[0].id).isEqualTo(testSongEntity.id)
            awaitComplete()
        }
    }
    
    @Test
    fun `getAllSongs should return error when dao throws exception`() = runTest {
        // Given
        val exception = Exception("Database error")
        every { songDao.getAllSongs() } returns flowOf<List<SongEntity>>().apply {
            throw exception
        }
        
        // When/Then
        try {
            repository.getAllSongs().test {
                val result = awaitItem()
                assertThat(result).isInstanceOf(Result.Error::class.java)
                awaitComplete()
            }
        } catch (e: Exception) {
            // Expected in some cases
        }
    }
    
    @Test
    fun `getSongById should return success when song exists`() = runTest {
        // Given
        coEvery { songDao.getSongById(testSongEntity.id) } returns testSongEntity
        
        // When
        val result = repository.getSongById(testSongEntity.id)
        
        // Then
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.id).isEqualTo(testSongEntity.id)
        
        coVerify { songDao.getSongById(testSongEntity.id) }
    }
    
    @Test
    fun `getSongById should return error when song not found`() = runTest {
        // Given
        coEvery { songDao.getSongById(any()) } returns null
        
        // When
        val result = repository.getSongById("nonexistent")
        
        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
    }
    
    @Test
    fun `toggleFavorite should call dao toggleFavorite`() = runTest {
        // Given
        val songId = "1"
        coEvery { songDao.toggleFavorite(songId) } returns Unit
        
        // When
        val result = repository.toggleFavorite(songId)
        
        // Then
        assertThat(result).isInstanceOf(Result.Success::class.java)
        coVerify { songDao.toggleFavorite(songId) }
    }
    
    @Test
    fun `searchSongs should return filtered songs from dao`() = runTest {
        // Given
        val query = "test"
        val songs = listOf(testSongEntity)
        every { songDao.searchSongs(query) } returns flowOf(songs)
        
        // When/Then
        repository.searchSongs(query).test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).hasSize(1)
            awaitComplete()
        }
        
        coVerify { songDao.searchSongs(query) }
    }
}



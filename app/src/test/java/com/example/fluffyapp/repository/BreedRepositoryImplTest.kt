package com.example.fluffyapp.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.example.fluffyapp.data.local.dao.BreedDao
import com.example.fluffyapp.data.local.dao.FavouriteBreedDao
import com.example.fluffyapp.data.local.database.BreedDatabase
import com.example.fluffyapp.data.mapper.toBreedDetail
import com.example.fluffyapp.data.mapper.toFavouriteBreed
import com.example.fluffyapp.data.mapper.toFavouriteEntity
import com.example.fluffyapp.data.remote.BreedApi
import com.example.fluffyapp.data.repository.BreedRepositoryImpl
import com.example.fluffyapp.domain.repository.BreedRepository
import com.example.fluffyapp.model.TestData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BreedRepositoryImplTest {
    private lateinit var breedRepository: BreedRepository
    private val mockBreedApi = mockk<BreedApi>()
    private val mockBreedDb = mockk<BreedDatabase>()
    private val mockBreedDao = mockk<BreedDao>()
    private val mockFavouriteBreedDao = mockk<FavouriteBreedDao>()
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        breedRepository = BreedRepositoryImpl(
            mockBreedApi,
            mockBreedDb,
            mockBreedDao,
            mockFavouriteBreedDao,
            dispatcher
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun shouldInsertFavouriteBreedSuccessfully() = runTest {

        coEvery { mockFavouriteBreedDao.insertFavouriteBreed(any()) } returns Unit

        val favouriteBreed = TestData.favouriteBreed

        val expected = favouriteBreed.toFavouriteEntity()

        breedRepository.insertFavouriteBreed(favouriteBreed)

        coVerify(exactly = 1) { mockFavouriteBreedDao.insertFavouriteBreed(expected) }

    }

    @Test
    fun shouldReturnFavouriteBreedIdFromDb() = runTest {
        val expected = TestData.favouriteId

        every { mockFavouriteBreedDao.getFavouritesId() } returns flowOf(expected)

        val actual = breedRepository.getFavoriteBreedId().first()

        assertThat(actual).isEqualTo(expected)

        verify(exactly = 1) { mockFavouriteBreedDao.getFavouritesId() }
    }

    @Test
    fun shouldRemoveBreedFromFavouriteById() = runTest {

        coEvery { mockFavouriteBreedDao.removeBreedFromFavourite(TestData.id) } returns Unit

        breedRepository.removeBreedFromFavourite(TestData.id)

        coVerify(exactly = 1) { mockFavouriteBreedDao.removeBreedFromFavourite(TestData.id) }

    }

    @Test
    fun shouldGetFavouriteBreeds() = runTest {
        val expected = TestData.favouriteBreedEntity

        every { mockFavouriteBreedDao.getFavouriteBreeds() } returns flowOf(expected)

        val actual = breedRepository.getFavoriteBreeds().first()

        assertThat(actual).isEqualTo(expected.map { it.toFavouriteBreed() })

        verify(exactly = 1) { mockFavouriteBreedDao.getFavouriteBreeds() }

    }

    @Test
    fun shouldGetBreedDetailById() = runTest {
        val expected = TestData.breedEntity

        every { mockBreedDao.findBreedById(TestData.id) } returns flowOf(expected)

        val actual = breedRepository.getBreedDetail(TestData.id).first()

        assertThat(actual).isEqualTo(expected.toBreedDetail())

        verify { mockBreedDao.findBreedById(TestData.id) }

    }

    @Test
    fun shouldGetAllBreedsFromPagingSource() = runTest {
        val entity = listOf(TestData.breedEntity)

        val pagingConfig = PagingConfig(20)
        val pagingSource = FakePagingSource(entity)

        every { mockBreedDao.getBreeds(any()) } returns pagingSource

        breedRepository.getBreeds(null).first()

        val pager = TestPager(pagingConfig, pagingSource)

        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertThat(result.data).containsExactlyElementsIn(entity).inOrder()
    }

}
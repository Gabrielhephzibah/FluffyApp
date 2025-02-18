package com.example.fluffyapp.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.example.fluffyapp.data.local.dao.BreedDao
import com.example.fluffyapp.data.local.dao.FavouriteBreedDao
import com.example.fluffyapp.data.local.database.BreedDatabase
import com.example.fluffyapp.data.local.entity.BreedEntity
import com.example.fluffyapp.data.local.entity.FavouriteBreedEntity
import com.example.fluffyapp.data.mapper.toBreedDetail
import com.example.fluffyapp.data.mapper.toFavouriteBreed
import com.example.fluffyapp.data.mapper.toFavouriteEntity
import com.example.fluffyapp.data.remote.BreedApi
import com.example.fluffyapp.data.repository.BreedRepositoryImpl
import com.example.fluffyapp.domain.model.FavouriteBreed
import com.example.fluffyapp.domain.repository.BreedRepository
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
   fun setUp(){
      breedRepository = BreedRepositoryImpl(mockBreedApi, mockBreedDb, mockBreedDao, mockFavouriteBreedDao, dispatcher)
   }

   @After
   fun tearDown(){
      unmockkAll()
   }

   @Test
   fun shouldInsertFavouriteBreedSuccessfully() = runTest{

      coEvery { mockFavouriteBreedDao.insertFavouriteBreed(any()) } returns Unit

      val favouriteBreed = FavouriteBreed(
         breedId = "ert",
         breedName = "HIPPOP",
         url = "https",
         lifespan = "3-5"
      )
      val expectedEntity = favouriteBreed.toFavouriteEntity()

      breedRepository.insertFavouriteBreed(favouriteBreed)

      coVerify(exactly = 1) { mockFavouriteBreedDao.insertFavouriteBreed(expectedEntity)  }

   }

   @Test
   fun shouldReturnFavouriteBreedIdFromDb() = runTest {
      val expectedId = listOf("arg", "ert", "red")
      every { mockFavouriteBreedDao.getFavouritesId() } returns flowOf(expectedId)

      val result = breedRepository.getFavoriteBreedId().first()

      assertThat(result).isEqualTo(expectedId)

      verify(exactly = 1) { mockFavouriteBreedDao.getFavouritesId() }
   }

   @Test
   fun shouldRemoveBreedFromFavouriteById() = runTest {

      coEvery { mockFavouriteBreedDao.removeBreedFromFavourite("arg") } returns Unit

      breedRepository.removeBreedFromFavourite("arg")

      coVerify(exactly = 1) { mockFavouriteBreedDao.removeBreedFromFavourite("arg") }

   }

   @Test
   fun shouldGetFavouriteBreeds() = runTest {
      val expected = listOf(FavouriteBreedEntity(
         breedId = "ert",
         breedName = "HIPPOP",
         url = "https",
         lifespan = "3-5"
      ))
      every { mockFavouriteBreedDao.getFavouriteBreeds() } returns flowOf(expected)

      val actual = breedRepository.getFavoriteBreeds().first()

      assertThat(actual).isEqualTo(expected.map { it.toFavouriteBreed() })

      verify(exactly = 1) { mockFavouriteBreedDao.getFavouriteBreeds() }

   }

   @Test
   fun shouldGetBreedDetailById() = runTest{
      val expected = BreedEntity(
         breedId = "ard",
         breedName = "tolu",
         url = "utl",
         origin = "origin",
         temperament = "temperament",
         description = "description",
         lifespan = "lifespan",
      )

      every { mockBreedDao.findBreedById("ard") } returns flowOf(expected)

      val actual = breedRepository.getBreedDetail("ard").first()

      assertThat(actual).isEqualTo(expected.toBreedDetail())

      verify { mockBreedDao.findBreedById("ard") }

   }

   @Test
   fun shouldGetAllBreedsFromPagingSource() = runTest {
      val entity = listOf(BreedEntity(
         breedId = "ard",
         breedName = "tolu",
         url = "utl",
         origin = "origin",
         temperament = "temperament",
         description = "description",
         lifespan = "lifespan",
      ))
      val pagingConfig = PagingConfig(20)
      val pagingSource = FakePagingSource(entity)
      every { mockBreedDao.getBreeds(any()) } returns pagingSource

      breedRepository.getBreeds(null).first()

      val pager = TestPager(pagingConfig, pagingSource)

      val result = pager.refresh() as PagingSource.LoadResult.Page

      assertThat(result.data).containsExactlyElementsIn(entity).inOrder()
   }

}
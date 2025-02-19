package com.example.fluffyapp.ui.breed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.fluffyapp.domain.model.Breed
import com.example.fluffyapp.domain.model.FavouriteBreed
import com.example.fluffyapp.domain.repository.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class BreedViewModel @Inject constructor(
    private val repository: BreedRepository,
) : ViewModel() {

    private val _breeds = MutableStateFlow<PagingData<Breed>>(PagingData.empty())
    val breeds: MutableStateFlow<PagingData<Breed>> = _breeds

    private val queryFlow = MutableStateFlow("")

    init {
        pagingWithFavourite()
    }

    fun searchRepositories(query: String) {
        queryFlow.value = query
    }

    private fun pagingWithFavourite() {
        viewModelScope.launch {
            queryFlow
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    repository.getBreeds(query).cachedIn(viewModelScope)
                        .combine(repository.getFavoriteBreedId()) { cat, favourite ->
                            _breeds.value = cat.map {
                                it.copy(
                                    breedId = it.breedId,
                                    breedName = it.breedName,
                                    url = it.url,
                                    lifespan = it.lifespan,
                                    isFavourite = favourite.contains(it.breedId)
                                )
                            }
                        }

                }.collect()
        }
    }

    fun insertFavouriteBreed(favouriteBreed: FavouriteBreed) {
        viewModelScope.launch {
            repository.insertFavouriteBreed(favouriteBreed)
        }
    }
    fun removeFavouriteBreed(id: String) {
        viewModelScope.launch {
            repository.removeBreedFromFavourite(id)
        }
    }
}


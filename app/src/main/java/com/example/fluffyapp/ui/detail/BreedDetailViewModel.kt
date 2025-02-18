package com.example.fluffyapp.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fluffyapp.domain.model.BreedDetail
import com.example.fluffyapp.domain.model.FavouriteBreed
import com.example.fluffyapp.domain.repository.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val repository: BreedRepository
): ViewModel() {

    var state by mutableStateOf(BreedDetailScreenState())

    fun getBreedDetail(id:String){
        viewModelScope.launch {
            repository.getBreedDetail(id).combine(repository.getFavoriteBreedId()){ response, favouriteId ->
                state = state.copy(
                    breedDetail = BreedDetail(
                        breedId = response.breedId,
                        breedName = response.breedName,
                        url = response.url,
                        origin = response.origin,
                        temperament = response.temperament,
                        description = response.description,
                        lifespan = response.lifespan,
                        isFavorite = favouriteId.contains(response.breedId)
                    )
                )
              }.collect()
            }
        }

    fun insertFavourite(favouriteBreed: FavouriteBreed){
        viewModelScope.launch {
            repository.insertFavouriteBreed(favouriteBreed)
        }
    }

    fun removeFavourite(id: String){
        viewModelScope.launch {
            repository.removeBreedFromFavourite(id)
        }
    }

}
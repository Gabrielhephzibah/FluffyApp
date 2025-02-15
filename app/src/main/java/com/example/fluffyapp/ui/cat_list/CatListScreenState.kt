package com.example.fluffyapp.ui.cat_list

import com.example.fluffyapp.domain.model.Cat

data class CatListScreenState(
    val data : List<Cat> = emptyList(),
    val error : Throwable? = null,
    val loading : Boolean? = false
)
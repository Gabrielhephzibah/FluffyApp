package com.example.fluffyapp.utils

sealed class Resource<out T> (
    val data: T? = null,
    val error: Throwable? = null
){
    data class Success<T>(val value: T): Resource<T>(value)
    data class Error<T>(val exception: Throwable): Resource<T>(error =  exception)
    data object Loading: Resource<Nothing>()
}
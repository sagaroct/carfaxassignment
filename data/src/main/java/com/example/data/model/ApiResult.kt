package com.example.data.model

sealed class ApiResult<out T> {
        object Loading : ApiResult<Nothing>()
        data class Success<out R>(val data: R) : ApiResult<R>()
        object Error : ApiResult<Nothing>() //TODO: We can return an error object which has response code and msg inside ApiResult.
    }
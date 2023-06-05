package com.example.data.model.local

sealed class ApiResult<out T> {
        object Loading : ApiResult<Nothing>()
        data class Success<out R>(val data: R) : ApiResult<R>()
        object Error : ApiResult<Nothing>()
    }
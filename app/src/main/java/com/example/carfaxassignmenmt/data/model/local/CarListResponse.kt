package com.example.carfaxassignmenmt.data.model.local

sealed class CarListResponse {
        object Loading : CarListResponse()
        data class Success(val carListItems: List<CarListItem>) : CarListResponse()
        object Error : CarListResponse()
    }
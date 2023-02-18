package com.example.carfaxassignmenmt.data.repository

import com.example.carfaxassignmenmt.data.database.CarListingDatabase
import com.example.carfaxassignmenmt.data.model.local.CarListItem
import com.example.carfaxassignmenmt.data.model.local.CarListItemMapper
import com.example.carfaxassignmenmt.data.model.remote.Response
import com.example.carfaxassignmenmt.data.network.CarListingApiService
import com.example.carfaxassignmenmt.data.network.ICarListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CarListRepository @Inject constructor(private val carListingApiService: CarListingApiService,
                                            carListingDatabase: CarListingDatabase,
                                            private val carListItemMapper: CarListItemMapper,
                                            private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ICarListRepository {


    companion object {
        private const val TAG = "CarListRepository"
    }

    private val localSource = carListingDatabase.getCarListingLocalSource()

    override fun getCarList(): Flow<List<CarListItem>> {
        return flow{emit(carListingApiService.getCarListings())}
            .map{ saveCarListings(it) }
            .catch {emit(getLocalCarList())}
            .flowOn(dispatcher)
    }

    private suspend fun saveCarListings(item: Response): List<CarListItem> {
        localSource.deleteAll()
        localSource.insertOrUpdate(carListItemMapper.dbMapper.map(item.listings))
        return getLocalCarList()
    }

    private suspend fun getLocalCarList(): List<CarListItem> {
        return carListItemMapper.localMapper.map(localSource.get())
    }


}
package com.example.data.carlist.repository

import com.example.data.database.CarListingDatabase
import com.example.data.model.local.CarListItemMapper
import com.example.data.model.remote.Response
import com.example.data.carlist.network.CarListingApiService
import com.example.domain.models.CarListItem
import com.example.domain.repositories.ICarListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarListRepository @Inject constructor(private val carListingApiService: CarListingApiService,
                                            carListingDatabase: CarListingDatabase,
                                            private val carListItemMapper: CarListItemMapper,
                                            private val dispatcher: CoroutineDispatcher = Dispatchers.IO) :
    ICarListRepository {


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

    override fun getCarItem(id: String): Flow<CarListItem> {
        return flow{ emit(localSource.get(id)) }
            .map{ carListItemMapper.localMapper.map(it) }
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
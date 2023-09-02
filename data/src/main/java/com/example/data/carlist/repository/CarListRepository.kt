package com.example.data.carlist.repository

import com.example.data.carlist.model.db.CarListingLocalSource
import com.example.data.carlist.model.mapper.CarListItemMapper
import com.example.data.carlist.model.remote.Response
import com.example.data.carlist.network.CarListingApiService
import com.example.domain.models.ApiResult
import com.example.domain.models.CarItem
import com.example.domain.repositories.ICarListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarListRepository @Inject constructor(
    private val carListingApiService: CarListingApiService,
    private val localSource: CarListingLocalSource,
    private val carListItemMapper: CarListItemMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICarListRepository {

    companion object {
        private const val TAG = "CarListRepository"
    }

    override fun getCarList(): Flow<ApiResult<List<CarItem>>> {
        return flow{emit(carListingApiService.getCarListings())}
            .map{ ApiResult.Success(saveCarListings(it)) }
            .catch { exception -> ApiResult.Error(exception) }
            .flowOn(dispatcher)
    }

    override fun getCarItem(id: String): Flow<ApiResult<CarItem>> {
        return localSource.get(id)
            .map{ ApiResult.Success(carListItemMapper.localMapper.map(it)) }
            .catch { exception -> ApiResult.Error(exception) }
            .flowOn(dispatcher)
    }

    private suspend fun saveCarListings(item: Response): List<CarItem> {
        localSource.deleteAll()
        localSource.insertOrUpdate(carListItemMapper.dbMapper.map(item.listings))
        return getLocalCarList()
    }

    private suspend fun getLocalCarList(): List<CarItem> {
        return carListItemMapper.localMapper.map(localSource.get().first())
    }

}
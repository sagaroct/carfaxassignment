package com.example.data.carlist.model.mapper

import com.example.data.mappers.BasicMapper
import com.example.data.mappers.Mapper
import com.example.data.carlist.model.db.RoomCarListItem
import com.example.data.carlist.model.remote.RemoteCarItem
import com.example.domain.models.CarItem

/**
 * @author Sagar Pujari
 * localMapper: will convert room entity to model which will be consumed in ui.
 * dbMapper: will convert remote entity to room enity.
 */
object CarListItemMapper {

    //    val networkMapper: Mapper<ListingsItem, CarListItem> = NetworkMapper()
    val localMapper: Mapper<RoomCarListItem, CarItem> = LocalMapper()
    val dbMapper: Mapper<RemoteCarItem, RoomCarListItem> = DbMapper()

    internal class LocalMapper : BasicMapper<RoomCarListItem, CarItem>() {
        override fun map(item: RoomCarListItem): CarItem {
            return with(item) {
                CarItem(
                    vin, transmission, mileage, image, interiorColor, drivetype,
                    engine, bodytype, exteriorColor, currentPrice, phone,
                    address, year, make, model, trim, fuel)
            }
        }

    }

    internal class DbMapper : BasicMapper<RemoteCarItem, RoomCarListItem>() {
        override fun map(item: RemoteCarItem): RoomCarListItem {
            return with(item) {
                RoomCarListItem(
                    vin, transmission, mileage, images.firstPhoto.large, interiorColor, drivetype,
                    engine, bodytype, exteriorColor, currentPrice, dealer.phone,
                    dealer.address, year, make, model, trim, fuel)
            }
        }
    }
}
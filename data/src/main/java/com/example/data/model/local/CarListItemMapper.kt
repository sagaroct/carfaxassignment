package com.example.data.model.local

import com.example.data.mappers.BasicMapper
import com.example.data.mappers.Mapper
import com.example.data.model.db.RoomCarListItem
import com.example.data.model.remote.ListingsItem
import com.example.domain.models.CarListItem

/**
 * @author Sagar Pujari
 * localMapper: will convert room entity to model which will be consumed in ui.
 * dbMapper: will convert remote entity to room enity.
 */
object CarListItemMapper {

    //    val networkMapper: Mapper<ListingsItem, CarListItem> = NetworkMapper()
    val localMapper: Mapper<RoomCarListItem, CarListItem> = LocalMapper()
    val dbMapper: Mapper<ListingsItem, RoomCarListItem> = DbMapper()

    internal class LocalMapper : BasicMapper<RoomCarListItem, CarListItem>() {
        override fun map(item: RoomCarListItem): CarListItem {
            return with(item) {
                CarListItem(
                    vin, transmission, mileage, image, interiorColor, drivetype,
                    engine, bodytype, exteriorColor, currentPrice, phone,
                    address, year, make, model, trim, fuel)
            }
        }

    }

    internal class DbMapper : BasicMapper<ListingsItem, RoomCarListItem>() {
        override fun map(item: ListingsItem): RoomCarListItem {
            return with(item) {
                RoomCarListItem(
                    vin, transmission, mileage, images.firstPhoto.large, interiorColor, drivetype,
                    engine, bodytype, exteriorColor, currentPrice, dealer.phone,
                    dealer.address, year, make, model, trim, fuel)
            }
        }
    }
}
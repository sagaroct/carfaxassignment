package com.example.data.feature.vehicle.model.mapper

import com.example.data.mappers.BasicMapper
import com.example.data.feature.vehicle.model.db.VehicleEntity
import com.example.data.feature.vehicle.model.remote.RemoteVehicle
import com.example.domain.models.Vehicle

/**
 * @author Sagar Pujari
 *  RemoteToDbMapper: will convert remote entity to room enity.
 * DbToUiMapper: will convert room entity to model which will be consumed in ui.
 */
object VehicleMapper {

	 object RemoteToDbMapper : BasicMapper<RemoteVehicle, VehicleEntity>() {
		override fun map(item: RemoteVehicle): VehicleEntity =
			VehicleEntity(
				vin = item.vin,
				transmission = item.transmission,
				mileage = item.mileage,
				image = item.images.large.firstOrNull().orEmpty(),
				interiorColor = item.interiorColor,
				drivetype = item.drivetype,
				engine = item.engine,
				bodytype = item.bodytype,
				exteriorColor = item.exteriorColor,
				currentPrice = item.currentPrice,
				phone = item.dealer.phone,
				address = item.dealer.address,
				year = item.year,
				make = item.make,
				model = item.model,
				trim = item.trim,
				fuel = item.fuel
			)
	}

	object DbToUiMapper : BasicMapper<VehicleEntity, Vehicle>() {
		override fun map(item: VehicleEntity): Vehicle {
			return with(item) {
				Vehicle(
					vin = vin,
					transmission = transmission,
					mileage = mileage,
					image = image,
					interiorColor = interiorColor,
					drivetype = drivetype,
					engine = engine,
					bodytype = bodytype,
					exteriorColor = exteriorColor,
					currentPrice = currentPrice,
					phone = phone,
					address = address,
					year = year,
					make = make,
					model = model,
					trim = trim,
					fuel = fuel
				)
			}
		}
	}

}
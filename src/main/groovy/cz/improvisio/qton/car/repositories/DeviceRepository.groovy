package cz.improvisio.qton.car.repositories

import cz.improvisio.qton.car.entities.Device
import org.springframework.data.repository.CrudRepository

/**
 * Created by Osa-S on 24.06.2017.
 */
interface DeviceRepository extends CrudRepository<Device, Long> {
	Device findByUuid(String uuid)
}

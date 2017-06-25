package cz.improvisio.qton.car.repositories

import cz.improvisio.qton.car.entities.Device
import cz.improvisio.qton.car.entities.DeviceLog
import org.springframework.data.repository.CrudRepository

/**
 * Created by Osa-S on 24.06.2017.
 */
interface DeviceLogRepository extends CrudRepository<DeviceLog, Long> {

}

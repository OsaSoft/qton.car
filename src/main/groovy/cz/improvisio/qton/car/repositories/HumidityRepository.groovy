package cz.improvisio.qton.car.repositories

import cz.improvisio.qton.car.entities.Humidity
import org.springframework.data.repository.CrudRepository

/**
 * Created by Osa-S on 24.06.2017.
 */
interface HumidityRepository extends CrudRepository<Humidity, Long> {

	Humidity findByTimestamp(long l)
}

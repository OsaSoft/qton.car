package cz.improvisio.qton.car.repositories

import cz.improvisio.qton.car.entities.Temperature
import org.springframework.data.repository.CrudRepository

/**
 * Created by Osa-S on 24.06.2017.
 */
interface TemperatureRepository extends CrudRepository<Temperature, Long> {
	Temperature findFirstByOrderByTimestampDesc()
	List<Temperature> findAllByTimestampBetween(long from, long to)
}

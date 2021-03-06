package cz.improvisio.qton.car.repositories

import cz.improvisio.qton.car.entities.CO2
import org.springframework.data.repository.CrudRepository

/**
 * Created by Osa-S on 24.06.2017.
 */
interface CO2Repository extends CrudRepository<CO2, Long> {
	CO2 findByTimestamp(long l)

	CO2 findAllByTimestampBetween(long from, long to)

	CO2 findFirstByTimestampLessThanOrderByTimestampDesc(long l)
}

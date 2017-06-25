package cz.improvisio.qton.car.repositories

import cz.improvisio.qton.car.entities.Movement
import org.springframework.data.repository.CrudRepository

/**
 * Created by Osa-S on 24.06.2017.
 */
interface MovementRepository extends CrudRepository<Movement, Long> {
	
}

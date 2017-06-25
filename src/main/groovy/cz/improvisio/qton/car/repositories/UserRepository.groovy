package cz.improvisio.qton.car.repositories

import cz.improvisio.qton.car.entities.User
import org.springframework.data.repository.CrudRepository

/**
 * Created by Osa-S on 24.06.2017.
 */
interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username)
}

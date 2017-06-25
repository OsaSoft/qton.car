package cz.improvisio.qton.car.entities

import javax.persistence.*

/**
 * Created by Osa-S on 24.06.2017.
 */
@Entity
class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id
	@Column(nullable = false)
	String username
	@Column(nullable = false)
	String password

	@OneToMany(mappedBy = "user")
	List<Device> devices
}

package cz.improvisio.qton.car.entities

import javax.persistence.*

/**
 * Created by Osa-S on 24.06.2017.
 */
@Entity
class Device {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id
	String uuid

	@ManyToOne
	User user

	@OneToMany(mappedBy = "device")
	List<Movement> movements

	@OneToMany(mappedBy = "device")
	List<CO2> co2s

	@OneToMany(mappedBy = "device")
	List<Humidity> humidities

	@OneToMany(mappedBy = "device")
	List<Temperature> temperatures

	@OneToMany(mappedBy = "device")
	List<DeviceLog> devicelogs
}

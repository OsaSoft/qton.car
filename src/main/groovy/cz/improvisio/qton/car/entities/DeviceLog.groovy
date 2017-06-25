package cz.improvisio.qton.car.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

/**
 * Created by Osa-S on 24.06.2017.
 */
@Entity
class DeviceLog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id

	Float battery
	Float rssi
	Float snr
	long timestamp = new Date().time

	@ManyToOne
	Device device

	void setDate(Date date) {
		timestamp = date.time
	}

	Date getDate() {
		return new Date(timestamp)
	}
}

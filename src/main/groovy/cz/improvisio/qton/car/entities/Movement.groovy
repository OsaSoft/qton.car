package cz.improvisio.qton.car.entities

import javax.persistence.*

/**
 * Created by Osa-S on 24.06.2017.
 */
@Entity
class Movement {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id

	long value
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

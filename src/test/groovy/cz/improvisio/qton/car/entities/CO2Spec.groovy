package cz.improvisio.qton.car.entities

import spock.lang.Specification

/**
 * Created by Osa-S on 24.06.2017.
 */
class CO2Spec extends Specification {
	def setup(){}
	def cleanup(){}

	def "timestamp is default set"() {
		when:
			def co2 = new CO2()
		then:
			co2.timestamp > 0
	}

	def "setting timestamp via date works"() {
		given:
			def date = new Date()

		when:
			def co2 = new CO2()
			co2.date = date
		then:
			co2.date == date
	}
}

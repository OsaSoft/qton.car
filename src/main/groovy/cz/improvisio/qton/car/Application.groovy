package cz.improvisio.qton.car

import cz.improvisio.qton.car.entities.Device
import cz.improvisio.qton.car.entities.User
import cz.improvisio.qton.car.repositories.DeviceRepository
import cz.improvisio.qton.car.repositories.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

	static void main(String[] args) {
		SpringApplication.run Application, args
	}

	@Bean
	CommandLineRunner init(final UserRepository userRepository, final DeviceRepository deviceRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				User user = userRepository.findByUsername("user1")
				if (!user) {
					user = new User(username: "user1", password: "heslo12345")
					userRepository.save(user)
				}

				if (!deviceRepository.findByUuid("21D544")) {
					deviceRepository.save(new Device(uuid: "21D544", user: user))
				}
			}
		};
	}
}

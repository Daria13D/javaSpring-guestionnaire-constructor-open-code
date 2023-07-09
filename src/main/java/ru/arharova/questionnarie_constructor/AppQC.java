package ru.arharova.questionnarie_constructor;


import ru.arharova.questionnarie_constructor.models.Role;
import ru.arharova.questionnarie_constructor.models.Status;
import ru.arharova.questionnarie_constructor.models.User;
import ru.arharova.questionnarie_constructor.repos.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class AppQC {
    public static void main(String[] args) {
        SpringApplication.run(AppQC.class, args);
    }

	@Bean
	CommandLineRunner run(UserRepo userService) {
		return args -> {

			boolean test = userService.existsByEmail("admin@admin.ru");
			if (!userService.existsByEmail("admin@admin.ru")) {
				User user = new User();
				user.setEmail("admin@admin.ru");
				user.setPassword("qwerty");

				user.setPassword(String.valueOf(new BCryptPasswordEncoder(12).encode(user.getPassword())));
				user.setRole(Role.ADMIN);
				user.setStatus(Status.ACTIVE);
				userService.save(user);
			}
		};
	}
}

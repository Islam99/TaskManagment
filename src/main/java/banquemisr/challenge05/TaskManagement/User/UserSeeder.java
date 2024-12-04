package banquemisr.challenge05.TaskManagement.User;

import banquemisr.challenge05.TaskManagement.User.User;
import banquemisr.challenge05.TaskManagement.User.UserDao;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserSeeder {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @SneakyThrows
    @PostConstruct
    @Transactional
    public void seedUsers() {
        if (userDao.count() == 0) {
            userDao.save(new User("admin@gmail.com",passwordEncoder.encode("12345"),"ADMIN"));
            userDao.save(new User("user@gmail.com",passwordEncoder.encode("12345"),"USER"));
    }
  }
}

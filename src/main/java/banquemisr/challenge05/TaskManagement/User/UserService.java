package banquemisr.challenge05.TaskManagement.User;

import banquemisr.challenge05.TaskManagement.Exception.CustomError;
import banquemisr.challenge05.TaskManagement.Exception.CustomException;
import banquemisr.challenge05.TaskManagement.Task.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final ObjectMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    public User findByEmail(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        return user;
    }

    public User create(CreateUserDTO createUserDTO)  {

        if(userDao.findByEmail(createUserDTO.getEmail()) != null)
            throw new CustomException(CustomError.USER_ALREADY_EXISTS);
        User user = mapper.convertValue(createUserDTO,User.class);
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        return userDao.save(user);
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO,User user)  {
        if(!passwordEncoder.matches(changePasswordDTO.getOldPassword() , user.getPassword()))
            throw new CustomException(CustomError.INVALID_PASSWORD);
        if(!changePasswordDTO.getPassword().equals(changePasswordDTO.getConfirmedPassword()))
            throw new CustomException(CustomError.PASSWORD_DOESNT_MATCH);
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        userDao.save(user);
    }
}
package utn.poc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import utn.poc.configurations.UserConfigurationToken;
import utn.poc.dto.UserDtoRequest;
import utn.poc.exceptions.AlreadyExistsException;
import utn.poc.exceptions.NotFoundException;
import utn.poc.models.User;
import utn.poc.repositories.UserRepository;
import utn.poc.utils.GrantedAuthorities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static utn.poc.utils.Constants.USER_ALREADY_EXISTS;
import static utn.poc.utils.Constants.USER_KEY;
import static utn.poc.utils.Constants.USER_NOT_FOUND;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    public UserService(UserRepository userRepository, RedisTemplate<String, User> redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public User save(UserDtoRequest user){
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new AlreadyExistsException(USER_ALREADY_EXISTS);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        User newUser = userRepository.save(new User(user));

        redisTemplate.opsForHash().put(USER_KEY, newUser.getId(), newUser);

        return newUser;
    }

    public List<User> getAll(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> list = new ArrayList<>();
        String json = "";

        if(redisTemplate.opsForHash().keys(USER_KEY).isEmpty()){
            userRepository.findAll()
                    .forEach((User user) -> redisTemplate.opsForHash().put(USER_KEY, user.getId(), user));
            redisTemplate.boundHashOps(USER_KEY).expire(24L, TimeUnit.HOURS);
        }

        try {
            json = objectMapper.writeValueAsString(redisTemplate.opsForHash().values(USER_KEY));
            list = objectMapper.readValue(json, new TypeReference<List<User>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(s)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return new UserConfigurationToken(user.getUsername(), user.getPwd(), GrantedAuthorities.getGrantedAuthority(user.getRole()));
    }

    public void delete(Integer idUser) {
        User user = this.userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        redisTemplate.opsForHash().delete(USER_KEY, user.getId());

        this.userRepository.deleteById(idUser);
    }
}

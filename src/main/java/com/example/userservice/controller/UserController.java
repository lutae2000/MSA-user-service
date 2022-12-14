package com.example.userservice.controller;

import com.example.userservice.UserDto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.apache.http.protocol.HTTP;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/user-service")
public class UserController {
    @Autowired
    private Environment env;
    @Autowired
    private UserService userService;

    @Autowired
    private Greeting greeting;


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + "\n, port(local.server.port)=" + env.getProperty("local.server.port")
                + "\n, port(server.port)=" + env.getProperty("server.port")
                + "\n, gateway ip=" + env.getProperty("gateway.ip")
                + "\n, message=" + env.getProperty("greeting.message")
                + "\n, token secret=" + env.getProperty("token.secret")
                + "\n, token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    public String greeting(){
        //return environment.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable String userId){

        UserDto userDto = userService.getUserById(userId);
        ResponseUser returnUser = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnUser);
    }
}
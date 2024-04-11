package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.request.UserUpdateRequest;
import com.group.libraryapp.dto.response.UserResponse;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserServiceV2 userService;

    public  UserController(UserServiceV2 userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
    }

/*
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        // 데이터를 담아줄 dto 가 필요
        List<UserResponse> responses = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            // new UserResponse(id, name, id)  →  생성자
            // users.get(i).getName() → User.java 에 getter 추가
            // responses.add(new UserResponse(i+1, users.get(i).getName(), users.get(i).getAge()));
            responses.add(new UserResponse(i+1, users.get(i)));
        }
        return responses;
    }
        ↓ 이렇게 바뀌엇습니다
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
    }
*/


    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }
}

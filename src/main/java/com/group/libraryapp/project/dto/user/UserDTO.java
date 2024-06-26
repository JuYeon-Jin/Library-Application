package com.group.libraryapp.project.dto.user;

import com.group.libraryapp.project.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String id;
    private String username;
    private String password;

    private String signupDate;
    private boolean deleted;

    public UserDTO() {}

    public UserDTO(String id, String username, String password, String signupDate, boolean deleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.signupDate = signupDate;
        this.deleted = deleted;
    }

    // List<User>
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.signupDate = String.valueOf(user.getSignupDate());
        this.deleted = user.isDeleted();
    }
}
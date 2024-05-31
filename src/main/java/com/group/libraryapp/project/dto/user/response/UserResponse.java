package com.group.libraryapp.project.dto.user.response;

import com.group.libraryapp.project.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    public int number;
    public String id;
    public String signup_date;

    public UserResponse(User user) {
        this.id = user.getId();
        this.signup_date = user.getSignupDate();
    }

}

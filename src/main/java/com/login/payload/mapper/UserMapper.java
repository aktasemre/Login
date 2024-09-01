package com.login.payload.mapper;
import com.login.entity.user.User;
import com.login.payload.request.abstracts.BaseUserRequest;
import com.login.payload.response.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserResponse mapUserToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .email(user.getEmail())
                .role(user.getUserRole().getName())
                .build();
    }


    public User mapUserRequestToUser(BaseUserRequest userRequest) {

        return User.builder()

                .first_name(userRequest.getFirst_name())
                .last_name(userRequest.getLast_name())
                .sifre(userRequest.getSifre())

                .email(userRequest.getEmail())


                .build();

    }
}
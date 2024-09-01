package com.login.service.validator;


import com.login.entity.user.User;
import com.login.exeption.ConflictException;
import com.login.payload.messeges.ErrorMessages;
import com.login.payload.request.abstracts.AbstractUserRequest;
import com.login.payload.request.user.UpdateUserRequest;
import com.login.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {
    private final UserRepository userRepository;


    public void checkUniqueProperties(User user, AbstractUserRequest baseUserRequest){

        String updatedEmail ="";
        boolean isChanged = false;


        if(!user.getEmail().equalsIgnoreCase(baseUserRequest.getEmail())){
            updatedEmail = baseUserRequest.getEmail();
            isChanged = true;
        }

        if(isChanged){
            checkDuplicate(updatedEmail);
        }
    }

    public void checkUniquePropertiess(User user, UpdateUserRequest baseUserRequest){

        String updatedEmail ="";
        boolean isChanged = false;


        if(!user.getEmail().equalsIgnoreCase(baseUserRequest.getEmail())){
            updatedEmail = baseUserRequest.getEmail();
            isChanged = true;
        }

        if(isChanged){
            checkDuplicate(updatedEmail);
        }
    }

    public void checkDuplicate(String email){

        if(userRepository.existsByEmail(email)){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }


    }
}

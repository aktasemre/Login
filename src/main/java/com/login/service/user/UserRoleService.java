package com.login.service.user;

import com.login.entity.enums.RoleType;
import com.login.entity.user.UserRole;
import com.login.exeption.ResourceNotFoundException;
import com.login.payload.messeges.ErrorMessages;
import com.login.repository.User.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    public UserRole getUserRole(RoleType roleType){
        return userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));
    }

    public List<UserRole> getAllUserRole() {
        return  userRoleRepository.findAll();
    }
}

package com.login.service.user;
import com.login.entity.enums.RoleType;
import com.login.entity.user.User;
import com.login.entity.user.UserRole;
import com.login.payload.mapper.UserMapper;
import com.login.payload.messeges.SuccessMessages;
import com.login.payload.request.user.LoginRequest;
import com.login.payload.request.user.UserRequest;
import com.login.payload.response.ResponseMessage;
import com.login.payload.response.user.AuthResponse;
import com.login.payload.response.user.UserResponse;
import com.login.repository.User.UserRepository;
import com.login.security.jwt.JwtUtils;
import com.login.security.service.UserDetailsImpl;
import com.login.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;
        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;
        private final UserRoleService userRoleService;
        private final UserMapper userMapper;
        private final UniquePropertyValidator uniquePropertyValidator;
        private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // BCryptPasswordEncoder kullanımı

        // F01 - login
        public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {
                String email = loginRequest.getEmail();
                String password = loginRequest.getSifre();

                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String token = "Bearer " + jwtUtils.generateJwtToken(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                Set<String> roles = userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());

                Optional<String> role = roles.stream().findFirst();

                AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
                authResponse.email(userDetails.getEmail());
                authResponse.first_name(userDetails.getFirstName());
                authResponse.last_name(userDetails.getLast_name());
                authResponse.token(token.substring(7));

                role.ifPresent(authResponse::role);
                return ResponseEntity.ok(authResponse.build());
        }

        // F02 - register
        public ResponseMessage<UserResponse> saveUser(UserRequest userRequest) {
                uniquePropertyValidator.checkDuplicate(userRequest.getEmail());

                Set<UserRole> userRole = new HashSet<>();
                UserRole customer = userRoleService.getUserRole(RoleType.CUSTOMER);
                userRole.add(customer);

                User user = userMapper.mapUserRequestToUser(userRequest);
                user.setBuilt_in(Boolean.FALSE);
                user.setUserRole(customer);
                user.setSifre(passwordEncoder.encode(user.getSifre())); // Şifre hashleniyor

                User savedUser = userRepository.save(user);

                return ResponseMessage.<UserResponse>builder()
                        .message(SuccessMessages.USER_CREATED)
                        .object(userMapper.mapUserToUserResponse(savedUser))
                        .build();
        }

        public long countAllAdmins(){
                return userRepository.countAdmin(RoleType.ADMIN);
        }

        public String saveAdmin(UserRequest userRequest) {
                Set<UserRole> userRole = new HashSet<>();
                UserRole admin = userRoleService.getUserRole(RoleType.ADMIN);
                userRole.add(admin);

                User user = userMapper.mapUserRequestToUser(userRequest);
                user.setBuilt_in(Boolean.TRUE);
                user.setSifre(passwordEncoder.encode(userRequest.getSifre())); // Şifre hashleniyor
                user.setUserRole(admin);
                userRepository.save(user);

                return SuccessMessages.USER_CREATED;
        }

        public List<UserResponse> userArama(String q) {
                List<User> users = userRepository.araUser(q);
                List<UserResponse> userResponses = new ArrayList<>();
                for (User z : users) {
                        userResponses.add(userMapper.mapUserToUserResponse(z));
                }
                return userResponses;
        }

        // Şifre sıfırlama kodunu oluştur ve kaydet
        public void generateResetPasswordCode(User user) {
                user.resetPasswordCode();
                userRepository.save(user);
        }

        // Email ile kullanıcıyı bul
        public Optional<User> findByEmail(String email) {
                return userRepository.findByEmail(email);
        }

        public Optional<User> findByEmailAndResetCode(String email, String resetCode) {
                return userRepository.findByEmailAndResetPasswordCode(email, resetCode);
        }


        public void updatePassword(User user, String newPassword) {
                user.setSifre(passwordEncoder.encode(newPassword)); // Şifreyi hashleyin
                user.setResetPasswordCode(null); // Reset kodunu temizle
                userRepository.save(user);
        }
}

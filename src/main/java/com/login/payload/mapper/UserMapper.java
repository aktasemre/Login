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
                .first_name(user.getFirstName()) // İsim validasyonlu
                .last_name(user.getLastName())   // Soyisim validasyonlu
                .email(user.getEmail())          // Email validasyonlu
                .create_at(user.getCreatDate())   // Oluşturulma zamanı
                .update_at(user.getUpdateAt())   // Güncellenme zamanı
                .role(user.getUserRole().getName()) // Kullanıcı rolü
                .telephone(user.getTelephone())  // Telefon numarası validasyonlu
                .birthDate(user.getDogumTarihi())  // Doğum tarihi validasyonlu
                .birthPlace(user.getDogumYeri()) // Doğum yeri validasyonlu
                .nationality(user.getUyruk()) // Uyruk validasyonlu
                .fatherName(user.getBabaAdi()) // Baba adı validasyonlu
                .motherName(user.getAnneAdi()) // Anne adı validasyonlu
                .build();
    }


    public User mapUserRequestToUser(BaseUserRequest userRequest) {

        return User.builder()

                .firstName(userRequest.getFirst_name())    // İsim
                .lastName(userRequest.getLast_name())      // Soyisim
                .sifre(userRequest.getSifre())             // Şifre
                .email(userRequest.getEmail())             // Email
                .telephone(userRequest.getTelephone())     // Telefon numarası
                .dogumTarihi(userRequest.getBirthDate())   // Doğum tarihi
                .dogumYeri(userRequest.getBirthPlace())    // Doğum yeri
                .uyruk(userRequest.getNationality())       // Uyruk
                .babaAdi(userRequest.getFatherName())      // Baba adı
                .anneAdi(userRequest.getMotherName())      // Anne adı
                .build();

    }
}
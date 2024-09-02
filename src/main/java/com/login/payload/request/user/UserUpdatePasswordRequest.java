package com.login.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordRequest {


    private String resetKod;
    private String yeniSifre;

    private String tekrarYeniSifre;
}

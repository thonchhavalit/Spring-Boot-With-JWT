package com.example.springbootjwt.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class User {
    private String username;
    private int userID;
    private String gender;
    private String address;
    private String email;
    private String passcode;

}

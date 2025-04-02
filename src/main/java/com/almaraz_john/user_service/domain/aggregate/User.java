package com.almaraz_john.user_service.domain.aggregate;

import com.almaraz_john.user_service.application.dto.UserDTO;
import com.almaraz_john.user_service.domain.enums.Role;
import com.almaraz_john.user_service.domain.exception.PasswordNotMatchException;
import com.almaraz_john.user_service.domain.valueObjects.Email;
import com.almaraz_john.user_service.domain.valueObjects.FullName;
import com.almaraz_john.user_service.domain.valueObjects.ID;
import com.almaraz_john.user_service.domain.valueObjects.Password;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private ID id;
    private FullName fullName;
    private Email email;
    private Password password;
    private Role role;

    public void updatePassword(String currentPassword,String newPassword){
        if (!this.password.matchesPassword(currentPassword)){
            throw new PasswordNotMatchException("Password is incorrect");
        }

        this.password = new Password(newPassword);
    }

    public void updateUserProfile(UserDTO userDTO){
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String email = userDTO.getEmail();

        if(firstName != null || lastName != null){
            if(firstName != null && lastName != null){
                this.setFullName(new FullName(firstName,lastName));
            }else{
                if (firstName != null){
                    lastName = this.getFullName().lastName();
                    this.setFullName(new FullName(firstName,lastName));
                }else{
                    firstName = this.getFullName().firstName();
                    this.setFullName(new FullName(firstName,lastName));
                }
            }
        }

        if(email != null) {
            this.setEmail(new Email(email));
        }
    }

}

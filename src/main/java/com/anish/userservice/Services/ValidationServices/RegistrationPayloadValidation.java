package com.anish.userservice.Services.ValidationServices;

import com.anish.userservice.Entity.UsersEntity;
import com.anish.userservice.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class RegistrationPayloadValidation implements PayloadValidate {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Boolean validate(Map<String,Object> requestMap){

        return requestMap.get("mobileNumber") != null &&
                requestMap.get("userName") != null &&
                requestMap.get("firstName") != null &&
                requestMap.get("lastName") != null &&
                requestMap.get("emailId") != null &&
                checkPayloadUniqueness((String) requestMap.get("mobileNumber"));
    }

    public Boolean checkPayloadUniqueness(String mobileNumber){

        Optional<UsersEntity> result= usersRepository.findByMobileNumber(mobileNumber);
        return result.isEmpty();
    }


}

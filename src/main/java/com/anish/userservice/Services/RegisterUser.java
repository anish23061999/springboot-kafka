package com.anish.userservice.Services;

import com.anish.userservice.Entity.UsersEntity;
import com.anish.userservice.Repositories.UsersRepository;
import com.anish.userservice.Services.ValidationServices.RegistrationPayloadValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class RegisterUser {

    @Autowired
    RegistrationPayloadValidation registrationPayloadValidation;


    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    @KafkaListener(topics = "registration-request", groupId = "my-group-id")
    public void consume(Map<String, Object> message) {
        System.out.println("Received message: " + message);

        try {
            onboardUser(message);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private void onboardUser(Map<String,Object> requestMap) throws ParseException {

        if(registrationPayloadValidation.validate(requestMap)){

            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setUserName(requestMap.get("userName").toString());
            UUID uuid = UUID.randomUUID();
            String userId= "US."+uuid.toString().replace("-","").substring(0,27);
            usersEntity.setId(userId);
            usersEntity.setFirstName(requestMap.get("firstName").toString());
            usersEntity.setLastName(requestMap.get("lastName").toString());
            usersEntity.setEmailId(requestMap.get("emailId").toString());
            usersEntity.setMobileNumber(requestMap.get("mobileNumber").toString());
            requestMap.put("userId",userId);
            usersRepository.saveAndFlush(usersEntity);
            System.out.println("Successful onboarding");
            saveUserCredential(requestMap,"save-user-credential-request");
        }else {
            System.out.println("Failed Onboarding");
            Map<String,Object> errorResponseMap = new HashMap<>();
            errorResponseMap.put("error","400");
            errorResponseMap.put("message","Invalid Request");
            errorResponseMap.put("correlationId",requestMap.get("correlationId"));
            errorResponse(errorResponseMap,"registration-error-response");
        }
    }


    private void saveUserCredential(Map<String,Object> requestMap,String topic){
        sendMessage("messageKey",requestMap,topic);

    }

    private void errorResponse(Map<String,Object> errorResponseMap,String topic){
        sendMessage("messageKey",errorResponseMap,topic);
    }

    public void sendMessage(String key, Map<String, Object> message,String topic) {

        kafkaTemplate.send(topic, key, message);
        System.out.println("Message sent: " + message);
    }
}


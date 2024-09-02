package com.anish.userservice.Services.ValidationServices;

import java.util.Map;

public interface PayloadValidate {


    Boolean validate(Map<String,Object> requestMap);
}

package com.anish.userservice.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;


@Getter
@Setter
@Data
@Entity
@Table(name = "USERS")
public class UsersEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "DOB")
    private Date dateOfBirth;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
}

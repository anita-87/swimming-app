package com.anarodriguez.licenses.models;

import com.anarodriguez.licenses.enums.Gender;
import com.anarodriguez.licenses.enums.LicenceType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Document
public class Profile {

    @Id
    private String dni;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String countryOfBirth;
    private String phone;
    private String email;
    private String address;
    private String postalCode;
    private List<LicenceType> licences;
}

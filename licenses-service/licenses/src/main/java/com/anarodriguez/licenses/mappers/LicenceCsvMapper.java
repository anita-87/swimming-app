package com.anarodriguez.licenses.mappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"Tipo licencia", "Club", "Disciplina", "Cargo", "Fecha nacimiento", "Correo electrónico", "Nombre", "Sexo",
        "Documento identidad", "Apellidos", "Teléfono", "Dirección", "País de nacimiento", "Lugar de nacimiento",
        "Provincia de nacimiento", "Código Postal"})
@Data
public class LicenceCsvMapper {

    @JsonProperty("Tipo licencia")
    private String licenceTye;
    @JsonProperty("Club")
    private String club;
    @JsonProperty("Disciplina")
    private String discipline;
    @JsonProperty("Cargo")
    private String position;
    @JsonProperty("Fecha nacimiento")
    private String dateOfBirth;
    @JsonProperty("Correo electrónico")
    private String email;
    @JsonProperty("Nombre")
    private String firstName;
    @JsonProperty("Sexo")
    private String sex;
    @JsonProperty("Documento identidad")
    private String dni;
    @JsonProperty("Apellidos")
    private String lastName;
    @JsonProperty("Teléfono")
    private String phone;
    @JsonProperty("Dirección")
    private String address;
    @JsonProperty("País de nacimiento")
    private String countryOfBirth;
    @JsonProperty("Lugar de nacimiento")
    private String placeOfBirth;
    @JsonProperty("Provincia de nacimiento")
    private String stateOfBirth;
    @JsonProperty("Código Postal")
    private String postalCode;
}

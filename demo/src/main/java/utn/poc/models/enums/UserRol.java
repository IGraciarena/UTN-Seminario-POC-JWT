package utn.poc.models.enums;

import utn.poc.exceptions.NotValidRolException;

public enum UserRol {
    client, employee, administrator;

    public static UserRol getRol(String rol) {

        switch (rol) {
            case "client":
                return client;

            case "employee":
                return employee;

            case "administrator":
                return administrator;

            default:
                throw new NotValidRolException("Invalid rol");
        }
    }}

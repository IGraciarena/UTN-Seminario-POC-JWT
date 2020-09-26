package utn.poc.models.enums;

import utn.poc.exceptions.NotValidRolException;

public enum Role {
    client, employee, administrator;

    public static Role getRole(String role) {

        switch (role) {
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

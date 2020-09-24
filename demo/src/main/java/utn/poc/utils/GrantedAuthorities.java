package utn.poc.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class GrantedAuthorities {

    public static List<GrantedAuthority> getGrantedAuthority(UserRol rol){

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (rol.equals(UserRol.client)){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + UserRol.client.toString()));
        } else if (rol.equals(UserRol.employee)){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + UserRol.client.toString()));
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + UserRol.employee.toString()));
        } else if (rol.equals(UserRol.administrator)){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + UserRol.client.toString()));
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + UserRol.employee.toString()));
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + UserRol.administrator.toString()));
        } else {
            throw new NotValidRolException("Invalid rol");
        }

        return grantedAuthorities;
    }
}

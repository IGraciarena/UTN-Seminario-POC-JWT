package utn.poc.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import utn.poc.models.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class GrantedAuthorities {

    public static List<GrantedAuthority> getGrantedAuthority(Role role){

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (role.equals(Role.client)){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + Role.client.toString()));
        } else if (role.equals(Role.employee)){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + Role.client.toString()));
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + Role.employee.toString()));
        } else if (role.equals(Role.administrator)){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + Role.client.toString()));
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + Role.employee.toString()));
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + Role.administrator.toString()));
        }

        return grantedAuthorities;
    }
}

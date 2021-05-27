package backend.security;

import backend.model.personne;
import backend.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    PersonneRepository UR ;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       personne u = UR.loadUser(s);
        System.out.println(u.getUsername()+""+u.getPassword());
        return (UserDetails) u;
    }
}

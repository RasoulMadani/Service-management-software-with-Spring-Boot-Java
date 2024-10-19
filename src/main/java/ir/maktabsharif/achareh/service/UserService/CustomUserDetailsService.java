package ir.maktabsharif.achareh.service.UserService;

import ir.maktabsharif.achareh.config.CustomUserDetails;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.repository.userRepository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user.not.found"));

        return new CustomUserDetails(user);
    }
}
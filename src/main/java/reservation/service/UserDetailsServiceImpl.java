package reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reservation.entity.UserProfile;
import reservation.repository.UserProfileRepository;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileRepository.findByUsername(username).orElseThrow();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userProfile.getRoleAuthorityName()));

        return new User(
                userProfile.getUsername(),
                userProfile.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}

package reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservation.entity.UserProfile;
import reservation.model.UserProfileModel;
import reservation.repository.UserProfileRepository;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public int createUser(UserProfileModel userProfileModel) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(userProfileModel.getUsername());
        userProfile.setPassword(passwordEncoder.encode(userProfileModel.getPassword()));
        userProfile.setRole(UserProfile.Role.USER);

        userProfile = userProfileRepository.save(userProfile);
        return userProfile.getId();
    }
}

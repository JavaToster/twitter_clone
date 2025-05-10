package com.example.twitter_clone.services.user;

import com.example.twitter_clone.DTO.auth.AuthDTO;
import com.example.twitter_clone.models.user.User;
import com.example.twitter_clone.repository.user.UserRepository;
import com.example.twitter_clone.security.JWTUtil;
import com.example.twitter_clone.security.UserClaimData;
import com.example.twitter_clone.util.Determine;
import com.example.twitter_clone.util.enums.AuthenticationType;
import com.example.twitter_clone.util.enums.Status;
import com.example.twitter_clone.util.exceptions.AuthException;
import com.example.twitter_clone.util.exceptions.NonExistTypeException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final Determine determine;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(userOptional.isPresent()){
            return new com.example.twitter_clone.security.UserDetails(userOptional.get(), AuthenticationType.EMAIL);
        }

        userOptional = userRepository.findByPhone(username);
        if (userOptional.isPresent()){
            return new com.example.twitter_clone.security.UserDetails(userOptional.get(), AuthenticationType.PHONE);
        }

        throw new AuthException("Authentication data is incorrect");
    }

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setCreated_at(new Date());
        userRepository.save(user);
    }

    public String sing_in(AuthDTO authDTO) {
        String login = authDTO.getLogin();
        String password = authDTO.getPassword();

        UserDetails userDetails = loadUserByUsername(login);
        if (!comparePassword(password, userDetails.getPassword())){
            throw new AuthException("Authentication data is incorrect");
        }
        AuthenticationType type;
        try{
            type = getLoginType(login);
        }catch (NonExistTypeException e){
            throw new AuthException("Authentication data is incorrect");
        }
        return jwtUtil.generateToken(new UserClaimData(login, type));
    }

    private boolean comparePassword(String newPassword, String oldPassword){
        return passwordEncoder.matches(newPassword, oldPassword);
    }

    private AuthenticationType getLoginType(String login){
        if(determine.isEmail(login)){
            return AuthenticationType.EMAIL;
        }else if(determine.isPhone(login)){
            return AuthenticationType.PHONE;
        }else{
            throw new NonExistTypeException();
        }
    }
}

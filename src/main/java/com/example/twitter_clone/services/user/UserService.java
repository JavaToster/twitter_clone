package com.example.twitter_clone.services.user;

import com.example.twitter_clone.DTO.auth.AuthDTO;
import com.example.twitter_clone.DTO.auth.RegisterDTO;
import com.example.twitter_clone.DTO.user.DateDTO;
import com.example.twitter_clone.DTO.user.UserSettingsDTO;
import com.example.twitter_clone.models.user.User;
import com.example.twitter_clone.repository.user.UserRepository;
import com.example.twitter_clone.security.JWTUtil;
import com.example.twitter_clone.security.UserClaimData;
import com.example.twitter_clone.util.Convertor;
import com.example.twitter_clone.util.Determine;
import com.example.twitter_clone.util.enums.AuthenticationType;
import com.example.twitter_clone.util.enums.Status;
import com.example.twitter_clone.util.exceptions.AuthException;
import com.example.twitter_clone.util.exceptions.NonExistTypeException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final Determine determine;
    private final Convertor convertor;

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

    public void register(RegisterDTO registerDTO) {
        User user = convertor.convertToUser(registerDTO);
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

    public void updateUserSettings(Long userId, UserSettingsDTO dto) throws IllegalAccessException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Обновляем только непустые поля через Reflection
        updateNonNullFields(dto, user);

        userRepository.save(user);

    }

    private void updateNonNullFields(UserSettingsDTO dto, User user) throws IllegalAccessException {
        // Получаем все поля DTO и User через Reflection
        Field[] dtoFields = UserSettingsDTO.class.getDeclaredFields();
        Field[] userFields = User.class.getDeclaredFields();

        for (Field dtoField : dtoFields) {
            dtoField.setAccessible(true);
            Object dtoValue = dtoField.get(dto);

            // Пропускаем null и пустые строки
            if (dtoValue == null || (dtoValue instanceof String && ((String) dtoValue).isEmpty())) {
                continue;
            }

            // Особый случай для birthDate (вложенный DateDTO)
            if ("birthDate".equals(dtoField.getName()) && dtoValue instanceof DateDTO) {
                DateDTO dateDto = (DateDTO) dtoValue;
                if (!dateDto.isEmpty()) {
                    user.setBirthDate(convertToDate(dateDto));
                }
                continue;
            }

            // Ищем соответствующее поле в User и обновляем его
            Field userField = Arrays.stream(userFields)
                    .filter(field -> field.getName().equals(dtoField.getName()))
                    .findFirst()
                    .orElse(null);

            if (userField != null) {
                userField.setAccessible(true);
                userField.set(user, dtoValue);
            }
        }
    }

    private Date convertToDate(DateDTO dateDto) {
        // Реализуйте конвертацию DateDTO в Date
        int year = dateDto.getYear();
        int month = dateDto.getMonth();
        int day = dateDto.getDay();
        LocalDate localDate =  LocalDate.of(year, month, day);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

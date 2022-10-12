package io.electrosalaf.reward_your_teacher.infrastructure.configuration.security;

import io.electrosalaf.reward_your_teacher.domain.dao.AppUserDao;
import io.electrosalaf.reward_your_teacher.domain.entities.AppUserEntity;
import io.electrosalaf.reward_your_teacher.infrastructure.error_handler.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final AppUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUserEntity user = userDao.findAppUserEntityByEmail(email);
        if(user == null){
           throw new CustomNotFoundException("user not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}

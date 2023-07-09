package ru.arharova.questionnarie_constructor.service.impl;

import ru.arharova.questionnarie_constructor.models.SecurityUser;
import ru.arharova.questionnarie_constructor.models.User;
import ru.arharova.questionnarie_constructor.repos.QuestionnaireRepo;
import ru.arharova.questionnarie_constructor.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsServiceImpl")
public class ServiceImplUserDetails implements UserDetailsService {

    private UserRepo userRepo;
    @Autowired
    public void setReposUser(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Autowired
    public ServiceImplUserDetails(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.fromUser(user);
    }
}

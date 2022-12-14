package com.example.bookManagement.service;
import com.example.bookManagement.model.*;
import com.example.bookManagement.controller.RegisterUserRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.bookManagement.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	private final static String USER_NOT_FOUND =
            "user with email %s not found";
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepo.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
//    
        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + email));
         return user;
	
	
	}
	
	private  GrantedAuthority mapRolesToAuthorities(UserRole role){
        return new SimpleGrantedAuthority(role.name());
    }
	
	
	@Autowired
	private UserRepository userRepo;
	
	public User registerUser(User user) {
	

		boolean userExists = userRepo
                .findByEmail(user.getEmail())
                .isPresent();

        if (userExists) {

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());

        user.setPassword(encodedPassword);
	
	
		return userRepo.save(user);
	}

}
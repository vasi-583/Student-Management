package com.vasi.controller;


import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.vasi.model.User;
import com.vasi.repository.UserRepository;
import com.vasi.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
public class AuthController {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil ;
	
	public AuthController(UserRepository userRepo ,PasswordEncoder passwordEncoder , JwtUtil jwtUtil ) {
		this.userRepo = userRepo;
		this.passwordEncoder =passwordEncoder ;
		this.jwtUtil = jwtUtil;
		
	}
 
	@PostMapping("/signup")
	public Map<String , String> signup(@RequestBody User request){
		if(userRepo.findByUsername(request.getUsername()).isPresent()) {
			return Map.of("error", "Username already Exist");
		}
		User user= new User(
				request.getUsername(),
				passwordEncoder.encode(request.getPassword()),
				"USER"
				);
		userRepo.save(user);
		return Map.of("message" , "Signup Succesful");
		
	}
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody User request) {
        var userOpt = userRepo.findByUsername(request.getUsername());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return Map.of("error", "Invalid username or password");
        }
        User user = userOpt.get();
        String token = jwtUtil.generateTokens(user.getUsername(), user.getRole());
        return Map.of("token", token, "role", user.getRole());
    }
}
	
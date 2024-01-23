package lm.swith.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lm.swith.user.mapper.UsersMapper;
import lm.swith.user.model.SwithUser;

@Service
public class UserService {
	
	private UsersMapper usersMapper;
	
	
	private PasswordEncoder passwordEncoder; //encoding password
    

	public UserService(UsersMapper usersMapper, PasswordEncoder passwordEncoder) {
		super();
		this.usersMapper = usersMapper;
		this.passwordEncoder = passwordEncoder;
	}
	public SwithUser signUpUser(SwithUser swithUser) { //save the register user  
		SwithUser user = new SwithUser();
		
		user.setEmail(swithUser.getEmail());
		user.setPassword(passwordEncoder.encode(swithUser.getPassword()));
		user.setUsername(swithUser.getUsername());
		user.setNickname(swithUser.getNickname());
		user.setUserprofile(swithUser.getUserprofile());
		user.setUseraddress(swithUser.getUseraddress());
		user.setUserintroduction(swithUser.getUserintroduction());
		user.setRole(swithUser.getRole());
		
		usersMapper.insertUser(user);
		return user;
	}
	//login
	public SwithUser login(String email, String password) {
		return usersMapper.loginUser(email, password);
	}
	//find role
	public SwithUser findUserRole(String role) {
		return usersMapper.findUserRole(role);
	}
	public List<SwithUser> findUsersAll(){
		return usersMapper.findUsersAll();
	}

	

}
package lm.swith.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lm.swith.user.common.MsgEntity;
import lm.swith.user.model.SwithUser;
import lm.swith.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class RegisterController {
	
	
	private final UserService userService;
	

	

	
	@PostMapping("/register")
	public ResponseEntity<SwithUser> registerUser(@RequestBody SwithUser swithUser){
		SwithUser createUser = userService.signUpUser(swithUser);
		return ResponseEntity.ok(createUser);
	}


}
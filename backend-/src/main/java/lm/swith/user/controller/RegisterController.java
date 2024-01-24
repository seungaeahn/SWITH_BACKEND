package lm.swith.user.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lm.swith.user.Service.UserService;
import lm.swith.user.model.ResponseDTO;
import lm.swith.user.model.SwithDTO;
import lm.swith.user.model.SwithUser;
import lm.swith.user.token.TokenProvider;
import lombok.RequiredArgsConstructor;

//@RestController
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class RegisterController {
	private final UserService userService;
	private final TokenProvider tokenProvider;
	
	private final PasswordEncoder passwordEncoder;

	// -------- 토큰 발급 --------
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody SwithDTO siwthDTO) {
		SwithUser user = userService.getByCredentials(siwthDTO.getEmail(), siwthDTO.getPassword(), passwordEncoder);
		
        	// 사용자의 id, pwd 일치할 경우
		if(user != null) {
			// 토큰 생성
			final String token = tokenProvider.createAccessToken(user);  
			final SwithDTO responseUserDTO = SwithDTO.builder()
					.email(user.getEmail())
					.user_no(user.getUser_no())
					.username(user.getUsername())
					.useraddress(user.getUseraddress())
					.token(token)          //반환된 토큰 적용
					.build();
			return ResponseEntity.ok().body(responseUserDTO);
		} else {
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("Login faild.")
					.build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
		
	}

	
	@GetMapping("/userinfo")
	public ResponseEntity<SwithUser> getUserInfo() {
        // 현재 인증된 사용자의 정보를 가져오는 로직
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        // MyBatis를 이용하여 사용자 정보를 조회
        SwithUser user = userService.getUserByEmail(userEmail);
        // User 엔터티를 UserDTO로 변환하여 반환
       // byte[] profile_img = profile_img.getBytes(1, (int) profile_img.length());

        byte[] profile_img = user.getUser_profile();
        String imageBase64 = Base64.getEncoder().encodeToString(profile_img);
        
        String cutString = imageBase64.substring(imageBase64.indexOf("data:image/jpeg;base64") + "data:image/jpeg;base64".length());
        String imageUrl = "data:image/jpeg;base64,/" + cutString;
        user.setPassword(null);
        user.setImg(imageUrl);
        System.out.println(imageUrl);
        return ResponseEntity.ok(user);
    }
	  @GetMapping("/")
	  public String MailPage(){
	      return "/";
	  }
	  
	 
	  @GetMapping("/register")
	  public String showRegisterForm(Model model) {
		  model.addAttribute("users",new SwithUser());
		  return "register";
	  }
	/*@GetMapping("/register")
	public List<SwithUser> findUsersAll() {
		return userService.findUsersAll();
	}
	*/	
	  @PostMapping("/register")
	  public ResponseEntity<SwithUser> registerUser(@RequestBody SwithUser swithUser) {
	      try {
	          // Base64 이미지 데이터 디코딩
	          String imageData = swithUser.getImg().split(",")[1];
	          byte[] imageBytes = Base64.getDecoder().decode(imageData);

	          // BufferedImage로 이미지 읽기
	          ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
	          BufferedImage originalImage = ImageIO.read(bis);
	          bis.close();

	          // 이미지 크기 조절 (예: 가로 100px로 조절)
	          int newWidth = 500;
	          int newHeight = (int) (originalImage.getHeight() * (1.0 * newWidth / originalImage.getWidth()));
	          BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	          resizedImage.getGraphics().drawImage(originalImage, 0, 0, newWidth, newHeight, null);

	          // 압축된 이미지를 Base64로 인코딩
	          ByteArrayOutputStream bos = new ByteArrayOutputStream();
	          ImageIO.write(resizedImage, "png", bos);
	          byte[] compressedImageBytes = bos.toByteArray();
	          bos.close();

	          // 압축된 이미지를 엔티티에 설정
	          swithUser.setUser_profile(compressedImageBytes);

	          // 사용자 등록 처리
	          SwithUser createUser = userService.signUpUser(swithUser);

	          return ResponseEntity.ok(createUser);
	      } catch (IOException e) {
	          e.printStackTrace();
	          // 에러 처리
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	      }
	  }
		/*
	      try {
	          // 이미지를 Base64로 인코딩하여 SwithUser 객체에 설정
	          if (image != null) {
	              Base64.Encoder encoder = Base64.getEncoder();
	              byte[] photoEncode = encoder.encode(image.getBytes());
	              String photoImg = new String(photoEncode, StandardCharsets.UTF_8);
	              swithUser.setUserprofile(photoImg);
	          }

	          // 사용자 등록 및 이미지 업로드
	          SwithUser createdUser = userService.signUpUser(swithUser);

	          // 성공적으로 등록된 경우
	          return ResponseEntity.status(200).body("User registered successfully with ID: " + createdUser.getUser_no());
	      } catch (Exception e) {
	          e.printStackTrace();
	          // 등록 실패 시
	          return ResponseEntity.status(500).body("Error registering user");
	      }
	  }*/
	

}
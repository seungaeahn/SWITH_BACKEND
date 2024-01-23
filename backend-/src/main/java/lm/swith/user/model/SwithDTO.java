package lm.swith.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SwithDTO {
	private String token;
	private String email;
	private String username;
	private String password;
	private Long user_no;
}

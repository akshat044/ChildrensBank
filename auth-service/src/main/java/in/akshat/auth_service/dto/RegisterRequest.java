package in.akshat.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
	
	@Email(message="Invalid Email Format")
	@NotNull(message="Email cannot be null")
	private String email;
	
	@NotNull(message="Password cannot be null")
	@Size(min = 8, message="Password must be atleast of 8 character")
	private String password;
	
	 
	

}

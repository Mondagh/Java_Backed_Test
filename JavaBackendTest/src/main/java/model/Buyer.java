package model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity		
public class Buyer {
	
	private String email;
	@NotBlank
	private String seatLetter;
	@Positive
	private int seatNumber;

}

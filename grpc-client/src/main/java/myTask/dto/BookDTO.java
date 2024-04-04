package myTask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.Length;

@Getter
@Setter
public class BookDTO {
	@NotBlank
	private String title;
	@NotBlank
	private String author;
	@NotBlank
	@Pattern(regexp = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+"
			+ "[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+"
			+ "[- ]?[0-9X]$",
			message = "length must be 10 digits")
	private String isbn;
	@Positive
	private int quantity;
}
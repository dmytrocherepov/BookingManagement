package myTask.controller;

import com.google.protobuf.Descriptors;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myTask.dto.BookDTO;
import myTask.service.BookClientService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class bookController {
	private final BookClientService clientService;


	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public Map<Descriptors.FieldDescriptor, Object> getBook(@PathVariable String id) {
		return clientService.getBook(UUID.fromString(id));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Map<Descriptors.FieldDescriptor, Object> createBook(@RequestBody @Valid BookDTO request) {
		return clientService.createBook(request);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{id}")
	public Map<Descriptors.FieldDescriptor, Object> createBook(@PathVariable String id, @RequestBody BookDTO request) {
		return clientService.updateBook(id , request);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/book/{id}")
	public void deleteBook(@PathVariable String id) {
		clientService.deleteBook(UUID.fromString(id));
	}

}

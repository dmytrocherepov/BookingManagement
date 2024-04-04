package mytask.grpcserver.service;

import com.example.bookstore.grpc.Book;
import com.example.bookstore.grpc.BookWithId;
import com.example.bookstore.grpc.CreateBookRequest;
import com.example.bookstore.grpc.DeleteBookRequest;
import com.example.bookstore.grpc.GetBookRequest;
import com.example.bookstore.grpc.UpdateBookRequest;
import io.grpc.internal.testing.StreamRecorder;
import jdk.jfr.Description;
import myTask.mapper.BookMapper;
import myTask.mapper.impl.BookMapperImpl;
import myTask.model.BookEntity;
import myTask.repository.BookRepository;
import myTask.service.BookServiceGrpc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceGrpcTest {
	@Mock
	private BookRepository bookRepository;

	@Spy
	private BookMapper bookMapper = new BookMapperImpl();

	@InjectMocks
	private BookServiceGrpc bookService;


	@Test
	@Description("create a book")
	void saveBook_ValidRequest_Success() throws Exception {
		CreateBookRequest request = CreateBookRequest.newBuilder()
				.setBook(Book.newBuilder()
						.setAuthor("Test Author")
						.setTitle("Test Title")
						.setIsbn("123456")
						.setQuantity(1)
						.build())
				.build();

		BookEntity bookEntity = new BookEntity();
		when(bookRepository.save(any())).thenReturn(bookEntity);
		StreamRecorder<BookWithId> responseObserver = StreamRecorder.create();

		bookService.createBook(request, responseObserver);

		responseObserver.awaitCompletion();

		// Assert
		BookWithId response = responseObserver.getValues().get(0);
		assertThat(response.getTitle()).isEqualTo("Test Title");
		assertThat(response.getAuthor()).isEqualTo("Test Author");
		assertThat(response.getIsbn()).isEqualTo("123456");
		assertThat(response.getQuantity()).isEqualTo(1);
	}

	@Test
	@Description("get a book")
	void getBook_validId_shouldReturnValidBook() throws Exception {


		BookEntity book = new BookEntity();
		book.setId(UUID.fromString("5ccbf6da-8b86-42dc-87ab-28ead586acd8"));
		book.setAuthor("Test Author");
		book.setTitle("Test Title");
		book.setIsbn("123456");
		book.setQuantity(1);
		GetBookRequest request = GetBookRequest.newBuilder()
				.setId("5ccbf6da-8b86-42dc-87ab-28ead586acd8")
				.build();

		when(bookRepository.findById(any())).thenReturn(Optional.of(book));
		StreamRecorder<BookWithId> responseObserver = StreamRecorder.create();

		bookService.getBook(request, responseObserver);

		responseObserver.awaitCompletion();

		BookWithId response = responseObserver.getValues().get(0);
		assertThat(response.getTitle()).isEqualTo(book.getTitle());
		assertThat(response.getAuthor()).isEqualTo(book.getAuthor());
		assertThat(response.getIsbn()).isEqualTo(book.getIsbn());
		assertThat(response.getQuantity()).isEqualTo(book.getQuantity());
	}

	@Test
	@Description("delete a book")
	void deleteBook_validId_shouldReturnValidBook() throws Exception {

		UUID id = UUID.fromString("5ccbf6da-8b86-42dc-87ab-28ead586acd8");
		DeleteBookRequest request = DeleteBookRequest.newBuilder()
				.setId(String.valueOf(id))
				.build();
		BookEntity book = new BookEntity();
		book.setId(id);

		when(bookRepository.findById(id)).thenReturn(Optional.of(book));

		StreamRecorder<BookWithId> responseObserver = StreamRecorder.create();

		bookService.deleteBook(request, responseObserver);

		responseObserver.awaitCompletion();

		verify(bookRepository, times(1)).delete(book);
	}

	@Test
	@Description("update a book")
	void updateBook() throws Exception {
		UUID id = UUID.fromString("5ccbf6da-8b86-42dc-87ab-28ead586acd8");

		UpdateBookRequest request = UpdateBookRequest.newBuilder()
				.setBook(BookWithId.newBuilder()
						.setId(String.valueOf(id))
						.setAuthor("Updated Author")
						.setTitle("Updated Title")
						.setIsbn("654321")
						.setQuantity(2)
						.build())
				.build();

		BookEntity bookEntity = new BookEntity();
		bookEntity.setId(id);
		bookEntity.setAuthor("test");
		bookEntity.setIsbn("test");
		bookEntity.setTitle("test");
		bookEntity.setQuantity(0);

		when(bookRepository.existsById(id)).thenReturn(true);
		when(bookRepository.save(any())).thenReturn(bookEntity);
		StreamRecorder<BookWithId> responseObserver = StreamRecorder.create();

		bookService.updateBook(request, responseObserver);

		responseObserver.awaitCompletion();

		BookWithId response = responseObserver.getValues().get(0);
		assertThat(response.getId()).isEqualTo(request.getBook().getId());
		assertThat(response.getTitle()).isEqualTo("Updated Title");
		assertThat(response.getAuthor()).isEqualTo("Updated Author");
		assertThat(response.getIsbn()).isEqualTo("654321");
		assertThat(response.getQuantity()).isEqualTo(2);
	}
}

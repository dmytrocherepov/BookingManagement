package myTask.service;

import com.example.bookstore.grpc.Book;
import com.example.bookstore.grpc.BookServiceGrpc;
import com.example.bookstore.grpc.BookWithId;
import com.example.bookstore.grpc.CreateBookRequest;
import com.example.bookstore.grpc.DeleteBookRequest;
import com.example.bookstore.grpc.GetBookRequest;
import com.example.bookstore.grpc.UpdateBookRequest;
import com.google.protobuf.Descriptors;
import myTask.dto.BookDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class BookClientService {
	@GrpcClient("GLOBAL")
	BookServiceGrpc.BookServiceBlockingStub synchronousClient;

	public Map<Descriptors.FieldDescriptor, Object> getBook(UUID bookId) {
		GetBookRequest request = GetBookRequest.newBuilder()
				.setId(String.valueOf(bookId))
				.build();
		BookWithId response = synchronousClient.getBook(request);
		return response.getAllFields();
	}


	public Map<Descriptors.FieldDescriptor, Object> createBook(BookDTO bookDTO) {
		CreateBookRequest a = CreateBookRequest.newBuilder()
				.setBook(Book.newBuilder()
						.setTitle(bookDTO.getTitle())
						.setQuantity(bookDTO.getQuantity())
						.setIsbn(bookDTO.getIsbn())
						.setAuthor(bookDTO.getTitle())
						.build()
				)
				.build();
			BookWithId book = synchronousClient.createBook(a);
			return book.getAllFields();
	}

	public Map<Descriptors.FieldDescriptor, Object> updateBook(String id, BookDTO bookDTO) {
		UpdateBookRequest request = UpdateBookRequest.newBuilder()
				.setBook(BookWithId.newBuilder()
						.setId(id)
						.setIsbn(bookDTO.getIsbn())
						.setQuantity(bookDTO.getQuantity())
						.setAuthor(bookDTO.getAuthor())
						.setTitle(bookDTO.getTitle())
						.build()

				)
				.build();
		BookWithId response = synchronousClient.updateBook(request);
		return response.getAllFields();
	}

	public void deleteBook(UUID bookId) {
		DeleteBookRequest request = DeleteBookRequest.newBuilder()
				.setId(String.valueOf(bookId))
				.build();
		synchronousClient.deleteBook(request);
	}
}

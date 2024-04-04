package myTask.service;


import com.example.bookstore.grpc.BookWithId;
import com.example.bookstore.grpc.CreateBookRequest;
import com.example.bookstore.grpc.DeleteBookRequest;
import com.example.bookstore.grpc.GetBookRequest;
import com.example.bookstore.grpc.UpdateBookRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import myTask.mapper.BookMapper;
import myTask.model.BookEntity;
import myTask.repository.BookRepository;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;
import java.util.UUID;

import static com.example.bookstore.grpc.BookServiceGrpc.BookServiceImplBase;


@GrpcService
@RequiredArgsConstructor
public class BookServiceGrpc extends BookServiceImplBase {
	private final BookRepository bookRepository;
	private final BookMapper bookMapper;

	@Override
	public void getBook(GetBookRequest request, StreamObserver<BookWithId> responseObserver) {
		Optional<BookEntity> book = bookRepository.findById(UUID.fromString(request.getId()));
		if (book.isPresent()) {
			BookEntity bookEntity = book.get();
			BookWithId response = bookMapper.entityToProto(bookEntity);
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		} else {
			responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND
					.withDescription("No such book with id " + request.getId())));
		}
	}

	@Override
	public void createBook(CreateBookRequest request, StreamObserver<BookWithId> responseObserver) {
		BookEntity bookEntity = bookMapper.protoToEntity(request.getBook());
		bookRepository.save(bookEntity);
		BookWithId response = bookMapper.entityToProto(bookEntity);
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void updateBook(UpdateBookRequest request, StreamObserver<BookWithId> responseObserver) {
		isBookExistById(UUID.fromString(request.getBook().getId()));
		BookEntity book = bookMapper.protoWithIdToEntity(request.getBook());
		book.setId(UUID.fromString(request.getBook().getId()));
		bookRepository.save(book);
		BookWithId response = bookMapper.entityToProto(book);
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void deleteBook(DeleteBookRequest request, StreamObserver<BookWithId> responseObserver) {
		BookEntity bookEntity = bookRepository.findById(UUID.fromString(request.getId())).get();
		BookWithId response = bookMapper.entityToProto(bookEntity);
		bookRepository.delete(bookEntity);
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	private void isBookExistById(UUID id) {
		if (!bookRepository.existsById(id)) {
			throw new EntityNotFoundException("No such book with id: " + id);
		}
	}

}

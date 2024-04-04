package myTask.mapper;

import com.example.bookstore.grpc.Book;

import com.example.bookstore.grpc.BookWithId;
import myTask.config.MapperConfig;
import myTask.model.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

	@Mapping(target = "id", source = "id")
	@Mapping(target = "title", source = "title")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "isbn", source = "isbn")
	@Mapping(target = "quantity", source = "quantity")
	BookWithId entityToProto(BookEntity book);

	@Mapping(target = "title", source = "title")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "isbn", source = "isbn")
	@Mapping(target = "quantity", source = "quantity")
	BookEntity protoToEntity(Book book);

	@Mapping(target = "title", source = "title")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "isbn", source = "isbn")
	@Mapping(target = "quantity", source = "quantity")
	BookEntity protoWithIdToEntity(BookWithId book);

}

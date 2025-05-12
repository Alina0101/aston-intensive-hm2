package crudUtils;

import entities.Book;
import java.util.List;

public interface BookDao {
    void save(Book book);

    Book findById(Long id);

    List<Book> findAll();

    void update(Book book);

    void delete(Long id);
}

package crudUtils;
import entities.Author;
import java.util.List;

public interface AuthorDao {
    void save(Author book);

    Author findById(Long id);

    List<Author> findAll();

    void update(Author book);

    void delete(Long id);
}

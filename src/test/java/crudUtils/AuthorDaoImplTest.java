package crudUtils;

import entities.Author;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorDaoImplTest {

    private static AuthorDao authorDao;

    @BeforeAll
    public static void setup() {
        authorDao = new AuthorDaoImpl();
    }

    @Test
    @Order(1)
    public void testSave() {
        Author author = new Author("Тест Автор", 1990);

        authorDao.save(author);

        assertNotNull(author.getId(), "ID автора должен быть присвоен");
    }

    @Test
    @Order(2)
    public void testFindById() {
        Author author = new Author("Поиск Автор", 1985);
        authorDao.save(author);

        Author found = authorDao.findById(author.getId());

        assertNotNull(found, "Автор должен быть найден");
        assertEquals(author.getName(), found.getName(), "Имена должны совпадать");
    }

    @Test
    @Order(3)
    public void testFindAll() {
        List<Author> authors = authorDao.findAll();

        assertFalse(authors.isEmpty(), "Список авторов не должен быть пустым");
    }

    @Test
    @Order(4)
    public void testUpdate() {
        Author author = new Author("Старое имя", 1975);
        authorDao.save(author);

        author.setName("Новое имя");
        authorDao.update(author);

        Author updated = authorDao.findById(author.getId());

        assertEquals("Новое имя", updated.getName(), "Имя должно быть обновлено");
    }

    @Test
    @Order(5)
    public void testDelete() {
        Author author = new Author("Удаляемый Автор", 1960);
        authorDao.save(author);
        Long id = author.getId();

        authorDao.delete(id);

        assertNull(authorDao.findById(id), "Автор должен быть удалён");
    }
}

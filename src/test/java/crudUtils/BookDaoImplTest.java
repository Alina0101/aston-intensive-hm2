package crudUtils;

import entities.Author;
import entities.Book;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoImplTest {

    private static BookDao bookDao;
    private static AuthorDao authorDao;
    private static Author testAuthor;

    @BeforeAll
    public static void setup() {
        bookDao = new BookDaoImpl();
        authorDao = new AuthorDaoImpl();

        // Создаём автора для тестовых книг
        testAuthor = new Author("Автор для книги", 1980);
        authorDao.save(testAuthor);
    }

    @Test
    @Order(1)
    public void testSave() {
        Book book = new Book("Тестовая книга", testAuthor, 2020, "жанр");

        bookDao.save(book);

        assertNotNull(book.getId(), "ID книги должен быть присвоен");
    }

    @Test
    @Order(2)
    public void testFindById() {
        Book book = new Book("Поиск книги", testAuthor, 2015, "жанр");
        bookDao.save(book);

        Book found = bookDao.findById(book.getId());

        assertNotNull(found, "Книга должна быть найдена");
        assertEquals(book.getTitle(), found.getTitle(), "Названия книг должны совпадать");
    }

    @Test
    @Order(3)
    public void testFindAll() {
        List<Book> books = bookDao.findAll();

        assertFalse(books.isEmpty(), "Список книг не должен быть пустым");
    }

    @Test
    @Order(4)
    public void testUpdate() {
        Book book = new Book("Старое название", testAuthor, 2010, "жанр");
        bookDao.save(book);

        book.setTitle("Новое название");
        bookDao.update(book);

        Book updated = bookDao.findById(book.getId());

        assertEquals("Новое название", updated.getTitle(), "Название должно быть обновлено");
    }

    @Test
    @Order(5)
    public void testDelete() {
        Book book = new Book("Удаляемая книга", testAuthor, 2005, "жанр");
        bookDao.save(book);
        Long id = book.getId();

        bookDao.delete(id);

        assertNull(bookDao.findById(id), "Книга должна быть удалена");
    }
}

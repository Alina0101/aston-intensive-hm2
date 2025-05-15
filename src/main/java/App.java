import crudUtils.*;

import entities.Author;
import entities.Book;
import entities.Member;

import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {
        AuthorDao authorDao = new AuthorDaoImpl();
        BookDao bookDao = new BookDaoImpl();
        MemberDao memberDao = new MemberDaoImpl();

        System.out.println("=== НАЧАЛО ТЕСТИРОВАНИЯ (Hibernate) ===");

        testAuthors(authorDao);
        testBooks(bookDao, authorDao);
        testMembers(memberDao);

        System.out.println("=== ТЕСТИРОВАНИЕ ЗАВЕРШЕНО ===");
    }

    private static void testAuthors(AuthorDao authorDao) {
        System.out.println("\n=== ТЕСТ АВТОРОВ ===");

        authorDao.findAll().forEach(a -> authorDao.delete(a.getId()));

        Author author1 = new Author("Лев Толстой", 1828);
        Author author2 = new Author("Фёдор Достоевский", 1821);
        authorDao.save(author1);
        authorDao.save(author2);
        System.out.println("Созданы авторы: " + author1 + ", " + author2);

        List<Author> authors = authorDao.findAll();
        assertEqual(authors.size(), 2, "Количество авторов после создания");

        Author foundAuthor = authorDao.findById(author1.getId());
        assertEqual(foundAuthor.getName(), "Лев Толстой", "Поиск автора по ID");

        foundAuthor.setName("Лев Николаевич Толстой");
        authorDao.update(foundAuthor);
        Author updatedAuthor = authorDao.findById(foundAuthor.getId());
        assertEqual(updatedAuthor.getName(), "Лев Николаевич Толстой", "Обновление автора");

        authorDao.delete(author2.getId());
        assertEqual(authorDao.findAll().size(), 1, "Количество авторов после удаления");
    }

    private static void testBooks(BookDao bookDao, AuthorDao authorDao) {
        System.out.println("\n=== ТЕСТ КНИГ ===");

        bookDao.findAll().forEach(b -> bookDao.delete(b.getId()));

        List<Author> authors = authorDao.findAll();
        if (authors.isEmpty()) {
            Author author = new Author("Александр Пушкин", 1799);
            authorDao.save(author);
            authors = authorDao.findAll();
        }
        Long authorId = authors.get(0).getId();

        Book book1 = new Book("Евгений Онегин", authorDao.findById(authorId), 1833, "Роман в стихах");
        Book book2 = new Book("Капитанская дочка", authorDao.findById(authorId), 1836, "Повесть");
        bookDao.save(book1);
        bookDao.save(book2);
        System.out.println("Созданы книги: " + book1 + ", " + book2);

        List<Book> books = bookDao.findAll();
        assertEqual(books.size(), 2, "Количество книг после создания");

        Book foundBook = bookDao.findById(book1.getId());
        assertEqual(foundBook.getTitle(), "Евгений Онегин", "Поиск книги по ID");

        foundBook.setTitle("Евгений Онегин (полное собрание)");
        bookDao.update(foundBook);
        Book updatedBook = bookDao.findById(foundBook.getId());
        assertEqual(updatedBook.getTitle(), "Евгений Онегин (полное собрание)", "Обновление книги");

        bookDao.delete(book2.getId());
        assertEqual(bookDao.findAll().size(), 1, "Количество книг после удаления");
    }

    private static void testMembers(MemberDao memberDao) {
        System.out.println("\n=== ТЕСТ ЧЛЕНОВ БИБЛИОТЕКИ ===");

        memberDao.findAll().forEach(m -> memberDao.delete(m.getId()));

        Member member1 = new Member("Иван Иванов", "ivanov@mail.com", LocalDate.now());
        Member member2 = new Member("Петр Петров", "petrov@mail.com", LocalDate.now().minusDays(1));
        memberDao.save(member1);
        memberDao.save(member2);
        System.out.println("Созданы члены: " + member1 + ", " + member2);

        List<Member> members = memberDao.findAll();
        assertEqual(members.size(), 2, "Количество членов после создания");

        Member foundMember = memberDao.findById(member1.getId());
        assertEqual(foundMember.getName(), "Иван Иванов", "Поиск члена по ID");

        foundMember.setEmail("ivanov.new@mail.com");
        memberDao.update(foundMember);
        Member updatedMember = memberDao.findById(foundMember.getId());
        assertEqual(updatedMember.getEmail(), "ivanov.new@mail.com", "Обновление члена");

        memberDao.delete(member2.getId());
        assertEqual(memberDao.findAll().size(), 1, "Количество членов после удаления");
    }

    private static void assertEqual(Object actual, Object expected, String testName) {
        if ((actual == null && expected == null) ||
                (actual != null && actual.equals(expected))) {
            System.out.println("[OK] " + testName);
        } else {
            System.out.println("[FAIL] " + testName +
                    ". Ожидалось: " + expected + ", получено: " + actual);
        }
    }
}

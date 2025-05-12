package crudUtils;

import entities.Book;
import utils.DBUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    @Override
    public void save(Book book) {
        String sql = "INSERT INTO books (title, author_id, published_year, genre) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, book.getTitle());
            stmt.setLong(2, book.getAuthorId());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setString(4, book.getGenre());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getLong("author_id"),
                            rs.getInt("published_year"),
                            rs.getString("genre")
                    );
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getLong("author_id"),
                        rs.getInt("published_year"),
                        rs.getString("genre")
                ));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author_id = ?, published_year = ?, genre = ? WHERE id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setLong(2, book.getAuthorId());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setString(4, book.getGenre());
            stmt.setLong(5, book.getId());
            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
package crudUtils;

import entities.Author;
import utils.DBUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    @Override
    public void save(Author author) {
        String sql = "INSERT INTO authors (name, birth_year) VALUES (?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, author.getName());
            stmt.setInt(2, author.getBirthYear());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author findById(Long id) {
        String sql = "SELECT * FROM authors WHERE id = ?";
        Author author = null;
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    author = new Author(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getInt("birth_year")
                    );
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return author;
    }

    @Override
    public List<Author> findAll() {
        String sql = "SELECT * FROM authors";
        List<Author> authors = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                authors.add(new Author(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("birth_year")
                ));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public void update(Author author) {
        String sql = "UPDATE authors SET name = ?, birth_year = ? WHERE id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, author.getName());
            stmt.setInt(2, author.getBirthYear());
            stmt.setLong(3, author.getId());
            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
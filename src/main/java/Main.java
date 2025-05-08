import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DBUtils.getConnection()) {
            System.out.println("Connected to MySQL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
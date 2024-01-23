package core.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        String jdbcUrl = "jdbc:postgresql://localhost:5432/runtimeassignment";
        String username = "shira";
        String password = "shira";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO runtime_policies (policy_name, author, controls) VALUES (?, ?, ?)")) {

                    //                statement.setString(1, policy.getPolicyName());
                    //                statement.setString(2, policy.getAuthor());
                    //                statement.setObject(3, policy.getControls(), Types.OTHER);

                statement.executeUpdate();
                //            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package la.hack.kiron.gov.kironlanguageassessment;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Christian on 29.04.2016.
 */
public class DatabaseConnection {

    public DatabaseConnection() {
        try {
            Log.d(DatabaseConnection.class.getSimpleName(), "Hello World!");
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://athen052.startdedicated.de:5432/hackathon","hackathon", "hack4kiron");
            Log.d(DatabaseConnection.class.getSimpleName(), "Connection established: " + connection.isValid(5));
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

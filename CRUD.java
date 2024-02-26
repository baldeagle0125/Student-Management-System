import java.sql.*;

public class CRUD {
    // Create operation
    public static void createRecord(String firstName, String lastName, String tableName, String[] columns,
            String[] values, String url, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
            for (int i = 0; i < columns.length; i++) {
                query.append(columns[i]);
                if (i < columns.length - 1)
                    query.append(", ");
            }
            query.append(") VALUES (");
            for (int i = 0; i < values.length; i++) {
                query.append("?");
                if (i < values.length - 1)
                    query.append(", ");
            }
            query.append(")");
            preparedStatement = connection.prepareStatement(query.toString());
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i + 1, values[i]);
            }
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(rowsInserted + " record successfully added to the table.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Read operation
    public static void readRecords(String tableName, String url, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM " + tableName;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.print(metaData.getColumnName(i) + ": " + resultSet.getObject(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Update operation
    public static void updateRecord(String tableName, String[] setColumns, String[] setValues, String conditionColumn,
            String conditionValue, String url, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
            for (int i = 0; i < setColumns.length; i++) {
                query.append(setColumns[i]).append("=?");
                if (i < setColumns.length - 1)
                    query.append(", ");
            }
            query.append(" WHERE ").append(conditionColumn).append("=?");
            preparedStatement = connection.prepareStatement(query.toString());
            for (int i = 0; i < setValues.length; i++) {
                preparedStatement.setString(i + 1, setValues[i]);
            }
            preparedStatement.setString(setValues.length + 1, conditionValue);
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println(rowsUpdated + " record successfully updated in the table.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Delete operation
    public static void deleteRecord(String tableName, String conditionColumn, String conditionValue, String url,
            String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            String query = "DELETE FROM " + tableName + " WHERE " + conditionColumn + "=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, conditionValue);
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted + " record successfully removed from the table.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/books";
        String username = "root";
        String password = "password";

        // Example usage:
        // createRecord("John", "Doe", "Authors", new String[]{"FirstName", "LastName"},
        // new String[]{"John", "Doe"}, url, username, password);
        // readRecords("Authors", url, username, password);
        // updateRecord("Authors", new String[]{"LastName"}, new String[]{"Smith"},
        // "FirstName", "John", url, username, password);
        // deleteRecord("Authors", "FirstName", "John", url, username, password);
    }
}

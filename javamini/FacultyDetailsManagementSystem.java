import java.sql.*;
import java.util.Scanner;

public class FacultyDetailsManagementSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/faculty_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;
    private Scanner scanner;

    public FacultyDetailsManagementSystem() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Ensure scanner is initialized
            scanner = new Scanner(System.in);
        }
    }
    

    public void addFaculty() {
        try {
            System.out.print("Enter Faculty Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Department: ");
            String department = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();

            String query = "INSERT INTO faculty (name, department, email, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, department);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Faculty record added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFaculty() {
        try {
            System.out.print("Enter ID of the faculty to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter New Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter New Department: ");
            String department = scanner.nextLine();

            System.out.print("Enter New Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter New Phone Number: ");
            String phone = scanner.nextLine();

            String query = "UPDATE faculty SET name=?, department=?, email=?, phone=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, department);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Faculty record updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewFacultyRecords() {
        try {
            String query = "SELECT * FROM faculty";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("ID | Name | Department | Email | Phone");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                System.out.printf("%d | %s | %s | %s | %s%n", id, name, department, email, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFaculty() {
        try {
            System.out.print("Enter ID of the faculty to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            String query = "DELETE FROM faculty WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Faculty record deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\nFaculty Details Management System");
            System.out.println("1. Add Faculty Record");
            System.out.println("2. Update Faculty Record");
            System.out.println("3. View Faculty Records");
            System.out.println("4. Delete Faculty Record");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                try {
                    int choice = Integer.parseInt(input);

                    switch (choice) {
                        case 1:
                            addFaculty();
                            break;
                        case 2:
                            updateFaculty();
                            break;
                        case 3:
                            viewFacultyRecords();
                            break;
                        case 4:
                            deleteFaculty();
                            break;
                        case 5:
                            System.out.println("Exiting program.");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    public static void main(String[] args) {
        FacultyDetailsManagementSystem system = new FacultyDetailsManagementSystem();
        system.menu();
    }
}

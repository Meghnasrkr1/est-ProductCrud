// ProductCRUD.java
import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASS = "yourpassword";
    static Connection con;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            int choice = 0;
            while (choice != 5) {
                System.out.println("\n===== PRODUCT MENU =====");
                System.out.println("1. Add Product");
                System.out.println("2. View All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                switch (choice) {
                    case 1: addProduct(sc); break;
                    case 2: viewProducts(); break;
                    case 3: updateProduct(sc); break;
                    case 4: deleteProduct(sc); break;
                    case 5: System.out.println("Exiting..."); break;
                    default: System.out.println("Invalid Choice!");
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    static void addProduct(Scanner sc) throws Exception {
        System.out.print("Enter Product Name: ");
        String name = sc.next();
        System.out.print("Enter Product Price: ");
        double price = sc.nextDouble();
        PreparedStatement pst = con.prepareStatement("INSERT INTO product(name,price) VALUES (?,?)");
        pst.setString(1, name);
        pst.setDouble(2, price);
        System.out.println(pst.executeUpdate() + " Product Added!");
    }

    static void viewProducts() throws Exception {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM product");
        System.out.println("\nID\tNAME\tPRICE");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("price"));
        }
    }

    static void updateProduct(Scanner sc) throws Exception {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        System.out.print("Enter New Name: ");
        String name = sc.next();
        System.out.print("Enter New Price: ");
        double price = sc.nextDouble();
        PreparedStatement pst = con.prepareStatement("UPDATE product SET name=?, price=? WHERE id=?");
        pst.setString(1, name);
        pst.setDouble(2, price);
        pst.setInt(3, id);
        System.out.println(pst.executeUpdate() + " Product Updated!");
    }

    static void deleteProduct(Scanner sc) throws Exception {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        PreparedStatement pst = con.prepareStatement("DELETE FROM product WHERE id=?");
        pst.setInt(1, id);
        System.out.println(pst.executeUpdate() + " Product Deleted!");
    }
}

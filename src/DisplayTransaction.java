// Shamoil Soni Proj5 11/19/2017

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DisplayTransaction {
    public static void main(String[] args) {
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        int id = 0, age = 0, price = 0;
        String sql = null, name = null, buyer = null;
        Scanner fromKeyboard = new Scanner(System.in);

        try {
            // Define Connection and Statement objects.
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:transactions.db");
            s = c.createStatement();
            
            while (id != -1) {
                // Read id and display corresponding table row.
                System.out.print("Enter desired id: ");
                id = fromKeyboard.nextInt( ); 
                sql = "select name, age, buyer, price from transactions " +
                    "where transactionid = " + id + ";";
                System.out.println(sql);
                rs = s.executeQuery(sql);
                while (rs.next( )) {
                    name = rs.getString("name");
                    age = rs.getInt("age");
                    buyer = rs.getString("buyer");
                    price = rs.getInt("price");
                    
                    System.out.println("Name: " + name);
                    System.out.println("Age: " + age);
                    System.out.println("Buyer: " + buyer);
                    System.out.println("Price: " + price);
                }
            }
        }
        catch(SQLException e) {
            System.out.println("SQLException.");
            System.err.println( e.getClass().getName() + 
                ": " + e.getMessage() );
        }
        catch (ClassNotFoundException e ) {
            System.err.println( e.getClass().getName() + 
                ": " + e.getMessage() );
        }
        finally {
            fromKeyboard.close( );
        }

    }
}

// Shamoil Soni Proj5 11/19/2017

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LoadTransaction {
	
    public static void main(String[] args) {
        Connection c = null;
        Statement s = null;
        Scanner fromFile = null;
        String sql1 = null, sql2 = null;
        String line = null, name = null, buyer = null;
        String[ ] fields;
        int age = 0, price = 0;

        try {
            // Define Connection and Statement objects.
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:transactions.db");
            s = c.createStatement();

            // Instantiate scanner to read from file.
            fromFile = new Scanner(new File("transactions.csv"));
            
            // Create kids table.
            sql1 = "create table if not exists " +
                "transactions(transactionid integer, " +
                "name varchar(50), " +
                "age integer, " +
                "buyer varchar(50), " +
                "price integer);";
            System.out.println("sql1: " + sql1);
            s.executeUpdate(sql1);

            // Read and throw away header line.
            fromFile.nextLine();
            // Populate kids table.
            for (int id = 1001; fromFile.hasNextLine( ); id++) {
                line = fromFile.nextLine( );
                fields = line.split(",");
                name = fields[0].trim( );
                age = Integer.parseInt(fields[1].trim( ));
                buyer = fields[2].trim( );
                price = Integer.parseInt(fields[3].trim( ));
                sql2 = String.format(
                    "insert into transactions (transactionid, name, age, buyer, price) " +
                    "values (%d, '%s', %d, '%s', %d);", 
                    id, name, age, buyer, price);
                System.out.println(sql2);
                s.executeUpdate(sql2);
            }
            c.close( );
        }
        catch (FileNotFoundException e) {
            System.out.println("File queries.sql not found.");
            System.err.println( e.getClass().getName() + 
                ": " + e.getMessage() );
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
            fromFile.close( );
        }
    }
}

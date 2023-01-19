import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import util.JdbcUtil;

public class MainONE {
    public static Scanner scanner = null;
    public static Connection connection = null;
    public static PreparedStatement pstmt = null;
    public static ResultSet resulSet = null;

    public static void main(String[] args) {
        System.out.println("\t\t=====================");
        System.out.println("\t\t Welcome to CRUD App");
        System.out.println("\t\t=====================");
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSelect an operation:");
            System.out.println("1. Insert");
            System.out.println("2. Select");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            System.out.print("Enter a number (1-5): ");
            int choice = scanner.nextInt();

            switch (choice) {
            case 1:
                insert();
                break;
            case 2:
                select();
                break;
            case 3:
                update();
                break;
            case 4:
                delete();
                break;
            case 5:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid operation. Please enter a number between 1 and 5.");
                break;
            }
        }
    }

    // 1st
    public static void insert() {
        // Code to perform insert operation
        System.out.println(" INSERT operation selected\n");

        try {
            connection = JdbcUtil.getJdbcConnection();

            String sqlInsertQuery = "insert into students(`sname`,`sage`,`saddress`)values(?,?,?);";
            if (connection != null)
                pstmt = connection.prepareStatement(sqlInsertQuery);

            if (pstmt != null) {

                System.out.print("Enter Student Name :: ");
                String sname = scanner.next();

                System.out.print("Enter Student Age :: ");
                int sage = scanner.nextInt();

                System.out.print("Enter Student Address :: ");
                String saddress = scanner.next();

                pstmt.setString(1, sname);
                pstmt.setInt(2, sage);
                pstmt.setString(3, saddress);

                int rowCount = pstmt.executeUpdate();
                System.out.println( rowCount + " recored successfully inserted");
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcUtil.CleanUp(connection, null, null);
//              System.out.println("Closing the resource...");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    // 2nd
    public static void select() {
        // Code to perform select operation
        System.out.println(" SELECT operation selected\n ");
        int sid = 0;

        try {
            connection = JdbcUtil.getJdbcConnection();

            String sqlSelectQuery = "select sid,sname,sage,saddress from students where sid = ?";
            if (connection != null)
                pstmt = connection.prepareStatement(sqlSelectQuery);

            if (pstmt != null) {
                scanner = new Scanner(System.in);

                System.out.print("Enter Student ID :: ");
                sid = scanner.nextInt();

                pstmt.setInt(1, sid);

                resulSet = pstmt.executeQuery();
            }
            if (resulSet != null) {
                if (resulSet.next()) {
                    System.out.println("-----------------------------------");
                    System.out.println("SID\tSNAME\tSAGE\tADDRESS");
                    System.out.println("-----------------------------------");
                    System.out.println(resulSet.getInt(1) + "\t" + resulSet.getString(2) + "\t" + resulSet.getString(3)
                            + "\t" + resulSet.getString(4));
                    System.out.println("===================================\n");
                } else {
                    System.out.println("Record is not available for the given ID :: " + sid);
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcUtil.CleanUp(connection, pstmt, resulSet);
//              System.out.println("Closing the resource...");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    // 3rd
    public static void update() {
        // Code to perform update operation
        System.out.println("UPDATE operation selected\n");

        try {
            connection = JdbcUtil.getJdbcConnection();

            String sqlUpdateQuery = "update students set sname = ?, sage = ? where sid = ?";
            if (connection != null) {
                pstmt = connection.prepareStatement(sqlUpdateQuery);
            }
            if (pstmt != null) {
                scanner = new Scanner(System.in);
                System.out.print("Enter Student ID :: ");
                int sid = scanner.nextInt();

                System.out.print("Enter Student Name :: ");
                String sname = scanner.next();

                System.out.print("Enter Student Age :: ");
                int sage = scanner.nextInt();

                pstmt.setString(1, sname);
                pstmt.setInt(2, sage);
                pstmt.setInt(3, sid);

                int rowCount = pstmt.executeUpdate();
                System.out.println( rowCount + " recored successfully Updated");
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcUtil.CleanUp(connection, pstmt, null);
//              System.out.println("Closing the resource...");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    // 4th
    public static void delete() {
        // Code to perform delete operation
        System.out.println(" DELETE operation selected.");
        
        try {
            connection = JdbcUtil.getJdbcConnection();

            String sqlDeleteQuery = "delete from students where sid = ?";
            if (connection != null)
                pstmt = connection.prepareStatement(sqlDeleteQuery);

            if (pstmt != null) {
                scanner = new Scanner(System.in);

                System.out.print("Enter Student ID :: ");
                int sid = scanner.nextInt();

                pstmt.setInt(1, sid);

                int rowCount = pstmt.executeUpdate();
                System.out.println( rowCount + " recored successfully Deleted");
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcUtil.CleanUp(connection, pstmt, null);
//              System.out.println("Closing the resource...");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }
}

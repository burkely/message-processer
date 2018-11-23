package com.lydia;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHandler {

    // database URL
    private static final String DB_URL = "jdbc:mysql://35.242.167.242:3306/trade_messages";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "Dublin!123";

    public static Connection connectToDatabase() {
        Connection conn = null;

        try {
            //Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return conn;
    }

    public static Statement createAndExecuteStatement(Connection conn, Message message){
        Statement stmt = null;

        try {
            //Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;

            /*sql = "INSERT INTO messages (userId, currencyFrom, currencyTo, amountSell, amountBuy, rate, timePlaced, originatingCountry) " +
                    "VALUES ('123456', 'EUR', 'GBP', 30, 36, 1.2, '2018-01-01 23:59:59', 'FR')";
            */
            // the mysql prepared insert statement
            String query = "INSERT INTO message (userId, currencyFrom, currencyTo, amountSell, amountBuy, rate, timePlaced, originatingCountry)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, message.getUserId());
            preparedStmt.setString (2, message.getCurrencyFrom());
            preparedStmt.setString (3, message.getCurrencyTo());
            preparedStmt.setBigDecimal (4, message.getAmountSell());
            preparedStmt.setBigDecimal (5, message.getAmountBuy());
            preparedStmt.setBigDecimal (6, message.getRate());
            preparedStmt.setString (7, message.getTimePlaced());
            preparedStmt.setString (8, message.getOriginatingCountry());

            // execute the preparedstatement
            preparedStmt.execute();
            
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }

        return stmt;
    }

}


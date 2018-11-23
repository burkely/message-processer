package com.lydia;

import java.sql.*;

public class DatabaseHandler {

    // database URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/trade_messages";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "Dublin123";

    public static Connection connectToInstance() {
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
            stmt = conn.createStatement();
            String sql;

            //create table if i doesnt exist
            sql = "CREATE TABLE IF NOT EXISTS message (userId VARCHAR(32), currencyFrom CHAR(4), " +
                    "currencyTo CHAR(4), amountSell DECIMAL(32, 8), amountBuy DECIMAL(32, 8), " +
                    "rate DECIMAL(24, 8),  timePlaced CHAR(32), originatingCountry CHAR(4), " +
                    "entryID INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(entryID))";

            stmt.executeUpdate(sql);

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


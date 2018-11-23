package com.lydia;

import org.json.JSONObject;
import java.sql.Connection;
import java.sql.Statement;

public class MessageHandler {

    MessageHandler(){}

    public static Message processPost(String jsonBody) {
        Message message = new Message();
        if(jsonBody!=null && jsonBody.length()>0 ){
            JSONObject obj= new JSONObject(jsonBody);

            //***TODO validate data, ie check each value is correct length/exists etc

            // set message values from json object
            message.setUserId(obj.getString("userId"));
            message.setCurrencyFrom(obj.getString("currencyFrom"));
            message.setCurrencyTo(obj.getString("currencyTo"));
            message.setAmountSell(obj.getBigDecimal("amountSell"));
            message.setAmountBuy(obj.getBigDecimal("amountBuy"));
            message.setRate(obj.getBigDecimal("rate"));
            message.setTimePlaced(obj.getString("timePlaced"));
            message.setOriginatingCountry(obj.getString("originatingCountry"));

            Connection conn = DatabaseHandler.connectToInstance();

            Statement stmt = DatabaseHandler.createAndExecuteStatement(conn, message);

        }else{
            // TODO handle empty post
        }

        return message;
    }

}

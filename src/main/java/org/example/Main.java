package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // scanner
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount in USD");
        double usdAmount = scanner.nextDouble();
        scanner.nextLine();
        //get current exchange rate
        try {
        URL url = new URL("https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_r2Rlvh05m1tdtqUWmjgSmBrE28CoIS3VrlG6dbO0&currencies=USD%2CEUR&");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        StringBuffer response = new StringBuffer();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        System.out.println("Response Code: " + responseCode);
        //parse json response
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
        JSONObject data = (JSONObject) jsonResponse.get("data");
        // convert json response to variables
            Number eurValue = (Number) data.get("EUR");
            Number usdValue = (Number) data.get("USD");
            double eurDouble = eurValue.doubleValue();
            double usdDouble = usdValue.doubleValue();

            // convert entered number
            double euroAmount = usdAmount * eurDouble;
            System.out.println("$"+usdAmount+" is €"+euroAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
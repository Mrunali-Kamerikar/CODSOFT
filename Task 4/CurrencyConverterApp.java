
/*
    Codsoft Java Internship

    Task 4 ( Currency Converter  )

    This code provides a Java Swing GUI for currency conversion, allowing users to select base and target currencies, input an amount, 
    fetch real-time exchange rates from an API, perform the conversion, and display the converted amount and target currency symbol.

    By Mrunali Kamerikar
 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class CurrencyConverterApp extends JFrame {

    private static final String API_KEY = "https://v6.exchangerate-api.com/v6/57de91db6992e7a354d33ade/latest/USD";
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    private JComboBox<String> baseCurrencyBox;
    private JComboBox<String> targetCurrencyBox;
    private JTextField amountField;
    private JLabel resultLabel;

    public CurrencyConverterApp() {
        setTitle("Currency Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout manager
        setLayout(new GridLayout(5, 2));

        // List of currencies
        List<String> currencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "SEK", "NZD");

        // Create UI components
        JLabel baseCurrencyLabel = new JLabel("Base Currency:");
        baseCurrencyBox = new JComboBox<>(currencies.toArray(new String[0]));

        JLabel targetCurrencyLabel = new JLabel("Target Currency:");
        targetCurrencyBox = new JComboBox<>(currencies.toArray(new String[0]));

        JLabel amountLabel = new JLabel("Amount to Convert:");
        amountField = new JTextField();

        JButton convertButton = new JButton("Convert");
        resultLabel = new JLabel("Converted Amount: ");

        // Add components to frame
        add(baseCurrencyLabel);
        add(baseCurrencyBox);
        add(targetCurrencyLabel);
        add(targetCurrencyBox);
        add(amountLabel);
        add(amountField);
        add(new JLabel("")); // Empty cell
        add(convertButton);
        add(new JLabel("")); // Empty cell
        add(resultLabel);

        // Add action listener to the button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });
    }

    private void convertCurrency() {
        String baseCurrency = (String) baseCurrencyBox.getSelectedItem();
        String targetCurrency = (String) targetCurrencyBox.getSelectedItem();
        double amount;
        
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid amount. Please enter a valid number.");
            return;
        }

        try {
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
            if (exchangeRate != -1) {
                double convertedAmount = amount * exchangeRate;
                resultLabel.setText(String.format("Converted Amount: %.2f %s", convertedAmount, targetCurrency));
            } else {
                resultLabel.setText("Failed to fetch exchange rate. Please try again.");
            }
        } catch (Exception e) {
            resultLabel.setText("An error occurred: " + e.getMessage());
        }
    }

    private double fetchExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        String urlStr = API_URL + baseCurrency + "?apikey=" + API_KEY;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String jsonResponse = response.toString();
            return parseExchangeRate(jsonResponse, targetCurrency);
        } else {
            System.out.println("GET request failed. Response Code: " + responseCode);
            return -1;
        }
    }

    private double parseExchangeRate(String jsonResponse, String targetCurrency) {
        String searchString = "\"" + targetCurrency + "\":";
        int targetIndex = jsonResponse.indexOf(searchString);
        if (targetIndex != -1) {
            int start = targetIndex + searchString.length();
            int end = jsonResponse.indexOf(",", start);
            if (end == -1) {
                end = jsonResponse.indexOf("}", start);
            }
            return Double.parseDouble(jsonResponse.substring(start, end));
        } else {
            System.out.println("Target currency not found in the response.");
            return -1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CurrencyConverterApp().setVisible(true);
            }
        });
    }
}

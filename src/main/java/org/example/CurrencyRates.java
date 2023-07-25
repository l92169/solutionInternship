package org.example;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyRates {
    static String CODE_PATTERN = "^[A-Z]{3}$";
    static String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    public static void main(String[] args) {
        String date = null, currencyCode = null;

        if (args.length != 2) {
            System.out.println("Enter arguments");
            return;
        }

        for (String arg : args) {
            if (arg.startsWith("--code=")) {
                currencyCode = arg.substring(7);
            } else if (arg.startsWith("--date=")) {
                date = arg.substring(7);
            }
        }

        if (currencyCode == null || date == null ) {
            System.out.println("Invalid arguments. Example: java CurrencyRates.java --code=USD --date=2022-10-08");
            return;
        }

        if (isValidDate(date) && isValidCurrencyCode(currencyCode)) {
            try {
                date = parseDate(date);
                String xmlUrl = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date;
                URL url = new URL(xmlUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == 200) {
                    InputStream responseStream = connection.getInputStream();

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(responseStream);
                    NodeList currencyList = doc.getElementsByTagName("Valute");

                    for (int i = 0; i < currencyList.getLength(); i++) {
                        Element currencyElement = (Element) currencyList.item(i);
                        String code = currencyElement.getElementsByTagName("CharCode").item(0).getTextContent();
                        if (code.equals(currencyCode)) {
                            String name = currencyElement.getElementsByTagName("Name").item(0).getTextContent();
                            String rate = currencyElement.getElementsByTagName("Value").item(0).getTextContent();
                            System.out.println(code + " (" + name + "): " + rate);
                            return;
                        }
                    }
                    System.out.println("Currency code not found.");
                } else {
                    System.out.println("Failed to fetch data. HTTP error code: " + connection.getResponseCode());
                }
                connection.disconnect();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Args validation failed. Date format should be YYYY-MM-DD and code should be like USD");
        }
    }

    public static String parseDate(String date){
        String[] parts = date.split("-");
        return parts[2] + "/" + parts[1] + "/" + parts[0];
    }

    public static boolean isValidCurrencyCode(String currencyCode) {
        return  currencyCode.matches(CODE_PATTERN);
    }

    public static boolean isValidDate(String date) {
        return date.matches(DATE_PATTERN);
    }
}

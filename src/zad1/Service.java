/**
 *
 *  @author Krawiecki Tymoteusz S25611
 *
 */

package zad1;

import com.sun.xml.internal.ws.util.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Service {

    String country;
    private String city;
    private String ex;
    String table = "a";

    public Service(String country) {
        this.country = country;
    }

    public String getWeather(String city2) {
        city = city2;
        URL url = null;
        try {
            url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + getISO(country) + "&appid=e666a7f4acd6ea43cb9e42bde6bb0f76");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(url.openConnection().getInputStream())));
            String collectJson = bufferedReader.lines().collect(Collectors.joining());

            JSONParser parser = new JSONParser();

            return collectJson;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Double getRateFor(String ex2) {

        ex = ex2;

        String urlString = "https://api.exchangerate.host/latest?base=" + getCurrencyCode(country) + "&symbols=" + ex;

        try {
            URL url = new URL(urlString);
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader((new InputStreamReader(url.openConnection().getInputStream())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String collectJson = bufferedReader.lines().collect(Collectors.joining());

            //   System.out.println(collectJson);

            JSONParser parser = new JSONParser();

            try {
                JSONObject parse = (JSONObject) parser.parse(collectJson);
                JSONObject ratesInfo = (JSONObject) parse.get("rates");

                String ratesInfoString = ratesInfo.toJSONString();

                System.out.println(ratesInfoString);

                String[] data = ratesInfoString.split(":");

                if (data[1] != null && data[1].length() > 0 && data[1].charAt(data[1].length() - 1) == '}') {
                    data[1] = data[1].substring(0, data[1].length() - 1);
                }

                return Double.parseDouble(data[1]);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    public Double getNBPRate() {

        if(getCurrencyCode(country).equals("PLN")){
            return 1.0;
        }
        else{

        String urlStringA = "http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + getCurrencyCode(country) + "/?format=json";

        try {
            URL urlA = new URL(urlStringA);
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(urlA.openConnection().getInputStream())));
            String collectJson = bufferedReader.lines().collect(Collectors.joining());

            JSONParser parser = new JSONParser();
            JSONObject parse = (JSONObject) parser.parse(collectJson);

            JSONArray rateInfoArray = (JSONArray) parse.get("rates");
            JSONObject rateInfo = (JSONObject) rateInfoArray.get(0);

            Double rate = Double.parseDouble(rateInfo.get("mid").toString());
            return rate;


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            table = "b";
            return getNBPRate();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}
    public String getISO(String country){
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(Locale.ENGLISH), iso);
        }

        return countries.get(country);
    }


    public String getCurrencyCode(String country) {
        return Currency.getInstance(new Locale("", getISO(country))).getCurrencyCode();
    }

    public String getCity() {
        return city;
    }

    public String getEx() {
        return ex;
    }
}

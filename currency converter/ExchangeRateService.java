
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateService {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public double getExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        String urlStr = API_URL + baseCurrency.toUpperCase();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        return parseRateFromJSON(content.toString(), targetCurrency);
    }

    // Simple JSON parsing to avoid pulling in a heavy library for a single value
    private double parseRateFromJSON(String json, String targetCurrency) {
        // We look for "targetCurrency": 1.23
        // This is a naive parser but sufficient for the specific API structure
        String searchKey = "\"" + targetCurrency.toUpperCase() + "\":";
        int index = json.indexOf(searchKey);

        if (index == -1) {
            throw new IllegalArgumentException("Currency not found: " + targetCurrency);
        }

        int startValue = index + searchKey.length();
        int endValue = json.indexOf(",", startValue);
        if (endValue == -1) {
            endValue = json.indexOf("}", startValue); // Case for last item
        }

        String valueStr = json.substring(startValue, endValue).trim();
        return Double.parseDouble(valueStr);
    }
}

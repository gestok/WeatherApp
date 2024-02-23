package plh.team1.weatherapp.api;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {

    private final String urlToCall;
    private String responseString;

    public Api(String cityname) {
        urlToCall = "https://wttr.in/" + cityname + "?format=j1";
    }

    public String getJSONString() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlToCall).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return null; 
    }

    public String getResponseString() {
        if (responseString == null) {
            try {
                responseString = getJSONString();
            } catch (IOException e) {
                // Handle the exception if needed
                e.printStackTrace();
            }
        }
        return responseString;
    }
}

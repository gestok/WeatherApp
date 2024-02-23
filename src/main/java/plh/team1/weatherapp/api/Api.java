package plh.team1.weatherapp.api;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {

  private final String urlToCall;
  private String responseString;

  // Constructor to initialize the API with the specified city name
  public Api(String cityname) {
    // Build the URL based on the city name and desired JSON format
    urlToCall = "https://wttr.in/" + cityname + "?format=j1";
  }

  // Method to fetch JSON data from the specified URL using OkHttp
  public String getJSONString() throws IOException {
    OkHttpClient client = new OkHttpClient(); // Create an instance of OkHttpClient
    Request request = new Request.Builder().url(urlToCall).build(); // Build the request

    try (Response response = client.newCall(request).execute()) {
      // Check if the response is successful and the body is not null
      if (response.isSuccessful() && response.body() != null) {
        // Set the responseString with the content of the response body
        return response.body().string();
      }
    } catch (IOException e) {
      // Log the exception or throw a custom exception for better error handling
      e.printStackTrace();
      throw e;
    }

    return null; // Return null if the response is not successful or the body is null
  }

  // Method to retrieve the fetched JSON response
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
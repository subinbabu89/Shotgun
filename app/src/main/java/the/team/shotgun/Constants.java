package the.team.shotgun;

/**
 * Created by Subin on 10/1/2016.
 */

public class Constants {
    public static final String login_url = "http://ec2-54-191-60-17.us-west-2.compute.amazonaws.com:8080/login";
    public static final String registration_url = "http://ec2-54-191-60-17.us-west-2.compute.amazonaws.com:8080/register";
    public static final String question_url = "http://ec2-54-191-60-17.us-west-2.compute.amazonaws.com:8080/questions";
    public static final String provide_pool_url = "http://ec2-54-191-60-17.us-west-2.compute.amazonaws.com:8080/provide_pool";
    public static final String add_destination_url = "http://ec2-54-191-60-17.us-west-2.compute.amazonaws.com:8080/add_destination";
    public static final String request_pool_url = "http://ec2-54-191-60-17.us-west-2.compute.amazonaws.com:8080/request_pool";
    public static final String weather_url = "http://ec2-54-191-60-17.us-west-2.compute.amazonaws.com:8080/weather";

    public static String currentId = "";
    public static String current_lat = "";
    public static String current_lng = "";
    public static String dest_lat = "";
    public static String dest_lng = "";
    public static String dest_location_address = "";

    public static final String LOG_TAG = "Google Places Autocomplete";
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String OUT_JSON = "/json";

    public static final String API_KEY = "-----------your API_KEY--------------------";
}

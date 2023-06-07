package backend.Flights;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wyatt Duberstein and Asher Gust
 *
 */
@RestController
public class FlightController {

    @Autowired
    FlightRepository flightRepository;

    private final int FAIL = 0;
    private final int SUCCESS = 1;

    /**
     * Fetches a complete list of flights with all of their attributes
     * @return list of flights
     */
    @GetMapping(path = "/flights")
    public List<Flight> getAllFlights(){
        return flightRepository.findAll();
    }

    /**
     * Fetches a flight by id
     * @param id id of flight
     * @return flight object
     */
    @GetMapping(path = "/flights/{id}")
    public Flight getFlightById(@PathVariable int id){
        return flightRepository.findById(id);
    }

    /**
     * Creates a flight
     * @param flight newly created flight object to be inserted into database
     * @return success/fail message
     */
    @PostMapping(path = "/flights")
    public Flight createFlight(@RequestBody Flight flight){
        if (flight == null)
            return null;
        flightRepository.save(flight);
        return flight;
    }

    /**
     * fetches a list of real flights with the provided information.
     * @param from the airport code for the departure
     * @param to the airport code for the arrival
     * @param date the date of the flight in format YYYY-MM-DD
     * @return a list of real flights that match the provided information
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping(path = "/flights/from/{from}/to/{to}/date/{date}")
    public List<Flight> getRealFLights(@PathVariable String from, @PathVariable String to, @PathVariable String date) throws IOException, InterruptedException {
        List<Flight> flights = new ArrayList<Flight>();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://flight-fare-search.p.rapidapi.com/flight/search?from=" + from + "&to=" + to + "&date=" + date + "&currency=USD&type=Economy&adult=1&child=0&infant=0"))
                .header("x-rapidapi-host", "flight-fare-search.p.rapidapi.com")                 // below are different keys to use for testing since each can only be used 10 times a day
                .header("x-rapidapi-key", "f0e66f3759msh5e79b01f81ed812p1b1a44jsnb9ed60196054") // f0e66f3759msh5e79b01f81ed812p1b1a44jsnb9ed60196054  // 42ebb1181amshd8c5285d3b892f5p1c76ecjsn938624986490   // f9f7d3e53amsha42281674073edfp14cc80jsn431b7b2498e2
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jo = new JSONObject(response.body());
        if (!jo.getString("status").equals("success")) return null;

        JSONArray ja = jo.getJSONArray("data");
        for (int i = 0; i < ja.length(); i++){
            JSONObject jo2 = new JSONObject(ja.get(i).toString());
            String airlineName = jo2.getString("flight_name");
            double price = jo2.getJSONObject("price").getDouble("total");

//            JSONArray ja2 = jo2.getJSONArray("stopSummary");
//            int[] stopDurations = new int[ja2.length()];
//            String[] stropLocations = new String[ja2.length()];
//            for (int j = 0; j < ja2.length(); j++){
//                JSONObject jo3 = new JSONObject(ja2.get(i).toString());
//                stopDurations[i] = (jo3.getInt("duration"));
//                stropLocations[i] = (jo3.getString("airportCode"));
//            }
//            Flight flight = new Flight(airlineName,price,date,from,to,stopDurations,stopLocations);

            Flight flight = new Flight(airlineName,price,date,from,to);
            flights.add(flight);
        }

        return flights;
    }

    /**
     * This method is exactly the same as the one for real flights except instead of grabbing real data
     * from an API, it returns data from an API call I made previously in testing. The point of this method
     * is for testing, as if this method works the one for real data should. This method is necessary because
     * the API for real flights can only be called 10 times a day unless I pay for a premium subscription to it.
     * @param from the airport code for the departure - WILL NOT BE CORRECT FOR RETURNED FLIGHT
     * @param to the airport code for the arrival - WILL NOT BE CORRECT FOR RETURNED FLIGHT
     * @param date the date of the flight in format YYYY-MM-DD - WILL NOT BE CORRECT FOR RETURNED FLIGHT
     * @return a list of mock flights that DON'T match the provided information. Used for testing.
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping(path = "/flights/from/{from}/to/{to}/date/{date}/mock")
    public List<Flight> getMockFLights(@PathVariable String from, @PathVariable String to, @PathVariable String date) throws IOException, InterruptedException {
        List<Flight> flights = new ArrayList<Flight>();

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://flight-fare-search.p.rapidapi.com/flight/search?from=" + from + "&to=" + to + "&date=" + date + "&currency=USD&type=Economy&adult=1&child=0&infant=0"))
//                .header("x-rapidapi-host", "flight-fare-search.p.rapidapi.com")
//                .header("x-rapidapi-key", "42ebb1181amshd8c5285d3b892f5p1c76ecjsn938624986490")
//                .method("GET", HttpRequest.BodyPublishers.noBody())
//                .build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        JSONObject jo = new JSONObject(response.body());
        JSONObject jo = new JSONObject("{\"date\":\"2021-12-02T17:09:11+00:00\",\"data\":[{\"path\":[\"NK-953\",\"NK-316\"],\"price\":{\"total\":255.22744352},\"stopSummary\":[{\"duration\":309,\"airportCode\":\"DTW\"}],\"flight_name\":\"Spirit Airlines\",\"fligh_code\":\"NK\",\"stops\":\"1 Stop\",\"tripDuration\":675},{\"path\":[\"NK-868\",\"NK-718\"],\"price\":{\"total\":284.23283951999997},\"stopSummary\":[{\"duration\":174,\"airportCode\":\"DFW\"}],\"flight_name\":\"Spirit Airlines\",\"fligh_code\":\"NK\",\"stops\":\"1 Stop\",\"tripDuration\":555},{\"path\":[\"NK-2152\"],\"price\":{\"total\":233.43339565},\"stopSummary\":[{\"duration\":null,\"airportCode\":\"\"}],\"flight_name\":\"Spirit Airlines\",\"fligh_code\":\"NK\",\"stops\":\"Direct\",\"tripDuration\":326},{\"path\":[\"NK-310\",\"NK-710\"],\"price\":{\"total\":515.2757951599999},\"stopSummary\":[{\"duration\":432,\"airportCode\":\"FLL\"}],\"flight_name\":\"Spirit Airlines\",\"fligh_code\":\"NK\",\"stops\":\"1 Stop\",\"tripDuration\":895},{\"path\":[\"B6-2797\",\"B6-1278\"],\"price\":{\"total\":201.50199999999998,\"tax\":43.568,\"refundable\":false,\"base\":157.934},\"stopSummary\":[{\"duration\":112,\"airportCode\":\"JAX\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":503},{\"path\":[\"B6-354\"],\"price\":{\"total\":220.563,\"tax\":35.399,\"refundable\":false,\"base\":185.164},\"stopSummary\":[{\"duration\":null,\"airportCode\":\"\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"Direct\",\"tripDuration\":311},{\"path\":[\"AA-2296\",\"AA-562\"],\"price\":{\"total\":231.45499999999998,\"tax\":46.291,\"refundable\":false,\"base\":185.164},\"stopSummary\":[{\"duration\":50,\"airportCode\":\"ORD\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"1 Stop\",\"tripDuration\":415},{\"path\":[\"AA-5914\",\"AA-1967\"],\"price\":{\"total\":250.516,\"tax\":46.291,\"refundable\":false,\"base\":204.225},\"stopSummary\":[{\"duration\":50,\"airportCode\":\"PHX\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"1 Stop\",\"tripDuration\":398},{\"path\":[\"B6-1700\",\"B6-706\"],\"price\":{\"total\":264.131,\"tax\":46.291,\"refundable\":false,\"base\":217.83999999999997},\"stopSummary\":[{\"duration\":129,\"airportCode\":\"FLL\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":594},{\"path\":[\"AA-421\",\"AA-1533\"],\"price\":{\"total\":285.91499999999996,\"tax\":49.013999999999996,\"refundable\":false,\"base\":236.90099999999998},\"stopSummary\":[{\"duration\":234,\"airportCode\":\"CLT\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"1 Stop\",\"tripDuration\":610},{\"path\":[\"B6-988\",\"B6-531\"],\"price\":{\"total\":299.53,\"tax\":49.013999999999996,\"refundable\":false,\"base\":250.516},\"stopSummary\":[{\"duration\":35,\"airportCode\":\"BOS\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":440},{\"path\":[\"B6-1800\",\"B6-472\"],\"price\":{\"total\":307.699,\"tax\":51.736999999999995,\"refundable\":false,\"base\":255.962},\"stopSummary\":[{\"duration\":134,\"airportCode\":\"FLL\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":599},{\"path\":[\"B6-1800\",\"B6-06\"],\"price\":{\"total\":321.31399999999996,\"tax\":51.736999999999995,\"refundable\":false,\"base\":269.577},\"stopSummary\":[{\"duration\":70,\"airportCode\":\"FLL\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":548},{\"path\":[\"B6-132\",\"B6-2528\"],\"price\":{\"total\":334.929,\"tax\":51.736999999999995,\"refundable\":false,\"base\":283.192},\"stopSummary\":[{\"duration\":120,\"airportCode\":\"MCO\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":544},{\"path\":[\"B6-132\",\"B6-598\"],\"price\":{\"total\":337.652,\"tax\":51.736999999999995,\"refundable\":false,\"base\":285.91499999999996},\"stopSummary\":[{\"duration\":125,\"airportCode\":\"MCO\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":543},{\"path\":[\"B6-2797\",\"B6-678\"],\"price\":{\"total\":340.375,\"tax\":59.906,\"refundable\":false,\"base\":280.469},\"stopSummary\":[{\"duration\":352,\"airportCode\":\"JAX\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":743},{\"path\":[\"B6-288\",\"B6-317\"],\"price\":{\"total\":351.267,\"tax\":54.459999999999994,\"refundable\":false,\"base\":296.80699999999996},\"stopSummary\":[{\"duration\":46,\"airportCode\":\"BOS\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":450},{\"path\":[\"UA-408\",\"UA-1197\"],\"price\":{\"total\":356.71299999999997,\"tax\":54.459999999999994,\"refundable\":false,\"base\":302.253},\"stopSummary\":[{\"duration\":137,\"airportCode\":\"PHX\"}],\"flight_name\":\"United Airlines\",\"fligh_code\":\"UA\",\"stops\":\"1 Stop\",\"tripDuration\":485},{\"path\":[\"UA-1990\"],\"price\":{\"total\":362.159,\"tax\":43.568,\"refundable\":false,\"base\":318.591},\"stopSummary\":[{\"duration\":null,\"airportCode\":\"\"}],\"flight_name\":\"United Airlines\",\"fligh_code\":\"UA\",\"stops\":\"Direct\",\"tripDuration\":308},{\"path\":[\"B6-888\",\"B6-2379\"],\"price\":{\"total\":370.328,\"tax\":54.459999999999994,\"refundable\":false,\"base\":315.868},\"stopSummary\":[{\"duration\":98,\"airportCode\":\"BOS\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":508},{\"path\":[\"AA-658\",\"AA-4668\"],\"price\":{\"total\":373.051,\"tax\":54.459999999999994,\"refundable\":false,\"base\":318.591},\"stopSummary\":[{\"duration\":30,\"airportCode\":\"PHL\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"1 Stop\",\"tripDuration\":392},{\"path\":[\"B6-132\",\"B6-698\"],\"price\":{\"total\":375.774,\"tax\":62.629,\"refundable\":false,\"base\":313.145},\"stopSummary\":[{\"duration\":305,\"airportCode\":\"MCO\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":724},{\"path\":[\"B6-988\",\"B6-717\"],\"price\":{\"total\":378.49699999999996,\"tax\":54.459999999999994,\"refundable\":false,\"base\":324.037},\"stopSummary\":[{\"duration\":134,\"airportCode\":\"BOS\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":531},{\"path\":[\"B6-636\",\"B6-1516\"],\"price\":{\"total\":386.666,\"tax\":57.183,\"refundable\":false,\"base\":329.483},\"stopSummary\":[{\"duration\":31,\"airportCode\":\"SFO\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":430},{\"path\":[\"B6-100\",\"B6-902\"],\"price\":{\"total\":389.38899999999995,\"tax\":57.183,\"refundable\":false,\"base\":332.20599999999996},\"stopSummary\":[{\"duration\":165,\"airportCode\":\"FLL\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":619},{\"path\":[\"AA-1994\",\"AA-2339\"],\"price\":{\"total\":394.835,\"tax\":57.183,\"refundable\":false,\"base\":337.652},\"stopSummary\":[{\"duration\":52,\"airportCode\":\"PHX\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"1 Stop\",\"tripDuration\":419},{\"path\":[\"B6-888\",\"B6-517\"],\"price\":{\"total\":400.281,\"tax\":57.183,\"refundable\":false,\"base\":343.09799999999996},\"stopSummary\":[{\"duration\":128,\"airportCode\":\"BOS\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":526},{\"path\":[\"B6-132\",\"B6-1184\"],\"price\":{\"total\":403.00399999999996,\"tax\":57.183,\"refundable\":false,\"base\":345.82099999999997},\"stopSummary\":[{\"duration\":130,\"airportCode\":\"MCO\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":547},{\"path\":[\"AA-1534\",\"AA-1640\"],\"price\":{\"total\":405.727,\"tax\":65.352,\"refundable\":false,\"base\":340.375},\"stopSummary\":[{\"duration\":248,\"airportCode\":\"DFW\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"1 Stop\",\"tripDuration\":630},{\"path\":[\"B6-132\",\"B6-2028\"],\"price\":{\"total\":411.173,\"tax\":57.183,\"refundable\":false,\"base\":353.99},\"stopSummary\":[{\"duration\":240,\"airportCode\":\"MCO\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":665},{\"path\":[\"AA-9155\"],\"price\":{\"total\":427.51099999999997,\"tax\":49.013999999999996,\"refundable\":false,\"base\":378.49699999999996},\"stopSummary\":[{\"duration\":null,\"airportCode\":\"\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"Direct\",\"tripDuration\":320},{\"path\":[\"B6-1080\",\"B6-2348\"],\"price\":{\"total\":430.234,\"tax\":59.906,\"refundable\":false,\"base\":370.328},\"stopSummary\":[{\"duration\":118,\"airportCode\":\"LAS\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":471},{\"path\":[\"B6-132\",\"B6-1584\"],\"price\":{\"total\":449.29499999999996,\"tax\":68.075,\"refundable\":false,\"base\":381.21999999999997},\"stopSummary\":[{\"duration\":305,\"airportCode\":\"MCO\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":720},{\"path\":[\"DL-897\",\"DL-524\"],\"price\":{\"total\":465.633,\"tax\":62.629,\"refundable\":false,\"base\":403.00399999999996},\"stopSummary\":[{\"duration\":31,\"airportCode\":\"SLC\"}],\"flight_name\":\"Delta Air Lines\",\"fligh_code\":\"DL\",\"stops\":\"1 Stop\",\"tripDuration\":409},{\"path\":[\"B6-100\",\"B6-606\"],\"price\":{\"total\":484.69399999999996,\"tax\":62.629,\"refundable\":false,\"base\":422.065},\"stopSummary\":[{\"duration\":176,\"airportCode\":\"FLL\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":638},{\"path\":[\"DL-321\",\"DL-1613\"],\"price\":{\"total\":487.417,\"tax\":62.629,\"refundable\":false,\"base\":424.78799999999995},\"stopSummary\":[{\"duration\":40,\"airportCode\":\"ATL\"}],\"flight_name\":\"Delta Air Lines\",\"fligh_code\":\"DL\",\"stops\":\"1 Stop\",\"tripDuration\":427},{\"path\":[\"AA-2201\"],\"price\":{\"total\":506.47799999999995,\"tax\":54.459999999999994,\"refundable\":false,\"base\":452.018},\"stopSummary\":[{\"duration\":null,\"airportCode\":\"\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"Direct\",\"tripDuration\":320},{\"path\":[\"B6-132\",\"B6-884\"],\"price\":{\"total\":511.924,\"tax\":73.521,\"refundable\":false,\"base\":438.40299999999996},\"stopSummary\":[{\"duration\":370,\"airportCode\":\"MCO\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":788},{\"path\":[\"AS-1370\",\"AS-628\"],\"price\":{\"total\":517.37,\"tax\":65.352,\"refundable\":false,\"base\":452.018},\"stopSummary\":[{\"duration\":57,\"airportCode\":\"SEA\"}],\"flight_name\":\"China United Airlines\",\"fligh_code\":\"HR\",\"stops\":\"1 Stop\",\"tripDuration\":549},{\"path\":[\"DL-1041\",\"DL-556\"],\"price\":{\"total\":522.816,\"tax\":65.352,\"refundable\":false,\"base\":457.464},\"stopSummary\":[{\"duration\":58,\"airportCode\":\"DFW\"}],\"flight_name\":\"Delta Air Lines\",\"fligh_code\":\"DL\",\"stops\":\"1 Stop\",\"tripDuration\":442},{\"path\":[\"B6-100\",\"B6-402\"],\"price\":{\"total\":530.985,\"tax\":65.352,\"refundable\":false,\"base\":465.633},\"stopSummary\":[{\"duration\":92,\"airportCode\":\"FLL\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":545},{\"path\":[\"AS-1055\",\"AS-628\"],\"price\":{\"total\":560.938,\"tax\":68.075,\"refundable\":false,\"base\":492.863},\"stopSummary\":[{\"duration\":207,\"airportCode\":\"SEA\"}],\"flight_name\":\"China United Airlines\",\"fligh_code\":\"HR\",\"stops\":\"1 Stop\",\"tripDuration\":699},{\"path\":[\"AA-6248\",\"AA-4922\"],\"price\":{\"total\":588.168,\"tax\":70.798,\"refundable\":false,\"base\":517.37},\"stopSummary\":[{\"duration\":31,\"airportCode\":\"IAH\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"1 Stop\",\"tripDuration\":431},{\"path\":[\"DL-3654\",\"DL-487\"],\"price\":{\"total\":601.783,\"tax\":70.798,\"refundable\":false,\"base\":530.985},\"stopSummary\":[{\"duration\":35,\"airportCode\":\"SFO\"}],\"flight_name\":\"Delta Air Lines\",\"fligh_code\":\"DL\",\"stops\":\"1 Stop\",\"tripDuration\":449},{\"path\":[\"B6-100\",\"B6-306\"],\"price\":{\"total\":631.736,\"tax\":73.521,\"refundable\":false,\"base\":558.2149999999999},\"stopSummary\":[{\"duration\":76,\"airportCode\":\"FLL\"}],\"flight_name\":\"JetBlue Airways\",\"fligh_code\":\"B6\",\"stops\":\"1 Stop\",\"tripDuration\":537},{\"path\":[\"AS-330\",\"AS-426\"],\"price\":{\"total\":639.905,\"tax\":73.521,\"refundable\":false,\"base\":566.384},\"stopSummary\":[{\"duration\":118,\"airportCode\":\"SEA\"}],\"flight_name\":\"China United Airlines\",\"fligh_code\":\"HR\",\"stops\":\"1 Stop\",\"tripDuration\":614},{\"path\":[\"UA-2277\",\"UA-3583\"],\"price\":{\"total\":686.1959999999999,\"tax\":76.244,\"refundable\":false,\"base\":609.952},\"stopSummary\":[{\"duration\":46,\"airportCode\":\"ORD\"}],\"flight_name\":\"United Airlines\",\"fligh_code\":\"UA\",\"stops\":\"1 Stop\",\"tripDuration\":428},{\"path\":[\"DL-930\",\"DL-5203\"],\"price\":{\"total\":729.764,\"tax\":78.967,\"refundable\":false,\"base\":650.7969999999999},\"stopSummary\":[{\"duration\":64,\"airportCode\":\"MSP\"}],\"flight_name\":\"Delta Air Lines\",\"fligh_code\":\"DL\",\"stops\":\"1 Stop\",\"tripDuration\":440},{\"path\":[\"UA-2277\",\"UA-2164\"],\"price\":{\"total\":835.961,\"tax\":87.136,\"refundable\":false,\"base\":748.8249999999999},\"stopSummary\":[{\"duration\":46,\"airportCode\":\"ORD\"}],\"flight_name\":\"United Airlines\",\"fligh_code\":\"UA\",\"stops\":\"1 Stop\",\"tripDuration\":421},{\"path\":[\"AA-421\",\"AA-1489\"],\"price\":{\"total\":841.4069999999999,\"tax\":95.30499999999999,\"refundable\":false,\"base\":746.102},\"stopSummary\":[{\"duration\":348,\"airportCode\":\"CLT\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"1 Stop\",\"tripDuration\":721},{\"path\":[\"AA-1914\",\"AA-4631\"],\"price\":{\"total\":1307.04,\"tax\":119.812,\"refundable\":false,\"base\":1187.2279999999998},\"stopSummary\":[{\"duration\":133,\"airportCode\":\"IND\"}],\"flight_name\":\"American Airlines\",\"fligh_code\":\"AA\",\"stops\":\"1 Stop\",\"tripDuration\":500}],\"from\":\"LAX\",\"currency\":\"USD\",\"to\":\"NYC\",\"status\":\"success\"}");
        if (!jo.getString("status").equals("success")) return null;

        JSONArray ja = jo.getJSONArray("data");
        for (int i = 0; i < ja.length(); i++){
            JSONObject jo2 = new JSONObject(ja.get(i).toString());
            String airlineName = jo2.getString("flight_name");
            double price = jo2.getJSONObject("price").getDouble("total");

//            JSONArray ja2 = jo2.getJSONArray("stopSummary");
//            int[] stopDurations = new int[ja2.length()];
//            String[] stropLocations = new String[ja2.length()];
//            for (int j = 0; j < ja2.length(); j++){
//                JSONObject jo3 = new JSONObject(ja2.get(i).toString());
//                stopDurations[i] = (jo3.getInt("duration"));
//                stropLocations[i] = (jo3.getString("airportCode"));
//            }
//            Flight flight = new Flight(airlineName,price,date,from,to,stopDurations,stopLocations);

            Flight flight = new Flight(airlineName,price,date,from,to);
            flights.add(flight);
        }

        return flights;
    }

    /**
     * Deletes a flight
     * @param id id of flight
     * @return success/fail message
     */
    @DeleteMapping(path = "/flights/{id}")
    public int deleteFlight(@PathVariable int id){
        Flight flight = flightRepository.findById(id);
        if (flight == null)
            return FAIL;
        flightRepository.delete(flight);
        return SUCCESS;
    }
}

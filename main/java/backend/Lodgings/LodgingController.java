package backend.Lodgings;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class LodgingController {

    @Autowired
    private LodgingRepository lodgingRepository;

    private final int FAIL = 0;
    private final int SUCCESS = 1;

    /**
     * Fetches a complete list of lodgings with all their attributes
     * @return list of lodgings
     */
    @GetMapping(path = "/lodgings")
    public List<Lodging> getAllLodgings(){
        return lodgingRepository.findAll();
    }

    /**
     * Fetches a lodging by id
     * @param id id of lodging
     * @return lodging object
     */
    @GetMapping(path = "/lodgings/{id}")
    public Lodging getLodgingById(@PathVariable int id){
        return lodgingRepository.findById(id);
    }

    /**
     * Create a lodging
     * @param lodging logding object to be inserted into database
     * @return success/fail message
     */
    @PostMapping(path = "/lodgings")
    public Lodging createLodging(@RequestBody Lodging lodging){
        if (lodging == null)
            return null;
        lodgingRepository.save(lodging);
        return lodging;
    }

    /**
     * Deletes a lodging
     * @param id id of lodging
     * @return success/fail message
     */
    @DeleteMapping(path = "/lodgings/{id}")
    public int deleteLodging(@PathVariable int id){
        Lodging lodging = lodgingRepository.findById(id);
        if (lodging == null)
            return FAIL;
        lodgingRepository.delete(lodging);
        return SUCCESS;
    }

    @GetMapping(path = "lodgings/location/{location}/checkin/{checkIn}/checkout/{checkOut}")
    public List<Lodging> getRealLodgings(@PathVariable String location, @PathVariable String checkIn, @PathVariable String checkOut) {
        List<Lodging> lodgings = new ArrayList<Lodging>();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://booking-com.p.rapidapi.com/v1/hotels/locations?locale=en-us&name=" + location))
                .header("x-rapidapi-host", "booking-com.p.rapidapi.com")
                .header("x-rapidapi-key", "42ebb1181amshd8c5285d3b892f5p1c76ecjsn938624986490")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray ja = new JSONArray(response.body());
        if (ja.length() == 0) return null;
        JSONObject jo = null;
        boolean ret = true;
        for(int i = 0; i < ja.length(); i++){
            jo = ja.getJSONObject(i);
            if (jo.getString("dest_type").equals("city")) {
                ret = false;
                break;
            }
        }
        if (ret) return null;
        int loc = jo.getInt("dest_id");

        request = HttpRequest.newBuilder()
                .uri(URI.create("https://booking-com.p.rapidapi.com/v1/hotels/search?units=imperial&order_by=popularity&checkout_date=" + checkOut +"&adults_number=2&checkin_date=" + checkIn + "&room_number=1&filter_by_currency=USD&dest_type=city&locale=en-us&dest_id=" + loc + "&include_adjacency=true&page_number=0"))
                .header("x-rapidapi-host", "booking-com.p.rapidapi.com")
                .header("x-rapidapi-key", "42ebb1181amshd8c5285d3b892f5p1c76ecjsn938624986490")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jo2 = new JSONObject(response.body());
        ja = new JSONArray(jo2.get("result").toString());

        for (int i = 0; i < ja.length(); i++){
            JSONObject jo3 = ja.getJSONObject(i);
            String name = jo3.getString("hotel_name_trans");
            double price = jo3.getDouble("min_total_price");
            String locat = jo3.getString("city_name_en");
            Lodging lodging = new Lodging(name, price, checkIn, checkOut, locat, LodgingType.HOTEL);
            lodgings.add(lodging);
        }

        return lodgings;
    }

}

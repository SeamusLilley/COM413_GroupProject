package com.example.groupproject;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // user's location is hard coded
    LatLng myLocation = new LatLng(55.00883183718947, -7.31890368972177);

    // arrays for coordinates, names, descriptions, and images
    ArrayList<LatLng> coordinateList = new ArrayList<LatLng>();
    ArrayList<String> placeNameList = new ArrayList<String>();
    ArrayList<String> descriptionList = new ArrayList<String>();
    ArrayList<String> imageList = new ArrayList<String>();

    CustomInfoWindowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // populate the arrays based on button clicked
        String choice = getIntent().getStringExtra("choice");
        switch (choice){
            case "restaurants":
                addRestaurants();
                break;
            case "hotels":
                addHotels();
                break;
            case "history":
                addHistorical();
                break;
            case "pubs":
                addPubs();
                break;
            case "coffee":
                addCoffeeShops();
                break;
            case "shopping":
                addShopping();
                break;
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // adds markers to the map using details from the 3 arrays
        for(int i = 0; i < coordinateList.size(); i++){
            Marker a = mMap.addMarker(new MarkerOptions().position(coordinateList.get(i)).title(placeNameList.get(i)).snippet(descriptionList.get(i)));
            a.setTag("https://lh5.googleusercontent.com/p/AF1QipOYuNFsq_nBQjGXs-T6zc1Khlr8Q4Afl3PLTuYj=w408-h240-k-no-pi-0-ya53.00263-ro-0-fo100");
        }

        mMap.addMarker(new MarkerOptions().position(coordinateList.get(0)).title(placeNameList.get(0)).snippet(descriptionList.get(0)));

        // moves the camera to the user's location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));

        // begins the more info activity when a marker is clicked
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                /*
                // passes along the marker's name and description
                Intent i = new Intent(MapsActivity.this, MoreInfo.class);
                i.putExtra("title", marker.getTitle());
                i.putExtra("information", marker.getSnippet());
                startActivity(i);
                 */

                adapter = new CustomInfoWindowAdapter(MapsActivity.this, marker.getTag().toString());

                try {
                    adapter.SetImage(marker.getTag().toString());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mMap.setInfoWindowAdapter(adapter);
                marker.showInfoWindow();

                return false;
            }
        });


    }

    // adds a location's details to the three arrays
    public void addLocation(LatLng a, String b, String c){
        coordinateList.add(a);
        placeNameList.add(b);
        descriptionList.add(c);
    }

    // populates the arrays with restaurants
    public void addRestaurants(){
        addLocation(new LatLng(54.9995204463077,    -7.319964908155055),    "The Exchange",                     "4.6 (682)\nModern European restaurant\nQueens Quay, Londonderry BT48 7AY\nOpening Hours:  12–2:30pm, 5–10pm\nexchangerestaurant.com\n028 7127 3990");
        addLocation(new LatLng(55.01840589321865,   -7.325598487625408),    "Scarpello & Co",                   "4.9 (37)\nPizza restaurant\n22B Buncrana Rd, Londonderry BT48 8AB\nOpening Hours: 10am - 10pm\nscarpelloandco.ie\n028 7121 1046");
        addLocation(new LatLng(55.004088041480074,  -7.321582661592111),    "Quaywest Wine Bar & Restaurant",   "4.5 (1,307)\nRestaurant\n28 Boating Club Ln, Londonderry BT48 7QB\nOpening Hours: 4–10pm\nquaywestrestaurant.com\n028 7137 0977");
        addLocation(new LatLng(54.997736567183274,  -7.322765322966934),    "Mekong Street Food",               "4.9 (50)\nRestaurant\n7-8 Magazine St, Londonderry BT48 6HJ\nOpening Hours: 3pm – 9pm\nfacebook.com/MekongSF\n07835 330360");
        addLocation(new LatLng(55.00231509478489,   -7.322757792279522),    "Guapo Fresh Mexican",              "4.8 (577)\nBurrito restaurant\n69 Strand Rd, Londonderry BT48 7AD\nOpening Hours: 12–9pm\n028 7136 5585");
        addLocation(new LatLng(55.01082716489911,   -7.318217048508025),    "Chillis Indian Restaurant",        "4.5 (105)\nIndian restaurant\n145 Strand Rd, Londonderry BT48 7PW\nOpening Hours: 5pm - 11pm\nchillisrestaurant.net\n028 7126 2050");
        addLocation(new LatLng(55.015406001668126,  -7.31238055858301),     "Caterina’s bistro",                "4.6 (19)\nRestaurant\n15 Culmore Rd, Londonderry BT48 8JB\nOpening Hours:  12–9:30pm");
        addLocation(new LatLng(54.99799325915123,   -7.321525438842466),    "Castle Street Social",             "5.0 (12)\nRestaurant\n12-14 Castle St, Londonderry BT48 6HQ\nOpening Hours: 12–9pm\n028 7137 2888");
        imageList.add("https://lh5.googleusercontent.com/p/AF1QipOYuNFsq_nBQjGXs-T6zc1Khlr8Q4Afl3PLTuYj=w408-h240-k-no-pi-0-ya53.00263-ro-0-fo100");
    }

    // populates the arrays with hotels
    public void addHotels(){
        addLocation(new LatLng(54.99607644345425,   -7.322795202277289),    "Maldron Hotel Derry",          "4.4 (1,238)\nButcher St, Londonderry BT48 6HL\n028 7137 1000");
        addLocation(new LatLng(54.99966086801288,   -7.321643721950678),    "Holiday Inn Express Derry",    "4.6 (168)\n31 Strand Rd, Londonderry BT48 7BL\nihg.com\n028 7116 2400");
        addLocation(new LatLng(55.01457091089826,   -7.312483272041897),    "Da Vinci's Hotel",             "4.4 (288)\n15 Culmore Rd, Londonderry BT48 8JB\ndavincishotel.com\n028 7127 9111");
        addLocation(new LatLng(54.999315710612656,  -7.3229031569087475),   "Hogg & Mitchell Apartments",   "4.4 (70)\n15 Great James St, Londonderry BT48 7AB\nhoggandmitchells.co.uk\n028 7187 8028");
        addLocation(new LatLng(54.99923957380815,   -7.3203926405975555),   "City Hotel Derry",             "4.3 (1,492)\nQueens Quay, Londonderry BT48 7AS\ncityhotelderry.com\n028 7136 5800");
        addLocation(new LatLng(54.99478259617965,   -7.3230446427488625),   "Bishop's Gate Hotel Derry",    "4.7 (1,041)\n24 Bishop St, Londonderry BT48 6PP\nbishopsgatehotelderry.com\n028 7114 0300");
        addLocation(new LatLng(55.01002805936406,   -7.279739576925149),    "The Waterfoot Hotel",          "4.1 (752)\nCaw Roundabout, Londonderry BT47 6TB\nwaterfoothotel.com\n028 7134 5500");
        addLocation(new LatLng(54.9962053664103,    -7.3208659986431766),   "Shipquay Hotel",               "4.7 (339)\n15-17 Shipquay St, Londonderry BT48 6DJ\nshipquayhotel.com\n028 7126 7266");
    }

    // populates the arrays with historical places
    public void addHistorical(){
        addLocation(new LatLng(54.99518412768551, -7.323958860571264), "The Derry Walls",           "4.7 (1,778)\nThe Diamond, Londonderry BT48 6HW\nOpen 24 hours\nthederrywalls.com\n07894 534553");
        addLocation(new LatLng(54.99658118925993, -7.325357259360941), "Bloody Sunday Monument",    "4.5 (112)\n29-37 Joseph Pl, Londonderry BT48 6LH\nOpen 24 hours");
        addLocation(new LatLng(54.99343650072822, -7.321219709173047), "Church Bastion",            "4.0 (1)\n222 The Fountain, Londonderry BT48 6JP\nOpen now:  Open 24 hours");
        addLocation(new LatLng(54.99576408872815, -7.326773835718739), "Free Derry Corner",         "4.7 (1,237)\nLondonderry BT48 9DR\nOpen 24 hours");
        addLocation(new LatLng(54.99701283319828, -7.319891463775096), "Shipquay Gate",             "4.8 (13)\n1A Shipquay Pl, Londonderry BT48 6DH\nOpen now:  Open 24 hours");
    }

    // populates the arrays with pubs
    public void addPubs(){
        addLocation(new LatLng(54.996488079025255,  -7.323344094983625),    "The Rocking Chair Bar",        "4.4 (197)\n15-17 Waterloo St, Londonderry BT48 6HA\nOpening Hours  11am – 1am\nrockingchair.online\n028 7128 1200");
        addLocation(new LatLng(54.9952559894251,    -7.319138853471543),    "Badgers Bar and Restaurant",   "4.6 (635)\n18 Orchard St, Londonderry BT48 6EG\nOpening Hours: 12pm – 1am\n028 7136 3306");
        addLocation(new LatLng(54.99653237153532,   -7.318288726253522),    "Blackbird",                    "4.5 (890)\n24 Foyle St, Londonderry BT48 6AL\nOpening Horus: 11:30am – 1am\nblackbirdderry.com\n028 7136 2111");
        addLocation(new LatLng(54.997050999382985,  -7.322201894995344),    "Dungloe Bar",                  "4.4 (567)\n41-43 Waterloo St, Londonderry BT48 6HD\nOpening Hours: 11:30am – 1am\n028 7126 7716");
        addLocation(new LatLng(54.997077933376296,  -7.319473961091456),    "Gainsborough Bar",             "4.3 (106)\n6 Shipquay Pl, Londonderry BT48 6DF\nOpen 24 hours");
        addLocation(new LatLng(54.99747116063894,   -7.321771032542148),    "Peadar O'Donnell's",           "4.6 (1,111)\n63 Waterloo St, Londonderry BT48 6HD\nOpening Hours: 11:30am – 1:30am\npeadars.com\n028 7126 7295");
        addLocation(new LatLng(54.996929886332865,  -7.32019860382748),     "River Inn",                    "4.3 (381)\n34-38 Shipquay St, Londonderry BT48 6DW\nOpening Hours: 11:30am – 1:30am\nriverinn1684.com\n028 7137 1965");
        addLocation(new LatLng(54.99692975170105,   -7.322197361028215),    "The Castle Bar",               "4.4 (72)\n26 Waterloo St, Londonderry BT48 6HF\nOpening Hours: 12:30pm – 1:30am\nthe-castle-bar.poi.place\n028 7136 5013");
    }

    // populates the arrays with coffee shops
    public void addCoffeeShops(){
        addLocation(new LatLng(54.99774846214825,   -7.319748637770263),    "Warehouse No 1 Ltd",                           "4.4 (163)\n1-3 Guildhall St, Londonderry BT48 6BB\nOpening Hours: 9am – 9:30pm\nthewarehousederry.com\n028 7126 4798");
        addLocation(new LatLng(54.99490041815157,   -7.317949437978594),    "Costa",                                        "4.2 (84)\n19 Orchard St, Londonderry BT48 6XY\nOpening Hours: 8am – 9pm\ncostaireland.ie\n028 7136 5556");
        addLocation(new LatLng(54.99536857056998,   -7.3099874839065055),   "Leprechaun Bakery & Coffee Shop",              "4.4 (50)\n41 Clooney Terrace, Londonderry BT47 6AP\n028 7134 5141");
        addLocation(new LatLng(55.00660514467516,   -7.319545046987329),    "Patricia's Coffee House",                      "4.6 (207)\nUnit 1a, Atlantic Quay, 100-114 Strand Rd, Londonderry BT48 7NR\nOpening Hours: 9am - 5pm\n028 7137 0302");
        addLocation(new LatLng(54.99474999112457,   -7.318566086914426),    "Starbucks",                                    "4.3 (322)\nLondenderry Foyleside, Londonderry BT48 6XY\nOpening Hours: 10am – 5:30pm\nstarbucks.co.uk\n028 7136 8113");
        addLocation(new LatLng(55.000388270971705,  -7.322351332649952),    "The Coffee Tree",                              "4.9 (154)\n49 Strand Rd, Londonderry BT48 7BN\nOpening Hours: 10:30am–4am");
        addLocation(new LatLng(54.99693931503422,   -7.320985231578226),    "The Cottage Craft Gallery and Coffee Shop",    "4.9 (33)\nUnit 21 The, Shipquay St, Londonderry BT48 6AR\nOpening Hours: 8:30am – 6pm\n07783 882521");
        addLocation(new LatLng(55.00210962855509,   -7.322337244267519),    "The Hatter Express Derry",                     "5.0 (3)\n70 Strand Rd, Londonderry BT48 7AJ\nOpening Hours: 8am – 5pm\n028 7187 8034");
    }

    // populates the arrays with shopping centres
    public void addShopping(){
        addLocation(new LatLng(54.994730956866356,  -7.319163705980926), "Foyleside Shopping Centre",   "4.4 (3,376)\n19 Orchard St, Londonderry BT48 6XY\n9am–7pm\nfoyleside.co.uk\n028 7137 7575");
        addLocation(new LatLng(54.989082343822005,  -7.296606059240908), "Lisnagelvin Shopping Centre", "4.1 (1,484)\nLisnagelvin Rd, Londonderry BT47 6DF\nOpening Hours: 8am – 8pm\nlisnagelvinsc.com\n028 7132 9409");
        addLocation(new LatLng(55.000245374566056,  -7.321586821056816), "Quayside Shopping Centre",    "4.1 (849)\n42 Strand Rd, Londonderry BT48 7PX\nOpening Hours: 7am – 9:45pm\nquaysidecentre.co.uk\n028 7137 4037");
        addLocation(new LatLng(54.99578665086524,   -7.320638311365454), "Richmond Shopping Centre",    "4.2 (1,739)\nFerryquay St, Londonderry BT48 6QP\nOpening Hours: 8:30am – 9pm\nrichmondcentre.co.uk\n028 7126 0525");
    }
}
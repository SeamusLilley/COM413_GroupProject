package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    // stuff for getting the user's location
    final private int REQUEST_COARSE_ACCESS = 123;
    boolean permissionGranted = false;
    LocationManager lm;
    LocationListener locationListener;

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
            a.setTag(imageList.get(i));
        }

        // moves the camera to the user's location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));

        // begins the more info activity when a marker is clicked
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.getPosition().equals(myLocation)){
                    //return false;
                }

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

        // this is for getting the user's current location
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_COARSE_ACCESS);
            return;
        } else {
            permissionGranted = true;
        }
        if (permissionGranted) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }

    // adds a location's details to the three arrays
    public void addLocation(LatLng a, String b, String c, String d){
        coordinateList.add(a);
        placeNameList.add(b);
        descriptionList.add(c);
        imageList.add(d);
    }

    // populates the arrays with restaurants
    public void addRestaurants(){
        addLocation(new LatLng(54.9995204463077,    -7.319964908155055),    "The Exchange",                     "4.6 (682)\nModern European restaurant\nQueens Quay, Londonderry BT48 7AY\nOpening Hours:  12–2:30pm, 5–10pm\nexchangerestaurant.com\n028 7127 3990", "https://lh5.googleusercontent.com/p/AF1QipOYuNFsq_nBQjGXs-T6zc1Khlr8Q4Afl3PLTuYj=w408-h240-k-no-pi-0-ya53.00263-ro-0-fo100");
        addLocation(new LatLng(55.01840589321865,   -7.325598487625408),    "Scarpello & Co",                   "4.9 (37)\nPizza restaurant\n22B Buncrana Rd, Londonderry BT48 8AB\nOpening Hours: 10am - 10pm\nscarpelloandco.ie\n028 7121 1046", "https://lh5.googleusercontent.com/p/AF1QipPFaxSOr_y2D5Q3s3Ypxb1UwOX3QWwym0B5y90A=w408-h306-k-no");
        addLocation(new LatLng(55.004088041480074,  -7.321582661592111),    "Quaywest Wine Bar & Restaurant",   "4.5 (1,307)\nRestaurant\n28 Boating Club Ln, Londonderry BT48 7QB\nOpening Hours: 4–10pm\nquaywestrestaurant.com\n028 7137 0977", "https://lh5.googleusercontent.com/p/AF1QipMZmPQEnaNeLe4j1GorVBQwvjGuc_wGFX92U_s=w408-h544-k-no");
        addLocation(new LatLng(54.997736567183274,  -7.322765322966934),    "Mekong Street Food",               "4.9 (50)\nRestaurant\n7-8 Magazine St, Londonderry BT48 6HJ\nOpening Hours: 3pm – 9pm\nfacebook.com/MekongSF\n07835 330360", "https://lh5.googleusercontent.com/p/AF1QipO1Pz7-D27j7N0RTnYTGNXpQugCPvmpGzQv_e2A=w408-h544-k-no");
        addLocation(new LatLng(55.00231509478489,   -7.322757792279522),    "Guapo Fresh Mexican",              "4.8 (577)\nBurrito restaurant\n69 Strand Rd, Londonderry BT48 7AD\nOpening Hours: 12–9pm\n028 7136 5585", "https://lh5.googleusercontent.com/p/AF1QipM6Qkb4sxE6m3uEO-2wdqsrY97MsuNx7rfl5MI=w408-h306-k-no");
        addLocation(new LatLng(55.01082716489911,   -7.318217048508025),    "Chillis Indian Restaurant",        "4.5 (105)\nIndian restaurant\n145 Strand Rd, Londonderry BT48 7PW\nOpening Hours: 5pm - 11pm\nchillisrestaurant.net\n028 7126 2050", "https://lh5.googleusercontent.com/p/AF1QipMaURAEZIvPg9oHCTnbseaqzNgFS8JN851Y7riG=w426-h240-k-no");
        addLocation(new LatLng(55.015406001668126,  -7.31238055858301),     "Caterina’s bistro",                "4.6 (19)\nRestaurant\n15 Culmore Rd, Londonderry BT48 8JB\nOpening Hours:  12–9:30pm", "https://lh5.googleusercontent.com/p/AF1QipN_4bh_8aDEIXOKG5hQwfLgfWxz8xbKpOL5Igbs=w408-h306-k-no");
        addLocation(new LatLng(54.99799325915123,   -7.321525438842466),    "Castle Street Social",             "5.0 (12)\nRestaurant\n12-14 Castle St, Londonderry BT48 6HQ\nOpening Hours: 12–9pm\n028 7137 2888", "https://lh5.googleusercontent.com/p/AF1QipM4qt62WYLmG-5ciF8SAndAeebyynP21r-I2eZk=w408-h494-k-no");
    }

    // populates the arrays with hotels
    public void addHotels(){
        addLocation(new LatLng(54.99607644345425,   -7.322795202277289),    "Maldron Hotel Derry",          "4.4 (1,238)\nButcher St, Londonderry BT48 6HL\n028 7137 1000", "https://lh5.googleusercontent.com/p/AF1QipPDntXRiFqahSfU_cGx-_UDkZQp8y8CaF2SBRuy=w408-h271-k-no");
        addLocation(new LatLng(54.99966086801288,   -7.321643721950678),    "Holiday Inn Express Derry",    "4.6 (168)\n31 Strand Rd, Londonderry BT48 7BL\nihg.com\n028 7116 2400", "https://lh5.googleusercontent.com/p/AF1QipMsjNWfBHq6x5gp992UXd1qZGSNaMDQU2f7puhl=w408-h272-k-no");
        addLocation(new LatLng(55.01457091089826,   -7.312483272041897),    "Da Vinci's Hotel",             "4.4 (288)\n15 Culmore Rd, Londonderry BT48 8JB\ndavincishotel.com\n028 7127 9111", "https://lh5.googleusercontent.com/p/AF1QipMxvsr2KgqNQux-oCT0xRX-8tdYDW1zzm_rNNSu=w408-h265-k-no");
        addLocation(new LatLng(54.999315710612656,  -7.3229031569087475),   "Hogg & Mitchell Apartments",   "4.4 (70)\n15 Great James St, Londonderry BT48 7AB\nhoggandmitchells.co.uk\n028 7187 8028", "https://lh5.googleusercontent.com/p/AF1QipO6XktaIIdNPIcZNa5ZwPh9nyQhpc7ohnuZUcyT=w408-h271-k-no");
        addLocation(new LatLng(54.99923957380815,   -7.3203926405975555),   "City Hotel Derry",             "4.3 (1,492)\nQueens Quay, Londonderry BT48 7AS\ncityhotelderry.com\n028 7136 5800", "https://lh5.googleusercontent.com/p/AF1QipMjIlS447An_RdQqYt7o_j_Jsj9g20Kipdg6K6b=w408-h327-k-no");
        addLocation(new LatLng(54.99478259617965,   -7.3230446427488625),   "Bishop's Gate Hotel Derry",    "4.7 (1,041)\n24 Bishop St, Londonderry BT48 6PP\nbishopsgatehotelderry.com\n028 7114 0300", "https://lh5.googleusercontent.com/p/AF1QipOPGfKvTL_RMBKjeqbxAKc-FhKEgkaDS5fBMEKO=w408-h272-k-no");
        addLocation(new LatLng(55.01002805936406,   -7.279739576925149),    "The Waterfoot Hotel",          "4.1 (752)\nCaw Roundabout, Londonderry BT47 6TB\nwaterfoothotel.com\n028 7134 5500", "https://lh3.googleusercontent.com/proxy/n4aIiZRFE-8Tg_bMwgnk9rlnB6jHz3lk9uamJ9lQITlKdHS9nn2P3w3qwj2_cH4zvxH1kT7utF4_JHUTALveCN9VRTLbuy7JC7pb-pYTsazl7DlMFz-URmE9N1hz7OOk40JXTbEYl3m-ME44rnbAwUZ_hSnye30=w408-h270-k-no");
        addLocation(new LatLng(54.9962053664103,    -7.3208659986431766),   "Shipquay Hotel",               "4.7 (339)\n15-17 Shipquay St, Londonderry BT48 6DJ\nshipquayhotel.com\n028 7126 7266", "https://lh5.googleusercontent.com/p/AF1QipMVN9qSZ7kxipMxRayFlxWsF95brcC5ukTst0-j=w408-h272-k-no");
    }

    // populates the arrays with historical places
    public void addHistorical(){
        addLocation(new LatLng(54.99518412768551, -7.323958860571264), "The Derry Walls",           "4.7 (1,778)\nThe Diamond, Londonderry BT48 6HW\nOpen 24 hours\nthederrywalls.com\n07894 534553", "https://lh5.googleusercontent.com/p/AF1QipORcwWgS2SLLUY19guxFAE4HMfmyvC4Lxl_Ljph=w408-h335-k-no");
        addLocation(new LatLng(54.99658118925993, -7.325357259360941), "Bloody Sunday Monument",    "4.5 (112)\n29-37 Joseph Pl, Londonderry BT48 6LH\nOpen 24 hours", "https://lh5.googleusercontent.com/p/AF1QipPw-6UgCZak5R49ddrgFKA47fSCPCTOrQOBmsSl=w408-h725-k-no");
        addLocation(new LatLng(54.99343650072822, -7.321219709173047), "Church Bastion",            "4.0 (1)\n222 The Fountain, Londonderry BT48 6JP\nOpen now:  Open 24 hours", "https://lh5.googleusercontent.com/p/AF1QipNkhkupwCF5oQXfuu6HoA5eLnz6xE_neemguQVS=w408-h271-k-no");
        addLocation(new LatLng(54.99576408872815, -7.326773835718739), "Free Derry Corner",         "4.7 (1,237)\nLondonderry BT48 9DR\nOpen 24 hours", "https://lh5.googleusercontent.com/p/AF1QipOfueBwAOoX2V74kw2G56yn9gZEqKUckrSqtlPa=w408-h544-k-no");
        addLocation(new LatLng(54.99701283319828, -7.319891463775096), "Shipquay Gate",             "4.8 (13)\n1A Shipquay Pl, Londonderry BT48 6DH\nOpen now:  Open 24 hours", "https://geo1.ggpht.com/maps/photothumb/fd/v1?bpb=ChEKD3NlYXJjaC5nd3MtcHJvZBIgChIJ-Vn75oLhX0gRr8Z17FHg2LsqCg0AAAAAFQAAAAAaBgjwARCYAw&gl=GB");
    }

    // populates the arrays with pubs
    public void addPubs(){
        addLocation(new LatLng(54.996488079025255,  -7.323344094983625),    "The Rocking Chair Bar",        "4.4 (197)\n15-17 Waterloo St, Londonderry BT48 6HA\nOpening Hours  11am – 1am\nrockingchair.online\n028 7128 1200", "https://lh5.googleusercontent.com/p/AF1QipMhB9FmSrQtPdfQjM13A9kgxThQhEjP1TQoMaKP=w408-h272-k-no");
        addLocation(new LatLng(54.9952559894251,    -7.319138853471543),    "Badgers Bar and Restaurant",   "4.6 (635)\n18 Orchard St, Londonderry BT48 6EG\nOpening Hours: 12pm – 1am\n028 7136 3306", "https://lh5.googleusercontent.com/p/AF1QipN04-Uhpmd9pS81AjQ7Q1pRkRMzJNoKNlPKEFZV=w408-h306-k-no");
        addLocation(new LatLng(54.99653237153532,   -7.318288726253522),    "Blackbird",                    "4.5 (890)\n24 Foyle St, Londonderry BT48 6AL\nOpening Horus: 11:30am – 1am\nblackbirdderry.com\n028 7136 2111", "https://lh5.googleusercontent.com/p/AF1QipNpsagU9LPzYRmmRGsqe2Op5w36e6o9685jYUAu=w432-h240-k-no");
        addLocation(new LatLng(54.997050999382985,  -7.322201894995344),    "Dungloe Bar",                  "4.4 (567)\n41-43 Waterloo St, Londonderry BT48 6HD\nOpening Hours: 11:30am – 1am\n028 7126 7716", "https://lh5.googleusercontent.com/p/AF1QipO4oqrEdKFuDjr3nAxVxm_AepHEW8h34LPEsGrU=w416-h240-k-no");
        addLocation(new LatLng(54.997077933376296,  -7.319473961091456),    "Gainsborough Bar",             "4.3 (106)\n6 Shipquay Pl, Londonderry BT48 6DF\nOpen 24 hours", "https://lh5.googleusercontent.com/p/AF1QipMJy4voLnoombG_OZxqwocmJdOf1BOOCe-l9CyA=w408-h306-k-no");
        addLocation(new LatLng(54.99747116063894,   -7.321771032542148),    "Peadar O'Donnell's",           "4.6 (1,111)\n63 Waterloo St, Londonderry BT48 6HD\nOpening Hours: 11:30am – 1:30am\npeadars.com\n028 7126 7295", "https://lh5.googleusercontent.com/p/AF1QipPQ5GS2a_xd2cqs5dZVsTtizCu2LoI6NCDIPqii=w428-h240-k-no");
        addLocation(new LatLng(54.996929886332865,  -7.32019860382748),     "River Inn",                    "4.3 (381)\n34-38 Shipquay St, Londonderry BT48 6DW\nOpening Hours: 11:30am – 1:30am\nriverinn1684.com\n028 7137 1965", "https://lh5.googleusercontent.com/p/AF1QipPeYTQmBmxWcxoUT8ts1nZ3JHxxQchXSfPSSAIP=w426-h240-k-no");
        addLocation(new LatLng(54.99692975170105,   -7.322197361028215),    "The Castle Bar",               "4.4 (72)\n26 Waterloo St, Londonderry BT48 6HF\nOpening Hours: 12:30pm – 1:30am\nthe-castle-bar.poi.place\n028 7136 5013", "https://lh5.googleusercontent.com/p/AF1QipPHEGsvqIhYxAxNp5XJMWEPnq9iTnomV_-aBqW5=w408-h306-k-no");
    }

    // populates the arrays with coffee shops
    public void addCoffeeShops(){
        addLocation(new LatLng(54.99774846214825,   -7.319748637770263),    "Warehouse No 1 Ltd",                           "4.4 (163)\n1-3 Guildhall St, Londonderry BT48 6BB\nOpening Hours: 9am – 9:30pm\nthewarehousederry.com\n028 7126 4798", "https://lh5.googleusercontent.com/p/AF1QipO8LnWd4oz2Cg8PbnkEeBfGMGjW1sOG2Fw3hCgq=w408-h306-k-no");
        addLocation(new LatLng(54.99490041815157,   -7.317949437978594),    "Costa",                                        "4.2 (84)\n19 Orchard St, Londonderry BT48 6XY\nOpening Hours: 8am – 9pm\ncostaireland.ie\n028 7136 5556", "https://lh5.googleusercontent.com/p/AF1QipPx1ktTHBKJfjBnfvxQ1-IWTxMs0hAIhZ4WxNKg=w426-h240-k-no");
        addLocation(new LatLng(54.99536857056998,   -7.3099874839065055),   "Leprechaun Bakery & Coffee Shop",              "4.4 (50)\n41 Clooney Terrace, Londonderry BT47 6AP\n028 7134 5141", "https://lh5.googleusercontent.com/p/AF1QipMQbynuWJ9Pf3J6Iuuv3h_iSKh9tdujjMhaOQjQ=w408-h544-k-no");
        addLocation(new LatLng(55.00660514467516,   -7.319545046987329),    "Patricia's Coffee House",                      "4.6 (207)\nUnit 1a, Atlantic Quay, 100-114 Strand Rd, Londonderry BT48 7NR\nOpening Hours: 9am - 5pm\n028 7137 0302", "https://lh5.googleusercontent.com/p/AF1QipNwlbk0sDX_maE0Hzis1_XiYOHe4km1qOGDAe5G=w408-h529-k-no");
        addLocation(new LatLng(54.99474999112457,   -7.318566086914426),    "Starbucks",                                    "4.3 (322)\nLondenderry Foyleside, Londonderry BT48 6XY\nOpening Hours: 10am – 5:30pm\nstarbucks.co.uk\n028 7136 8113", "https://lh5.googleusercontent.com/p/AF1QipMrXBKhGhn2_wsqmkd2lQCGMQ1K716AIaD0dp9_=w493-h240-k-no");
        addLocation(new LatLng(55.000388270971705,  -7.322351332649952),    "The Coffee Tree",                              "4.9 (154)\n49 Strand Rd, Londonderry BT48 7BN\nOpening Hours: 10:30am–4am", "https://lh5.googleusercontent.com/p/AF1QipNFQOFeZVkBImvqPDYwpWvQDeXlA2K4uzkJKE05=w408-h272-k-no");
        addLocation(new LatLng(54.99693931503422,   -7.320985231578226),    "The Cottage Craft Gallery and Coffee Shop",    "4.9 (33)\nUnit 21 The, Shipquay St, Londonderry BT48 6AR\nOpening Hours: 8:30am – 6pm\n07783 882521", "https://lh5.googleusercontent.com/p/AF1QipPSaH7d5r8IOxWgejYigZzTJZQhQVVaR0fMSKdF=w425-h240-k-no");
        addLocation(new LatLng(55.00210962855509,   -7.322337244267519),    "The Hatter Express Derry",                     "5.0 (3)\n70 Strand Rd, Londonderry BT48 7AJ\nOpening Hours: 8am – 5pm\n028 7187 8034", "https://lh5.googleusercontent.com/p/AF1QipO2B2JxxmhNC9WcRbhYja1bElrRcnlWIaB63pHF=w408-h408-k-no");
    }

    // populates the arrays with shopping centres
    public void addShopping(){
        addLocation(new LatLng(54.994730956866356,  -7.319163705980926), "Foyleside Shopping Centre",   "4.4 (3,376)\n19 Orchard St, Londonderry BT48 6XY\n9am–7pm\nfoyleside.co.uk\n028 7137 7575", "https://lh5.googleusercontent.com/p/AF1QipMwPtlmrp_POpCEOoAXXTonDLD527d9Zz2CL-Hw=w408-h306-k-no");
        addLocation(new LatLng(54.989082343822005,  -7.296606059240908), "Lisnagelvin Shopping Centre", "4.1 (1,484)\nLisnagelvin Rd, Londonderry BT47 6DF\nOpening Hours: 8am – 8pm\nlisnagelvinsc.com\n028 7132 9409", "https://lh5.googleusercontent.com/p/AF1QipNP6mGqMVX__bz7CcM56fkXwY3RDpKaFrPBs3HH=w426-h240-k-no");
        addLocation(new LatLng(55.000245374566056,  -7.321586821056816), "Quayside Shopping Centre",    "4.1 (849)\n42 Strand Rd, Londonderry BT48 7PX\nOpening Hours: 7am – 9:45pm\nquaysidecentre.co.uk\n028 7137 4037", "https://geo2.ggpht.com/cbk?panoid=S9TSIkwkySASF61PP7nRBw&output=thumbnail&cb_client=search.gws-prod.gps&thumb=2&w=408&h=240&yaw=88.00855&pitch=0&thumbfov=100");
        addLocation(new LatLng(54.99578665086524,   -7.320638311365454), "Richmond Shopping Centre",    "4.2 (1,739)\nFerryquay St, Londonderry BT48 6QP\nOpening Hours: 8:30am – 9pm\nrichmondcentre.co.uk\n028 7126 0525", "https://lh5.googleusercontent.com/p/AF1QipOFoQO64zA4DK7SPd9CGqvmpqrXy8j5axMOoWtZ=w408-h544-k-no");
    }

    // everything below here is for getting the user's current location
    private class MyLocationListener implements LocationListener{

        // adds a marker and moves the camera to current location whenever your current location changes
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if(location != null){
                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Marker b = mMap.addMarker(new MarkerOptions().position(myLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("You are here"));
                b.setTag("https://www.google.com/url?sa=i&url=https%3A%2F%2Fimgur.com%2Fgallery%2Fmm1EEVs&psig=AOvVaw2_lrNSAvaGe2SEACElLBb2&ust=1607809790664000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPCR1K30xu0CFQAAAAAdAAAAABAD");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_COARSE_ACCESS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } else {
                    permissionGranted = false;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // remove the location listener
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, REQUEST_COARSE_ACCESS);
            return;
        } else {
            permissionGranted = true;
        }
        if (permissionGranted) {
            lm.removeUpdates(locationListener);
        }
    }
}
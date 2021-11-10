package com.example.metarmac.ui.infos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metarmac.ForecastAdapter;
import com.example.metarmac.GlobalApplication;
import com.example.metarmac.R;
import com.example.metarmac.databinding.FragmentInfosBinding;
import com.example.metarmac.model.Airport;
import com.example.metarmac.ui.OnSwipeTouchListener;
import com.mapbox.mapboxsdk.plugins.annotation.Line;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class InfosFragment extends Fragment {
    private FragmentInfosBinding binding;

    private ScrollView data;

    private TextView name;
    private TextView a_country;

    private LinearLayout metar_content;
    private TextView metar;
    private TextView obs_time;
    private TextView latitude;
    private TextView longitude;
    private TextView temperature;
    private TextView dewpoint;
    private TextView wind_dir;
    private TextView wind_spd;
    private TextView visibility;
    private TextView pressure;
    private TextView clouds;


    private TextView taf;

    private TextView current_oaci;
    private Button next_oaci;
    private Button previous_oaci;

    private ArrayList<Airport> lstAirport;
    private int airportIterator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInfosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        name = (TextView) root.findViewById(R.id.airport_name);
        a_country = (TextView) root.findViewById(R.id.airport_country);
        current_oaci = (TextView) root.findViewById(R.id.current_airport);
        next_oaci = (Button) root.findViewById(R.id.next_airport);
        previous_oaci = (Button) root.findViewById(R.id.previous_airport);
        data = (ScrollView) root.findViewById(R.id.metar_taf_data);

        metar_content = (LinearLayout) root.findViewById(R.id.metar_content);
        metar = (TextView) root.findViewById(R.id.metar);
        obs_time = (TextView) root.findViewById(R.id.observation_time);
        longitude = (TextView) root.findViewById(R.id.longitude);
        latitude = (TextView) root.findViewById(R.id.latitude);
        temperature = (TextView) root.findViewById(R.id.temperature);
        dewpoint = (TextView) root.findViewById(R.id.dewpoint);
        wind_dir = (TextView) root.findViewById(R.id.wind_direction);
        wind_spd = (TextView) root.findViewById(R.id.wind_speed);
        visibility = (TextView) root.findViewById(R.id.visibility);
        pressure = (TextView) root.findViewById(R.id.pressure);
        clouds = (TextView) root.findViewById(R.id.clouds);

        taf = (TextView) root.findViewById(R.id.taf);

        lstAirport = ((GlobalApplication)getActivity().getApplication()).getLstAirport();
        airportIterator = 0;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(lstAirport.size() == 0) name.setText(R.string.no_airport);
        else if(lstAirport.size() == 1){
            modifyView(lstAirport.get(airportIterator));
            previous_oaci.setVisibility(View.INVISIBLE);
            next_oaci.setVisibility(View.INVISIBLE);
        }
        else {
            previous_oaci.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_left,0);
            next_oaci.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_right,0,0,0);

            modifyView(lstAirport.get(airportIterator));

            previous_oaci.setOnClickListener(v -> {
                if (airportIterator == 0) airportIterator = lstAirport.size() - 1;
                else airportIterator--;

                modifyView(lstAirport.get(airportIterator));
            });

            next_oaci.setOnClickListener(v -> {
                if (airportIterator == lstAirport.size() - 1) airportIterator = 0;
                else airportIterator++;

                modifyView(lstAirport.get(airportIterator));
            });


            view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {    //Go créer une méthode qui va s'occuper de l'affichage en fonction de l'aéroport à afficher au lieu de copiuer le code 2 fois
                        @Override
                        public void onSwipeRight() {

                            if (airportIterator == 0) airportIterator = lstAirport.size() - 1;
                            else airportIterator--;

                            modifyView(lstAirport.get(airportIterator));
                        }

                        @Override
                        public void onSwipeLeft() {

                            if (airportIterator == lstAirport.size() - 1) airportIterator = 0;
                            else airportIterator++;

                            modifyView(lstAirport.get(airportIterator));

                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void modifyView(Airport airport) {
        RecyclerView rvForecast = (RecyclerView) getView().findViewById(R.id.forecast_list);

        if(airport.getTaf().getLstForecast() != null) {
            rvForecast.setVisibility(View.VISIBLE);
            ForecastAdapter adapter = new ForecastAdapter(airport.getTaf().getLstForecast());
            rvForecast.setAdapter(adapter);

            rvForecast.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        }else {
            rvForecast.setVisibility(View.GONE);
        }

        data.setVisibility(View.VISIBLE);

        if(airport.getTaf().getLstForecast() != null){
            taf.setBackground(getResources().getDrawable(R.drawable.shape3));
            taf.setText(airport.getTaf().getRaw_text());
        } else {
            taf.setBackground(getResources().getDrawable(R.drawable.shape4));
            taf.setText(getResources().getString(R.string.no_taf));
        }


        if (airport.getMetar().getObservation_time() != null) {
            metar_content.setVisibility(View.VISIBLE);
            metar.setBackground(getResources().getDrawable(R.drawable.shape3));
        metar.setText(airport.getMetar().getRaw_text());
        obs_time.setText(airport.getMetar().getObservation_time() + "UTC");
        longitude.setText(Float.toString(airport.getMetar().getLongitude()));
        latitude.setText(Float.toString(airport.getMetar().getLatitude()));
        temperature.setText(airport.getMetar().getTemp_c() + " °C");
        dewpoint.setText(airport.getMetar().getDewpoint_c() + " °C");
        wind_dir.setText(airport.getMetar().getWind_dir_degrees() + "°");
        wind_spd.setText(airport.getMetar().getWind_speed_kt() + " kts");
        visibility.setText(airport.getMetar().getVisibility_statute_mi() + " mi");
        pressure.setText(airport.getMetar().getAltim_in_hg() + " hg");
        clouds.setText(airport.getMetar().getSky_condition().get(0).getSky_cover() + getResources().getString(R.string.clouds_at) + airport.getMetar().getSky_condition().get(0).getCloud_base_ft_agl());
        } else {
            metar_content.setVisibility(View.GONE);
            metar.setBackground(getResources().getDrawable(R.drawable.shape4));
            metar.setText(getResources().getString(R.string.no_metar));
        }

        Locale l = new Locale("", airport.getCountry());
        String country = l.getDisplayCountry();
        a_country.setText(country);
        name.setText("N°" + (lstAirport.indexOf(airport)+1) + " : " + airport.getSite());

        current_oaci.setText(airport.getStation_id());


        if(airportIterator == lstAirport.size() - 1)
            next_oaci.setText(lstAirport.get(0).getStation_id());
        else next_oaci.setText(lstAirport.get(airportIterator+1).getStation_id());
        if(airportIterator == 0)
            previous_oaci.setText(lstAirport.get(lstAirport.size()-1).getStation_id());
        else previous_oaci.setText(lstAirport.get(airportIterator-1).getStation_id());
    }
}
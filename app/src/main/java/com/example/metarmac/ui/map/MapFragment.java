package com.example.metarmac.ui.map;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.metarmac.GlobalApplication;
import com.example.metarmac.R;
import com.example.metarmac.databinding.FragmentMapBinding;
import com.example.metarmac.model.Airport;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    private MapViewModel mapViewModel;
    private FragmentMapBinding binding;

    private com.mapbox.mapboxsdk.maps.MapView mapView;
    private MapboxMap map;

    private ArrayList<Airport> lstAirport;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mapView = (MapView) root.findViewById(R.id.mapViewAirport);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        lstAirport = ((GlobalApplication)getActivity().getApplication()).getLstAirport();
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        map = mapboxMap;
        mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
            @Override public void onStyleLoaded(@NonNull Style style) {

                if(lstAirport.size() != 0) {
                    LatLng[] latLng = new LatLng[lstAirport.size()];

                    for (Airport airport : lstAirport) {
                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(airport.getLatitude(), airport.getLongitude()))
                                .title(getResources().getString(R.string.airport) + " : " + airport.getSite() +
                                        "\n"+ getResources().getString(R.string.country) +" : " + airport.getCountry() +
                                        "\n"+ getResources().getString(R.string.latitude) + " : " + airport.getLatitude() +
                                        "\n"+ getResources().getString(R.string.longitude) +" : " + airport.getLongitude()));
                        latLng[lstAirport.indexOf(airport)] = new LatLng(airport.getLatitude(), airport.getLongitude());

                    }
                    if(latLng.length > 1)
                        map.addPolyline(new PolylineOptions()
                                .add(latLng)
                                .color(Color.parseColor("#f74c4c"))
                                .width(2));

                    map.setCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(lstAirport.get(0).getLatitude(), lstAirport.get(0).getLongitude()))
                                    .zoom(5.0)
                                    .build());
                }
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
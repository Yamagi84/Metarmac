package com.example.metarmac.ui.infos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.metarmac.GlobalApplication;
import com.example.metarmac.R;
import com.example.metarmac.databinding.FragmentInfosBinding;
import com.example.metarmac.model.Airport;
import com.example.metarmac.ui.OnSwipeTouchListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class InfosFragment extends Fragment {

    private InfosViewModel infosViewModel;
    private FragmentInfosBinding binding;

    private TextView test;
    private TextView current_oaci;
    private Button next_oaci;
    private Button previous_oaci;

    private ArrayList<Airport> lstAirport;
    private int airportIterator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infosViewModel =
                new ViewModelProvider(this).get(InfosViewModel.class);

        binding = FragmentInfosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        test = (TextView) root.findViewById(R.id.text_test);
        current_oaci = (TextView) root.findViewById(R.id.current_airport);
        next_oaci = (Button) root.findViewById(R.id.next_airport);
        previous_oaci = (Button) root.findViewById(R.id.previous_airport);

        lstAirport = ((GlobalApplication)getActivity().getApplication()).getLstAirport();
        airportIterator = 0;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(lstAirport.size() == 0) test.setText(R.string.no_airport);

        else {

            test.setText("N°" + airportIterator + " : " + lstAirport.get(airportIterator).getSite());
            current_oaci.setText(lstAirport.get(airportIterator).getStation_id());
            if(airportIterator == lstAirport.size() - 1)
                next_oaci.setText(lstAirport.get(0).getStation_id());
            else next_oaci.setText(lstAirport.get(airportIterator+1).getStation_id());
            if(airportIterator == 0)
                previous_oaci.setText(lstAirport.get(lstAirport.size()-1).getStation_id());
            else previous_oaci.setText(lstAirport.get(airportIterator-1).getStation_id());

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

    private void modifyView(Airport airport) {
        test.setText("N°" + lstAirport.indexOf(airport) + " : " + airport.getSite());
        current_oaci.setText(airport.getStation_id());


        if(airportIterator == lstAirport.size() - 1)
            next_oaci.setText(lstAirport.get(0).getStation_id());
        else next_oaci.setText(lstAirport.get(airportIterator+1).getStation_id());
        if(airportIterator == 0)
            previous_oaci.setText(lstAirport.get(lstAirport.size()-1).getStation_id());
        else previous_oaci.setText(lstAirport.get(airportIterator-1).getStation_id());
    }
}
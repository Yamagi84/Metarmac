package com.example.metarmac.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.metarmac.GlobalApplication;
import com.example.metarmac.R;
import com.example.metarmac.databinding.FragmentNotificationsBinding;
import com.example.metarmac.model.Airport;
import com.example.metarmac.ui.OnSwipeTouchListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    private TextView test;

    private ArrayList<Airport> lstAirport;
    private int airportIterator;
    private Airport actualAirport;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        test = (TextView) root.findViewById(R.id.text_test);

        lstAirport = ((GlobalApplication)getActivity().getApplication()).getLstAirport();
        airportIterator = 0;
        actualAirport = lstAirport.get(airportIterator);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        test.setText("N°" + airportIterator + " : " + lstAirport.get(airportIterator).getSite());

        view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {    //Go créer une méthode qui va s'occuper de l'affichage en fonction de l'aéroport à afficher au lieu de copiuer le code 2 fois
            @Override
            public void onSwipeRight() {

                if(airportIterator == 0) airportIterator = lstAirport.size()-1;
                else airportIterator--;

                test.setText("N°" + airportIterator + " : " + lstAirport.get(airportIterator).getSite());

            }

            @Override
            public void onSwipeLeft() {

                if(airportIterator == lstAirport.size()-1) airportIterator = 0;
                else airportIterator++;

                test.setText("N°" + airportIterator + " : " + lstAirport.get(airportIterator).getSite());

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
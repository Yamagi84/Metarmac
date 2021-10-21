package com.example.metarmac.ui.home;

import static com.example.metarmac.XMLReader.convertStringToXMLDocument;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metarmac.AirportAdapter;
import com.example.metarmac.R;
import com.example.metarmac.databinding.FragmentHomeBinding;
import com.example.metarmac.model.Airport;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private EditText tv_oaci;
    private Button btn_confirm_oaci;

    ArrayList<Airport> lstAirport;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_oaci = (EditText) getView().findViewById(R.id.tv_oaci);
        btn_confirm_oaci = (Button) getView().findViewById(R.id.btn_confirm_oaci);

        RecyclerView rvAirport = (RecyclerView) getView().findViewById(R.id.rv_airport_list);

        lstAirport = new ArrayList<>();

        AirportAdapter adapter = new AirportAdapter(lstAirport);

        rvAirport.setAdapter(adapter);

        rvAirport.setLayoutManager(new LinearLayoutManager(getView().getContext()));

        btn_confirm_oaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&stationString=" + tv_oaci.getText().toString() + "&hoursBeforeNow=4&mostRecent=true")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                        final String responseData = response.body().string();

                        Log.d("Réponse", responseData);

                        Document doc = convertStringToXMLDocument(responseData);

                        String test = doc.getChildNodes().item(0).getChildNodes().item(13).getChildNodes().item(1).getChildNodes().item(1).getTextContent();
                        Log.d("<Debug>", test);

                        lstAirport.add(new Airport("test", test));

                    }
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
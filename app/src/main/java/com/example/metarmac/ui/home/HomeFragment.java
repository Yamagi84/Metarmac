package com.example.metarmac.ui.home;

import static com.example.metarmac.XMLReader.convertStringToXMLDocument;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metarmac.AirportAdapter;
import com.example.metarmac.GlobalApplication;
import com.example.metarmac.MainActivity;
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

    private FragmentHomeBinding binding;

    private EditText tv_oaci;
    private Button btn_confirm_oaci;

    private ArrayList<Airport> lstAirport;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        tv_oaci = (EditText) getView().findViewById(R.id.tv_oaci);
        tv_oaci.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        btn_confirm_oaci = (Button) getView().findViewById(R.id.btn_confirm_oaci);

        RecyclerView rvAirport = (RecyclerView) getView().findViewById(R.id.rv_airport_list);

        lstAirport = ((GlobalApplication)getActivity().getApplication()).getLstAirport();

        AirportAdapter adapter = new AirportAdapter(lstAirport);

        rvAirport.setAdapter(adapter);

        rvAirport.setLayoutManager(new LinearLayoutManager(getView().getContext()));

        btn_confirm_oaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler mainHandler = new Handler(context.getMainLooper());

                if(tv_oaci.getText().toString().isEmpty()) {
                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_code_err),
                                    Toast.LENGTH_SHORT).show();
                        }
                    };
                    mainHandler.post(myRunnable);
                }
                else {

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=stations&requestType=retrieve&format=xml&stationString=" + tv_oaci.getText().toString())
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException { //VERIFICATION DU NOMBRE DE RESULTATS > 0 SINON AFFICHER ERREUR AU LIEU DE CRASH L'APP
                            if (!response.isSuccessful()) {
                                throw new IOException("Unexpected code " + response);
                            }
                            final String responseData = response.body().string();

                            Document doc = convertStringToXMLDocument(responseData);

                            if(doc.getChildNodes().item(0).getChildNodes().item(13).getAttributes().item(0).getTextContent().equals("0")) {
                                Runnable myRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), getResources().getString(R.string.oaci_unknown),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                };
                                mainHandler.post(myRunnable);
                            }
                            else {

                                lstAirport.add(new Airport(doc.getChildNodes().item(0).getChildNodes().item(13).getChildNodes().item(1).getChildNodes()));

                                Runnable myRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                };
                                mainHandler.post(myRunnable);
                            }
                        }
                    });
                    tv_oaci.getText().clear();
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

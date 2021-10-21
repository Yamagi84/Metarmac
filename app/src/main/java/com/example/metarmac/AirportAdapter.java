package com.example.metarmac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metarmac.model.Airport;

import java.util.List;

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View airportView = inflater.inflate(R.layout.item_airport, parent, false);

        ViewHolder viewHolder = new ViewHolder(airportView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Airport airport = lstAirport.get(position);

        TextView textView = holder.tv_oaci;
        textView.setText(airport.getOaci());
        textView = holder.tv_name;
        textView.setText(airport.getName());
        Button button = holder.btn_delete;
        button.setText("Delete");
        button.setEnabled(true);
    }

    @Override
    public int getItemCount() {
        return lstAirport.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_oaci;
        public TextView tv_name;
        public Button btn_delete;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_oaci = (TextView) itemView.findViewById(R.id.airport_oaci);
            tv_name = (TextView) itemView.findViewById(R.id.airport_name);
            btn_delete = (Button) itemView.findViewById(R.id.delete_button);
        }
    }

    private List<Airport> lstAirport;

    public AirportAdapter(List<Airport> lstAirport) {
        this.lstAirport = lstAirport;
    }
}

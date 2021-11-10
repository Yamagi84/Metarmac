package com.example.metarmac;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metarmac.model.Airport;
import com.example.metarmac.model.Forecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private Context parentContext;

    private ArrayList<Forecast> lstForecast;

    public ForecastAdapter(ArrayList<Forecast> lstForecast) {
        this.lstForecast = lstForecast;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parentContext);

        View forecastView = inflater.inflate(R.layout.item_forecast, parent, false);

        ViewHolder viewHolder = new ViewHolder(forecastView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Forecast forecast = lstForecast.get(position);

        if (position > 0) {
            TextView taf = holder.taf;
            taf.setText(parentContext.getResources().getString(R.string.forecast)+ (position+1));
            taf.setVisibility(View.VISIBLE);
        }
        TextView textView = holder.forecast_start;
        textView.setText(forecast.getFcst_time_from());
        textView = holder.forecast_end;
        textView.setText(forecast.getFcst_time_to());
        textView = holder.forecast_type;
        textView.setText(forecast.getChange_indicator());
        textView = holder.wind_direction_taf;
        textView.setText(forecast.getWind_dir_degrees() + "°");
        textView = holder.wind_speed_taf;
        textView.setText(forecast.getWind_speed_kt() + " kts");
        textView = holder.visibility_taf;
        textView.setText(forecast.getVisibility_statute_mi() + " mi");
        textView = holder.clouds_taf;
        if(forecast.getSky_condition().size() != 0)
            textView.setText(forecast.getSky_condition().get(0).getSky_cover() + " " + parentContext.getResources().getString(R.string.clouds_at) + " " + forecast.getSky_condition().get(0).getCloud_base_ft_agl() + "m");
        textView = holder.weather_taf;
        if(position != 0)
            textView.setText(forecast.getWx_string());
        else
            textView.setText(parentContext.getResources().getString(R.string.missing_data));


    }

    @Override
    public int getItemCount() {
        return lstForecast.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView taf;
        public TextView forecast_start;
        public TextView forecast_end;
        public TextView forecast_type;
        public TextView wind_direction_taf;
        public TextView wind_speed_taf;
        public TextView visibility_taf;
        public TextView clouds_taf;
        public TextView weather_taf;

        public ViewHolder(View itemView) {
            super(itemView);

            taf = (TextView) itemView.findViewById(R.id.taf);
            forecast_start = (TextView) itemView.findViewById(R.id.forecast_start);
            forecast_end = (TextView) itemView.findViewById(R.id.forecast_end);
            forecast_type = (TextView) itemView.findViewById(R.id.forecast_type);
            wind_direction_taf = (TextView) itemView.findViewById(R.id.wind_direction_taf);
            wind_speed_taf = (TextView) itemView.findViewById(R.id.wind_speed_taf);
            visibility_taf = (TextView) itemView.findViewById(R.id.visibility_taf);
            clouds_taf = (TextView) itemView.findViewById(R.id.clouds_taf);
            weather_taf = (TextView) itemView.findViewById(R.id.weather_taf);
        }
    }
}

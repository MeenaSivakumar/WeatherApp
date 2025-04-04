package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class weatherAdapter extends RecyclerView.Adapter<weatherAdapter.ViewHolder> {
   private Context context;

    public weatherAdapter(Context context, ArrayList<weatherRVModel> weatherRVModelArrayList) {
        this.context = context;
        this.weatherRVModelArrayList = weatherRVModelArrayList;
    }

    private ArrayList<weatherRVModel> weatherRVModelArrayList;
    @NonNull
    @Override
    public weatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull weatherAdapter.ViewHolder holder, int position) {
        weatherRVModel model = weatherRVModelArrayList.get(position);
        holder.temperatureTV.setText(model.getTemperature()+"°C");
        Picasso.get().load("http:".concat(model.getIcon())).into(holder.conditionIV);
        holder.windTV.setText(model.getWindSpeed()+"km/hr");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mmm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        try {
            Date t = input.parse(model.getTime());
            holder.timeTV.setText(output.format(t));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return weatherRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView timeTV,temperatureTV,windTV;
        private ImageView conditionIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            windTV =itemView.findViewById(R.id.windTV);
            temperatureTV= itemView.findViewById(R.id.temperatureTV);
            timeTV = itemView.findViewById(R.id.timeTV);
            conditionIV = itemView.findViewById(R.id.conditionIV);
        }
    }
}

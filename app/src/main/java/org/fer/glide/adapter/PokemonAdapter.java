package org.fer.glide.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.fer.glide.R;
import org.fer.glide.data.PokemonShort;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<PokemonShort> items;
    PokemonItemListener listener;


    public PokemonAdapter(List<PokemonShort> items, PokemonItemListener listener) {
        this.items = items;
        this.listener = listener;
    }
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType
    ) {
        return new PokemonViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_cell, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PokemonViewHolder) {
            ((PokemonViewHolder) holder).decorateWith(items.get(position));
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<PokemonShort> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public PokemonShort getItem(int position) {
        if(items == null || items.size() <= position) return null;
        return items.get(position);
    }
}
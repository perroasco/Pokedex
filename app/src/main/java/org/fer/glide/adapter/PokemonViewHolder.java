package org.fer.glide.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.fer.glide.R;
import org.fer.glide.data.PokemonShort;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonViewHolder extends RecyclerView.ViewHolder {

    ImageView pokemonImage;
    TextView pokemonName;
    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);
        pokemonImage = itemView.findViewById(R.id.pokemonImage);
        pokemonName = itemView.findViewById(R.id.pokemonName);
    }
    public void decorateWith(PokemonShort model) {
        pokemonName.setText(model.getName());
        Glide.with(itemView.getContext())
                .load(model.getUrl())
                .centerInside()
                .into(pokemonImage);
    }
}



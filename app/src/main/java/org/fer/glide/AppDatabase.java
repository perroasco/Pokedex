package org.fer.glide;

import org.fer.glide.data.PokemonDao;
import org.fer.glide.data.PokemonShort;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PokemonShort.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonDao pokemonDao();
}
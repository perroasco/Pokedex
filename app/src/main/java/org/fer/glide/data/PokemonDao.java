package org.fer.glide.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PokemonDao {
    @Query("SELECT * FROM pokemonshort")
    List<PokemonShort> getAll();

    @Query("SELECT * FROM pokemonshort WHERE id = :first")
    PokemonShort findByName(String first);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertAll(PokemonShort... users);

    @Delete
    void delete(PokemonShort user);
}

package org.fer.glide;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.fer.glide.data.PokemonShort;

import androidx.appcompat.app.AppCompatActivity;

public class AddPokemonActivity extends AppCompatActivity {
    EditText pokemonName, pokemonId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pokemon);
        pokemonName = findViewById(R.id.pokemonName);
        pokemonId = findViewById(R.id.pokemonId);
    }
    public void addPokemon(View view) {
        PokemonShort pokemonShort = new PokemonShort(
                pokemonName.getText().toString(),
                formatPokemonId(pokemonId.getText().toString())
        );
        AddPokemonAsyncTask task = new AddPokemonAsyncTask();
        task.execute(pokemonShort);
    }
    private String formatPokemonId(String id) {
        switch (id.length()) {
            case 0:
                return "001";
            case 1:
                return "00" + id;
            case 2:
                return "0" + id;
            default:
                return id;
        }
    }
    private class AddPokemonAsyncTask extends AsyncTask<PokemonShort, Void, Integer> {
        @Override
        protected Integer doInBackground(PokemonShort... pokemonShorts) {
            if (pokemonShorts.length == 0) return 0;
            AppDatabase db = AppDataBaseSingleton.getInstance(getApplicationContext()).appDatabase;
            return db.pokemonDao().insertAll(pokemonShorts).length;
        }
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            String message = (integer > 0) ? "Pokemon was added" : "Something went wrong";
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            if (integer > 0) {
                pokemonId.setText("");
                pokemonName.setText("");
                finish();
            }
        }
    }
}
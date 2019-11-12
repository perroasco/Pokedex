package org.fer.glide;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.fer.glide.data.PokemonShort;

import androidx.appcompat.app.AppCompatActivity;

public class PokemonDetailActivity extends AppCompatActivity {
    PokemonShort pokemonShort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        Parcelable extra = getIntent().getParcelableExtra(MainActivity.EXTRA_DATA);
        if (extra instanceof PokemonShort) {
            pokemonShort = (PokemonShort) extra;
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            finish();
        }

        /*String url = "nlaksndlkasd/" + pokemonShort.getId();
        DeletePokemonAsyncTask task = new DeletePokemonAsyncTask();
        task.execute(url);
        TextView textView = findViewById(R.id.pokemonInfo);
        String info =
                "ID: " + pokemonShort.getId() + "\n"
                        + "NAME: " + pokemonShort.getName() + "\n"
                        + "URL: " + pokemonShort.getUrl();
        textView.setText(info);*/
    }
    public void deletePokemon(View view) {
        DeletePokemonAsyncTask task = new DeletePokemonAsyncTask();
        task.execute(pokemonShort);
    }
    private class DeletePokemonAsyncTask extends AsyncTask<PokemonShort, Void, Integer> {
        @Override
        protected Integer doInBackground(PokemonShort... pokemonShorts) {
            if (pokemonShorts.length == 0) return 0;
            AppDatabase db = AppDataBaseSingleton.getInstance(getApplicationContext()).appDatabase;
            db.pokemonDao().delete(pokemonShorts[0]);
            return 1;
        }
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            String message = (integer > 0) ? "Pokemon was deleted" : "Something went wrong";
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            if (integer > 0) {
                finish();
            }
        }
    }
}
package org.fer.glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.fer.glide.adapter.PokemonAdapter;
import org.fer.glide.adapter.PokemonItemListener;
import org.fer.glide.data.ListDataHelper;
import org.fer.glide.data.Pokemon;
import org.fer.glide.data.PokemonShort;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PokemonItemListener {

    private PokemonAdapter adapter;
    RecyclerView recyclerView;
    private String LOG_TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(recyclerView.getContext(), 3);
        recyclerView.setLayoutManager(manager);
        // adapter= new PokemonAdapter(new ArrayList<PokemonShort>(),this);
        adapter = new PokemonAdapter(new ArrayList<PokemonShort>(), this);
        //recyclerView.setAdapter(new PokemonAdapter(ListDataHelper.provideElements(), this));
        recyclerView.setAdapter(adapter);
        new JsonTask().execute("https://pokeapi.co/api/v2/generation/3/");
    }


    private class JsonTask extends AsyncTask<String, String, List<PokemonShort>> {

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            if (url == null) {
                return jsonResponse;
            }
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);//milliseconds
                urlConnection.setConnectTimeout(15000);//milliseconds
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected List<PokemonShort> doInBackground(String... params) {
            ArrayList<PokemonShort> pokemons = new ArrayList<>();

            // Creamos el objeto URL desde el string que recibimos.
            if (params.length == 0) return pokemons;
            URL url = createUrl(params[0]);
            // Hacemos la petición. Ésta puede tirar una exepción.
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);

                JSONObject r = new JSONObject(jsonResponse);
                JSONArray pm = r.getJSONArray("pokemon_species");

                for (int i = 0; i < pm.length(); i++) {
                    JSONObject t = pm.getJSONObject(i);
                    pokemons.add(new PokemonShort(t.getString("name"), t.getString("url")));
                }
            } catch (IOException | JSONException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }
            return pokemons;
        }

        @Override
        protected void onPostExecute(List<PokemonShort> result) {
            super.onPostExecute(result);
            adapter.setItems(result);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updatePokemonList(List<PokemonShort> pokemonShortList) {
        adapter.setItems(pokemonShortList);
    }

    public static final String EXTRA_DATA = "EXTRA_DATA";

    @Override
    public void onPokemonClicked(int position) {
        PokemonShort pokemonShort = adapter.getItem(position);
        Intent intent = new Intent(this, PokemonDetailActivity.class);
        intent.putExtra(EXTRA_DATA, pokemonShort);
        startActivity(intent);
    }

    public void addPokemon(View view) {
        Intent intent = new Intent(this, AddPokemonActivity.class);
        startActivity(intent);
    }

    private class GetPokemonsAsyncTask extends AsyncTask<Void, Void, List<PokemonShort>> {

        private List<PokemonShort> getList(String jsonStr) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObj.getJSONArray("results");
                ArrayList<PokemonShort> pokemonShortList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String name = jsonArray.getJSONObject(i).getString("name");
                    String url = jsonArray.getJSONObject(i).getString("url");
                    pokemonShortList.add(new PokemonShort(name, url));
                }
                return pokemonShortList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected List<PokemonShort> doInBackground(Void... voids) {
            AppDatabase db = AppDataBaseSingleton.getInstance(getApplicationContext()).appDatabase;
            return db.pokemonDao().getAll();
        }

        @Override
        protected void onPostExecute(List<PokemonShort> pokemonShortList) {
            super.onPostExecute(pokemonShortList);
            if (pokemonShortList != null) {
                updatePokemonList(pokemonShortList);
            }
        }

    }
}


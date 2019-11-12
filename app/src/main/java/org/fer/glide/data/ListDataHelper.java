package org.fer.glide.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ListDataHelper {


    public static ArrayList<PokemonShort> provideElements() {
        ArrayList<PokemonShort> pokemons = new ArrayList<>();
        return pokemons;
    }

}

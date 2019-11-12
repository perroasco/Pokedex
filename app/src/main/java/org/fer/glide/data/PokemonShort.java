package org.fer.glide.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PokemonShort implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String url;

    public PokemonShort() {

    }

    public PokemonShort(String name, String foreignUrl) {
        this.name = name;
        this.id = getStringId(foreignUrl);

        Log.d("Hola", "name "+this.name+" id "+this.id);

        this.url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" + formatPokemonId(id) + ".png";
        //this.url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" + "010" + ".png";
    }

    String dameElPincheId(String url){
        return url.substring(url.length()-4, url.length()-1);


    }




    public PokemonShort(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.url = in.readString();
    }
    public static final Creator<PokemonShort> CREATOR = new Creator<PokemonShort>() {
        @Override
        public PokemonShort createFromParcel(Parcel in) {
            return new PokemonShort(in);
        }
        @Override
        public PokemonShort[] newArray(int size) {
            return new PokemonShort[size];
        }
    };
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(url);
    }

    public String getStringId(String url) {
        String substring = url.replace("https://pokeapi.co/api/v2/pokemon-species", "");
        String[] array = substring.split("/");
        return array[1];
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
}
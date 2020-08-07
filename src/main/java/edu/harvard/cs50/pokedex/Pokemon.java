package edu.harvard.cs50.pokedex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Pokemon {
    private String name;
    private String url;

    Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

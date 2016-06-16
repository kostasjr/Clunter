package com.kostas.clunter;

/**
 * Created by Kostas on 2015-12-01.
 */
public class AdventureListItem {

    private String adventure_name;
    private String adventure_location;
    private String adventure_length;
    private String adventure_id;
    private String adventure_toughness;

    public AdventureListItem(String adventure_name, String adventure_location, String adventure_toughness) {
        this.adventure_name = adventure_name;
        this.adventure_location = adventure_location;
        this.adventure_toughness = adventure_toughness;
    }

    public String getAdventure_name() {
        return adventure_name;
    }

    public String getAdventure_toughness() {
        return adventure_toughness;
    }

    public void setAdventure_name(String adventure_name) {
        this.adventure_name = adventure_name;
    }

    public String getAdventure_location() {
        return adventure_location;
    }

    public void setAdventure_location(String adventure_location) { this.adventure_location = adventure_location;}

    public String getAdventure_length() {
        return adventure_length;
    }

    public void setAdventure_length(String adventure_length) { this.adventure_length = adventure_length; }

    public String getAdventure_id() {
        return adventure_id;
    }
}

package model;

import java.util.ArrayList;
import java.util.Date;

public class Track {
    private String name;
    private Artist artist;
    private int duration;
    private Date releaseDate;
    private boolean type;
    private static ArrayList<Track> tracks = new ArrayList<>();

    public Track(Date releaseDate, Artist artist, boolean type, int duration, String name) {
        this.name = name;
        this.type = type;
        this.artist = artist;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public Artist getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public boolean isType() {
        return type;
    }

    public static void addTrack(Track track) {
        tracks.add(track);
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public static Track getTrackByName(String name) {
        for (Track track : tracks) {
            if (track.name.equals(name)) {
                return track;
            }
        }
        return null;
    }
}

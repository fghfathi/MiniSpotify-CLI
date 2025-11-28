package model;

import java.util.ArrayList;

public class Artist extends ArtistMenu {
    private String username;
    private String password;
    private String nickname;
    private int follower;
    private ArrayList<Track> tracks = new ArrayList<>();

    private static Artist loggedInArtist;
    private static ArrayList<Artist> artists = new ArrayList<>();

    public Artist(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        artists.add(this);
    }

    public static Artist getArtistByUsername(String username) {
        for (Artist artist : artists) {
            if (artist != null) {
                if ((artist.getUsername()).equals(username)) {
                    return artist;
                }
            }
        }
        return null;
    }

    public static ArrayList<Artist> getArtists() {
        return artists;
    }

    public static void addArtist(Artist artist) {
        artists.add(artist);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void addToTracks(Track track) {
        Artist artist = loggedInArtist;
        artist.tracks.add(track);
    }

    public static Artist getLoggedInArtist() {
        return Artist.loggedInArtist;
    }

    public static void setLoggedInArtist(Artist artist) {
        Artist.loggedInArtist = artist;
    }

    public static boolean isThereArtistWithUsername(String username) {
        if (!artists.isEmpty()) {
            for (Artist artist : artists) {
                if (artist != null) {
                    if (artist.getUsername().equals(username)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

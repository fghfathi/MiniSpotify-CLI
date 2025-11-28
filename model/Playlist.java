package model;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private User owner;
    private int duration;
    private ArrayList<Track> tracks = new ArrayList<>();
    private static ArrayList<Playlist> playlists = new ArrayList<>();
    private static Playlist currentPlaylist;

    public Playlist(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public void addTracks(Track track) {
        this.tracks.add(track);
    }

    public void removeTracks(Track track) {
        this.tracks.remove(track);
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public static void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public static void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    public static Playlist getPlaylistByName(String name) {
        if (!playlists.isEmpty()) {
            for (Playlist playlist : playlists) {
                if ((playlist.getName()).equals(name)) {
                    return playlist;
                }
            }
        }
        return null;
    }

    public static Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public static void setCurrentPlaylist(Playlist playlist) {
        Playlist.currentPlaylist = playlist;
    }

    public static boolean isTherePlaylistWithName(String name) {
        if (!playlists.isEmpty()) {
            for (Playlist playlist : playlists) {
                if (playlist.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public boolean isTrackInPlaylist(Track track) {
        for (Track eachTrack : this.tracks) {
            if (eachTrack.equals(track))
                return true;
        }
        return false;
    }
}

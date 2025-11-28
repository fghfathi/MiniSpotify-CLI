package model;

import java.util.ArrayList;

public class User extends UserMenu {
    private String username;
    private String password;
    private int followers;
    private int following;
    private ArrayList<Track> queue = new ArrayList<>();
    private ArrayList<Track> likedTracks = new ArrayList<>();
    private ArrayList<Playlist> playlists = new ArrayList<>();
    private static User loggedInUser;
    private static ArrayList<User> users = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }

    public void removePlaylist(Playlist playlist) {
        User user = getLoggedInUser();
        user.playlists.remove(playlist);
    }

    public void addToQueue(Track track) {
        User user = getLoggedInUser();
        user.queue.add(track);
    }

    public void removeFromQueue(Track track) {
        User user = getLoggedInUser();
        user.queue.remove(track);
    }

    public void addLikedTrack(Track track) {
        User user = getLoggedInUser();
        user.likedTracks.add(track);
    }

    public void removeLikedTrack(Track track) {
        User user = getLoggedInUser();
        user.likedTracks.remove(track);
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Track> getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<Track> queue) {
        this.queue = queue;
    }

    public String getUsername() {
        return username;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public ArrayList<Track> getLikedTracks() {
        return likedTracks;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static boolean isThereUserWithUsername(String username) {
        for (User user : users) {
            if (user != null) {
                if (user.getUsername().trim().equals(username.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThereTrackInLikedTracks(Track track) {
        for (Track track2 : likedTracks) {
            if (track2 != null) {
                if (track2.getName().equals(track.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThereTrackInQueue(Track track) {
        for (Track track2 : queue) {
            if (track2 != null) {
                if (track2.getName().equals(track.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

}

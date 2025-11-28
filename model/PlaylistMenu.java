package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaylistMenu {

    static String addTrackRegex = "add -t (?<trackname>[a-zA-Z\\d\\s]*)";
    static String removeTrackRegex = "remove -t (?<trackname>[a-zA-Z\\d\\s]*)";

    public static void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            Matcher matcher;
            if (input.equals("back")) {
                break;
            } else if (input.equals("show tracks")) {
                showTracks();
            } else if (input.equals("show duration")) { // majmoo e modat zaman ha
                showDuration();
            } else if ((matcher = getCommandMatcher(input, addTrackRegex)).matches()) {
                addTrack(matcher);
            } else if ((matcher = getCommandMatcher(input, removeTrackRegex)).matches()) {
                removeTrack(matcher);
            } else if (input.equals("show menu name")) {
                System.out.println("playlist menu");
            } else {
                System.out.println("invalid command");
            }
        }

    }

    private static Matcher getCommandMatcher(String input, String regex) {
        return (Pattern.compile(regex).matcher(input));
    }

    private static void showTracks() {
        Playlist playlist = Playlist.getCurrentPlaylist();
        ArrayList<Track> tracksOfPlaylist = playlist.getTracks();
        if (!tracksOfPlaylist.isEmpty()) {
            tracksOfPlaylist.sort(Comparator.comparing(Track::getReleaseDate).reversed().thenComparing(Track::getName));
            for (Track track : tracksOfPlaylist) {
                System.out.println(track.getName());
            }
        }
    }

    private static void showDuration() {
        Playlist playlist = Playlist.getCurrentPlaylist();
        ArrayList<Track> tracks = playlist.getTracks();
        int sum = 0;
        for (Track track : tracks) {
            sum += track.getDuration();
        }
        System.out.println(sum);
    }

    private static void addTrack(Matcher matcher) {
        if (Playlist.getCurrentPlaylist().getOwner().equals(User.getLoggedInUser())) {
            Track track = Track.getTrackByName(matcher.group("trackname"));
            if (track != null) {
                Playlist playlist = Playlist.getCurrentPlaylist();
                if (!playlist.isTrackInPlaylist(track)) {
                    Playlist.getCurrentPlaylist().addTracks(track);
                    System.out.println("track added to playlist successfully");
                } else
                    System.out.println("track is already in the playlist");
            } else
                System.out.println("no such track");
        } else {
            System.out.println("user doesn't own this playlist");
        }
    }

    private static void removeTrack(Matcher matcher) {
        Track track1 = Track.getTrackByName(matcher.group("trackname"));
        if (Playlist.getCurrentPlaylist().isTrackInPlaylist(track1)) {
            Playlist.getCurrentPlaylist().removeTracks(track1);
            System.out.println("track removed from playlist successfully");
        } else {
            System.out.println("no such track in playlist");
        }
    }
}

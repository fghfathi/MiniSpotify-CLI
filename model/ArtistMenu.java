package model;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArtistMenu {
    static String newTrack = "release -n (?<trackname>[a-zA-Z\\d\\s]*) -t (?<type>(song)||(podcast)) -d " +
            "(?<duration>\\d+) -r (?<releaseDate>\\d{4}/\\d{2}/\\d{2})";

    public static void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            Matcher matcher;
            if (input.equals("back")) {
                break;
            } else if (input.equals("show tracks")) {
                showTracks();
            } else if (input.equals("show songs")) {
                showSongs();
            } else if (input.equals("show podcasts")) {
                showPodcasts();
            } else if ((matcher = getCommandMatcher(input, newTrack)).matches()) {
                try {
                    ArtistMenu.releaseTrack(matcher);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (input.equals("num of followers")) {
                getNumberOfFollowers();
            } else if (input.equals("get rank")) {
                getRank();
            } else if (input.equals("show menu name")) {
                System.out.println("artist menu");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        return (Pattern.compile(regex).matcher(input));
    }

    private static void showTracks() {
        Artist artist = Artist.getLoggedInArtist();
        ArrayList<Track> tracksOfArtist = artist.getTracks();
        if (tracksOfArtist != null) {
            tracksOfArtist.sort(Comparator.comparing(Track::getReleaseDate).reversed().thenComparing(Track::getName));
            for (Track track : tracksOfArtist) {
                System.out.println(track.getName());
            }
        }
    }

    private static void showSongs() {
        Artist artist = Artist.getLoggedInArtist();
        ArrayList<Track> songs = new ArrayList<>();
        for (Track track : artist.getTracks()) {
            if (track.isType()) {
                songs.add(track);
            }
        }
        songs.sort(Comparator.comparing(Track::getReleaseDate).reversed().thenComparing(Track::getName));
        for (Track track : songs) {
            System.out.println(track.getName());
        }
    }

    private static void showPodcasts() {
        Artist artist = Artist.getLoggedInArtist();
        ArrayList<Track> podcastTracks = new ArrayList<>();
        for (Track track : artist.getTracks()) {
            if (!track.isType()) {
                podcastTracks.add(track);
            }
        }
        podcastTracks.sort(Comparator.comparing(Track::getReleaseDate).reversed().thenComparing(Track::getName));
        for (Track track : podcastTracks) {
            System.out.println(track.getName());
        }
    }

    private static void releaseTrack(Matcher matcher) throws ParseException {
        Track track = Track.getTrackByName(matcher.group("trackname"));
        Artist artist = Artist.getLoggedInArtist();
        if (track == null) {
            System.out.println("track released successfully");
            String pattern = "yyyy/MM/dd";
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern);
            Date date = simpleDateFormat1.parse(matcher.group("releaseDate"));
            int duration = Integer.parseInt(matcher.group("duration"));
            Track track1 = new Track(date, artist, matcher.group("type").equals("song"), duration,
                    matcher.group("trackname"));
            Track.addTrack(track1);
            artist.addToTracks(track1);
        } else {
            System.out.println("track name already exists");

        }
    }

    private static void getRank() {
        Artist artist = Artist.getLoggedInArtist();
        ArrayList<Artist> artists = Artist.getArtists();
        int rank = 1;
        ArrayList<Integer> followersNumber = new ArrayList<>();
        for (Artist artistTemp : artists) {
            if ((!followersNumber.contains(artistTemp.getFollower()))
                    && (artistTemp.getFollower() > artist.getFollower())) {
                followersNumber.add(artistTemp.getFollower());
                rank++;
            }
        }
        System.out.println(rank);
    }

    private static void getNumberOfFollowers() {
        Artist artist = Artist.getLoggedInArtist();
        System.out.println(artist.getFollower());
    }

}

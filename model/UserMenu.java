package model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMenu {

    String addTrackToQueueRegex = "add -t (?<trackname>[a-zA-Z\\d\\s]*) to queue";
    String addTrackToLikedTracksRegex = "like track -t (?<trackname>[a-zA-Z\\d\\s]*)";
    String removeFromQueueRegex = "remove -t (?<trackname>[a-zA-Z\\d\\s]*) from queue";
    String removeFromLikedTracksRegex = "remove -t (?<trackname>[a-zA-Z\\d\\s]*) from liked tracks";
    String reverseOrderOfQueueRegex = "reverse order of queue from (?<start>-?\\d+) to (?<end>-?\\d+)";
    String createPlaylistRegex = "create -p (?<playlistName>[a-zA-Z\\d\\s]*)";
    String deletePlaylist = "delete -p (?<playlistName>[a-zA-Z\\d\\s]*)";
    String followUserRegex = "follow user -u (?<username>[a-zA-Z\\d]*)";
    String followArtistRegex = "follow artist -u (?<username>[a-zA-Z\\d]*)";
    String unfollowUserRegex = "unfollow user -u (?<username>[a-zA-Z\\d]*)";
    String unfollowArtistRegex = "unfollow artist -u (?<username>[a-zA-Z\\d]*)";
    String gotoPlaylistRegex = "go to playlist menu -p (?<playlistName>[a-zA-Z\\d\\s]*)";
    String showInfoTrack = "show track info -t (?<trackname>[a-zA-Z\\d\\s]*)";
    String showInfoPlaylistRegex = "show playlist info -p (?<playlistName>[a-zA-Z\\d\\s]*)";
    String showInfoUser = "show user info -u (?<username>[a-zA-Z0-9]*)";
    String showInfoArtist = "show artist info -u (?<username>[a-zA-Z0-9]*)";

    public static void run(Scanner scanner) {
        User user = User.getLoggedInUser(); /// maybe i dont require to this
        while (true) {
            String input = scanner.nextLine().trim();
            Matcher matcher;
            if (input.equals("back")) {
                break;
            } else if (input.equals("show playlists")) {
                showPlaylists();
            } else if (input.equals("show liked tracks")) {
                showLikedTracks();
            } else if (input.equals("show queue")) {
                showQueue();
            } else if ((matcher = getCommandMatcher(input, user.addTrackToQueueRegex)).matches()) {
                addTrackToQueue(matcher);
            } else if ((matcher = getCommandMatcher(input, user.addTrackToLikedTracksRegex)).matches()) {
                addTrackTolikedTracks(matcher);
            } else if ((matcher = getCommandMatcher(input, user.removeFromQueueRegex)).matches()) {
                removeFromQueue(matcher);
            } else if ((matcher = getCommandMatcher(input, user.removeFromLikedTracksRegex)).matches()) {
                removeTrackFromlikedTracks(matcher);
            } else if ((matcher = getCommandMatcher(input, user.reverseOrderOfQueueRegex)).matches()) {
                reverseOrderOfQueue(scanner, matcher);
            } else if ((matcher = getCommandMatcher(input, user.createPlaylistRegex)).matches()) {
                createPlaylist(matcher);
            } else if ((matcher = getCommandMatcher(input, user.deletePlaylist)).matches()) {
                removePlaylist(matcher);
            } else if ((matcher = getCommandMatcher(input, user.followUserRegex)).matches()) {
                followUser(matcher);
            } else if ((matcher = getCommandMatcher(input, user.followArtistRegex)).matches()) {
                followArtist(matcher);
            } else if ((matcher = getCommandMatcher(input, user.unfollowUserRegex)).matches()) {
                unfollowUser(matcher);
            } else if ((matcher = getCommandMatcher(input, user.unfollowArtistRegex)).matches()) {
                unfollowArtist(matcher);
            } else if ((matcher = getCommandMatcher(input, user.gotoPlaylistRegex)).matches()) {
                if (Playlist.getPlaylistByName(matcher.group("playlistName")) == null) {
                    System.out.println("no such playlist");
                } else {
                    gotoPlaylistMenu(scanner, matcher);
                }
            } else if ((matcher = getCommandMatcher(input, user.showInfoTrack)).matches()) {
                showTrackInfo(matcher);
            } else if ((matcher = getCommandMatcher(input, user.showInfoPlaylistRegex)).matches()) {
                showPlaylistInfo(matcher);
            } else if ((matcher = getCommandMatcher(input, user.showInfoUser)).matches()) {
                showUserInfo(matcher);
            } else if ((matcher = getCommandMatcher(input, user.showInfoArtist)).matches()) {
                showArtistInfo(matcher);
            } else if (input.equals("show menu name")) {
                System.out.println("user menu");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        return (Pattern.compile(regex).matcher(input));
    }

    private static void addTrackToQueue(Matcher matcher) {
        User user = User.getLoggedInUser();
        // Track track = Track.getTrackByName(matcher.group("trackname"));
        if (Track.getTrackByName(matcher.group("trackname")) == null) {
            System.out.println("no such track");
        } else {
            System.out.println("track added to queue successfully");
            Track track = Track.getTrackByName(matcher.group("trackname"));
            user.addToQueue(track);
        }
    }

    private static void addTrackTolikedTracks(Matcher matcher) {
        User user = User.getLoggedInUser();
        if (Track.getTrackByName(matcher.group("trackname")) == null) {
            System.out.println("no such track");
        } else {
            Track track = Track.getTrackByName(matcher.group("trackname"));
            if (user.isThereTrackInLikedTracks(track)) {
                System.out.println("track is already liked");
            } else {
                System.out.println("liked track successfully");
                user.addLikedTrack(track);
            }
        }
    }

    private static void removeFromQueue(Matcher matcher) {
        // User user = User.getLoggedInUser();
        // if (Track.getTrackByName(matcher.group("trackname")) != null) {
        // Track track = Track.getTrackByName(matcher.group("trackname"));
        // if (!user.isThereTrackInQueue(track)) {
        // System.out.println("no such track in queue");
        // } else {
        // System.out.println("track removed from queue successfully");
        // user.removeFromQueue(track);
        // }
        // }

        Track track = Track.getTrackByName(matcher.group("trackname"));
        boolean existInQueue = false;
        ArrayList<Track> queue = User.getLoggedInUser().getQueue();
        ArrayList<Track> temp = new ArrayList<>(queue);
        for (Track track1 : temp) {
            if (track1.equals(track)) {
                queue.remove(track1);
                existInQueue = true;
            }
        }
        if (existInQueue) {
            System.out.println("track removed from queue successfully");
        } else {
            System.out.println("no such track in queue");
        }
    }

    private static void removeTrackFromlikedTracks(Matcher matcher) {
        User user = User.getLoggedInUser();
        if (Track.getTrackByName(matcher.group("trackname")) != null) {
            Track track = Track.getTrackByName(matcher.group("trackname"));
            if (!user.isThereTrackInLikedTracks(track)) {
                System.out.println("no such track in liked tracks");
            } else {
                System.out.println("track removed from liked tracks successfully");
                user.removeLikedTrack(track);
            }
        }
    }

    private static void showPlaylists() {
        User user = User.getLoggedInUser();
        ArrayList<Playlist> playlists = user.getPlaylists();
        String[] NameOfPlaylist = new String[playlists.size()];
        int index = 0;
        for (Playlist playlist : playlists) {
            NameOfPlaylist[index] = playlist.getName();
            index++;
        }
        Arrays.sort(NameOfPlaylist);
        for (String name : NameOfPlaylist) {
            System.out.println(name);
        }
    }

    private static void showLikedTracks() {
        User user = User.getLoggedInUser();
        ArrayList<Track> likedTracks = user.getLikedTracks();
        String[] NameOfTrack = new String[likedTracks.size()];
        int index = 0;
        for (Track track : likedTracks) {
            NameOfTrack[index] = track.getName();
            index++;
        }
        Arrays.sort(NameOfTrack);
        for (String Name : NameOfTrack) {
            System.out.println(Name);
        }
    }

    private static void showUserInfo(Matcher matcher) {
        if (User.getUserByUsername(matcher.group("username")) == null) {
            System.out.println("no such user");
        } else {
            User user = User.getUserByUsername(matcher.group("username"));
            System.out.printf("%s %d %d %d\n", user.getUsername(), user.getFollowers(), user.getFollowing(),
                    user.getPlaylists().size());

        }
    }

    private static void showTrackInfo(Matcher matcher) {
        if (Track.getTrackByName(matcher.group("trackname")) == null) {
            System.out.println("no such track");
        } else {
            Track track = Track.getTrackByName(matcher.group("trackname"));
            String info1 = track.getName();
            String info2 = "song";
            if (!track.isType()) {
                info2 = "podcast";
            }
            int info3 = track.getDuration();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String date = simpleDateFormat.format(track.getReleaseDate());
            String info5 = track.getArtist().getNickname();
            System.out.println(info1 + " " + info2 + " " + info3 + " " + date + " " + info5);
        }

    }

    private static void showQueue() {
        User user = User.getLoggedInUser();
        ArrayList<Track> queues = user.getQueue();
        for (Track track : queues) {
            System.out.println(track.getName());
        }
    }

    private static void showArtistInfo(Matcher matcher) {
        if (Artist.getArtistByUsername(matcher.group("username")) == null) {
            System.out.println("no such artist");
        } else {
            Artist artist = Artist.getArtistByUsername(matcher.group("username"));
            assert artist != null;
            System.out.printf("%s %s %d ", artist.getUsername(), artist.getNickname(), artist.getFollower());
            int rank = 1;
            ArrayList<Integer> followers = new ArrayList<>();
            for (Artist artist1 : Artist.getArtists()) {
                if ((!followers.contains(artist1.getFollower())) && (artist1.getFollower() > artist.getFollower())) {
                    followers.add(artist1.getFollower());
                    rank++;
                }
            }
            System.out.println(rank);
        }

    }

    private static void showPlaylistInfo(Matcher matcher) {
        if (Playlist.getPlaylistByName(matcher.group("playlistName")) == null) {
            System.out.println("no such playlist");
        } else {
            int sum = 0;
            Playlist playlist = Playlist.getPlaylistByName(matcher.group("playlistName"));
            for (Track track : playlist.getTracks()) {
                sum += track.getDuration();
            }
            System.out.println(playlist.getName() + " " + playlist.getOwner().getUsername() + " " + sum);
        }
    }

    private static void followUser(Matcher matcher) {
        User user = User.getLoggedInUser();
        String followedUsername = matcher.group("username");
        if (!User.isThereUserWithUsername(followedUsername)) {
            System.out.println("no such user");
        } else if (followedUsername.equals(user.getUsername())) {
            System.out.println("you can't follow yourself");
        } else if (User.isThereUserWithUsername(followedUsername)) {
            System.out.println("added user to followings");
            User followedUser = User.getUserByUsername(followedUsername);
            followedUser.setFollowers(followedUser.getFollowers() + 1);
            user.setFollowing(user.getFollowing() + 1);
        }
    }

    private static void unfollowUser(Matcher matcher) {
        User user = User.getLoggedInUser();
        String followedUsername = matcher.group("username");
        if (!User.isThereUserWithUsername(followedUsername)) {
            System.out.println("no such user");
        } else if (followedUsername.equals(user.getUsername())) {
            System.out.println("you can't unfollow yourself");
        } else if (User.isThereUserWithUsername(followedUsername)) {
            System.out.println("user unfollowed successfully");
            User followedUser = User.getUserByUsername(followedUsername);
            followedUser.setFollowers(followedUser.getFollowers() - 1);
            user.setFollowing(user.getFollowing() - 1);
        }
    }

    private static void followArtist(Matcher matcher) {
        User user = User.getLoggedInUser();
        Artist followedArtist = Artist.getArtistByUsername(matcher.group("username"));
        if (followedArtist == null) {
            System.out.println("no such artist");
        } else {
            System.out.println("added artist to followings");
            followedArtist.setFollower(followedArtist.getFollower() + 1);
            user.setFollowing(user.getFollowing() + 1);
        }
    }

    private static void unfollowArtist(Matcher matcher) {
        User user = User.getLoggedInUser();
        String followedUsername = matcher.group("username");
        if (!Artist.isThereArtistWithUsername(followedUsername)) {
            System.out.println("no such artist");
        } else if (Artist.isThereArtistWithUsername(followedUsername)) {
            Artist followedArtist = Artist.getArtistByUsername(followedUsername);
            followedArtist.setFollower(followedArtist.getFollower() - 1);
            user.setFollowing(user.getFollowing() - 1);
            System.out.println("artist unfollowed successfully");
        }
    }

    private static void createPlaylist(Matcher matcher) {
        User user = User.getLoggedInUser();
        String name = matcher.group("playlistName");
        if (Playlist.isTherePlaylistWithName(name)) {
            System.out.println("playlist name already exists");
        } else {
            Playlist playlist = new Playlist(name, user);
            Playlist.setCurrentPlaylist(playlist);
            user.addPlaylist(playlist);
            Playlist.addPlaylist(playlist);
            System.out.println("playlist created successfully");
        }
    }

    private static void removePlaylist(Matcher matcher) {
        User user = User.getLoggedInUser();
        String name = matcher.group("playlistName");
        if (Playlist.getPlaylistByName(name) == null) {
            System.out.println("user doesn't own such playlist");
        } else {
            Playlist playlist = Playlist.getPlaylistByName(name);
            User user1 = playlist.getOwner();
            if (!user.getUsername().equals(user1.getUsername())) {
                System.out.println("user doesn't own such playlist");
            } else {
                Playlist.removePlaylist(playlist);
                user.removePlaylist(playlist);
                System.out.println("playlist deleted successfully");
            }
        }
    }

    private static void gotoPlaylistMenu(Scanner scanner, Matcher matcher) {
        Playlist playlist = Playlist.getPlaylistByName(matcher.group("playlistName"));
        Playlist.setCurrentPlaylist(playlist);
        System.out.println("entered playlist menu successfully");
        PlaylistMenu.run(scanner);
    }

    private static void reverseOrderOfQueue(Scanner scanner, Matcher matcher) {
        // User user = User.getLoggedInUser();
        // ArrayList<Track> queue = user.getQueue();
        // int start = Integer.parseInt(matcher.group("start"));
        // int end = Integer.parseInt(matcher.group("end"));
        //
        // if (queue.isEmpty()) {
        // System.out.println("queue is empty");
        // } else if ((start < 1) || (end > user.getQueue().size()) || (end < start +
        // 1)) {
        // System.out.println("invalid bounds");
        // } else {
        // List<Track> subList = queue.subList(start, end + 1);
        // Collections.reverse(subList);
        //
        // for (int i = 0; i < subList.size(); i++) {
        // queue.set(start + i, subList.get(i));
        // }
        // System.out.println("order of queue reversed successfully");
        // }
        User user = User.getLoggedInUser();
        int startIndex = Integer.parseInt(matcher.group("start"));
        int endIndex = Integer.parseInt(matcher.group("end"));
        ArrayList<Track> queueTracks = user.getQueue();
        if (queueTracks.isEmpty())
            System.out.println("queue is empty");
        else if (startIndex < 1 || endIndex > queueTracks.size() || startIndex >= endIndex) {
            System.out.println("invalid bounds");
        } else {
            startIndex--;
            ArrayList<Track> subArraylist = new ArrayList<>();
            for (int i = startIndex; i < endIndex; i++)
                subArraylist.add(queueTracks.get(i));
            Collections.reverse(subArraylist);
            for (int i = startIndex; i < endIndex; i++) {
                queueTracks.set(i, subArraylist.get(i - startIndex));
            }
            user.setQueue(queueTracks);
            System.out.println("order of queue reversed successfully");
        }
    }
}

package model;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginMenu {

    public static void run(Scanner scanner) {
        String registerUser = "register as user -u (?<username>[a-zA-Z\\d]*) -p (?<password>[a-zA-Z\\d]*)";
        String registerArtist = "register as artist -u (?<username>[a-zA-Z\\d]*) -p (?<password>[a-zA-Z\\d]*)" +
                " -n (?<nickname>[a-zA-Z\\s\\d]*)";
        String loginUser = "login as user -u (?<username>[a-zA-Z\\d]*) -p (?<password>[a-zA-Z\\d]*)";
        String loginArtist = "login as artist -u (?<username>[a-zA-Z\\d]*) -p (?<password>[a-zA-Z\\d]*)";
        while (true) {
            String input = scanner.nextLine().trim();
            Matcher matcher;
            if ((matcher = loginMenu.getCommandMatcher(input, registerUser)).matches()) {
                userRegister(matcher);
            } else if ((matcher = loginMenu.getCommandMatcher(input, registerArtist)).matches()) {
                artistRegister(matcher);
            } else if ((matcher = loginMenu.getCommandMatcher(input, loginUser)).matches()) {
                userLogin(matcher, scanner);
            } else if ((matcher = loginMenu.getCommandMatcher(input, loginArtist)).matches()) {
                artistLogin(matcher, scanner);
            } else if (input.equals("show menu name")) {
                System.out.println("login menu");
            } else if (input.equals("exit")) {
                break;
            } else if (input.equals("back")) {
                break;
            } else {
                System.out.println("invalid command");
            }

        }
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        return (Pattern.compile(regex).matcher(input));
    }

    private static void artistRegister(Matcher matcher) {
        if (Artist.isThereArtistWithUsername(matcher.group("username"))) {
            System.out.println("username already exists");
        } else if (!isPasswordWeak(matcher)) {
            System.out.println("password is not strong enough");
        } else {
            System.out.println("artist registered successfully");
            Artist artist = new Artist(matcher.group("username"), matcher.group("password"), matcher.group("nickname"));
            Artist.addArtist(artist);
        }
    }

    private static void userRegister(Matcher matcher) {
        if (User.isThereUserWithUsername(matcher.group("username"))) {
            System.out.println("username already exists");
        } else if (!isPasswordWeak(matcher)) {
            System.out.println("password is not strong enough");
        } else {
            System.out.println("user registered successfully");
            User user = new User(matcher.group("username"), matcher.group("password"));
            User.addUser(user);
        }
    }

    private static void userLogin(Matcher matcher, Scanner scanner) {
        if (User.getUserByUsername(matcher.group("username")) == null) {
            System.out.println("username doesn't exist");
        } else if (!matcher.group("password").equals(User.getUserByUsername(matcher.group("username")).getPassword())) {
            System.out.println("password is wrong");
        } else {
            System.out.println("user logged in successfully");
            User user = User.getUserByUsername(matcher.group("username"));
            user.setLoggedInUser(user);
            UserMenu.run(scanner);
        }
    }

    // یکیش
    private static void artistLogin(Matcher matcher, Scanner scanner) {
        if (Artist.getArtistByUsername(matcher.group("username")) == null) {
            System.out.println("username doesn't exist");
        } else {
            Artist artist = Artist.getArtistByUsername(matcher.group("username"));
            assert artist != null;
            if (!matcher.group("password").equals(artist.getPassword())) {
                System.out.println("password is wrong");
            } else {
                System.out.println("artist logged in successfully");
                Artist.setLoggedInArtist(artist);
                ArtistMenu.run(scanner);
            }
        }
    }

    public static boolean isPasswordWeak(Matcher matcher) {
        String checkStrong = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).*";
        if (getCommandMatcher(matcher.group("password"), checkStrong).matches()) {
            return true;
        }
        return false;
    }
}

package gr.theofilis.getyourfacebookdata;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.cli.*;

public class App {

    public static void main(String[] args) throws FileNotFoundException, ParseException, UnsupportedEncodingException {
        Options options = new Options();

        options.addOption("dAccess", true, "set the access token");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);


        FacebookClient facebookClient = new DefaultFacebookClient(cmd.getOptionValue("dAccess"));


        String param = "id,name,first_name,middle_name,last_name,gender,locale,"
                + "languages,link,username,updated_time,bio,birthday,education,"
                + "hometown,interested_in,location,political,favorite_athletes,"
                + "favorite_teams,quotes,relationship_status,religion";

        Connection<User> friends = facebookClient.fetchConnection("me/friends",
                User.class, Parameter.with("fields", param));

        PrintStream out = new PrintStream(cmd.getArgs()[0], "UTF-8");
        out.println(param);

        for (User friend : friends.getData()) {
            out.print(friend.getId()+ ", ");
            printField(out, friend.getName(), false);
            printField(out, friend.getFirstName(), false);
            printField(out, friend.getMiddleName(), false);
            printField(out, friend.getLastName(), false);
            printField(out, friend.getGender(), false);
            printField(out, friend.getLocale(), false);
            printField(out, friend.getLanguages(), false);
            printField(out, friend.getLink(), false);
            printField(out, friend.getUsername(), false);
            out.print(friend.getUpdatedTime() + ", ");
            printField(out, friend.getBio(), false);
            out.print(friend.getBirthday() + ", ");
            out.print(friend.getEducation() + ", "); // TODO
            printField(out, friend.getHometown(), false);
            printFieldString(out, friend.getInterestedIn(), false);
            printField(out, friend.getLocation(), false);
            printField(out, friend.getPolitical(), false);
            printField(out, friend.getFavoriteAthletes(), false);
            printField(out, friend.getFavoriteTeams(), false); 
            printField(out, friend.getQuotes(), false);
            printField(out, friend.getRelationshipStatus(), false);
            printField(out, friend.getReligion(), true);
            out.println();
        }

    }

    public static void printFieldEducation(PrintStream out, List<User.Education> values, boolean last) {
        if (values == null) {
            out.print("N/A" + ((last) ? "" : ", "));
        } else {
            for (User.Education value : values) {
                value.getYear().getName();
                value.getType();
                out.print(value.getSchool().getName().replaceAll("\n", "").replaceAll("\r", "") + " ");
            }
            out.print(((last) ? "" : ", "));
        }
    }

    public static void printFieldString(PrintStream out, List<String> values, boolean last) {
        if (values == null) {
            out.print("N/A" + ((last) ? "" : ", "));
        } else {
            for (String value : values) {
                out.print(value.replaceAll("\n", "").replaceAll("\r", "") + " ");
            }
            out.print(((last) ? "" : ", "));
        }
    }

    public static void printField(PrintStream out, List<NamedFacebookType> values, boolean last) {
        if (values == null) {
            out.print("N/A" + ((last) ? "" : ", "));
        } else {
            for (NamedFacebookType value : values) {
                out.print(value.getName().replaceAll("\n", "").replaceAll("\r", "") + " ");
            }
            out.print(((last) ? "" : ", "));
        }
    }

    public static void printField(PrintStream out, NamedFacebookType value, boolean last) {
        if (value == null) {
            out.print("N/A" + ((last) ? "" : ", "));
        } else {
            out.print(value.getName().replaceAll("\n", "").replaceAll("\r", "") + ((last) ? "" : ", "));
        }
    }

    public static void printField(PrintStream out, String value, boolean last) {
        if (value == null) {
            out.print("N/A" + ((last) ? "" : ", "));
        } else {
            out.print(value.replaceAll("\n", "").replaceAll("\r", "") + ((last) ? "" : ", "));
        }
    }
}

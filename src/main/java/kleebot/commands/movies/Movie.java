package kleebot.commands.movies;

import net.dv8tion.jda.api.entities.Member;

import java.io.Serializable;

public class Movie implements Serializable {

    public String movieSender;
    public String Description;
    public String URL;

    public Movie(String ms, String d, String u ) {
        movieSender = ms;
        Description = d;
        URL = u;
    }

    public boolean equals(Object o) {
        Movie x = (Movie) o;
        return this.Description.equals(x.Description);
    }
}

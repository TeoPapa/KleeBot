package kleebot.commands.movies;

import kleebot.Klee;
import kleebot.MulComm;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;


import java.io.IOException;

public class AddMoviesCom extends MulComm {
    @Override
    public void Command() {
        if(messageSent.length < 2) {
            EmbedBuilder errorEmbed = new EmbedBuilder();
            errorEmbed.setTitle("❌Couldn't add movie!");
            errorEmbed.setDescription("The format of your command was wrong!");
            errorEmbed.addField("Command", Klee.prefix + "addmovie [Movie/Movie Description]", false);
            errorEmbed.setColor(0xc45200);

            channel.sendMessage(errorEmbed.build()).queue();
            errorEmbed.clear();
            return;
        }

        StringBuilder builder = new StringBuilder();

        for(int i = 1; i < messageSent.length; i++)
            builder.append(messageSent[i] + " ");

        String description = builder.toString();

        String ULR =getMovieURL();
        if(ULR == "URL NOT FOUND")
            return;


       String name;
       if(sender.getNickname() == null)
           name = sender.getUser().getName();
       else
           name = sender.getNickname();

       ShowMoviesComm.Movies.add(new Movie(name, description, ULR));


        ShowMoviesComm.SaveMovies(1);

        EmbedBuilder mbd = new EmbedBuilder();
        mbd.setTitle("✨Added Movie");
        mbd.setDescription( name +", added: '" + description + "' ");
        mbd.setColor(0xeb3434);
        channel.sendMessage(mbd.build()).queue();
        mbd.clear();
    }

    @Override
    public void setName() {
        name = "addmovie";
    }

    @Override
    public void setHelpsName() {
        helpName = "Add Movie";
    }

    @Override
    public void setHelp() {
        help.setDescription("Do you have a favourite movie? A story that you would like to share with Klee? It's fine, you can tell Klee!");
        help.addField("Add Movie", Klee.prefix + "addmovie [Movie name\\Movie description", false);
    }

    @Override
    public void setTheNames() {
        names.add("addmovies");
        names.add("movieadd");
        names.add("moviesadd");
    }

    private String getMovieURL() {
        String y ="";

        StringBuilder builder = new StringBuilder();

        for(int i = 1; i < messageSent.length; i++)
            builder.append("+" + messageSent[i]);

        String wantToSearch = builder.toString();

        try {
            Elements links = Jsoup.connect("https://www.google.com.au/search?q=" + wantToSearch + "+imdb").get()
                    .select("h3.r").select("a");

            String x = links.get(0).attr("href").toString();
            y = x.substring(0, x.lastIndexOf("/"));

        } catch (IOException e) {
        } catch (IndexOutOfBoundsException x) {
            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setTitle("❌ Couldn't find this movie on IMDb");
            mbd.setDescription("Please try again! (If the movie is from a trilogy, try adding the number too)");
            mbd.setColor(0xc45200);
            channel.sendMessage(mbd.build()).queue();

            return "URL NOT FOUND";
        }


        return y;
    }

}

package kleebot.commands.movies;

import kleebot.Klee;
import kleebot.MulComm;
import net.dv8tion.jda.api.EmbedBuilder;

public class RemoveMoviesCom extends MulComm {
    @Override
    public void Command() {
        if(messageSent.length < 2) {
            EmbedBuilder errorEmbed = new EmbedBuilder();
            errorEmbed.setTitle("❌Couldn't remove movie!");
            errorEmbed.setDescription("The format of your command was wrong!");
            errorEmbed.addField("Command (with name)", Klee.prefix + "removemovie [Movie/Movie Description]", false);
            errorEmbed.addField("Command (with ID)", Klee.prefix + "removemovieid [Movie ID]", false);
            errorEmbed.setColor(0xc45200);

            channel.sendMessage(errorEmbed.build()).queue();
            errorEmbed.clear();
            return;
        }

        StringBuilder builder = new StringBuilder();

        for(int i = 1; i < messageSent.length; i++)
            builder.append(messageSent[i] + " ");

        String description = builder.toString();

        boolean found;
        if(messageSent[0].equalsIgnoreCase(Klee.prefix + "removemovie")) {
            found = ShowMoviesComm.SearchMovie(1, new Movie(null, description, null), -1);
        } else {
            found = ShowMoviesComm.SearchMovie(2, null, Integer.parseInt(messageSent[1]));
        }

        if(found) {
            ShowMoviesComm.SaveMovies(2);

            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setTitle("✨Movie Removed✨");
            mbd.setDescription("'" + description + "' removed from the movies");
            mbd.setColor(0xeb3434);
            channel.sendMessage(mbd.build()).queue();
            mbd.clear();
        } else {
            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setTitle("💥Movie Didn't Removed✨");
            mbd.setDescription("'" + description + "' was not found in the movies");
            mbd.setColor(0xc45200);
            channel.sendMessage(mbd.build()).queue();
            mbd.clear();
        }
    }

    @Override
    public void setName() {
        name = "removemovie";
    }

    @Override
    public void setHelpsName() {
        helpName = "Remove Movie";
    }

    @Override
    public void setHelp() {
        help.setDescription("If you recommended a wrong movie to Klee, you can just tell me to not talk about it!");
        help.addField("Remove Movie (by name)", Klee.prefix + "removemovie [movie name/description]", false);
        help.addField("Remove Movie (by ID)", Klee.prefix + "removemovieid [Movie ID number]", false);
    }

    @Override
    public void setTheNames() {
        names.add("removemovieid");
    }
}

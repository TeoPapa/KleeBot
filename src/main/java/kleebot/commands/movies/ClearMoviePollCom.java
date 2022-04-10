package kleebot.commands.movies;

import kleebot.Comm;
import kleebot.Klee;
import kleebot.MulComm;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Date;

public class ClearMoviePollCom extends MulComm {
    @Override
    public void Command() {
        Date currDate = new Date();

        if(currDate.getDay() == 2 || currDate.getDay() == 1 || messageSent[0].equals(Klee.prefix + "c13arm0v13s")) {
            ShowMoviesComm.Movies.removeAll(ShowMoviesComm.Movies);
            ShowMoviesComm.SaveMovies(3);

            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setTitle("✨Cleared all movies!🎇");
            mbd.setDescription("Now you can recommend other movies to Klee, maybe better than the others!😁");
            mbd.setColor(0xeb3434);
            channel.sendMessage(mbd.build()).queue();
            mbd.clear();
        }
        else {
            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setTitle("✨Didn't clear movies🎇");
            mbd.setDescription("You can only clear the movies on Mondays and Tuesdays!");
            mbd.setColor(0xc45200);
            channel.sendMessage(mbd.build()).queue();
            mbd.clear();
        }
    }

    @Override
    public void setName() {
        name = "clearmovies";
    }

    @Override
    public void setHelpsName() {
        helpName = "Clear Movie Poll";
    }

    @Override
    public void setHelp() {
        help.setDescription("If you want to reset the poll with nothing inside, just say it. Just remember you can only do that on Mondays and Tuesdays!\nKlee can do that easily, Tee hee!");
        help.addField("Clear Movies", Klee.prefix + name, false);
    }

    @Override
    public void setTheNames() {
        names.add("c13arm0v13s");
    }
}

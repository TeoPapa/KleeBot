package kleebot.commands.movies;

import kleebot.Klee;
import kleebot.MulComm;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;

public class MoviePollCom extends MulComm {

    private ArrayList<String> emotes;

    public MoviePollCom() {
        super();
        emotes = new ArrayList<>();

        emotes.add("1️⃣");
        emotes.add("2️⃣");
        emotes.add("3️⃣");
        emotes.add("4️⃣");
        emotes.add("5️⃣");
        emotes.add("6️⃣");
        emotes.add("7️⃣");
        emotes.add("8️⃣");
        emotes.add("9️⃣");
        emotes.add("🔟");
    }

    @Override
    public void Command() {
        ArrayList<Movie> Movies = ShowMoviesComm.Movies;
        ArrayList<MoviePollTypes> mbds = new ArrayList<>();
        mbds.add(new MoviePollTypes());
        int j = 0;

        if(Movies.size() > 0) {

            int o = 1;
            mbds.get(j).mbd.setTitle("✨Movies🎇");
            mbds.get(j).mbd.setDescription("Choose the movies you want, by reacting to the number you want!");
            for (int i = 0; i < Movies.size(); i++) {
                if (i % 10 == 0 && i != 0) {
                    mbds.add(new MoviePollTypes());
                    j++;
                    o = 1;
                }
                mbds.get(j).mbd.addField(o + ") " + Movies.get(i).Description, "Recomended by " + Movies.get(i).movieSender, false);
                o++;
                mbds.get(j).emojis++;
            }

            for(int i = 0; i < mbds.size(); i++) {
                mbds.get(i).mbd.setColor(0xeb3434);
                final int z = i;
                channel.sendMessage(mbds.get(i).mbd.build()).queue(message -> {
                    for(int k = 0; k < mbds.get(z).emojis; k++) {
                        message.addReaction(emotes.get(k)).queue();
                    }
                });
                mbds.get(i).mbd.clear();
            }
        } else {
            mbds.get(j).mbd.setTitle("✨ Sadly, there are no recommendations made for Klee, so I can't show you the poll.");
            mbds.get(j).mbd.setDescription("Maybe try adding some and then ask again!");
            mbds.get(j).mbd.setColor(0xc45200);
            channel.sendMessage(mbds.get(j).mbd.build()).queue();
            mbds.get(j).mbd.clear();
        }
    }

    @Override
    public void setName() {
        name = "moviepoll";
    }

    @Override
    public void setHelpsName() {
        helpName = "Movie Poll";
    }

    @Override
    public void setHelp() {
        help.setDescription("When you want to ask your friends which movies they prefer, just say it to Klee and she can ask them!");
        help.addField("Movie Poll", Klee.prefix + name, false);
    }

    @Override
    public void setTheNames() {
        names.add("moviespoll");
        names.add("pollmovie");
        names.add("pollmovies");
    }
}

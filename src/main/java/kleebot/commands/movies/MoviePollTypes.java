package kleebot.commands.movies;

import net.dv8tion.jda.api.EmbedBuilder;

public class MoviePollTypes {
    public EmbedBuilder mbd;
    public int emojis;

    public MoviePollTypes() {
        mbd = new EmbedBuilder();
        emojis = 0;
    }
}

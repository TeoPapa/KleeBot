package kleebot;

import kleebot.commands.movies.ShowMoviesComm;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class C13arComm extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().equalsIgnoreCase(Klee.prefix + " c13arm0v13s") ) {
            ShowMoviesComm.Movies.removeAll(ShowMoviesComm.Movies);
            ShowMoviesComm.SaveMovies(3);

            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setTitle("✨Cleared all movies!🎇");
            mbd.setColor(0xeb3434);
            e.getChannel().sendMessage(mbd.build()).queue();
            mbd.clear();
        }
    }

}

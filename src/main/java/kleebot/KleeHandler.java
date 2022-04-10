package kleebot;

import kleebot.commands.*;
import kleebot.commands.SecretSantaComms.*;
import kleebot.commands.helpcomm.HelpCommand;
import kleebot.commands.helpcomm.HelpItem;
import kleebot.commands.movies.*;
import kleebot.commands.musicCommands.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class KleeHandler {

    private static JDABuilder builder;

    public static void addKleeCommands(JDABuilder bui) {
        builder = bui;
        //addCommand(new );

        //Load the movies that are saved to Movies.ser
        ShowMoviesComm.loadMovies();

        //Main Commands
        builder.addEventListeners(new HelpCommand());
        builder.addEventListeners(new SimpleCommands());
        builder.addEventListeners(new SendPrivateMessageCommand());
        //builder.addEventListeners(new C13arComm());
        builder.addEventListeners(new ListenerAdapter() {
            @Override
            public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
                String[] messageSent = event.getMessage().getContentRaw().split("\\s+");

                if(messageSent[0].equalsIgnoreCase(Klee.prefix + "readme")) {
                    EmbedBuilder mbd = new EmbedBuilder();
                    mbd.setTitle("Klee's Read Me");
                    mbd.setDescription("This command is for the beta version only. In the link bellow, you can see the patch notes of Klee.\n(This message will be deleted in 10 seconds)");
                    mbd.addField("Link:", "https://docs.google.com/document/d/1ytbJ9sh9uxTvUOXi_U_erBdu2DJRb3T3--eMuzXzp0s/edit?usp=sharing", false);
                    mbd.setColor(0xeb3434);

                    event.getMessage().delete().queue();
                    event.getChannel().sendMessage(mbd.build()).queue(message -> {
                        message.delete().queueAfter(10, TimeUnit.SECONDS);
                    });
                }
            }
        });

        //General Commands
        addCommand(new ClearComm());
        addCommand(new GenshinImpactComms());
        addCommand(new PollComm());

        //Song Commands
        addCommand(new PlayComm());
        addCommand(new PauseComm());
        addCommand(new NextComm());
        addCommand(new JumpComm());
        addCommand(new StopComm());
        //addCommand(new RepeatComm());
        addCommand(new NowPlayingComm());
        addCommand(new QueueComm());
        addCommand(new DisconnectComm());
        //addCommand(new QueueLoopComm());

        //Movie Poll Commands
        addCommand(new ShowMoviesComm());
        addCommand(new AddMoviesCom());
        addCommand(new RemoveMoviesCom());
        addCommand(new ClearMoviePollCom());
        addCommand(new MoviePollCom());

        //Secret Santa Commands
        addCommand(new ShowSantasComm());
        addCommand(new AddSantaCom());
        //addCommand(new RemoveSantaComm());
        addCommand(new InitiateSecretSantaComm());
        addCommand(new ResetSecretSanta());
    }

    private static void addCommand(Comm x) {
        HelpCommand.addHelp(new HelpItem(x.helpName, x.name, x.help));
        builder.addEventListeners(x);
    }
}

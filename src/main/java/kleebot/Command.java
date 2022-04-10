package kleebot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class Command extends ListenerAdapter {

    public String name; //The name of the command

    public Member sender;
    public TextChannel channel;
    public Guild messageGuild;
    public String[] messageSent;
    public Message message;

    public Command() {
        setName();
    }

    /*
     * When someone sends a message and the message is the name of the command
     * then the Command will be called
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        messageSent = e.getMessage().getContentRaw().split("\\s+");
        channel = e.getChannel();
        messageGuild = e.getGuild();
        sender = e.getMember();
        message = e.getMessage();



        if(messageSent[0].equalsIgnoreCase(Klee.prefix + name) )
            Command();

    }

    /*
     * Holds all the usages of the command. It gets the current message, the Guild that sent it
     * as well as the channel that the message was sent from
     */
    public abstract void Command();

    /*
     * This method sets the name of the command. Every usage of this name will be called as the command.
     * The command Name must be a single word or multiple ones with no spaces between
     */
    public abstract void setName();

}
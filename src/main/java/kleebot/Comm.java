package kleebot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.nio.channels.Channel;

/*
 * This is a general class for all the commands (excluding the help and the general ones)
 * Extends ListenerAdapter so the command may work
 */

public abstract class Comm extends Command {

    public String helpName; //The name that Help Command will show
    public EmbedBuilder help = new EmbedBuilder(); //What the help of the command will show

    public Comm() {
        super();
        setHelpsName();
        help.setTitle("✨" + helpName + "🎇");
        setHelp();
        help.setColor(0xeb3434);
    }

    /*
     * This command sets the name that will be displayed on the help command
     */
    public abstract void setHelpsName();

    /*
     * This method sets the Embed that will be shown when the help of this command is triggered.
     * The Help has already bean constructed, but there is no Title, description or field set.
     */
    public abstract void setHelp();

    /*
     * A simple class describing this is this (mind that you have to always set the name and help):
     *
     * class Example extends Comm {
     *
     * @Override
     * public void Command(String[] messageSent, Guild messageGuild, TextChannel channel) {
     *      e.channel.sendMessage("This does something").queue();
     * }
     *
     * @Override
     * public void setName() {
     *      this.name = "something";
     * }
     *
     * @Override
     * public void setHelp() {
     *      this.help.addTitle("This is a title");
     *      this.help.addDescription("This is a description");
     * }
     */
}

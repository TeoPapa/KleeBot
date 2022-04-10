package kleebot.commands.helpcomm;

import kleebot.Klee;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

/*
 * This class shows the command that helps the user
 * Holds an ArrayList of items that are the commands with their embeds.
 */

public class HelpCommand extends ListenerAdapter {

    private static ArrayList<HelpItem> hlp = new ArrayList<>(); //The ArrayList that holds the commands

    /*
     * Upon receiving a message if the first argument of the String is help then initiate the command
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] messageSent = e.getMessage().getContentRaw().split("\\s+");

        if(messageSent[0].equalsIgnoreCase(Klee.prefix + "help")) {
            if(messageSent.length < 2) { //If the user has not sent more arguments than help
                EmbedBuilder mbd = new EmbedBuilder();
                mbd.setTitle("✨Help🎇");
                mbd.setDescription("So, you need Klee's help? Tee hee!");
                for(int i = 0; i < hlp.size(); i++)
                    mbd.addField(hlp.get(i).getName(), Klee.prefix + "help " + hlp.get(i).getCommand(), false);

                mbd.setColor(0xeb3434);
                e.getChannel().sendMessage(mbd.build()).queue();

            }
            else {

                int x = hlp.indexOf(new HelpItem(null, messageSent[1], null));

                if(messageSent[1].equalsIgnoreCase("commands")) {
                    EmbedBuilder mbd = new EmbedBuilder();
                    mbd.setTitle("✨Commands✨");
                    mbd.setDescription("You know, Klee loves speaking with others. Why won't we take Dodoko and take a long walk together?");
                    mbd.addField("Hey", Klee.prefix + "hey", false);
                    mbd.addField("Adventure!!", Klee.prefix + "adventure", false);
                    mbd.setColor(0xeb3434);

                    e.getChannel().sendMessage(mbd.build()).queue();
                    return;
                }
                else if(x == -1) {
                    EmbedBuilder mbd = new EmbedBuilder();
                    mbd.setTitle("❌ No command found with this name");
                    mbd.setDescription("Please use " + Klee.prefix + "help [the command that you want]");
                    mbd.setColor(0xc45200);

                    e.getChannel().sendMessage(mbd.build()).queue();
                    return;
                }
                e.getChannel().sendMessage(hlp.get(x).getEmbed().build()).queue();
                hlp.get(x).getEmbed().clear();
            }
        }
    }

    /*
     * A method that is being used to add commands to the ArrayList
     */
    public static void addHelp(HelpItem hi) {
        hlp.add(hi);
    }
}

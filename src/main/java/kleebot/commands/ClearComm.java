package kleebot.commands;

import kleebot.Klee;
import kleebot.MulComm;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearComm extends MulComm {
    @Override
    public void Command() {
        if(messageSent.length < 2) {
            EmbedBuilder errorEmbed = new EmbedBuilder();
            errorEmbed.setTitle("❌ Didn't specify the ammount of messages you want to delete");
            errorEmbed.addField("Command", "" + Klee.prefix + "clear [number between 1 and 100]", false);
            errorEmbed.setColor(0xc45200);

           channel.sendMessage(errorEmbed.build()).queue();
            errorEmbed.clear();
        }
        else {
            try {
                List<Message> messages = channel.getHistory().retrievePast(Integer.parseInt(messageSent[1]) + 1).complete();
                channel.deleteMessages(messages).queue();

                EmbedBuilder msgEmbed = new EmbedBuilder();
                msgEmbed.setTitle("✨ Cleared " + messageSent[1] + " messages!");
                msgEmbed.setColor(0xeb3434);

                channel.sendMessage(msgEmbed.build()).queue( message -> message.delete().queueAfter(5, TimeUnit.SECONDS) );
                msgEmbed.clear();
            }
            catch(IllegalArgumentException ex) {
                EmbedBuilder errorEmbed = new EmbedBuilder();
                errorEmbed.setTitle("❌Couldn't clear these messages!");
                errorEmbed.setDescription("You can't delete more than 100 messages and less than 1. Also you can't delete messages older than 2 weeks.\nPlease try again!");
                errorEmbed.setColor(0xc45200);

                channel.sendMessage(errorEmbed.build()).queue();
                errorEmbed.clear();
            }
        }
    }

    @Override
    public void setName() {
        name = "clear";
    }

    @Override
    public void setHelpsName() {
        helpName = "Clear Messages";
    }

    @Override
    public void setHelp() {
        help.setDescription("Klee can help you clear unwanted messages! Or else, you could say Klee.. ar unwanted messages 🥁");
        help.addField("Clear", Klee.prefix + "clear", false);
    }

    @Override
    public void setTheNames() {
        names.add("kleear");
        names.add("swoopswoop");
    }
}

package kleebot.commands;

import kleebot.Comm;
import kleebot.Klee;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;

public class PollComm extends Comm {

    private ArrayList<String> emotes;

    public PollComm() {
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
        if(messageSent.length < 3) {
            EmbedBuilder errorEmbed = new EmbedBuilder();
            errorEmbed.setTitle("❌Couldn't create poll!");
            errorEmbed.setDescription("The format of your command was wrong!");
            errorEmbed.addField("Command", Klee.prefix + "poll [number 1 to 10] [Poll description]", false);
            errorEmbed.setColor(0xc45200);

            channel.sendMessage(errorEmbed.build()).queue();
            errorEmbed.clear();
            return;
        }

        int type = Integer.parseInt(messageSent[1]);

        if(type < 1 || type > 10) {
            EmbedBuilder errorEmbed = new EmbedBuilder();
            errorEmbed.setTitle("❌Couldn't create poll");
            errorEmbed.setDescription("You can't create a poll with a number less than 1 and more than 10");
            errorEmbed.setColor(0xc45200);

            channel.sendMessage(errorEmbed.build()).queue();
            errorEmbed.clear();
            return;
        }

        StringBuilder builder = new StringBuilder();

        for(int i = 2; i < messageSent.length; i++)
            builder.append(messageSent[i] + " ");

        String newMessage = builder.toString();

        message.delete().queue();
        EmbedBuilder mbd = new EmbedBuilder();
        mbd.setTitle("✨Poll✨");
        mbd.setDescription(newMessage);
        mbd.setColor(0xeb3434);

        if(type == 1) {
            channel.sendMessage(mbd.build()).queue(message -> {
                message.addReaction("👍").queue();
                message.addReaction("👎").queue();
            });
        }
        else {
            channel.sendMessage(mbd.build()).queue(message -> {
                for (int i = 0; i < type; i++)
                    message.addReaction(emotes.get(i)).queue();
            });
        }
    }

    @Override
    public void setName() {
        name = "poll";
    }

    @Override
    public void setHelpsName() {
        helpName = "Poll";
    }

    @Override
    public void setHelp() {
        help.setDescription("You know, Klee noticed how Jean does her polls and helped in some. I can help you too if you want!");
        help.addField("Poll", Klee.prefix + "poll [A number from 1-10] [The description of the poll]", false);
        help.addField("Poll Numbers", "If the number 1 is sent, I'll just add a like and dislike poll. From numbers two and up, I'll add the numbers you want me to", false);
    }
}

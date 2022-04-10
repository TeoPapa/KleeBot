package kleebot.commands;

import kleebot.Command;
import kleebot.Klee;
import kleebot.commands.SecretSantaComms.SecretSantaHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;

public class SendPrivateMessageCommand extends Command {
    @Override
    public void Command() {
        if(messageSent.length < 2) {
            EmbedBuilder errorEmbed = new EmbedBuilder();
            errorEmbed.setTitle("❌ I need a message to sent");
            errorEmbed.addField("Command", "" + Klee.prefix + "s3ndpr1vatem3ss@ge [Message to send]", false);
            errorEmbed.setColor(0xc45200);

            channel.sendMessage(errorEmbed.build()).queue();
            errorEmbed.clear();

            return;
        }

        StringBuilder builder = new StringBuilder();

        for(int i = 1; i < messageSent.length; i++)
            builder.append(messageSent[i] + " ");

        String MessageToSend = builder.toString();


        ArrayList<Member> Users = new ArrayList<Member>(messageGuild.getMembers());

        for(Member m : Users) {
            if(!m.getUser().isBot()) {
                m.getUser().openPrivateChannel().queue(privateChannel -> {
                    EmbedBuilder mbd = new EmbedBuilder();
                    mbd.setTitle("✨ Announcement ✨");
                    mbd.setDescription(MessageToSend);
                    mbd.setColor(0xeb3434);
                    privateChannel.sendMessage(mbd.build()).queue();
                    mbd.clear();
                });
            }
        }
    }

    @Override
    public void setName() {
        name = "s3ndpr1vatem3ss@ge";
    }
}

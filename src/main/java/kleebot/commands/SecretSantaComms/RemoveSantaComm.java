package kleebot.commands.SecretSantaComms;

import kleebot.Comm;
import kleebot.Klee;
import net.dv8tion.jda.api.EmbedBuilder;

public class RemoveSantaComm extends Comm {
    @Override
    public void Command() {
        for(int i = 0; i < SecretSantaHandler.Participants.size(); i++) {
            if(SecretSantaHandler.Participants.get(i).toString().equals(sender.getEffectiveName())) {
                SecretSantaHandler.Participants.remove(i);
                EmbedBuilder mbd = new EmbedBuilder();
                mbd.setTitle("✨ You got removed succesfully! 🎇");
                mbd.setDescription("Though... Klee is kinda sad...");
                mbd.setColor(0xeb3434);
                channel.sendMessage(mbd.build()).queue();
                mbd.clear();

                return;
            }
        }

        EmbedBuilder Embd = new EmbedBuilder();
        Embd.setTitle("❌ Couldn't do that 🎇");
        Embd.setDescription("You are not enrolled for Secret Santa!");
        Embd.setColor(0xc45200);
        channel.sendMessage(Embd.build()).queue();
        Embd.clear();
    }

    @Override
    public void setName() {
        name = "removesanta";
    }

    @Override
    public void setHelpsName() {
        helpName = "Remove Santa";
    }

    @Override
    public void setHelp() {
        help.setDescription("If you changed your mind and you don't want to participate as a secret santa, it's fine, Klee understands.. 😢");
        help.addField("Remove Santa", Klee.prefix + "removesanta", false);
    }
}

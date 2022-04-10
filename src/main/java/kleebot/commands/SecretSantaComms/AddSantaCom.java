package kleebot.commands.SecretSantaComms;

import kleebot.Comm;
import kleebot.Klee;
import net.dv8tion.jda.api.EmbedBuilder;

public class AddSantaCom extends Comm {
    @Override
    public void Command() {
        if(messageSent.length > 1) {
            if(SecretSantaHandler.NormalParticipants.contains(new NormalPerson(messageSent[1]))) {
                EmbedBuilder mbd = new EmbedBuilder();
                mbd.setTitle("✨ Couldn't do that 🎇");
                mbd.setDescription("There's already a user with this name");
                mbd.setColor(0xeb3434);
                channel.sendMessage(mbd.build()).queue();
                mbd.clear();
                return;
            }

            SecretSantaHandler.addParticipant(new NormalPerson(messageSent[1]), null);

            EmbedBuilder embd = new EmbedBuilder();
            embd.setTitle("✨ Santa added successfully 🎇");
            embd.setColor(0xeb3434);
            channel.sendMessage(embd.build()).queue();
            embd.clear();

            return;
        }
            if( SecretSantaHandler.DiscordParticipants.contains(new DiscordPerson(sender))) {
                EmbedBuilder mbd = new EmbedBuilder();
                mbd.setTitle("✨ Couldn't do that 🎇");
                mbd.setDescription("You have already enrolled for Secret Santa!");
                mbd.setColor(0xeb3434);
                channel.sendMessage(mbd.build()).queue();
                mbd.clear();
                return;
            }

        SecretSantaHandler.addParticipant(null, new DiscordPerson(sender));

        EmbedBuilder embd = new EmbedBuilder();
        embd.setTitle("✨ Santa added successfully 🎇");
        embd.setColor(0xeb3434);
        channel.sendMessage(embd.build()).queue();
        embd.clear();
    }

    @Override
    public void setName() {
        name = "enrollsanta";
    }

    public void setHelpsName() {
        helpName = "Be A Secret Santa!";
    }

    @Override
    public void setHelp() {
        help.setDescription("Klee loves christmas! More than christmas she loves the gifts she receives on that day. So, I would like to have as many friends as I can participate with me ! 😁");
        help.addField("Enroll Santa", Klee.prefix + "enrollsanta", false);
    }
}

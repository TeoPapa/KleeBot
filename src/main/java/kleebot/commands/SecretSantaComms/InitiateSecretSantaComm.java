package kleebot.commands.SecretSantaComms;

import kleebot.Comm;
import kleebot.Klee;
import net.dv8tion.jda.api.EmbedBuilder;

public class InitiateSecretSantaComm extends Comm {

    @Override
    public void Command() {
        if(SecretSantaHandler.Participants.size() < 2) {
            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setColor(0xc45200);
            mbd.setTitle("❌ Couldn't initialize Secret Santas!");
            mbd.setDescription("There have to be at least two participants. Isn't it lonely when you give gifts to yourself?");
            channel.sendMessage(mbd.build()).queue();
            mbd.clear();
        }

        SecretSantaHandler.Initiate();

        for(DiscordPerson i : SecretSantaHandler.DiscordParticipants) {
            i.getUser().getUser().openPrivateChannel().queue(privateChannel -> {
                EmbedBuilder mbd = new EmbedBuilder();
                mbd.setColor(0xeb3434);
                mbd.setTitle("✨And your Secret Receiver is:");
                mbd.setDescription(i.getReceiver());
                privateChannel.sendMessage(mbd.build()).queue();
                mbd.clear();
            });
        }

        for(NormalPerson i : SecretSantaHandler.NormalParticipants)
            System.out.println(i.getUser() + " has to give a gift to: " + SecretSantaHandler.Participants.get(i.getNumber()).toString());

        SecretSantaHandler.Restart();
    }

    @Override
    public void setName() {
        name = "initiatesecretsanta";
    }

    @Override
    public void setHelpsName() {
        helpName = "Initiate Secret Santa";
    }

    @Override
    public void setHelp() {
        help.setDescription("This is the best momment! Klee will send everyone that participated a private message and inform them of their receiver! Let the fun beggin!");
        help.addField("Initiate Secret Santa", Klee.prefix + "initiatesecretsanta", false);
    }
}

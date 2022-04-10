package kleebot.commands.musicCommands;

import kleebot.Klee;
import kleebot.MulComm;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class DisconnectComm extends MulComm {
    @Override
    public void Command() {
        final Member self = messageGuild.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel())
            return;

        GuildVoiceState memberVS = sender.getVoiceState();

        if (!memberVS.inVoiceChannel()) {
            EmbedBuilder msg = new EmbedBuilder();

            msg.setTitle("💥Sorry, couldn't do that");
            msg.setDescription("You have to be connected to a voice channel");
            msg.setColor(0xc45200);
            channel.sendMessage(msg.build()).queue();
            msg.clear();

            return;
        }


        final AudioManager audMan = messageGuild.getAudioManager();

        final VoiceChannel memChan = memberVS.getChannel();

        if (!selfVoiceState.getChannel().equals(memChan))
        {
            EmbedBuilder msg = new EmbedBuilder();

            msg.setTitle("💥Sorry, couldn't that");
            msg.setDescription("You have to be connected to the same voice channel as me for this to work");
            msg.setColor(0xc45200);
            channel.sendMessage(msg.build()).queue();
            msg.clear();

            return;
        }

        audMan.closeAudioConnection();

        if(messageSent[0].equalsIgnoreCase(Klee.prefix + "grounded")) {
            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setTitle("What did Klee do wrong... I guess... Time to go... Byee 🙁");
            mbd.setColor(0xeb3434);
            channel.sendMessage(mbd.build()).queue();
            return;
        }
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("✨Bye, byee!✨");
        msg.setColor(0xeb3434);
        channel.sendMessage(msg.build()).queue();
        msg.clear();

        PlayComm.x = true;
    }

    @Override
    public void setName() {
        name = "disconnect";
    }

    @Override
    public void setHelpsName() {
        helpName = "Disconnect";
    }

    @Override
    public void setHelp() {
        help.setDescription("When you want Klee to leave, you can just say so.");
        help.addField("Disconnect", Klee.prefix + "disconnect", false);
    }

    @Override
    public void setTheNames() {
        names.add("d");
        names.add("grounded");
    }
}

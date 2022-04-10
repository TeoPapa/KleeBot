package kleebot.commands.musicCommands;

import kleebot.Klee;
import kleebot.MulComm;
import kleebot.commands.musicCommands.lavaplayer.GuildMusicManager;
import kleebot.commands.musicCommands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;

public class QueueLoopComm extends MulComm {
    @Override
    public void Command() {
        final Member self = messageGuild.getSelfMember();
        final GuildVoiceState selfVoicestate = self.getVoiceState();

        if(!selfVoicestate.inVoiceChannel()) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ Klee is not currently in a voice channel");
            msg.setDescription("I have to be in a voice channel to repeat song");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        final GuildVoiceState memberVoiceState = sender.getVoiceState();

        if(!memberVoiceState.getChannel().equals(selfVoicestate.getChannel())) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ I am not in the same voice channel as you");
            msg.setDescription("Klee has to be in the same channel as Traveler in order for me to repeat this song");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(messageGuild);

        musicManager.scheduler.queueLoop = !musicManager.scheduler.queueLoop;

        String playing;
        if(musicManager.scheduler.queueLoop)
            playing = "On";
        else
            playing = "Off";

        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("✨ Queue Loop!");
        msg.setDescription("Queue looping is now " + playing);
        msg.setColor(0xeb3434);
        channel.sendMessage(msg.build()).queue();
        msg.clear();
    }

    @Override
    public void setName() {
        name = "loopq";
    }

    @Override
    public void setHelpsName() {
        helpName = "Queue Loop";
    }

    @Override
    public void setHelp() {
        help.setDescription("If you liked all of Klee's songs, don't be afraid to ask me to play again. It is my pleasure");
        help.addField("Loop Queue", Klee.prefix + "loopq", false);
    }

    @Override
    public void setTheNames() {
        names.add("qloop");
        names.add("loopqueue");
    }
}

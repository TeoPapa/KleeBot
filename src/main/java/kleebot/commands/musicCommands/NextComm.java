package kleebot.commands.musicCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import kleebot.Klee;
import kleebot.MulComm;
import kleebot.commands.musicCommands.lavaplayer.GuildMusicManager;
import kleebot.commands.musicCommands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;

public class NextComm extends MulComm {
    @Override
    public void Command() {
        final Member self = messageGuild.getSelfMember();
        final GuildVoiceState selfVoicestate = self.getVoiceState();

        if(!selfVoicestate.inVoiceChannel()) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ Klee is not currently in a voice channel");
            msg.setDescription("I have to be in a voice channel to go to the next song");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        final GuildVoiceState memberVoiceState = sender.getVoiceState();

        if(!memberVoiceState.getChannel().equals(selfVoicestate.getChannel())) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ I am not in the same voice channel as you");
            msg.setDescription("Klee has to be in the same channel as Traveler in order for me to skip this song");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(messageGuild);
        AudioPlayer audioPlayer = musicManager.audioPlayer;

        if(audioPlayer.getPlayingTrack() == null) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ There are not tracks currently playing");
            msg.setDescription("There has to be a song for me to skip");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        musicManager.scheduler.nextTrack();

        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("✨Going to the next song!");
        msg.setColor(0xeb3434);

        channel.sendMessage(msg.build()).queue();
        msg.clear();
    }

    @Override
    public void setName() {
        name = "next";
    }

    @Override
    public void setHelpsName() {
        helpName = "Next Song";
    }

    @Override
    public void setHelp() {
        help.setDescription("If you want Klee to play the next song, just ask! I can play maany songs!");
        help.addField("Next", Klee.prefix + "next", false);
    }

    @Override
    public void setTheNames() {
        names.add("n");
    }
}

package kleebot.commands.musicCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kleebot.Comm;
import kleebot.Klee;
import kleebot.commands.musicCommands.lavaplayer.GuildMusicManager;
import kleebot.commands.musicCommands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;

public class NowPlayingComm extends Comm {
    @Override
    public void Command() {
        final Member self = messageGuild.getSelfMember();
        final GuildVoiceState selfVoicestate = self.getVoiceState();

        if(!selfVoicestate.inVoiceChannel()) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ Klee is not currently in a voice channel");
            msg.setDescription("I have to be in a voice channel to go to show the currently playing song");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        final GuildVoiceState memberVoiceState = sender.getVoiceState();

        if(!memberVoiceState.getChannel().equals(selfVoicestate.getChannel())) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ I am not in the same voice channel as you");
            msg.setDescription("Klee has to be in the same channel as Traveler in order for me to show the currently playing song");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(messageGuild);
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if(track == null) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ There are not tracks currently playing");
            msg.setDescription("There has to be a song for me to show it");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("✨Now playing:");
        msg.addField("Song name", track.getInfo().title, false);
        msg.addField("By", track.getInfo().author, false);
        msg.setColor(0xeb3434);

        channel.sendMessage(msg.build()).queue();
        msg.clear();
    }

    @Override
    public void setName() {
        name = "playing";
    }

    @Override
    public void setHelpsName() {
        helpName = "Now Playing";
    }

    @Override
    public void setHelp() {
        help.setDescription("Klee can tell you what song she plays. And then, if you want I can teach it to you.\nOnce I tried to teach it to Dodoko, but he didn't seem to like it. That was the first time Jean got me punished.");
        help.addField("Playing", Klee.prefix + "playing", false);
    }
}

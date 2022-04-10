package kleebot.commands.musicCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kleebot.Klee;
import kleebot.MulComm;
import kleebot.commands.musicCommands.lavaplayer.GuildMusicManager;
import kleebot.commands.musicCommands.lavaplayer.PlayerManager;
import kleebot.commands.musicCommands.lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;

public class JumpComm extends MulComm {
    @Override
    public void Command() {
        if(messageSent.length < 2) {
            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setTitle("💥Couldn't do that");
            mbd.setDescription("You have to give Klee a position or a name for this to work");
            mbd.setColor(0xc45200);
            channel.sendMessage(mbd.build()).queue();
            mbd.clear();
            return;
        }

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

        int x;

        if(messageSent[0].equalsIgnoreCase(Klee.prefix + name))
            x = jumpToSongViaName(messageSent[1]);
        else
            x = jumpToSongViaID(Integer.parseInt(messageSent[1]) );

        if(x == -1) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌Couldn't jump there");
            msg.setDescription("Song could not be found in the list, please try again");
            msg.setColor(0xc45200);
            channel.sendMessage(msg.build()).queue();
            msg.clear();

            return;
        }

        musicManager.scheduler.jumpTo(x);

        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("✨Jumping to the song!");
        msg.setColor(0xeb3434);

        channel.sendMessage(msg.build()).queue();
        msg.clear();
    }

    private int jumpToSongViaID(int y) {
        if(PlayerManager.getInstance().getMusicManager(messageGuild).scheduler.queue.size() < y)
            return -1;

        y -= 1;

        return y;
    }

    private int jumpToSongViaName(String sName) {
        ArrayList<AudioTrack> cTracks = PlayerManager.getInstance().getMusicManager(messageGuild).scheduler.queue;
        for(int i = 0; i < cTracks.size(); i++) {
            String[] tracksName = cTracks.get(i).getInfo().title.split("\\s+");

            for(int j = 0; j < tracksName.length; j++) {
                if(tracksName[j].equalsIgnoreCase(sName)) {
                    return i;
                }
            }
        }


        return -1;
    }

    @Override
    public void setName() {
        name = "jump";
    }

    @Override
    public void setHelpsName() {
        helpName = "Jump to Song";
    }

    @Override
    public void setHelp() {
        help.setDescription("If you wanna go quicker to another song, just tell me the name of it or it's position and Klee is gonna do it!");
        help.addField("Jump", Klee.prefix + name + " [song name]", false);
        help.addField("Jump (with position)", Klee.prefix + "jumppos [song position in queue]", false);
    }

    @Override
    public void setTheNames() {
        names.add("jumppos");
    }
}

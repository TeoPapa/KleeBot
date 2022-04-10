package kleebot.commands.musicCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kleebot.Klee;
import kleebot.MulComm;
import kleebot.commands.musicCommands.lavaplayer.GuildMusicManager;
import kleebot.commands.musicCommands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueComm extends MulComm {
    @Override
    public void Command() {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(messageGuild);
        final ArrayList<AudioTrack> queue = musicManager.scheduler.queue;

        final Member self = messageGuild.getSelfMember();
        final GuildVoiceState selfVoicestate = self.getVoiceState();

        if(!selfVoicestate.inVoiceChannel()) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ Klee is not currently in a voice channel");
            msg.setDescription("I have to be in a voice channel to go to show the queue");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        final GuildVoiceState memberVoiceState = sender.getVoiceState();

        if(!memberVoiceState.getChannel().equals(selfVoicestate.getChannel())) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ I am not in the same voice channel as you");
            msg.setDescription("Klee has to be in the same channel as Traveler in order for me to show you the current queue");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if(audioPlayer.getPlayingTrack() == null) {
            EmbedBuilder msg = new EmbedBuilder();
            msg.setTitle("❌ There are not tracks currently playing");
            msg.setDescription("There has to be a song for me to show it");
            msg.setColor(0xc45200);

            channel.sendMessage(msg.build()).queue();
            msg.clear();
            return;
        }

        ArrayList<EmbedBuilder> mbeds = new ArrayList<>();

        mbeds.add(new EmbedBuilder());
        mbeds.get(0).setTitle("✨Current Queue✨");
        mbeds.get(0).addField("Song " + 1 + ":", "'" + audioPlayer.getPlayingTrack().getInfo().title + "' by " + audioPlayer.getPlayingTrack().getInfo().author, false);
        int size = queue.size();
        int j = 0;
        for(int i = 1; i < size; i++) {
            if(i%25 == 0) {
                mbeds.add(new EmbedBuilder());
                j++;
            }
            mbeds.get(j).addField("Song " + (i + 1) + ":", "'" + queue.get(i).getInfo().title + "' by " + queue.get(i).getInfo().author, false);
        }

        for(int i = 0; i < mbeds.size(); i++) {
            mbeds.get(i).setColor(0xeb3434);
            channel.sendMessage(mbeds.get(i).build()).queue();
            mbeds.get(i).clear();
        }
    }

    @Override
    public void setName() {
        name = "queue";
    }

    @Override
    public void setHelpsName() {
        helpName = "Song Queue";
    }

    @Override
    public void setHelp() {
        help.setDescription("I can display the queue for you!! Just say it!");
        help.addField("Queue", Klee.prefix + name, false);
    }

    @Override
    public void setTheNames() {
        names.add("q");
    }
}

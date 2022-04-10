package kleebot.commands.musicCommands.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    public final AudioPlayer player;
    public final ArrayList<AudioTrack> queue;
    public int trackIndex;
    public boolean repeating = false;
    public boolean queueLoop = false;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new ArrayList<>();
        this.trackIndex = 0;
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.add(track);
        }
    }

    public void nextTrack() {
        this.player.startTrack(this.queue.get(trackIndex), false);
    }

    public void jumpTo(int x) {
        trackIndex = x;
        this.player.startTrack(this.queue.get(trackIndex), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            if(this.repeating) {
                this.player.startTrack(queue.get(trackIndex), false);
                return;
            }
            nextTrack();
            return;
        }
    }
}
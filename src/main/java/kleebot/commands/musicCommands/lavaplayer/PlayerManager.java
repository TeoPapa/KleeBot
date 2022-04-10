package kleebot.commands.musicCommands.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());


        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {

                if(!track.getInfo().title.equals("Tatata - Klee") && !track.getInfo().title.equals("FBI OPEN UP MEME FULL")) {

                    EmbedBuilder msg = new EmbedBuilder();
                    msg.setTitle("✨Song added to queue✨");
                    msg.addField("Title", track.getInfo().title, false);
                    msg.addField("By", track.getInfo().author, false);
                    msg.setColor(0xeb3434);

                    channel.sendMessage(msg.build()).queue();
                    msg.clear();
                }

                musicManager.scheduler.queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();
                AudioTrack track = tracks.get(0);

                musicManager.scheduler.queue(track);

                EmbedBuilder msg = new EmbedBuilder();
                msg.setTitle("✨Song added to queue✨");
                msg.addField("Title", track.getInfo().title, false);
                msg.addField("By", track.getInfo().author, false);
                msg.setColor(0xeb3434);

                channel.sendMessage(msg.build()).queue();
                msg.clear();
            }

            @Override
            public void noMatches() {
                EmbedBuilder msg = new EmbedBuilder();
                msg.setTitle("💥Sorry, couldn't play this");
                msg.setDescription("This song didn't match with anything!");
                msg.setColor(0xc45200);
                channel.sendMessage(msg.build()).queue();
                msg.clear();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                EmbedBuilder msg = new EmbedBuilder();
                msg.setTitle("💥Sorry, couldn't play this");
                msg.setDescription("Couldn't load the song!");
                msg.setColor(0xc45200);
                channel.sendMessage(msg.build()).queue();
                msg.clear();
            }
        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

}
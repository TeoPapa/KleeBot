package kleebot.commands.musicCommands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import kleebot.Klee;
import kleebot.MulComm;
import kleebot.commands.musicCommands.lavaplayer.GuildMusicManager;
import kleebot.commands.musicCommands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URI;
import java.net.URISyntaxException;

public class PlayComm extends MulComm {

    public static boolean x = true;

    @Override
    public void Command() {
        final Member self = messageGuild.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        GuildVoiceState memberVS = sender.getVoiceState();

        if (!memberVS.inVoiceChannel()) {
            EmbedBuilder msg = new EmbedBuilder();

            msg.setTitle("💥Sorry, couldn't connect to the voice channel");
            msg.setDescription("You have to be connected to a voice channel in order for me to connect to it");
            msg.setColor(0xc45200);
            channel.sendMessage(msg.build()).queue();
            msg.clear();

            return;
        }

        if (!memberVS.getChannel().equals(selfVoiceState.getChannel()) && selfVoiceState.inVoiceChannel()) {
            EmbedBuilder msg = new EmbedBuilder();

            msg.setTitle("💥Sorry, couldn't connect to the voice channel");
            msg.setDescription("I am already connected to another voice channel, please try again later");
            msg.setColor(0xc45200);
            channel.sendMessage(msg.build()).queue();
            msg.clear();

            return;
        }


        final AudioManager audMan = messageGuild.getAudioManager();

        final VoiceChannel memChan = memberVS.getChannel();

        if (self.hasPermission(memChan, Permission.VOICE_CONNECT)) {

            audMan.openAudioConnection(memChan);

            boolean temp = x;
            if (x) {
                x = false;
                EmbedBuilder msg = new EmbedBuilder();
                msg.setTitle("✨Coming!✨");
                msg.setColor(0xeb3434);

                channel.sendMessage(msg.build()).queue();
                msg.clear();
            }

            if (messageSent.length < 2 && temp)
                PlayerManager.getInstance().loadAndPlay(channel, "https://youtu.be/mRCJKQgqCkw");
            else if (messageSent.length < 2) {
                final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(messageGuild);
                AudioPlayer audioPlayer = musicManager.audioPlayer;

                if (audioPlayer.getPlayingTrack() == null) {
                    EmbedBuilder msg = new EmbedBuilder();
                    msg.setTitle("❌ There are not tracks currently in pause");
                    msg.setDescription("There has to be a paused song for me to play");
                    msg.setColor(0xc45200);

                    channel.sendMessage(msg.build()).queue();
                    msg.clear();
                    return;
                }
                audioPlayer.setPaused(false);
                PauseComm.playsMusic = true;
                //PlayerManager.getInstance().loadAndPlay(channel, "https://youtu.be/K_dpkEjR2sU");
            } else {
                x = false;
                StringBuilder builder = new StringBuilder();

                for (int i = 1; i < messageSent.length; i++)
                    builder.append(messageSent[i] + " ");

                String link = builder.toString();

                if (!isUrl(link)) {
                    link = "ytsearch:" + link;
                }

                PlayerManager.getInstance().loadAndPlay(channel, link);
            }
        }
    }

    @Override
    public void setName() {
        this.name = "play";
    }

    @Override
    public void setHelpsName() {
        helpName = "Play Song";
    }


    @Override
    public void setHelp() {
        help.setDescription("You know, Klee knows a lots of instruments. I play harp better, but if you want, I know a song or two");
        help.addField("Play a link", Klee.prefix + "play [song URL]", false);
        help.addField("Play a song", Klee.prefix +"play [song name]", false);
    }

    private boolean isUrl(String link) {
        try {
            new URI(link);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override
    public void setTheNames() {
        names.add("p");
    }
}

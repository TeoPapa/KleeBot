package kleebot.commands.musicCommands;

import kleebot.Comm;
import kleebot.Klee;
import kleebot.commands.musicCommands.lavaplayer.GuildMusicManager;
import kleebot.commands.musicCommands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;

public class PauseComm extends Comm {
    public static boolean playsMusic;

    @Override
    public void Command() {
        final Member self = messageGuild.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        GuildVoiceState memberVS = sender.getVoiceState();

        if (!memberVS.inVoiceChannel()) {
            EmbedBuilder msg = new EmbedBuilder();

            msg.setTitle("💥Sorry, Klee couldn't do that!");
            msg.setDescription("You have to be connected to a voice channel in order for me to connect to it");
            msg.setColor(0xc45200);
            channel.sendMessage(msg.build()).queue();
            msg.clear();

            return;
        }

        if (!memberVS.getChannel().equals(selfVoiceState.getChannel())) {
            EmbedBuilder msg = new EmbedBuilder();

            msg.setTitle("💥Sorry, couldn't stop that to the voice channel");
            msg.setDescription("I am already connected to another voice channel, please try again later");
            msg.setColor(0xc45200);
            channel.sendMessage(msg.build()).queue();
            msg.clear();

            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(messageGuild);

        playsMusic = !playsMusic;
        musicManager.scheduler.player.setPaused(playsMusic);

        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("✨ Track paused!");
        msg.setDescription("The track has been paused!");
        msg.setColor(0xeb3434);
        channel.sendMessage(msg.build()).queue();
        msg.clear();
    }

    @Override
    public void setName() {
        this.name = "pause";
    }

    @Override
    public void setHelpsName() {
        helpName = "Pause Song";
    }

    @Override
    public void setHelp() {
        help.setDescription("To pause a song, I must already be playing a song. Tell me when you want me to pause using this:");
        help.addField("Pause", Klee.prefix + "pause", false);
    }
}

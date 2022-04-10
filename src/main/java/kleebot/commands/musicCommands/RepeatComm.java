package kleebot.commands.musicCommands;

import kleebot.Comm;
import kleebot.Klee;
import kleebot.commands.musicCommands.lavaplayer.GuildMusicManager;
import kleebot.commands.musicCommands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;

public class RepeatComm extends Comm {
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

        musicManager.scheduler.repeating = !musicManager.scheduler.repeating;

        String playing;
        if(musicManager.scheduler.repeating)
            playing = "On";
        else
            playing = "Off";

        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("✨ Repeat!");
        msg.setDescription("Repeat is now " + playing);
        msg.setColor(0xeb3434);
        channel.sendMessage(msg.build()).queue();
        msg.clear();
    }

    @Override
    public void setName() {
        this.name = "loop";
    }

    @Override
    public void setHelpsName() {
        helpName = "Song Loop";
    }

    @Override
    public void setHelp() {
        help.setDescription("If you want me to play again a song, just ask for it! Klee would be happy to do it!");
        help.addField("Song Loop", Klee.prefix + "loop", false);
    }
}

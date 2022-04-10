package kleebot.commands;

import kleebot.Klee;
import kleebot.commands.musicCommands.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class SimpleCommands extends ListenerAdapter{

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] messageSent = e.getMessage().getContentRaw().split("\\s+");

        if(messageSent[0].equalsIgnoreCase(Klee.prefix + "hey"))
            e.getChannel().sendMessage("Heyy!✨ I'm Klee, how are you? I love to explore the world and make new friends. Wanna go to an adventure together?😁").queue();

        if(messageSent[0].equalsIgnoreCase(Klee.prefix + "adventure"))
            e.getChannel().sendMessage("Wait, let me bring Dodoko first! Don't tell Jean!").queue();


        if(messageSent[0].equalsIgnoreCase(Klee.prefix + "fuckyou"))
            e.getChannel().sendMessage("N O,  F U C K   Y O U U!").queue();

        if(messageSent[0].equalsIgnoreCase(Klee.prefix + "I") && messageSent[1].equalsIgnoreCase("love") && messageSent[2].equalsIgnoreCase("you")) {
            final Member self = e.getGuild().getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();

            GuildVoiceState memberVS = e.getMember().getVoiceState();

            if (!memberVS.inVoiceChannel()) {
                return;
            }

            if (!memberVS.getChannel().equals(selfVoiceState.getChannel()) && selfVoiceState.inVoiceChannel()) {
                return;
            }


            final AudioManager audMan = e.getGuild().getAudioManager();

            final VoiceChannel memChan = memberVS.getChannel();

            if (self.hasPermission(memChan, Permission.VOICE_CONNECT)) {

                EmbedBuilder mbd = new EmbedBuilder();

                mbd.setTitle("FBI OPEN UP");
                mbd.setColor(0xedd46f);
                e.getChannel().sendMessage(mbd.build()).queue();
                mbd.clear();

                audMan.openAudioConnection(memChan);

                PlayerManager.getInstance().loadAndPlay(e.getChannel(), "https://youtu.be/4wX2xBOuzRg");
            }
        }

        if(messageSent[0].equalsIgnoreCase(Klee.prefix + "what") && messageSent[1].equalsIgnoreCase("a") && messageSent[2].equalsIgnoreCase("save"))
            e.getChannel().sendMessage("What a save!\nWhat a save!\nWhat a save!\nWhat a save!\nWhat a save!\nChat disabled for 4 seconds.\nChat disabled for 4 seconds.\nChat disabled for 3 seconds.\nChat disabled for 2 seconds.\nChat disabled for 2 seconds.\nChat disabled for 1 seconds.\nWhat a save!\nChat disabled for 4 seconds.").queue();
    }
}

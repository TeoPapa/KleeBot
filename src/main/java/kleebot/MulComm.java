package kleebot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;

public abstract class MulComm extends Comm{

    public ArrayList<String> names;

    public MulComm() {
        super();
        names = new ArrayList<String>();
        setTheNames();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        messageSent = e.getMessage().getContentRaw().split("\\s+");
        channel = e.getChannel();
        messageGuild = e.getGuild();
        sender = e.getMember();

        boolean temp = false;
        int i = 0;
       while( i < names.size() && !temp) {
            if(messageSent[0].equalsIgnoreCase(Klee.prefix + names.get(i)))
                temp = true;

            i++;
        }

       if(temp || messageSent[0].equalsIgnoreCase(Klee.prefix + name))
           Command();
    }

    public abstract void setTheNames();
}

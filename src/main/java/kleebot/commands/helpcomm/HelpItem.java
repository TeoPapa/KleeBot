package kleebot.commands.helpcomm;

import net.dv8tion.jda.api.EmbedBuilder;

public class HelpItem {

    private String name;
    private String command;
    private EmbedBuilder embed;

    public HelpItem(String n, String c, EmbedBuilder e) {
        name = n;
        command = c;
        embed = e;
    }

    public boolean equals(Object o) {
        HelpItem x = (HelpItem) o;
        return this.command.equals(x.command);
    }

    public EmbedBuilder getEmbed() {
        return embed;
    }

    public String getName() {
        return name;
    }

    public String getCommand() { return command; }
}

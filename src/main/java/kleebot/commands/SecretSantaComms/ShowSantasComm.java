package kleebot.commands.SecretSantaComms;

import kleebot.Comm;
import kleebot.Klee;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;

public class ShowSantasComm extends Comm {
    @Override
    public void Command() {
        ArrayList<EmbedBuilder> mbeds = new ArrayList<EmbedBuilder>();
        int j = 0;

        mbeds.add(new EmbedBuilder());
        mbeds.get(j).setColor(0xeb3434);

        if(SecretSantaHandler.Participants.size() == 0) {
            mbeds.get(j).setTitle("❌ There are no Santas enrolled! 🎇");
            mbeds.get(j).setDescription("You can add Santas with the addsanta command!");
            mbeds.get(j).addField("Add Santa", Klee.prefix + "addsanta", false);
            mbeds.get(j).setColor(0xc45200);
            channel.sendMessage(mbeds.get(0).build()).queue();
            mbeds.get(j).clear();
            return;
        }


        int z = 1;
        mbeds.get(j).setTitle("🎅 Secret Santa List 🎅");
        mbeds.get(j).setDescription("Here are the Santas that are participating:");
        for (int i = 0; i < SecretSantaHandler.Participants.size(); i++) {
            if (i % 25 == 0 && i != 0) {
                mbeds.add(new EmbedBuilder());
                j++;
                z = 1;
            }
            String x = SecretSantaHandler.Participants.get(i).toString();

            if(x == null)
                x = SecretSantaHandler.Participants.get(i).toString();

            mbeds.get(j).addField("Santa " + z + ":", "" + x, false);
            z++;
        }

        for (int i = 0; i < mbeds.size(); i++) {
            mbeds.get(i).setColor(0xeb3434);
            channel.sendMessage(mbeds.get(i).build()).queue();
            mbeds.get(i).clear();
        }
    }

    @Override
    public void setName() {
        name = "showsantas";
    }

    @Override
    public void setHelpsName() {
        helpName = "Show Santas";
    }

    @Override
    public void setHelp() {
        help.setDescription("All the santas that are participating will be shown if you type this!");
        help.addField("Show Santas", Klee.prefix + "showsantas", false);
    }
}

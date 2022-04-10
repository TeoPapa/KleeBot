package kleebot.commands.SecretSantaComms;

import kleebot.Comm;
import kleebot.Klee;
import net.dv8tion.jda.api.EmbedBuilder;

public class ResetSecretSanta extends Comm {
    @Override
    public void Command() {
        SecretSantaHandler.Restart();

        EmbedBuilder mbd = new EmbedBuilder();
        mbd.setTitle("🎇Santas Resetted Successfully✨");
        mbd.setColor(0xeb3434);
        channel.sendMessage(mbd.build()).queue();
        mbd.clear();
    }

    @Override
    public void setName() {
        name = "resetsecretsanta";
    }

    @Override
    public void setHelpsName() {
        helpName = "Reset Secret Santas";
    }

    @Override
    public void setHelp() {
        help.setDescription("Resets all the Secret Santa process!");
        help.addField("Reset Secret Santas", Klee.prefix + "resetsecretsanta", false);
    }
}

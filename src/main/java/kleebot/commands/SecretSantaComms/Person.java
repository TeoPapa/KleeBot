package kleebot.commands.SecretSantaComms;

import net.dv8tion.jda.api.entities.Member;

public class Person {

    protected int Number = -1;

    protected String Receiver;

    public int getNumber() {
        return Number;
    }

    public void setNumber(int x) {
        Number = x;
    }

    public void setReceiver(String s) {
        Receiver = s;
    }

    public String getReceiver() {
        return Receiver;
    }

}

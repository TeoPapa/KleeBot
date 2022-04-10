package kleebot.commands.SecretSantaComms;

import net.dv8tion.jda.api.entities.Member;

public class DiscordPerson extends Person{

    private Member User;

    public DiscordPerson(Member m) {
        User = m;
    }


    public Member getUser() {
        return User;
    }

    public void setUser(Member user) {
        User = user;
    }

    public boolean equals(Object o) {
        DiscordPerson p = (DiscordPerson) o;
        return this.User.toString().equals(p.getUser().toString());
    }

    public String toString() {
        return User.getUser().getName();
    }
}

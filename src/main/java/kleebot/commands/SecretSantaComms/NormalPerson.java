package kleebot.commands.SecretSantaComms;

public class NormalPerson extends Person{

    private String User;

    public NormalPerson(String u) {
        User = u;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public boolean equals(Object o) {
        NormalPerson p = (NormalPerson)  o;
        return this.getUser().equals(p.getUser());
    }

    public String toString() {
        return User;
    }

}

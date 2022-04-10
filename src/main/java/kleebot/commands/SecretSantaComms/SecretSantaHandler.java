package kleebot.commands.SecretSantaComms;

import okhttp3.MultipartBody;

import java.util.ArrayList;
import java.util.Random;

public class SecretSantaHandler {

        public static ArrayList<NormalPerson> NormalParticipants = new ArrayList<NormalPerson>();
        public static ArrayList<DiscordPerson> DiscordParticipants = new ArrayList<DiscordPerson>();
        public static ArrayList<Person> Participants = new ArrayList<Person>();

        public static void addParticipant(NormalPerson x, DiscordPerson y) {
            if(x!=null) {
                NormalParticipants.add(x);
                Participants.add(x);
                return;
            }

            DiscordParticipants.add(y);
            Participants.add(y);
        }

        public static void removeParticipant(NormalPerson x, DiscordPerson y) {

        }

        public static int GetRandomNumber(Person y) {
            Random random = new Random();
            int ToSet;
            boolean Found = false;

            do {
                ToSet = random.nextInt(Participants.size());

                if(Participants.get(ToSet) != y) {
                    for(Person x : Participants) {
                        if(x.getNumber() == ToSet) {
                            Found = false;
                            break;
                        }

                        Found = true;
                    }
                }
            }while(!Found);

            return ToSet;
        }

        public static void Initiate() {
            for(Person i : Participants) {
                i.setNumber(GetRandomNumber(i));
            }

            for(Person i : Participants)
                i.setReceiver(Participants.get(i.getNumber()).toString());
        }

        public static void Restart() {
            Participants.clear();
            DiscordParticipants.clear();
            NormalParticipants.clear();
        }
    }

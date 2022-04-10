package kleebot.commands;

import kleebot.Klee;
import kleebot.MulComm;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Date;

public class GenshinImpactComms extends MulComm {

    private int Primogems;
    private int WelkinMoon;
    private int Days;
    private boolean BattlePass;

    @Override
    public void Command() {
        if(messageSent.length < 5) {
            EmbedBuilder mbd = new EmbedBuilder();
            mbd.setTitle("🎆 Sorry, couldn't calculate that!");
            mbd.setDescription("Klee is confused with the numbers you gave. Please be more specific!");
            mbd.addField("Command", Klee.prefix + "pcalc [Welkin Moon days]wm [y or n]bp [Ammount of your primos]prm [Number of days]days", false);
            mbd.setColor(0xc45200);

            return;
        }

        setGi(messageSent);
        int x = giCalcs();

        EmbedBuilder mbd = new EmbedBuilder();
        mbd.setDescription("Klee saw that you have some Primogems! Could you give me some to buy some things I need?!?");
        mbd.addField("Approximate Primogems:", Integer.toString(x), false);
        mbd.setColor(0xeb3434);
        channel.sendMessage(mbd.build()).queue();
        mbd.clear();
    }

    @Override
    public void setName() {
        name = "pcalc";
    }

    @Override
    public void setHelpsName() {
        helpName = "Genshin Impact Calculator";
    }

    @Override
    public void setHelp() {
        help.setDescription("If you want, Klee can help you calculate your primogems. And then maybe you will give some to Klee. Tee hee!");
        help.addField("Genshin Calculator", Klee.prefix + "pcalc [Welkin Moon days]wm [y or n (yes or no)]bp [Ammount of your primos]prm [Number of days]days", false);
    }

    @Override
    public void setTheNames() {
        names.add("gicalcs");
        names.add("genshincalculator");
        names.add("gicalc");
    }
    private boolean setGi(String[] x) {
         WelkinMoon = Integer.parseInt(x[1].substring(0, (x[1].length() - 2) ));

         if(!x[2].substring(0,x[2].length()-2).equals("y") && !x[2].substring(0,x[2].length()-2).equals("n") ) {
             EmbedBuilder mbd = new EmbedBuilder();
             mbd.setTitle("❌ Sorry, couldn't do that");
             mbd.setDescription("Not appropriate command");
             mbd.addField("Genshin Calculator", Klee.prefix + "pcalc [Welkin Moon days]wm [y or n (yes or no)]bp [Ammount of your primos]prm [Number of days]days", false);
             mbd.setColor(0xc45200);

             channel.sendMessage(mbd.build()).queue();
             mbd.clear();
             return false;
         }

         if(x[2].substring(0,x[2].length()-2).equals("y"))
             BattlePass = true;
         else
             BattlePass = false;

         Primogems = Integer.parseInt(x[3].substring(0, x[3].length() - 3) );

         Days = Integer.parseInt(x[4].substring(0, x[4].length() - 4));

         return true;
    }

    private int giCalcs() {
        Date curDate = new Date();
        int ammountOfPrimos = Primogems;

        if(Days < WelkinMoon)
            ammountOfPrimos += Days*150;
        else
            ammountOfPrimos += Days*60 + WelkinMoon*90;

        if(BattlePass)
            ammountOfPrimos += 4*160 + 680;

        if(curDate.getDate() + Days >= 90)
            ammountOfPrimos += 3*5*160;
        else if (curDate.getDate() + Days >= 60)
            ammountOfPrimos += 2*5*160;
        else if(curDate.getDate() + Days >= 30)
            ammountOfPrimos += 5*160;

        if(Days >= 42) {
            ammountOfPrimos += 1000 + 600 + 300;
        }

        if(Days >= 60)
            ammountOfPrimos += 9*150;
        else if(Days >= 30)
            ammountOfPrimos += 6*150;
        else if( Days >= 15)
            ammountOfPrimos += 3*150;

        return  ammountOfPrimos;
    }
}

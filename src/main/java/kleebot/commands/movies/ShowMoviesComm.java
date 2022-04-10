package kleebot.commands.movies;

import kleebot.Klee;
import kleebot.MulComm;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ShowMoviesComm extends MulComm {

    public static ArrayList<Movie> Movies = new ArrayList<>();
    public static int Indexes;

    @Override
    public void Command() {
        ArrayList<EmbedBuilder> mbeds = new ArrayList<>();
        int j = 0;

        mbeds.add(new EmbedBuilder());

        if (Movies.size() > 0) {
            int z = 1;
            mbeds.get(j).setTitle("✨Saved Movies🎇");
            mbeds.get(j).setDescription("Here are the movies you recommended to Klee:");
            for (int i = 0; i < Movies.size(); i++) {
                if (i % 25 == 0 && i != 0) {
                    mbeds.add(new EmbedBuilder());
                    j++;
                    z = 1;
                }
                mbeds.get(j).addField(z + ") " + Movies.get(i).Description, "Sugested by: " + Movies.get(i).movieSender + "\nIMDb: " + Movies.get(i).URL, false);
                z++;
            }

            for (int i = 0; i < mbeds.size(); i++) {
                mbeds.get(i).setColor(0xeb3434);
                channel.sendMessage(mbeds.get(i).build()).queue();
                mbeds.get(i).clear();
            }
        }
        else {
            mbeds.get(j).setTitle("🎇No Movies Recommended yet!✨");
            mbeds.get(j).setDescription("Use the addmovies command, to recommend new movies to Klee!");
            mbeds.get(j).setColor(0xeb3434);
            channel.sendMessage(mbeds.get(j).build()).queue();
            mbeds.get(j).clear();
        }
    }

    @Override
    public void setName() {
        name = "showmovies";
    }

    @Override
    public void setHelpsName() {
        helpName = "Show Movies";
    }

    @Override
    public void setHelp() {
        help.setDescription("Klee has seen some recomendation from some people. Want me to show you?");
        help.addField("Show Movies", Klee.prefix + "showmovies", false);
    }

    @Override
    public void setTheNames() {
        names.add("movieshow");
        names.add("moviesshow");
        names.add("showmovie");
    }

    public static void loadMovies () {
        //File savedMovies = new File("Movies.txt");

        try {
                Scanner readLine = new Scanner(new File("Movies.txt"));
                while (readLine.hasNextLine()) {
                    String line = readLine.nextLine();
                    String[] data = line.split(",");

                    Movies.add(new Movie(data[1], data[2], data[3]));
                }
                readLine.close();

                /*FileInputStream fins = new FileInputStream(savedMovies);
            ObjectInputStream dins = new ObjectInputStream(fins);

            Movies.addAll((ArrayList<Movie>) dins.readObject());

            Indexes = Movies.size();

            dins.close(); */
        } catch (FileNotFoundException e) {
            System.out.println("Nope");
        } catch (IOException e) {
            System.out.println("Nope");
        }
    }

    public static void SaveMovies(int x) {
        //File savedMovies = new File("Movies.ser");

        try {
            FileWriter fileWriter = new FileWriter("Movies.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for(int i = 0; i < Movies.size(); i++)
                printWriter.print( i + "," + Movies.get(i).movieSender + "," + Movies.get(i).Description + "," + Movies.get(i).URL +"\n");

            /*FileOutputStream fouts = new FileOutputStream(savedMovies);
            ObjectOutputStream douts = new ObjectOutputStream(fouts);

            douts.writeObject(Movies); */

            if(x == 1)
                Indexes += 1;
            else if (x == 3)
                Indexes = 0;

            //douts.close();
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nope");
        } catch (IOException e) {
            System.out.println("Nope");
        }
    }

    public static boolean SearchMovie(int x, Movie y, int z) {
            for(int i =0; i < Movies.size(); i++) {
                if(x == 1) {
                    if (Movies.get(i).equals(y)) {
                        Movies.remove(i);
                        return true;
                    }
                }
                else if(x == 2) {
                        Movies.remove(z-1);
                        return true;
                    }
                }
                return false;
            }
    }

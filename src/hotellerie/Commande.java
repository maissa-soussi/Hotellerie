package hotellerie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Commande {

    private int Num_R;
    private String Code_Plat;
    private int Nb_Plat;
    private int Date_C;

    public Commande(int n, String c, int nb, int d) {
        Num_R = n;
        Code_Plat = c;
        Nb_Plat = nb;
        Date_C = d;
    }

    public int getNum_R() {
        return Num_R;
    }

    public void setNum_R(int Num_R) {
        this.Num_R = Num_R;
    }

    public String getCode_Plat() {
        return Code_Plat;
    }

    public void setCode_Plat(String Code_Plat) {
        this.Code_Plat = Code_Plat;
    }

    public int getNb_Plat() {
        return Nb_Plat;
    }

    public void setNb_Plat(int Nb_Plat) {
        this.Nb_Plat = Nb_Plat;
    }

    public int getDate_C() {
        return Date_C;
    }

    public void setDate_C(int Date_C) {
        this.Date_C = Date_C;
    }

    public void initialiser() /*effacer la liste des commande chaque fin de semaine */ {
        /* determiner le nombre de ligne */
        int i = 0;
        try {
            File f = new File("src\\Hotellerie\\Files\\Commande.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                i++;
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* supprimer l'ancien fichier commande et creer un nouveau fichier commande si on est a la fin de semaine */
        try {
            File f = new File("src\\Hotellerie\\Files\\Commande.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            for (int j = 0; j < i; j++) {
                String readLine = b.readLine();
                String[] tab = readLine.split("-");
                if (j == i - 1) {
                    if ("7".equals(tab[3]) && (Date_C != 7)) {
                        b.close();
                        File entree = new File("src\\Hotellerie\\Files\\Commande.txt");
                        entree.delete();
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src\\Hotellerie\\Files\\Commande.txt")));
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

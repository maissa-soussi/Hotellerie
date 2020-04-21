package hotellerie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Restaurant {
    /* declaration de la classe Plat */
    public static class Plat {
        public String code;
        public String nom;
        public int prix;
        public Plat(String c, String n, int p) {
            code=c;
            nom=n;
            prix=p;
        }
    }
    
    private String Nom_R;
    private  int Nb_Fourchette;
    private int Nb_Plat;
    private Plat [] Plats;
    
    public int Calcul_Nb_Plat(){ 
        int i=0;
        try {
            /* Création du flux vers le fichier texte */
            File f = new File("src\\Hotellerie\\Files\\"+getNom_R()+".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            /* parcours du fichier ligne par ligne */
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                i++;
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
                return i;
    }
    
    public Plat [] importer_plat() {
        Plat [] p= new Plat[this.getNb_Plat()];
        try {
            File f = new File("src\\Hotellerie\\Files\\"+getNom_R()+".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            int i=0;
            String readLine = "";
            /* chaque ligne dans une case de tab1 */
            String [] tab1= new String [this.getNb_Plat()];
            while ((readLine = b.readLine()) != null) {
                tab1[i]=readLine;
                i++;
            }
            b.close();
            /* importer les plats */
            for(int j=0;j<this.getNb_Plat();j++){                               
		String[] tab=tab1[j].split("-");
                p[j]=new Plat(tab[0], tab[1], Integer.parseInt(tab[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return p;
    }    
    
    public Restaurant (String n, int nb) {
        Nom_R=n;
        Nb_Fourchette=nb;
        Nb_Plat=Calcul_Nb_Plat();
        Plats=this.importer_plat();
    }
    public String getNom_R() {
        return Nom_R;
    }
    public void setNom_R(String Nom_R) {
        this.Nom_R = Nom_R;
    }

       public int getNb_Fourchette() {
        return Nb_Fourchette;
    }
       public int getNb_Plat() {
           return Nb_Plat;
       }

        public void setNb_Fourchette(int Nb_Fourchette) {
        this.Nb_Fourchette = Nb_Fourchette;
    }

        public Plat[] getPlats() {
        return Plats;
    }

        public void setPlats(Plat[] Plats) {
            this.setPlats(Plats);
    } 

    public void setNb_Plat(int Nb_Plat) {
        this.Nb_Plat = Nb_Plat;
    }
}
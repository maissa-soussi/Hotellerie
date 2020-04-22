package hotellerie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
    
    public void Ajouter_C(Commande c)
    {
        /* Chemin vers le fichier à modifier*/
		String pathFichier = "src\\Hotellerie\\Files\\Commande.txt";
		
		/* Texte à ajouter */
		String newLine = System.getProperty("line.separator");
                String aAjouter =c.getNum_R() + "-" + c.getCode_Plat() + "-" + c.getNb_Plat() + "-" + c.getDate_C() + newLine;
		FileWriter writer = null;
		try	{
			/* Ouverture du fichier en écriture */
			writer = new FileWriter(pathFichier, true);
			writer.write(aAjouter, 0, aAjouter.length());
                        writer.close();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
    }
    public int getprix(Commande c, String co){
        int p=0;
        try {
            /* Création du flux vers le fichier texte */
            File f = new File("src\\Hotellerie\\Files\\"+getNom_R()+".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            /* parcours du fichier ligne par ligne */
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                if(readLine.startsWith(co)){
                  String[] tab=readLine.split("-");
                  p=Integer.parseInt(tab[2]);
            }
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
        
    }
    
    public void effectuer(int n, String co, int nb, int d)
    {
        Commande c=new Commande(n, co, nb, d);
        c.initialiser();
        this.Ajouter_C(c);
        int p=this.getprix(c, co);
        String t = String.valueOf(n);
        try
        {
        File entree = new File("src\\Hotellerie\\Files\\Reservation.txt");
        File sortie = new File("src\\Hotellerie\\Files\\temp.txt");
        BufferedReader br = new BufferedReader(new FileReader(entree));
        BufferedWriter bw = new BufferedWriter(new FileWriter(sortie));
        String ligne="";
 
        while ((ligne = br.readLine()) != null){
            if(ligne.startsWith(t)){
                  String[] tab=ligne.split("-");                 
                  int nouv_prix=Integer.parseInt(tab[7])+p*c.getNb_Plat();

                  bw.write(tab[0]+"-"+tab[1]+"-"+tab[2]+"-"+tab[3]+"-"+tab[4]+"-"+tab[5]+"-"+tab[6]+"-"+nouv_prix+"\n");
                  bw.flush();
             }else{
                  bw.write(ligne+"\n");
                  bw.flush();
             }
        }
        bw.close();
        br.close();
        entree.renameTo(new File("src\\Hotellerie\\Files\\poubelle.txt"));
        sortie.renameTo(new File("src\\Hotellerie\\Files\\Reservation.txt"));
        File poubelle = new File("src\\Hotellerie\\Files\\poubelle.txt");
        poubelle.delete();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
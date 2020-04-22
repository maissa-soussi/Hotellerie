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
    /* ajouter une commande a la liste des commande */
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
    /* determiner le prix du plat du code co */
    public int getprix(String co){
        int p=0;
        try {
            File f = new File("src\\Hotellerie\\Files\\"+getNom_R()+".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
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
    
    /* effectuer une commande */
    public void effectuer(int n, String co, int nb, int d)
    {
        Commande c=new Commande(n, co, nb, d);
        c.initialiser();
        this.Ajouter_C(c);
        int p=this.getprix(co);
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
    
    /* verifier si le plat du code co appartient au restaurant courant ou non */
    public Boolean verifier(String co)
    {
        Boolean bo=false;
        try {
            File f = new File("src\\Hotellerie\\Files\\"+getNom_R()+".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while (((readLine = b.readLine()) != null) && (bo==false)) {
                String[] tab=readLine.split("-");
                if(tab[0].equals(co)){
                  bo=true;
            }
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bo;
    }
    
    /* afficher la recette de la journee */
    public void recette_journee(int d)
    {
        int r=0;
        try {
            File f = new File("src\\Hotellerie\\Files\\Commande.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                String[] tab=readLine.split("-");
                if((Integer.parseInt(tab[3])==d) && (verifier(tab[1]))){
                  r=r+Integer.parseInt(tab[2])*this.getprix(tab[1]);
            }
            }
            b.close();
            System.out.println("la recette de la journée "+d+" est: "+r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /* afficher la recette de la journee */
    public void recette_semaine()
    {
        int r=0;
        try {
            File f = new File("src\\Hotellerie\\Files\\Commande.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                String[] tab=readLine.split("-");
                if(verifier(tab[1])){
                  r=r+Integer.parseInt(tab[2])*this.getprix(tab[1]);
            }
            }
            b.close();
            System.out.println("la recette de la semaine est: "+r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /* afficher la fréquence de demande de chaque plat pendant la semaine courante */
    public void frequence()
    {
        for(int i=0;i<this.Nb_Plat;i++)
        {
            int fr=0;
            try {
            File f = new File("src\\Hotellerie\\Files\\Commande.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                String[] tab=readLine.split("-");
                if(this.Plats[i].code.equals(tab[1])){
                    fr=fr+Integer.parseInt(tab[2]);
            }
            }
            b.close();
            System.out.println("la fréquence de demande du plat "+this.Plats[i].nom+" pendant la semaine courante est: "+fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}

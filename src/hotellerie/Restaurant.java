package hotellerie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Restaurant {
    /* declaration de la classe Plat */
    public static class Plat {
        public String code;
        public String nom;
        public float prix;
        public Plat(String c, String n, float p) {
            code=c;
            nom=n;
            prix=p;
        }
    }
    
    private String Nom_R;
    private  int Nb_Fourchette;
    private int Nb_Plat;
    private Plat [] Plats;
    // calculer le nombre de plats
    public int Calcul_Nb_Plat(){ 
        int i=0;
        try {
            /* Création du flux vers le fichier texte */
            File f = new File("src\\Hotellerie\\Files\\"+Nom_R+".txt");
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
    //remplir le tableau Plats par des plats a partir du fichier
    public Plat [] importer_plat() {
        Plat [] p= new Plat[Nb_Plat];
        try {
            File f = new File("src\\Hotellerie\\Files\\"+Nom_R+".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            int i=0;
            String readLine = "";
            /* importer les plats */
            while ((readLine = b.readLine()) != null) {
                String[] tab=readLine.split("-");
                p[i]=new Plat(tab[0], tab[1], Float.parseFloat(tab[2]));
                i++;
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return p;
    }    
    //constructeur
    public Restaurant (String n) {
        Nom_R=n;
        Nb_Plat=Calcul_Nb_Plat();
        Plats=importer_plat();
        if("Italiano".equals(n))
            Nb_Fourchette=3;
        if("Mexicano".equals(n))
            Nb_Fourchette=3;
        if("Royale".equals(n))
            Nb_Fourchette=2;
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

    public void setNb_Plat(int Nb_Plat) {
        this.Nb_Plat = Nb_Plat;
    }
    /* ajouter une commande au fichier Commande */
    public void Ajouter_C(Commande c)
    {    c.initialiser();
        /* Chemin vers le fichier à modifier*/
		String pathFichier = "src\\Hotellerie\\Files\\Commande.txt";
		
		/* Texte à ajouter */
                String aAjouter =c.getNum_R() + "-" + c.getCode_Plat() + "-" + c.getNb_Plat() + "-" + c.getDate_C() + "-" + this.getprix(c.getCode_Plat())*c.getNb_Plat() + "\n";
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
    public float getprix(String co){
        int i=0;
        Boolean b=false;
        float p=0;
        while((i<Nb_Plat) && (b==false))
        {
            if(Plats[i].code.equals(co))
            {
                b=true;
                p=this.Plats[i].prix;
            }
            i++;
        }
        return p;
    }
    
    /* effectuer une commande */
    public void effectuer(int n, String co, int nb, int d)
    {
        Commande c=new Commande(n, co, nb, d);
        this.Ajouter_C(c);
        float p=this.getprix(co);
        String t = String.valueOf(n);
    /* ajouter le prix de la commande au prix total et au reste a payer dans le fichier reservation */
        try
        {
        BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Reservation.txt"));
        String ligne="";
        File FileTemp = new File("Reservation.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(FileTemp, true));
 
        while ((ligne = br.readLine()) != null){
            if(ligne.startsWith(t)){
                  String[] tab=ligne.split("-");                 
                  float nouv_resteapayer=Float.parseFloat(tab[7])+p*c.getNb_Plat();
                  float nouv_prixtotal=Float.parseFloat(tab[6])+p*c.getNb_Plat();
                  bw.write(tab[0]+"-"+tab[1]+"-"+tab[2]+"-"+tab[3]+"-"+tab[4]+"-"+tab[5]+"-"+nouv_prixtotal+"-"+nouv_resteapayer+"\n");
                  bw.flush();
             }else{
                  bw.write(ligne+"\n");
             }
        }
        bw.close();
        br.close();
        Path temp = Files.move(Paths.get("Reservation.txt"), Paths.get("src\\Hotellerie\\Files\\Reservation.txt"), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("                         Restaurant "+ this.Nom_R);
        Date da = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/M/yyyy                                                   H':'m", Locale.FRANCE);
	System.out.println(f.format(da));
        System.out.println("NUM RESERVATION                                             "+n);
        System.out.println("__________________________________________________________________");
        System.out.println(co+"*"+nb+"                                                      "+p);
        System.out.println("__________________________________________________________________");
        System.out.println("TOTAL                                                       "+p*nb);
        System.out.println("__________________________________________________________________");
        System.out.println("                          MERCI A BIENTOT");
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
        float r=0;
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
    
    /* afficher la recette de la semaine */
    public void recette_semaine()
    {
        float r=0;
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
        for(int i=0;i<Nb_Plat;i++)
        {
            int fr=0;
            try {
            File f = new File("src\\Hotellerie\\Files\\Commande.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                String[] tab=readLine.split("-");
                if(Plats[i].code.equals(tab[1])){
                    fr=fr+Integer.parseInt(tab[2]);
            }
            }
            b.close();
            System.out.println("la fréquence de demande du plat "+Plats[i].nom+" pendant la semaine courante est: "+fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}

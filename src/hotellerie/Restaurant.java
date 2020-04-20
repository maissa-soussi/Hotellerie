package hotellerie;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Restaurant {

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
        String pathFichier="src\\Hotellerie\\Files\\"+getNom_R()+".txt";
        int i=-1;
		BufferedReader fluxEntree=null;
		try {
			/* Création du flux vers le fichier texte */
			fluxEntree = new BufferedReader(new FileReader(pathFichier));
			
			/* Lecture d'une ligne */
			String ligneLue = fluxEntree.readLine();
			while(ligneLue!=null){
                                
				ligneLue = fluxEntree.readLine();                                                      
                                i++;
                                 
			}
                        
                        fluxEntree.close();
                        
		}
		catch(IOException exc){
			exc.printStackTrace();
		}
                return i;
    }
    public Plat [] importer_plat() {
        Plat [] p= new Plat[this.getNb_Plat()];
        String pathFichier="src\\Hotellerie\\Files\\"+getNom_R()+".txt";

		BufferedReader fluxEntree=null;
		try {
			fluxEntree = new BufferedReader(new FileReader(pathFichier));
			
			String ligneLue = fluxEntree.readLine();
			
                        
                        int i = 0;
                        String [] tab1= new String [this.getNb_Plat()+1];
			while(ligneLue!=null){
                                tab1[i]=ligneLue;
				ligneLue = fluxEntree.readLine();                                                      
                                i++;
                                 
			}
                         fluxEntree.close();
                        for(int j=0;j<this.getNb_Plat();j++){
                                
				String[] tab=tab1[j].split("-");
                                p[j]=new Plat(tab[0], tab[1], Integer.parseInt(tab[2]));
                                
                                
                                
                                
			} 
                        
		}
		catch(IOException exc){
			exc.printStackTrace();
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
        this.Plats = Plats;
    }
    
        
          
    
}

package hotellerie;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private Plat [] Plats;
    public Plat [] importer_plat() {
        Plat [] p= new Plat[11];
        String pathFichier="C:\\Users\\moham\\Desktop\\java\\Hotellerie\\src\\hotellerie\\Files\\"+getNom_R();

		BufferedReader fluxEntree=null;
		try {
			fluxEntree = new BufferedReader(new FileReader(pathFichier));
			
			String ligneLue = fluxEntree.readLine();
			int taille=0;
                        while(ligneLue!=null){
				String[] tab=ligneLue.split("-");
                                p[taille].code=tab[0];
                                p[taille].nom=tab[1];
                                p[taille].prix=Integer.parseInt(tab[2]);                           
				ligneLue = fluxEntree.readLine();
                                taille++;
			}                                                
		}
		catch(IOException exc){
			exc.printStackTrace();
		}
		finally{
			try{
				if(fluxEntree!=null){
					/* Fermeture du flux vers le fichier */
					fluxEntree.close();
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}	
    return p;
    }
    
    
    public Restaurant (String n, int nb) {
        Nom_R=n;
        Nb_Fourchette=nb;
        Plat [] p= new Plat[11];
        p=this.importer_plat();
        
    }

    /**
     * @return the Nom_R
     */
    public String getNom_R() {
        return Nom_R;
    }

    /**
     * @param Nom_R the Nom_R to set
     */
    public void setNom_R(String Nom_R) {
        this.Nom_R = Nom_R;
    }

    /**
     * @return the Nb_Fourchette
     */
    public int getNb_Fourchette() {
        return Nb_Fourchette;
    }

    /**
     * @param Nb_Fourchette the Nb_Fourchette to set
     */
    public void setNb_Fourchette(int Nb_Fourchette) {
        this.Nb_Fourchette = Nb_Fourchette;
    }

    /**
     * @return the Plats
     */
    public Plat[] getPlats() {
        return Plats;
    }

    /**
     * @param Plats the Plats to set
     */
    public void setPlats(Plat[] Plats) {
        this.Plats = Plats;
    }
    
    
}

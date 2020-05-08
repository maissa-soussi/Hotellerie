package hotellerie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class Reservation {
        private int Num_R;
	private long Cin_client;
	private String Date_Reservation;
	private String Date_Arrivee;
	private int Nb_semaine;
	private int Nb_chambre;
	private double prix_total;
        private double prix_reservation;
	private double reste_payer;
        private int[] chambres;
        
        //il faut tester avant if semdebut+nbsemaine>5 ==> erreur
        // il faut tester les chambres dispo selon le type date .. ... et les mettre dans un tableau
        public Reservation(long b,int datearr,int e,int h, int [] ch)
        {   Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
            Num_R=this.NumR(reservation) + 1;
	    Cin_client=b;
	    
            Date d = new Date();
            SimpleDateFormat f = new SimpleDateFormat("dd/M/yyyy H':'m", Locale.FRANCE);
            Date_Reservation = f.format(d);
            
	    Nb_semaine=e;
	    Nb_chambre=h;  
            chambres=new int[ch.length];
            for (int i=0;i<ch.length;i++)
            {
                chambres[i]=ch[i];
                Chambre c = new Chambre();
                c.ReserverChambre(chambres[i], Nb_semaine, datearr);
            }
             if (datearr==1){
                 Date_Arrivee="2020/08/02";
             }  else if(datearr == 2) {
		Date_Arrivee="2020/08/09";
		} else if (datearr == 3) {
		Date_Arrivee="2020/08/16";
		} else if (datearr == 4) {
		Date_Arrivee="2020/08/23";
	    }
            
             prix_total=this.Calcul_prix_total();
             prix_reservation=prix_total;
             reste_payer=prix_total * 0.9;
             
        }
        
        //Constructeur avec un seul parametre
        public Reservation()
        {
            
        }
        public Reservation(int Num_R)
        {
            this.Num_R = Num_R;
        boolean res = false;
        String[] tab = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Reservation.txt"));
            String reservation;
            DecimalFormat nf=new DecimalFormat("00000000");
            while ((res == false) && ((reservation = br.readLine()) != null)) {
                tab = reservation.split("-");
                res = (tab[0].equals(Integer.toString(Num_R)));
            }

            br.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        
	Cin_client=Long.parseLong(tab[1]);
	Date_Reservation=tab[2];
	Date_Arrivee=tab[3];
	Nb_semaine=Integer.parseInt(tab[4]);
	Nb_chambre=Integer.parseInt(tab[5]);
	prix_total=Double.parseDouble(tab[6]);
	reste_payer=Double.parseDouble(tab[7]);
        chambres=new int[Nb_chambre];
        for (int i=0;i<Nb_chambre;i++)
            chambres[i]=Integer.parseInt(tab[8+i]);
        
        }
        
        //Cloturer reservation 
        public void cloturer() {
		Client c = new Client(Cin_client);
		
                String Num_Chambres="";
                DecimalFormat nf=new DecimalFormat("000");
                  for (int i=0;i<chambres.length;i++)
                  {
                     Num_Chambres=Num_Chambres+" | "+nf.format(chambres[i]);
                  }
		System.out.println("\t\t\t" + "Numéro de(s) chambre(s) résérvée(s) : " + Num_Chambres);
		System.out.println("Nom : " + c.getNom() + "\t\t" + "Prénom : " + c.getPrenom());
		System.out.println("E-mail : " + c.getEmail());
		System.out.println("Téléphone : " + c.getTel());
		System.out.println("Pays : " + c.getPays() + "\n");
		System.out.println("Prix total à payer (en dinars) : " + prix_total);
		System.out.println("Reste à payer (en dinars) : " + reste_payer);
                ajoutcloture();
                
	}
        
        public void ajoutcloture()
        {
            Boolean res = false;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Reservation.txt"));
            String reservation="";
            File File = new File("src\\Reservation.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(File, true));
            // recherche du reservation à partir du fichier reservation
            while (((reservation = br.readLine()) != null)) {
                String[] tab=reservation.split("-");
                res = (tab[0].equals(String.valueOf(Num_R)));
                if (res == false) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(reservation);                   
                }
                else
                {   
                    String pathFichier = "src\\Hotellerie\\Files\\Cloture.txt";
		
		/* Texte à ajouter */
                String aAjouter =reservation + "\n";
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
            }
            bufferedWriter.close();
            br.close();
            // remplacer l'ancien fichier reservation par le nouveau           
        Files.move(Paths.get("src\\Reservation.txt"), Paths.get("src\\Hotellerie\\Files\\Reservation.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        effacerlignevide("Reservation");
        effacerlignevide("Cloture");
        
        }
        //Annuler une reservation
        public void annuler()
        {
            Boolean res = false;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Reservation.txt"));
            String reservation="";
            File File = new File("src\\Reservation.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(File, true));
            // recherche du reservation à partir du fichier reservation
            while (((reservation = br.readLine()) != null)) {
                String[] tab=reservation.split("-");
                res = (tab[0].equals(String.valueOf(Num_R)));
                if (res == false) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(reservation);                   
                }
                else
                {   
                    int nums=0; 
                    switch (Date_Arrivee) {
                        case "2020/08/02":
                            nums=1;
                            break;
                        case "2020/08/09":
                            nums=2;
                            break;
                        case "2020/08/16":
                            nums=3;
                            break;
                        case "2020/08/23":
                            nums=4;
                            break;
                    }
                    for (int i=0;i<chambres.length;i++)
                    {
                        Chambre c=new Chambre(chambres[i]);
                        c.AnnulerReservation(chambres[i],Nb_semaine , nums);
                    }
                }
            }
            bufferedWriter.close();
            br.close();
            // remplacer l'ancien fichier reservation par le nouveau           
        Files.move(Paths.get("src\\Reservation.txt"), Paths.get("src\\Hotellerie\\Files\\Reservation.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        effacerlignevide("Reservation");
        }
         
        //Visualiser reservation 
        public void Visualiser()
        {   
            System.out.println("           Date Reservation : "+Date_Reservation);
            DecimalFormat nf=new DecimalFormat("000");
            System.out.println("       Numero de Reservation : "+nf.format(Num_R));
            Client c = new Client(Cin_client);
            System.out.println("Nom : " + c.getNom()+" \t   Prenom : "+c.getPrenom());
            System.out.println("E-mail : " + c.getEmail());
            System.out.println("Tel : " + c.getTel());
            System.out.println("Pays : " + c.getPays() + "\n");
            System.out.println("Nombre de chambres : " + Nb_chambre);
            String Num_Chambres="";
            for (int i=0;i<chambres.length;i++)
                Num_Chambres=Num_Chambres+" | "+nf.format(chambres[i]);
            System.out.println("Numero de chambre : " + Num_Chambres);
            System.out.println("Prix total en (dinars) : " + prix_total);
            System.out.println("Avance payee en (dinars) : " + ((reste_payer/0.9) * 0.1));
            System.out.println("Date d'arrive : " + Date_Arrivee);
            System.out.println("Nombre de semaine : " + Nb_semaine);
        }

        //Calcul prix total 
        
        private double Calcul_prix_total()
        {
            double prix=0.0;
            for (int i=0;i<chambres.length;i++)
            {
                Chambre c = new Chambre(chambres[i]);
                String type=c.GetType();
                if (type.contains("simple")) {
			prix=prix+500;
		} else if (type.contains("double")) {
			prix=prix+700;
		} else if (type.contains("triple")) {
			prix=prix+1000;
		} else if (type.contains("luxe")) {
			prix=prix+1500;
		}
            }
            
            return prix*Nb_semaine;
        }
        //Ajout de la reservation 
        public void AjouterReservation()
        {
            try {
            DecimalFormat nf=new DecimalFormat("00000000");
            DecimalFormat nf1=new DecimalFormat("000");
            String nvres = Num_R+"-"+nf.format(Cin_client) + "-" + Date_Reservation + "-" + Date_Arrivee + "-" + Nb_semaine + "-" + Nb_chambre + "-" + prix_total + "-" + reste_payer;
            for (int i=0;i<chambres.length;i++)
            {
                nvres=nvres+"-"+nf1.format(chambres[i]);
                
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\Hotellerie\\Files\\Reservation.txt", true));
            bufferedWriter.newLine();
            bufferedWriter.write(nvres);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
            effacerlignevide("Reservation");
        }
        
        // recherche d'une chambre selon les criteres
        
        public static int rechercher_chambre(String type, String vue, int nbSem, int numS) {
		int num = 0;
	            try{	
	            Path chambre = Paths.get("src\\Hotellerie\\Files\\Chambre.txt");
			List<String> lignes = Files.readAllLines(chambre);
			
			for (String ligne : lignes) {
				String[] detail = ligne.split("-");
				if ((detail[1].equals(type)) && (detail[2].equals(vue))) {
					boolean verif = true;
					int i = 0;
					while (verif == true && i < nbSem) {
						if (Integer.valueOf(detail[2 + numS + i]) != 0) {
							verif = false;
						} else {
							i++;
						}
					}
					if (verif == true) {
						num = Integer.parseInt(detail[0]);
						break;
					}

				}
			}
	            }
	        catch(IOException e)
	        {
	            e.getMessage();
	        }

			return num;
		}
        
        //incrementer le numero de reservation 
        public int NumR(Path reservation) {
		int a = 0;
		try {
			List<String> lignes = Files.readAllLines(reservation);
			for (String ligne : lignes) {
				String[] donnees = ligne.split("-");
                                if(Integer.parseInt(donnees[0])>=a)
				a = Integer.parseInt(donnees[0]);
			}
		} catch (IOException e) {
			System.out.println("erreur de lecture du fichier");

		}

		return a;
	}

    public int getNum_R() {
        return Num_R;
    }

    public long getCin_client() {
        return Cin_client;
    }

    public String getDate_Reservation() {
        return Date_Reservation;
    }

    public String getDate_Arrivee() {
        return Date_Arrivee;
    }

    public int getNb_semaine() {
        return Nb_semaine;
    }

    public int getNb_chambre() {
        return Nb_chambre;
    }

    public double getPrix_total() {
        return prix_total;
    }

    public double getPrix_reservation() {
        return prix_reservation;
    }
    public int[] getChambres()
    {
        return chambres;
    }
    
    public double getReste_payer() {
        return reste_payer;
    }

    public void setNum_R(int Num_R) {
        this.Num_R = Num_R;
    }

    public void setCin_client(long Cin_client) {
        this.Cin_client = Cin_client;
    }

    public void setDate_Reservation(String Date_Reservation) {
        this.Date_Reservation = Date_Reservation;
    }

    public void setDate_Arrivee(String Date_Arrivee) {
        this.Date_Arrivee = Date_Arrivee;
    }

    public void setNb_semaine(int Nb_semaine) {
        this.Nb_semaine = Nb_semaine;
    }

    public void setNb_chambre(int Nb_chambre) {
        this.Nb_chambre = Nb_chambre;
    }

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }

    public void setPrix_reservation(double prix_reservation) {
        this.prix_reservation = prix_reservation;
    }
    
    public void setReste_payer(double reste_payer) {
        this.reste_payer = reste_payer;
    }
    public int semaineDeb()
    {
        int nums=0;
        switch (Date_Arrivee) {
                        case "2020/08/02":
                            nums=1;
                            break;
                        case "2020/08/09":
                            nums=2;
                            break;
                        case "2020/08/16":
                            nums=3;
                            break;
                        case "2020/08/23":
                            nums=4;
                            break;
    }
        return nums;
    }
    public static void effacerlignevide(String ch) {
      try
  	{
    String fichier="src\\Hotellerie\\Files\\"+ch+".txt";
    InputStream fis = new FileInputStream(fichier);
    Reader reader = new InputStreamReader(fis, "utf-8");
    BufferedReader input =  new BufferedReader(reader);
    String line = null;
    StringBuilder str=new StringBuilder();
    while ((line = input.readLine()) != null)
    {
    	str.append(line);
    	str.append("\n");
  	}
  	writeTo(str.toString(), fichier);
        input.close();
  	}catch(IOException ex)
  	{
  		ex.printStackTrace();
  	} 
  }
  private static void writeTo(String data, String fichier)throws IOException
  {
  	FileWriter writer=new FileWriter(fichier);
  	writer.write(data.replaceAll("(?m)^[ \t]*\r?\n", ""));
  	writer.close();
  }
  public void visualiserRecette()
  {
      try {
            BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Cloture.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
  }
  public static  void Modifier(int numR)
  {
      Reservation r1=new Reservation(numR);
                 r1.annuler();
        Scanner input1 = new Scanner(System.in);
        System.out.println("Combien de semaine voulez-vous allouez? (1|2|3|4)");
        int nbsem = input1.nextInt();
        while (Hotellerie.VerifNbsem(nbsem) == false) {
            System.out.println("Nombre de semaine invalide");
            System.out.println("Combien de semaine voulez-vous allouez? (1|2|3|4)");
            nbsem = input1.nextInt();
        }
        Scanner input2 = new Scanner(System.in);
        System.out.println("Combien de chambre voulez-vous reservez?");
        int nb_chambre = input2.nextInt();
        while (nb_chambre < 0) {
            System.out.println("Saisir un Nombre de chambre correcte");
            System.out.println("Combien de chambre voulez-vous reservez?");
            nb_chambre = input2.nextInt();
        }
          Scanner input5 = new Scanner(System.in);
        System.out.println("A quelle semaine voulez-vous debuter votre sejour ?");
        int semdebut = input5.nextInt();
        while (semdebut + nbsem > 5 || semdebut <= 0) {
            System.out.println("saisir un nombre de semaine correcte");
            System.out.println("A quelle semaine voulez-vous debuter votre sejour ?");
            semdebut = input5.nextInt();
        }
       // Reservation r = new Reservation(nb1, nbsem, nb_chambre);//initialiser une reservation avec cin=250 , nb semaine � louer =1 ,nb de chambre a louer =1 
       
       int [] tab=new int[nb_chambre];
       int k=0;
        for (int i=1;i<=nb_chambre;i++)
        {
        Scanner input3 = new Scanner(System.in);
        System.out.println("Quel type voulez-vous choisir pour la chambre num "+i+"? (simple|double|triple|luxe)");
        String type = input3.nextLine();
        while (Hotellerie.VerifTypeC(type) == false) {
            System.out.println("type invalide");
            System.out.println("Quel type voulez-vous choisir ? (simple|double|triple|luxe)");
            type = input3.nextLine();
        }
        Scanner input4 = new Scanner(System.in);
        System.out.println("Voulez vous choisir une chambre avec vue mer/piscine/jardin ");
        String vue = input4.nextLine();
        while (Hotellerie.VerifVueC(vue) == false) {
            System.out.println("vue invalide");
            System.out.println("Voulez vous choisir une chambre avec vue mer/piscine/jardin ");
            vue = input4.nextLine();
        }
        int num;
        num=Reservation.rechercher_chambre(type.toLowerCase(), vue.toLowerCase(),nbsem,semdebut);
        if(num>0){
        tab[k]=num;
        k++;
        Chambre c = new Chambre();
        c.ReserverChambre(num, nbsem, semdebut);
        }
        else
        {
           Chambre c=new Chambre();
           if(c.Suggestion(type.toLowerCase(), nbsem, semdebut)){
           vue="aaaa";
            while (!vue.equalsIgnoreCase("non")&& Hotellerie.VerifVueC(vue) == false) {
            System.out.println("entrer une autre vue sinon saisir non");
            vue = input4.nextLine();
        }
          int num1;
          num1= Reservation.rechercher_chambre(type.toLowerCase(), vue.toLowerCase(),nbsem,semdebut);
            if(num1>0){
              tab[k]=num1;
               k++;
             Chambre c1 = new Chambre();
             c1.ReserverChambre(num1, nbsem, semdebut);
           }
           }
          else 
            System.out.println("chambre non dispo");
        }       
    }
     if(k < nb_chambre)
        {
            System.out.println("Modification non aboutie");
            for (int i=0;i<k;i++)
            {
                Chambre c=new Chambre(tab[i]);
                c.AnnulerReservation(tab[i],nbsem, semdebut);
            }
            Chambre c1 = new Chambre();
            int[] t1=r1.getChambres();
            for (int i=0;i<t1.length;i++)
            {
                c1.ReserverChambre(t1[i],r1.getNb_semaine(),r1.semaineDeb());
            }
            r1.AjouterReservation();
        }
     else 
     {  
    Client e1=new Client(r1.getCin_client());
     //r1.annuler();
         Reservation r=new Reservation(e1.getCin(),semdebut,nbsem,nb_chambre,tab);
         r.AjouterReservation();
        SendMail.sendmail(e1,r);
        r.Visualiser();
     }     
    }
  
}

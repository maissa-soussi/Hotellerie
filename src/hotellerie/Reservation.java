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
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


class ErrException extends Exception {
}

public class Reservation {

	private int Num_R;
	private long Cin_client;
	private String Date_Reservation;
	private String Date_Arrivee = "2020/08/02";
	private int Nb_semaine;
	private int Nb_chambre;
	private float prix_total;
	private float prix_reservation;
	private float reste_payer;

	public Reservation(int a,long b,String c,String d,int e,float f,float g, int h)
        {
            Num_R=a;
	    Cin_client=b;
	    Date_Reservation=c;
	    Date_Arrivee =d;
	    Nb_semaine=e;
	    prix_total=f;
	    reste_payer=g;
	    Nb_chambre=h;           
        }
        public Reservation(long cin, int nbs, int nbcham) {
		Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
		Num_R = this.NumR(reservation) + 1;
		Cin_client = cin;
		Nb_semaine = nbs;
		Nb_chambre = nbcham;
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("dd/M/yyyy H':'m", Locale.FRANCE);
		Date_Reservation = f.format(d);

	}

	public Reservation() {
		Num_R = 0;
		Cin_client = 0;
		Nb_semaine = 0;
		Nb_chambre = 0;
		Date_Reservation = "";
		Date_Arrivee = "";
		setPrix_reservation(0);
		setPrix_total(0);
		setReste_payer(0);
	}
	// rechercher une chambre selon les criteres

	public int rechercher_chambre(String type, String vue, int nbSem, int numS) {
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

	// faire la reservation
	public void reserver(String type, String vue, int nbsem, int semdebut) {
		try {
			if (semdebut < 0 || semdebut > 4) {
				throw new ErrException();
			}
			if (semdebut + nbsem > 5) {
				throw new ErrException();
			}
			Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
			Chambre c = new Chambre();
			int numero_chambre = rechercher_chambre(type, vue, nbsem, semdebut);
			if (numero_chambre != 0) {
				this.setNum_R(NumR(reservation) + 1);
				c.ReserverChambre(numero_chambre, nbsem, semdebut);
				this.Calcul_prix_total(type);
				this.Calcul_reste_payer();
				DecimalFormat nf = new DecimalFormat("000");
				Files.write(reservation, ("\n").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				Files.write(reservation, ("" + getNum_R()).getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
				Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				Files.write(reservation, ("" + getCin_client()).getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
				Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				Files.write(reservation, ("" + getDate_Reservation()).getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
				Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				if (semdebut == 2) {
					setDate_Arrivee("2020/08/09");
				} else if (semdebut == 3) {
					setDate_Arrivee("2020/08/16");
				} else if (semdebut == 4) {
					setDate_Arrivee("2020/08/23");
				}
				Files.write(reservation, getDate_Arrivee().getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
				Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				Files.write(reservation, ("" + getNb_semaine()).getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
				Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				Files.write(reservation, ("" + nf.format(numero_chambre)).getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
				Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				Files.write(reservation, ("" + getPrix_total()).getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
				Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				Files.write(reservation, ("" + getReste_payer()).getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
                                Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				Files.write(reservation, ("" + getNb_chambre()).getBytes(), StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
			} else {
				System.out.println("chambre non disponible");
			}
		} catch (ErrException e) {
			System.out.println("Saisir une semaine valide");
		} catch (IOException e) {
			System.out.println("erreur1");
		}

	}
	

	// le prix juste au moment de la reservation
	public void Calcul_prix_total(String type) {
		if (type.contains("simple")) {
			setPrix_total(500);
			setPrix_reservation(500);
		} else if (type.contains("double")) {
			setPrix_total(700);
			setPrix_reservation(700);
		} else if (type.contains("triple")) {
			setPrix_total(1000);
			setPrix_reservation(1000);
		} else if (type.contains("luxe")) {
			setPrix_total(1500);
			setPrix_reservation(1500);
		}
	}

	// calculer le reste ï¿½ paye
	public void Calcul_reste_payer() {
		setReste_payer((float) (getPrix_total() * 0.9));

	}

	// Visualiser une reservation ï¿½ partir de son numï¿½ro
	public void visualiser(int num) {
		try {
			int Num_Chambre = 0;
			Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
			List<String> lignes = Files.readAllLines(reservation);
			System.out.println("Visualisation de la reservation \n");
			long cin = 0;
			for (String ligne : lignes) {
				String[] donnees = ligne.split("-");
				if (Integer.parseInt(donnees[0]) == num) {
					cin = Long.valueOf(donnees[1]);
					Date_Reservation = donnees[2];
					Num_R = num;
					Nb_semaine = Integer.valueOf(donnees[4]);
					Nb_chambre = getNb_chambre();
					Num_Chambre = Integer.valueOf(donnees[5]);
					prix_total = Float.valueOf(donnees[6]);
					reste_payer = Float.valueOf(donnees[7]);
					break;
				}
			}
			System.out.println("\t\t\t Date de reservation : " + getDate_Reservation());
			System.out.println("\t\t\t Numero de reservation : " + getNum_R());
			Client c = new Client(cin);
			System.out.println("Nom : " + c.getNom() + "\t" + "Prenom : " + c.getPrenom());
			System.out.println("E-mail : " + c.getEmail());
			System.out.println("Tel : " + c.getTel());
			System.out.println("Pays : " + c.getPays() + "\n");
			System.out.println("Nombre de chambres : " + getNb_chambre());
			System.out.println("Numero de chambre : " + Num_Chambre);
			System.out.println("Prix total en (dinars) : " + getPrix_total());
			System.out.println("Avance payee en (dinars) : " + getPrix_reservation() * 0.1);
			System.out.println("Date d'arrive : " + getDate_Arrivee());
			System.out.println("Nombre de semaine : " + getNb_semaine());
		} catch (IOException e) {
			System.out.println("erreur de lecture du fichier");
		}

	}

	public static void cloturer(long cin) {
		Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
		Reservation r = new Reservation();
		Client c = new Client(cin);
		String chambres = "";
		chambres = r.facture(reservation, cin, chambres);
		System.out.println("\t\t\t" + "Numéro de(s) chambre(s) résérvée(s) : " + chambres);
		System.out.println("Nom : " + c.getNom() + "\t\t" + "Prénom : " + c.getPrenom());
		System.out.println("E-mail : " + c.getCin());
		System.out.println("Téléphone : " + c.getTel());
		System.out.println("Pays : " + c.getPays() + "\n");
		System.out.println("Prix total à payer (en dinars) : " + r.getPrix_total());
		System.out.println("Reste à payer (en dinars) : " + r.getReste_payer());

	}

	public String facture(Path reservation, long cin, String chambres) {
		try {
			List<String> lignes = Files.readAllLines(reservation);
			for (String ligne : lignes) {
				String[] donnees = ligne.split("-");
				if (donnees[1].equals("" + cin)) {
					setCin_client(Long.valueOf(donnees[1]));
					setDate_reservation(donnees[2]);
					setDate_Arrivee(donnees[3]);
					setNb_semaine(Integer.valueOf(donnees[4]));
					setNb_chambre(Integer.valueOf(donnees[5]));
					chambres = chambres + "-" + ("" + this.Nb_chambre);
					setPrix_total(prix_total + Float.valueOf(donnees[6]));
					setReste_payer(reste_payer + Float.valueOf(donnees[7]));
				}
			}
		} catch (IOException e) {
			System.out.println("erreur lors de la lecture du fichier reservation");
		}
		return chambres;
	}

	public void separer(int num) {
		try {
			boolean ok = false;
			Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
			Path reservation1 = Paths.get("src\\Hotellerie\\Files\\Reservation1.txt");
			Files.createFile(reservation1);
			Path destination = Paths.get("src\\Hotellerie\\Files\\destination.txt");
			Files.createFile(destination);
			List<String> lignes = Files.readAllLines(reservation);
			for (String ligne : lignes) {
				String[] donnees = ligne.split("-");
				if (donnees[0].equals("" + num)) {
					ok = true;
				} else if ((ok == false) && (ligne != null)) {

					Files.write(reservation1, ligne.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
							StandardOpenOption.APPEND);
					Files.write(reservation1, "\n".getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);

				}

				if ((ok == true) && (ligne != null)) {
					Files.write(destination, ligne.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
							StandardOpenOption.APPEND);
					Files.write(destination, "\n".getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);

				}

			}

		} catch (IOException e) {
			System.out.println("erreur");
		}

	}

	public int[] retour_details(Path destination, int num) {
		int[] details = new int[3];
		try {
			List<String> lignes2 = Files.readAllLines(destination);
			for (String ligne : lignes2) {
				String[] donnees = ligne.split("-");
				if (donnees[0].equals("" + num)) {
					details[0] = Integer.valueOf(donnees[5]);
					details[1] = Integer.valueOf(donnees[4]);
					if (donnees[3].equals("2020/08/02")) {
						details[2] = 1;
					} else if (donnees[3].equals("2020/08/09")) {
						details[2] = 2;
					} else if (donnees[3].equals("2020/08/16")) {
						details[2] = 3;
					} else if (donnees[3].equals("2020/08/23")) {
						details[2] = 4;
					}

				}
			}
		} catch (IOException e) {
			System.out.println("erreur lecture fichier destination");
		}
		return details;

	}

	public void annuler(int num) {
		try {
			this.separer(num);

			Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
			Path reservation1 = Paths.get("src\\Hotellerie\\Files\\Reservation1.txt");
			Path destination = Paths.get("src\\Hotellerie\\Files\\destination.txt");
			Path reservation2 = Paths.get("src\\Reservation.txt");
			Files.createFile(reservation2);

			List<String> lignes1 = Files.readAllLines(reservation1);
			List<String> lignes2 = Files.readAllLines(destination);
			for (String ligne : lignes1) {
				if (ligne != null) {
					Files.write(reservation2, ligne.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
					Files.write(reservation2, "\n".getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
				}

			}
			int[] details = retour_details(destination, num);
			Chambre c = new Chambre();
			c.AnnulerReservation(details[0], details[1], details[2]);
			for (String ligne : lignes2) {
				String[] donnees = ligne.split("-");
				if (!(donnees[0].equals("" + num))) {
					String ch = this.modifier_ligne(ligne);
					Files.write(reservation2, ch.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE,
							StandardOpenOption.APPEND);
					Files.write(reservation2, "\n".getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);

				}
			}
			Files.move(reservation2, reservation, StandardCopyOption.REPLACE_EXISTING);
			Files.write(reservation1, "\n".getBytes(), StandardOpenOption.DELETE_ON_CLOSE);
			Files.write(destination, "\n".getBytes(), StandardOpenOption.DELETE_ON_CLOSE);
			cloner();

		} catch (IOException e) {
			System.out.println("erreur");
		}
		

	}
	private void cloner()
    {
         try{
             File fichier1 = new File("src\\Reservation.txt");
        FileReader fichier = new FileReader("src\\Hotellerie\\Files\\Reservation.txt");
        BufferedReader br = new BufferedReader(fichier);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fichier1, true));
        String line=br.readLine();
        if (line != null)
         bufferedWriter.write(line);
        while ((line = br.readLine()) != null) {
       if(line.length()>0) {
           bufferedWriter.write("\r\n");
           bufferedWriter.write(line);
                ;}
        }
        bufferedWriter.close();
        br.close();
        Files.move(Paths.get("src\\Reservation.txt"), Paths.get("src\\Hotellerie\\Files\\Reservation.txt"), StandardCopyOption.REPLACE_EXISTING);
         }
         catch (IOException e) {
        e.printStackTrace();
    }
    }

	public static void nettoyer_fichier() {

		try {

			String fichier = "src\\Hotellerie\\Files\\Reservation.txt";
			InputStream fis = new FileInputStream(fichier);
			Reader reader = new InputStreamReader(fis, "utf-8");
			BufferedReader input = new BufferedReader(reader);
			String line = null;
			StringBuilder str = new StringBuilder();
			while ((line = input.readLine()) != null) {
				str.append(line);
				str.append("\n");
			}
			writeTo(str.toString(), fichier);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private static void writeTo(String data, String fichier)  {
		try {
		FileWriter writer = new FileWriter(fichier);
		writer.write(data.replaceAll("(?m)^[ \t]*\r?\n", ""));
		writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();

		}
	}

	public String modifier_ligne(String ligne) {
		String[] donnees = ligne.split("-");
		int num = Integer.valueOf(donnees[0]);

		num = num - 1;

		String ch = "-" + donnees[1] + "-" + donnees[2] + "-" + donnees[3] + "-" + donnees[4] + "-" + donnees[5] + "-"
				+ donnees[6] + "-" + donnees[7];
		return ("" + num) + ch;
	}
	

	// modifier une reservation
	public void modifier(int numr,String type, String vue, int nbsem, int semdebut) {
		System.out.println("Ancienne résérvation : ");
		this.visualiser(numr);
		this.annuler(numr);
		this.reserver(type, vue, nbsem, semdebut);
		System.out.println("Nouvelle résérvation :");
		this.visualiser(this.getNum_R());
	
	}
        
	/**
	 * @param reservation
	 * @return the Num_R
	 */
	public int getNum_R() {
		return Num_R;
	}

	/**
	 * @param Num_R the Num_R to set
	 */
	public void setNum_R(int Num_R) {
		this.Num_R = Num_R;
	}

	/**
	 * @return the Cin_client
	 */
	public long getCin_client() {
		return Cin_client;
	}

	/**
	 * @param Cin_client the Cin_client to set
	 */
	public void setCin_client(long Cin_client) {
		this.Cin_client = Cin_client;
	}

	/**
	 * @return the Date_Reservation
	 */
	public String getDate_Reservation() {
		return Date_Reservation;
	}

	/**
	 * @return the Date_Arrivee
	 */
	public String getDate_Arrivee() {
		return Date_Arrivee;
	}

	/**
	 * @param Date_Arrivee the Date_Arrivee to set
	 */
	public void setDate_Arrivee(String Date_Arrivee) {
		this.Date_Arrivee = Date_Arrivee;
	}

	public void setDate_reservation(String Date_Reservation) {
		this.Date_Reservation = Date_Reservation;

	}

	/**
	 * @return the Nb_semaine
	 */
	public int getNb_semaine() {
		return Nb_semaine;
	}

	/**
	 * @param Nb_semaine the Nb_semaine to set
	 */
	public void setNb_semaine(int Nb_semaine) {
		this.Nb_semaine = Nb_semaine;
	}

	/**
	 * @return the Nb_chambre
	 */
	public int getNb_chambre() {
		return Nb_chambre;
	}

	/**
	 * @param Nb_chambre the Nb_chambre to set
	 */
	public void setNb_chambre(int Nb_chambre) {
		this.Nb_chambre = Nb_chambre;
	}

	/**
	 * @return the prix_total
	 */
	public float getPrix_total() {
		return prix_total;
	}

	/**
	 * @param prix_total the prix_total to set
	 */
	public void setPrix_total(float prix_total) {
		this.prix_total = prix_total;
	}

	/**
	 * @return the prix_reservation
	 */
	public float getPrix_reservation() {
		return prix_reservation;
	}

	/**
	 * @param prix_reservation the prix_reservation to set
	 */
	public void setPrix_reservation(float prix_reservation) {
		this.prix_reservation = prix_reservation;
	}

	/**
	 * @return the reste_payer
	 */
	public float getReste_payer() {
		return reste_payer;
	}

	/**
	 * @param reste_payer the reste_payer to set
	 */
	public void setReste_payer(float reste_payer) {
		this.reste_payer = reste_payer;
	}

	public int NumR(Path reservation) {
		int a = 0;
		try {
			List<String> lignes = Files.readAllLines(reservation);
			for (String ligne : lignes) {
				String[] donnees = ligne.split("-");
				a = Integer.parseInt(donnees[0]);
			}
		} catch (IOException e) {
			System.out.println("erreur de lecture du fichier");

		}

		return a;
	}
}

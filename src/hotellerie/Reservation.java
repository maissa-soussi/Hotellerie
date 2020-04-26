package hotellerie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	private static int Num_R = 0;
	private long Cin_client;
	private String Date_Reservation;
	private String Date_Arrivee = "2020/08/02";
	private int Nb_semaine;
	private int Nb_chambre;
	private float prix_total;
	private float prix_reservation;
	private float reste_payer;

	public Reservation(int cin, int nbs, int nbcham) {
		++Num_R;
		Cin_client = cin;
		Nb_semaine = nbs;
		Nb_chambre = nbcham;
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("dd/M/yyyy H':'m", Locale.FRANCE);
		Date_Reservation = f.format(d);

	}
	// rechercher une chambre selon les criteres

	public int rechercher_chambre(String type, String vue, int nbSem,int numS) throws IOException {
		Path chambre = Paths.get("src\\Hotellerie\\Files\\Chambre.txt");
		List<String> lignes = Files.readAllLines(chambre);
		int num = 0;
		for (String ligne : lignes) {
			String[] detail = ligne.split("-");
			if ((detail[1].equals(type)) && (detail[2].equals(vue))) {
			boolean verif=true;
                        int i=0;
                        while (verif==true && i < nbSem)
                        {
                            if (Integer.valueOf(detail[2 + numS + i]) != 0)
                            {
                                verif=false;
                            }
                            else i++;
                        }
                        if(verif==true)
                        {
                            num = Integer.parseInt(detail[0]);
				break;
                        }
                       
			}
		}
              //  System.out.println(num); pour tester les numeros de chambres
		return num;
	}

	// faire la reservation
	public void reserver(String type, String vue, int semdebut, int nbsem) {
		try {
			if (semdebut < 0 || semdebut > 4)
				throw new ErrException();
                        if (semdebut+nbsem > 5)
				throw new ErrException();
			Chambre c = new Chambre();
			int numero_chambre = rechercher_chambre(type, vue, nbsem,semdebut);
			if (numero_chambre != 0) {
                            //Num_R++;
				c.ReserverChambre(numero_chambre,nbsem, semdebut);
				this.Calcul_prix_total(type);
				this.Calcul_reste_payer();
				            DecimalFormat nf = new DecimalFormat("000");
                                            System.out.println(nf.format(numero_chambre));

				Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
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
					setDate_Arrivee("2020_08_09");
				} else if (semdebut == 3) {
					setDate_Arrivee("2020-08-16");
				} else if (semdebut == 4) {
					setDate_Arrivee("2020-08-23");
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
				Files.write(reservation, ("\n").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			}
			else {System.out.println("chambre non disponible");}
		} catch (ErrException e) {
			System.out.println("Saisir une semaine valide");
		} catch (IOException e) {
			System.out.println("erreur1");
		}

	}

	// le prix juste au moment de la reservation
	public void Calcul_prix_total(String type) {
		if (type == "simple") {
			setPrix_total(500);
			setPrix_reservation(500);
		} else if (type == "double") {
			setPrix_total(700);
			setPrix_reservation(700);
		} else if (type == "triple") {
			setPrix_total(1000);
			setPrix_reservation(1000);
		} else if (type == "luxe") {
			setPrix_total(1500);
			setPrix_reservation(1500);
		}
	}

	// calculer le reste � paye
	public void Calcul_reste_payer() {
		setReste_payer((float) (getPrix_total() * 0.9));

	}

	// Visualiser une reservation � partir de son num�ro
	public void visualiser(int num) throws IOException {
		Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
		List<String> lignes = Files.readAllLines(reservation);
		long cin = 0;
		for (String ligne : lignes) {
			String[] donnees = ligne.split("-");
			if (Integer.valueOf(donnees[0]) == num) {
				cin = Integer.valueOf(donnees[1]);
				Date_Reservation = donnees[2];
				Num_R = num;
				Nb_semaine = Integer.valueOf(donnees[4]);
				Nb_chambre = Integer.valueOf(donnees[5]);
				prix_total = Float.valueOf(donnees[6]);
				reste_payer = Float.valueOf(donnees[7]);
				break;
			}
		}
		System.out.println("\t\t\t Date de r�servation : " + getDate_Reservation());
		System.out.println("\t\t\t Num�ro de r�servation : " + getNum_R());
		Client c = new Client(cin);
		System.out.println("Nom : " + c.getNom() + "\t" + "Pr�nom" + c.getPrenom());
		System.out.println("E-mail : " + c.getEmail());
		System.out.println("T�l : " + c.getTel());
		System.out.println("Pays : " + c.getPays() + "\n");
		System.out.println("Nombre de chambres : " + getNb_chambre());
		System.out.println("Prix total en (dinars) : " + getPrix_total());
		System.out.println("Avance pay�e en (dinars) :" + getPrix_reservation() * 0.1);
		System.out.println("Date d'arriv�e : " + getDate_Arrivee());
		System.out.println("Nombre de semaine : " + getNb_semaine());

	}

	// modifier une reservation
	public void modifier(int numr) {

	}

	/**
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
	public void setCin_client(int Cin_client) {
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
}
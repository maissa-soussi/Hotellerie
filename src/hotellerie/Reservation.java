package hotellerie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.util.Locale; 

class ErrException extends Exception {
}

public class Reservation {
	private int Num_R = 0;
	private int Cin_client;
	private String Date_Reservation;
	private String Date_Arrivee = "2020/08/02";
	private int Nb_semaine;
	private int Nb_chambre;
	private float prix_total;
	private float prix_reservation;
	private float reste_payer;

	public Reservation(int cin, int nbs, int nbcham) {
		Num_R++;
		Cin_client = cin;
		Nb_semaine = nbs;
		Nb_chambre = nbcham;
                Date d=new Date(); 
                SimpleDateFormat f=new SimpleDateFormat( "dd/M/yyyy H':'m", Locale.FRANCE); 
                Date_Reservation=f.format(d);
                
	}
	// rechercher une chambre selon les criteres

	public int rechercher_chambre(String type, String vue, int sem) throws IOException {
		Path chambre = Paths.get("src\\Hotellerie\\Files\\Chambre.txt");
		List<String> lignes = Files.readAllLines(chambre);
		int num = 0;
		for (String ligne : lignes) {
			String[] detail = ligne.split("-");
			if ((detail[1].equals(type)) && (detail[2].equals(vue)) && (Integer.valueOf(detail[2 + sem]) == 0)) {
				num = Integer.parseInt(detail[0]);
				break;

			}
		}
		return num;
	}

	// faire la reservation
	public void reserver(String type, String vue, int sem) throws IOException {
		try {
			if (sem < 0 || sem > 4)
				throw new ErrException();
			Chambre c = new Chambre();
			int numero_chambre = rechercher_chambre(type, vue, sem);
			c.ReserverChambre(numero_chambre, sem);
			this.Calcul_prix_total(type);
			this.Calcul_reste_payer();
			Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
			Files.write(reservation, ("" + getNum_R()).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + getCin_client()).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + getDate_Reservation()).getBytes(), StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			if (sem == 2) {
				setDate_Arrivee("2020_08_09");
			} else if (sem == 3) {
				setDate_Arrivee("2020-08-16");
			} else if (sem == 4) {
				setDate_Arrivee("2020-08-23");
			}
			Files.write(reservation, getDate_Arrivee().getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + getNb_semaine()).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + numero_chambre).getBytes(), StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + getPrix_total()).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + getReste_payer()).getBytes(), StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);
			Files.write(reservation, ("\n").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
		} catch (ErrException e) {
			System.out.println("Saisir une semaine valide");
		}

	}

	// le prix juste au moment de la reservation
	public void Calcul_prix_total(String type) {
		if (type == "simple") {
			setPrix_total(500);
		} else if (type == "double") {
			setPrix_total(700);
		} else if (type == "luxe") {
			setPrix_total(1000);
		}
	}


	// calculer le reste � paye
	public void Calcul_reste_payer() {
		setReste_payer((float) (getPrix_total()*0.9));

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
    public int getCin_client() {
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

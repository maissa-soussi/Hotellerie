package hotellerie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class ErrException extends Exception {
}

public class Reservation {
	private int Num_R = 0;
	private int Cin_client;
	private LocalDateTime Date_Reservation;
	private String Date_Arrivee = "2020-08-02";
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
			this.Calcul_prix_reservation(type);
			this.Calcul_reste_payer();
			Date_Reservation = LocalDateTime.now();
			Path reservation = Paths.get("src\\Hotellerie\\Files\\Reservation.txt");
			Files.write(reservation, ("" + Num_R).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + Cin_client).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + Date_Reservation).getBytes(), StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			if (sem == 2) {
				Date_Arrivee = "2020_08_09";
			} else if (sem == 3) {
				Date_Arrivee = "2020-08-16";
			} else if (sem == 4) {
				Date_Arrivee = "2020-08-23";
			}
			Files.write(reservation, Date_Arrivee.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + Nb_semaine).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + numero_chambre).getBytes(), StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + prix_total).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("-").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			Files.write(reservation, ("" + reste_payer).getBytes(), StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);
			Files.write(reservation, ("\n").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
		} catch (ErrException e) {
			System.out.println("Saisir une semaine valide");
		}

	}

	// le prix juste au moment de la reservation
	public void Calcul_prix_reservation(String type) {
		if (type == "simple") {
			prix_total = 500;
		} else if (type == "double") {
			prix_total = 700;
		} else if (type == "luxe") {
			prix_total = 1000;
		}
	}

	// Calculer le prix total
	public void Calcul_prix_total(String type) {
		float prix_commandes = 0;// attendre farouk
		prix_total = prix_reservation + prix_commandes;
	}

	// calculer le reste à paye
	public void Calcul_reste_payer() {
		reste_payer = (float) (prix_total - (prix_reservation * 0.1));

	}

	// modifier une reservation
	public void modifier(int numr) {

	}
}

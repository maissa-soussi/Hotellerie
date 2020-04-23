package hotellerie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

public class Reservation {
	private int Num_R;
	private int Cin_client;
	private Date Date_Reservation;
	private Date Date_Arrivee;
	private int Nb_semaine;
	private int Nb_chambre;
	private float prix_total;
	private float rest_payer;

	public Reservation(int numr, int cin, Date arrivee, int nbs, int nbcham) {
		Num_R = numr;
		Cin_client = cin;
		Date_Arrivee = arrivee;
		Nb_semaine = nbs;
		Nb_chambre = nbcham;
	}

	public int rechercher_chambre(String type, String vue, int sem) throws IOException { //rechercher une chambre selon les criteres
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

	public void reserver(String type, String vue, int sem) throws IOException {//faire la reservation
		Chambre c=new Chambre();
		int numero_chambre=rechercher_chambre(type,vue,sem);
		c.ReserverChambre(numero_chambre,sem);
		
		
	}

}

package hotellerie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hotellerie {

    public static void main(String[] args) {
        System.out.println("Bonjour ! ");
        Menu();
    }    
    
    public static void Menu() {
        System.out.println("\n \n Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Reservation     2- Reception     3- Restaurant   4-Resultats Feedback     0- quitter");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        while ((nb != 1) && (nb != 2) && (nb != 3) && (nb != 4) && (nb != 0)) {
            System.out.println("\n Veuillez entrez un chiffre parmis 1, 2, 3, 4, 0 ");
            sc = new Scanner(System.in);
            nb = sc.nextInt();            
        }
        if (nb == 1) {
            MenuReservation();
        } else if (nb == 2) {
            MenuReception();
        } else if (nb == 3) {
            MenuRestaurant();
        } else if (nb == 4) {
            //MenuFeedback
            Feedback f = new Feedback();
            f.resultatsFeedback();
        } else if (nb == 0) {
            System.out.println("\n \n MERCI A BIENTOT");
            System.exit(0);
        }
    }
    
    public static void MenuReception() {
        
        System.out.println("\n Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Visualiser une reservation     2- Clôturer un séjour   3-Etat des chammbres   4-Recette   0- quitter");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        while ((nb != 1) && (nb != 2) && (nb != 0) && (nb != 3) && (nb != 4)) {
            System.out.println("Veuillez entrez un chiffre parmis 1, 2,3,4, 0 ");
            sc = new Scanner(System.in);
            nb = sc.nextInt();            
        }
        if (nb == 1) {
            System.out.println("Entrer le numero de reservation  ou 0 Pour quitter");
            Scanner sc1 = new Scanner(System.in);
            int nb1 = sc1.nextInt();
            while (verifNum_R1(nb1) == false) {
                System.out.println("\n numero de reservation inexistant SVP essayer de nouveau");
                sc1 = new Scanner(System.in);
                nb1 = sc1.nextInt();                
            }
            if (nb1 == 0) {
                MenuReception();
            } else {
                Reservation r = new Reservation(nb1);
                r.Visualiser();
                MenuReception();
            }
        } else if (nb == 2) {
            System.out.println("Entrer le numero de reservation  ou 0 Pour quitter");
            Scanner sc1 = new Scanner(System.in);
            int nb1 = sc1.nextInt();
            while (nb1 != 0 && verifNum_R1(nb1) == false) {
                System.out.println("\n numero de reservation inexistant SVP essayer de nouveau");
                sc1 = new Scanner(System.in);
                nb1 = sc1.nextInt();                
            }
            if (nb1 == 0) {
                MenuReception();
            } else {
                Reservation r = new Reservation(nb1);
                r.cloturer();
                System.out.println("\n voulez-vous repondre à nos questions");
                System.out.println("1-Oui | 2-Non");
                int choix = sc.nextInt();
                while (choix != 1 && choix != 2) {                    
                    System.out.println("1-Oui | 2-Non");
                    choix = sc.nextInt();
                }
                if (choix == 1) {
                    Feedback f = new Feedback();
                    f.obtenirFeedback();
                }
                MenuReception();
            }
        } else if (nb == 3) {
            Chambre c = new Chambre();
            c.AfficherChambres();
            MenuReception();
        } else if (nb == 4) {
            Reservation r = new Reservation();
            r.visualiserRecette();
            MenuReception();
        } else if (nb == 0) {
            Menu();
        }
        
    }
    
    private static void MenuReservation() {        
        System.out.println("\n \n Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Effectuer une reservation     2- modifier une reservation     3- annuler une réservation     0- quitter");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        while ((nb != 1) && (nb != 2) && (nb != 3) && (nb != 0)) {
            System.out.println("\nVeuillez entrez un chiffre parmis 1, 2, 3, 0 ");
            sc = new Scanner(System.in);
            nb = sc.nextInt();
        }
        if (nb == 1) {
            
            System.out.println("\nEntrer CIN  ou 0 Pour quitter");
            Scanner sc1 = new Scanner(System.in);
            String nb1s = sc1.nextLine();
            while (verifcin(nb1s) == false) {
                System.out.println("\nEntrer le CIN  ou 0 Pour quitter");
                sc1 = new Scanner(System.in);
                nb1s = sc1.nextLine();
            }
            long nb1 = Long.valueOf(nb1s);
            if (nb1 == 0) {
                MenuReception();
            } else {
                if (Client.Verif(nb1)) {
                    Client e = new Client(nb1);
                    System.out.println("\nVoici les coordonnees du client");
                    e.affiche();
                    
                  System.out.println("\n 1- Reserver une chambre     2- Modifier vos cordonnées     0- Annuler");
                int nb2 = sc1.nextInt();
                if (nb2 == 1) {
                    MenuReserver(nb1);
                    MenuReservation();
                } else if (nb2 == 2) {
                  MenuModifierClient(nb1);
                    MenuReservation();
                } else if (nb2 == 0) {
                    MenuReservation();
                }
                    
                    
                } else {
                    System.out.println("\nVous etes un nouveau client :");
                    Scanner sc4 = new Scanner(System.in);
                    System.out.println("Veuillez saisir votre Nom : ");
                    String nom = sc4.nextLine();
                    System.out.println("Veuillez saisir votre Prenom : ");
                    String prenom = sc4.nextLine();                    
                    System.out.println("Veuillez saisir votre Date de naissance sous ce format : jj/mm/aaaa :");
                    String date = sc4.nextLine();                    
                    while (verifFormatDate(date) == false) {
                        System.out.println("\nLa format du date est incorrecte : ");
                        date = sc4.nextLine();
                    }
                    System.out.println("Veuillez saisir votre adresse Email : ");
                    String mail = sc4.nextLine();
                    while (isEmailAdress(mail) == false) {
                        System.out.println("\nLa format du mail est incorrecte .");
                        mail = sc4.nextLine();
                    }
                    System.out.println("Veuillez saisir votre numero de Telephone : ");
                    String tel1 = sc4.nextLine();
                    while ( ! isInteger(tel1)){
                    	System.out.println("\nIl faut entrer un numero de telephone valide");
                        tel1 = sc4.nextLine();
                    }
                    int tel =Integer.valueOf(tel1);
                    while (islength8(tel) == false) {
                        System.out.println("\nIl faut entrer un numero de 8 chiffres");
                        tel = sc4.nextInt();
                    }                    
                    System.out.println(" Veuillez saisir votre Pays d'habitat");
                    Scanner p = new Scanner(System.in);
                    String pays = p.nextLine();
                    Client e1 = new Client(nb1, nom, prenom, date, mail, tel, pays);
                    e1.affiche();
                    e1.Ajouter();  
                    MenuReserver(nb1);
                   Menu();
                }
            }
            
            
        } else if (nb == 2) {
            
            System.out.println("\n Entrer le numero de reservation  ou 0 Pour quitter");
            Scanner sc2 = new Scanner(System.in);
            int nb2 = sc2.nextInt();
            while (verifNum_R1(nb2) == false) {
                System.out.println("\n numero de reservation inexistant SVP essayer de nouveau");
                sc2 = new Scanner(System.in);
                nb2 = sc2.nextInt();                
            }
            if (nb2 == 0) {
                MenuReservation();
            } else {
                Reservation r = new Reservation(nb2);
                r.Visualiser();
                MenuModifier(nb2);
                //appel au fonction Modifier 
                Menu();
            }
        } else if (nb == 3) {
            System.out.println("\n Entrer le numero de reservation  ou 0 Pour quitter");
            Scanner sc3 = new Scanner(System.in);
            int nb3 = sc3.nextInt();
            while (verifNum_R1(nb3) == false) {
                System.out.println("\n numero de reservation inexistant SVP essayer de nouveau");
                sc3 = new Scanner(System.in);
                nb3 = sc3.nextInt();                
            }
            if (nb3 == 0) {
                Menu();
            } else {
                Reservation r = new Reservation(nb3);
                r.annuler();
                Menu();
            }
        } else if (nb == 0) {            
            Menu();
        }        
        
    }
    
    public static void MenuReserver(long nb1) {
        Scanner input1 = new Scanner(System.in);
        System.out.println("\n \n Combien de semaine voulez-vous allouez? (1|2|3|4)");
        int nbsem = input1.nextInt();
        while (VerifNbsem(nbsem) == false) {
            System.out.println("\n Nombre de semaine invalide");
            System.out.println("Combien de semaine voulez-vous allouez? (1|2|3|4)");
            nbsem = input1.nextInt();
        }
        Scanner input2 = new Scanner(System.in);
        System.out.println("Combien de chambre voulez-vous reservez?");
        int nb_chambre = input2.nextInt();
        while (nb_chambre < 0) {
            System.out.println("\n Saisir un Nombre de chambre correcte");
            System.out.println("Combien de chambre voulez-vous reservez?");
            nb_chambre = input2.nextInt();
        }
        Scanner input5 = new Scanner(System.in);
        System.out.println("A quelle semaine voulez-vous debuter votre sejour ?");
        int semdebut = input5.nextInt();
        while (semdebut + nbsem > 5 || semdebut <= 0) {
            System.out.println("\n saisir un nombre de semaine correcte");
            System.out.println("A quelle semaine voulez-vous debuter votre sejour ?");
            semdebut = input5.nextInt();
        }
        // Reservation r = new Reservation(nb1, nbsem, nb_chambre);//initialiser une reservation avec cin=250 , nb semaine � louer =1 ,nb de chambre a louer =1 
        
        int[] tab = new int[nb_chambre];
        int k = 0;
        for (int i = 1; i <= nb_chambre; i++) {
            Scanner input3 = new Scanner(System.in);
            System.out.println("Quel type voulez-vous choisir pour la chambre num " + i + "? (simple|double|triple|luxe)");
            String type = input3.nextLine();
            while (VerifTypeC(type) == false) {
                System.out.println("\n type invalide");
                System.out.println("Quel type voulez-vous choisir ? (simple|double|triple|luxe)");
                type = input3.nextLine();
            }
            Scanner input4 = new Scanner(System.in);
            System.out.println("Voulez vous choisir une chambre avec vue mer/piscine/jardin ");
            String vue = input4.nextLine();
            while (VerifVueC(vue) == false) {
                System.out.println("\n vue invalide");
                System.out.println("Voulez vous choisir une chambre avec vue mer/piscine/jardin ");
                vue = input4.nextLine();
            }
            int num;
            num = Reservation.rechercher_chambre(type.toLowerCase(), vue.toLowerCase(), nbsem, semdebut);
            if (num > 0) {
                tab[k] = num;
                k++;
                Chambre c = new Chambre();
                c.ReserverChambre(num, nbsem, semdebut);
            } else {
                Chambre c = new Chambre();
                if (c.Suggestion(type.toLowerCase(), nbsem, semdebut)) {
                    vue = "aaaa";
                    while (!vue.equalsIgnoreCase("non") && VerifVueC(vue) == false) {
                        System.out.println("\n entrer une autre vue sinon saisir non");
                        vue = input4.nextLine();
                    }
                    int num1;
                    num1 = Reservation.rechercher_chambre(type.toLowerCase(), vue.toLowerCase(), nbsem, semdebut);
                    if (num1 > 0) {
                        tab[k] = num1;
                        k++;
                        Chambre c1 = new Chambre();
                        c1.ReserverChambre(num1, nbsem, semdebut);
                    }
                } else {
                    System.out.println("\n chambre non dispo");
                }
            }            
        }
        if (k < nb_chambre) {
            System.out.println("\n Reservation non aboutie");
            for (int i = 0; i < k; i++) {
                Chambre c = new Chambre(tab[i]);
                c.AnnulerReservation(tab[i], nbsem, semdebut);
            }
        } else {
            Client e1 = new Client(nb1);
            Reservation r = new Reservation(nb1, semdebut, nbsem, nb_chambre, tab);
            r.AjouterReservation();
            SendMail.sendmail(e1, r);
            r.Visualiser();
        }        
    }
    
    public static void MenuModifierClient(long nb1)
    {
        Client e=new Client(nb1);
        System.out.println("Veuillez saisir votre adresse Email : ");
        Scanner sc4 = new Scanner(System.in);
                    String mail = sc4.nextLine();
                    while (isEmailAdress(mail) == false) {
                        System.out.println("\nLa format du mail est incorrecte .");
                        mail = sc4.nextLine();
                    }
                    System.out.println("Veuillez saisir votre numero de Telephone : ");
                    String tel1 = sc4.nextLine();
                    while ( ! isInteger(tel1)){
                    	System.out.println("\nIl faut entrer un numero de telephone valide");
                        tel1 = sc4.nextLine();
                    }
                    int tel =Integer.valueOf(tel1);
                    while (islength8(tel) == false) {
                        System.out.println("\nIl faut entrer un numero de 8 chiffres");
                        tel = sc4.nextInt();
                    }                    
                    System.out.println(" Veuillez saisir votre Pays d'habitat");
                    Scanner p = new Scanner(System.in);
                    String pays = p.nextLine();
                    e.Modifier(nb1, mail, tel, pays);
                    System.out.println("La modification est effectuee avec succes");
    }
    
    public static void MenuRestaurant() {
        System.out.println("\n \n entrer le nom du restaurant Royale/Italiano/Mexicano");
        Scanner s1 = new Scanner(System.in);
        String no = s1.nextLine();
        while (verifNom_R(no) == false) {
            System.out.println("\n restaurant inexistant SVP essayez de nouveau");
            s1 = new Scanner(System.in);
            no = s1.nextLine();
        }
        System.out.println("\n Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Effectuer une commande     2- Suivre les stats du restaurant     0- Annuler");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        while ((nb != 1) && (nb != 2) && (nb != 0)) {
            System.out.println("Veuillez entrez un chiffre parmis 1, 2, 0 ");
            sc = new Scanner(System.in);
            nb = sc.nextInt();
        }
        if (nb == 1) {
            MenuCommande(no);
            MenuRestaurant();
        } else if (nb == 2) {
            MenuStat(no);
            MenuRestaurant();
        } else if (nb == 0) {
            Menu();
        }
    }
    
    private static void MenuCommande(String nom) {
        System.out.println("\n \n 1- Effectuer une commande     0- Annuler");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        while ((nb != 1) && (nb != 0)) {
            System.out.println("Veuillez entrez un chiffre parmis 1, 0 ");
            sc = new Scanner(System.in);
            nb = sc.nextInt();
        }
        if (nb == 1) {
            Restaurant R = new Restaurant(nom);
            System.out.println("entrer le numero de reservation");
            Scanner c1 = new Scanner(System.in);
            int n = c1.nextInt();
            while (Reservation.verifNum_R(n) == false) {
                System.out.println("\n numero de reservation inexistant SVP essayez de nouveau");
                c1 = new Scanner(System.in);
                n = c1.nextInt();
            }
            System.out.println("entrer le code du plat");
            Scanner c2 = new Scanner(System.in);
            String co = c2.nextLine();
            while (verifCode_Plat(co, nom) == false) {
                System.out.println("\n plat inexistant SVP essayez de nouveau");
                c2 = new Scanner(System.in);
                co = c2.nextLine();
            }
            System.out.println("entrer le nombre de plats");
            Scanner c3 = new Scanner(System.in);
            int nbp = c3.nextInt();
            System.out.println("entrer le numero de la journee de la commande");
            Scanner c4 = new Scanner(System.in);
            int d = c4.nextInt();
            while (verifDate_C(d) == false) {
                System.out.println("\n numero de la journee invalede SVP essayez de nouveau");
                c4 = new Scanner(System.in);
                d = c4.nextInt();
            }
            R.effectuer(n, co, nbp, d);
            MenuCommande(nom);
        } else if (nb == 0) {
            Menu();
        }
    }
    
    private static void MenuStat(String no) {
        Restaurant R = new Restaurant(no);
        System.out.println("\n \n Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Visualiser la recette d'une journee");
        System.out.println(" 2- Visualiser la recette de la semaine courante");
        System.out.println(" 3- Visualiser la  fréquence de demande de chaque plat pendant la semaine courante");
        System.out.println(" 0- Annuler");
        Scanner a = new Scanner(System.in);
        int i = a.nextInt();
        while ((i != 1) && (i != 2) && (i != 3) && (i != 0)) {
            System.out.println("Veuillez entrez un chiffre parmis 1, 2, 3, 0 ");
            a = new Scanner(System.in);
            i = a.nextInt();
        }
        if (i == 1) {
            System.out.println("entrez le numero de la journee");
            Scanner b = new Scanner(System.in);
            int j = b.nextInt();
            while (verifDate_C(j) == false) {
                System.out.println("\n numero de la journee invalede SVP essayez de nouveau");
                b = new Scanner(System.in);
                j = b.nextInt();
            }
            R.recette_journee(j);
            MenuStat(no);
        }
        if (i == 2) {
            R.recette_semaine();
            MenuStat(no);
        }
        if (i == 3) {
            R.frequence();
            MenuStat(no);
        }
        if (i == 0) {
            Menu();
        }
    }
    
    // menu de modif d'une reservation
    public static void MenuModifier(int numR) {
        Reservation.Modifier(numR);
    }

    // verifier si la reservation existe ou non
    public static Boolean VerifTypeC(String r) {
        Boolean bo = false;
        if ((r.equalsIgnoreCase("simple")) || (r.equalsIgnoreCase("double")) || (r.equalsIgnoreCase("triple")) || (r.equalsIgnoreCase("luxe"))) {
            bo = true;
        }
        return bo;
    }
    
    public static Boolean VerifVueC(String r) {
        Boolean bo = false;
        if ((r.equalsIgnoreCase("mer")) || (r.equalsIgnoreCase("piscine")) || (r.equalsIgnoreCase("jardin"))) {
            bo = true;
        }
        return bo;
    }
    
    public static Boolean VerifNbsem(int nb) {
        if (nb < 1 || nb > 4) {
            return false;
        } else {
            return true;
        }
    }
    public static boolean isInteger(String str) {
        if(str == null ) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))) {
                return false;
            } 
        }
        return true;
    }
    
  

    /* verifier si le plat du code co appartient au restaurant de nom r ou non */
    private static Boolean verifCode_Plat(String co, String r) {
        Boolean bo = false;
        try {
            File f = new File("src\\Hotellerie\\Files\\" + r + ".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while (((readLine = b.readLine()) != null) && (bo == false)) {
                String[] tab = readLine.split("\\*");
                if (tab[0].equals(co)) {
                    bo = true;
                }
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bo;
    }
    
    //verifier si n=0 ou n est num de reservation existant
    public static Boolean verifNum_R1(int n) {
        Boolean bo = false;
        try {
            File f = new File("src\\Hotellerie\\Files\\Reservation.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while (((readLine = b.readLine()) != null) && (bo == false)) {
                String[] tab = readLine.split("\\*");
                if (Integer.parseInt(tab[0]) == n) {
                    bo = true;
                }
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (bo || n == 0);
    }

    // verifier si le restaurant du nom r existe ou non
    private static Boolean verifNom_R(String r) {
        Boolean bo = false;
        if (("Royale".equals(r)) || ("Mexicano".equals(r)) || ("Italiano".equals(r))) {
            bo = true;
        }
        return bo;
    }

    //verifier si le numero de journee est valide ou non
    private static Boolean verifDate_C(int d) {
        Boolean bo = false;
        if ((d <= 7) && (d >= 1)) {
            bo = true;
        }
        return bo;
    }

    //verifier si la format du date est correcte
    private static boolean verifFormatDate(String date) {
        boolean v = false;
        if (date.length() == 10) {
            String jour;
            String mois;
            String annee;
            jour = date.substring(0, 2);
            mois = date.substring(3, 5);
            annee = date.substring(6, 10);
            String sl1;
            String sl2;
            sl1 = date.substring(2, 3);
            sl2 = date.substring(5, 6);
            int m;
            m = Integer.parseInt(mois);
            int j;
            j = Integer.parseInt(jour);
            int a;
            a = Integer.parseInt(annee);
            
            if ((j > 0) && (j <= 31) && (m > 0) && (m <= 12) && (a > 1929) && (a <= 2003) && ("/".equals(sl1)) && ("/".equals(sl2))) {
                v = true;
            }
        }        
        return v;
    }
    // verifier si l'adresse mail est valide

    public static boolean isEmailAdress(String email) {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }

    // verifier la longueur du tel = 8
    public static boolean islength8(int num) {
        String chaine = Integer.toString(num);
        return (8 == chaine.length());
    }
    
    private static Boolean verifcin(String n) {
        Boolean bo = false;
        if (n.length() == 8) {
            bo = true;
        }        
        return (bo || "0".equals(n));
    }
}

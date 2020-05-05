package hotellerie;

import static java.awt.PageAttributes.MediaType.A;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javafx.scene.input.KeyCode.Z;
import javax.mail.MessagingException;

public class Hotellerie {
    public static void main(String[] args) {
        System.out.println("Bonjour ! ");
        Menu();
    } 
    
    public static void Menu() {
        System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Reservation     2- Reception     3- Restaurant");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        if (nb == 1) {
            MenuReservation();
            Menu();
        } else if (nb == 2) {
            MenuReception();
            Menu();
        } else if (nb == 3) {
            MenuRestaurant();
            Menu();
        } else {
            System.out.println("Operation annulee");
        }
    }
    
    public static void MenuReception()
    {
        System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Visualiser une reservation     2- Clôturer un séjour     0- quitter");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        if (nb == 1) {
            System.out.println("Bonjour ! ");
            System.out.println("Entrer le numero de reservation  ou 0 Pour quitter");
            Scanner sc1 = new Scanner(System.in);
            int nb1 = sc1.nextInt();
            if (nb1 == 0) {
            MenuReception();
            } else {
                Reservation r=recherchereservation1(nb1);
                r.visualiser(nb1);
                MenuReception();
            }
        } 
        else if (nb == 2) {
            System.out.println("Bonjour ! ");
            System.out.println("Entrer le CIN  ou 0 Pour quitter");
            Scanner sc1 = new Scanner(System.in);
            long nb1 = sc1.nextLong();
            if (nb1 == 0) {
            MenuReception();
            } else {
                Reservation.cloturer(nb1);
                MenuReception();
            }
        }
        else if (nb == 0) {                       
            Menu();
        }                    
    }
    
    //retourner la reservation avec numr=nb1
    public static Reservation recherchereservation1(int nb1)
    {
        Reservation r = null;
        try {
            File f = new File("src\\Hotellerie\\Files\\Reservation.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            int i=0;
            Boolean bo=false;
            String readLine = "";
            
            while (((readLine = b.readLine()) != null) && (bo==false)) {
                String[] tab=readLine.split("-");
                if(Integer.parseInt(tab[0])==nb1)
                {
                    r=new Reservation(Integer.parseInt(tab[0]),Long.parseLong(tab[1]),tab[2],tab[3],Integer.parseInt(tab[4]), Float.parseFloat(tab[6]),Float.parseFloat(tab[7]),Integer.parseInt(tab[8]));                   
                }
                i++;
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }
    
    //retourner la reservation avec CIN=nb1
    public static Reservation recherchereservation2(long nb1)
    {
        Reservation r = null;
        try {
            File f = new File("src\\Hotellerie\\Files\\Reservation.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            int i=0;
            Boolean bo=false;
            String readLine = "";
            
            while (((readLine = b.readLine()) != null) && (bo==false)) {
                String[] tab=readLine.split("-");
                if(Long.parseLong(tab[1])==nb1)
                {
                    r=new Reservation(Integer.parseInt(tab[0]),Long.parseLong(tab[1]),tab[2],tab[3],Integer.parseInt(tab[4]), Float.parseFloat(tab[6]),Float.parseFloat(tab[7]),Integer.parseInt(tab[8]));                   
                }
                i++;
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }
    
    private static void MenuReservation()
    {
        System.out.println("Bonjour ! ");
        System.out.println("Entrer CIN  ou 0 Pour quitter");
        Scanner sc1 = new Scanner(System.in);
        long nb1 = sc1.nextLong();
        if (nb1 == 0) {
            Menu();
        } else {
            if (Client.Verif(nb1)) {
                Client e = new Client(nb1);
                System.out.println("Voici les coordonnees du client");
                e.affiche();
                System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
                System.out.println(" 1- Effectuer une reservation     2- modifier une reservation     3- annuler une réservation     0- quitter");
                Scanner sc = new Scanner(System.in);
                int nb = sc.nextInt();
                if (nb == 1) {
                    MenuReserver(nb1);
                    Menu();
                    } 
                else if (nb == 2) {
                    MenuModifier(nb1);
                    Menu();
                }
                else if (nb == 3) {
                    Reservation r=recherchereservation2(nb1);
                    r.annuler(r.getNum_R());
                    Menu();
                    }
                else if (nb == 0) {                       
                    Menu();
                }                    
                
            } else {
                System.out.println("C'est un nouveau client");
                Scanner sc4 = new Scanner(System.in);
                System.out.println("nom");
                String nom = sc4.nextLine();
                System.out.println("Prenom");
                String prenom = sc4.nextLine();
                System.out.println("Date de naissance jj/mm/aaaa");
                String date = sc4.nextLine();
                
                while (verifFormatDate(date)==false)
                    {
                        System.out.println("La format du date est incorrecte");
                        date = sc4.nextLine();
                    }
		System.out.println("Email");
		String mail = sc4.nextLine();
                while (isEmailAdress(mail)==false)
                    {
                        System.out.println("La format du mail est incorrecte");
                        mail = sc4.nextLine();
                    }
		System.out.println("Telephone");
		int tel = sc4.nextInt();
                while (islength8(tel)==false)
                    {
                        System.out.println("Il faut entrer un numero de 8 chiffres");
                        tel = sc4.nextInt();
                    }               
                System.out.println("Pays d'habitat");
                Scanner p = new Scanner(System.in);
                String pays = p.nextLine();
                Client e1 = new Client(nb1, nom, prenom, date, mail, tel, pays);
                e1.affiche();
                e1.Ajouter();               
            }
        }
        System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Effectuer une reservation     0- Annuler");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        if (nb == 1) {
            MenuReserver(nb1);
            MenuReservation();
        } else if (nb == 0) {
            MenuReservation();
    }
    }
    
    public static void MenuModifier(long nb1)
    {
        //modifier reservation
    }
    public static void MenuReserver(long nb1) {
        Scanner input1 = new Scanner(System.in);
        System.out.println("Combien de semaine voulez-vous allouez? (1|2|3|4)");
        int nbsem = input1.nextInt();
        while (VerifNbsem(nbsem) == false) {
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
        Reservation r = new Reservation(nb1, nbsem, nb_chambre);//initialiser une reservation avec cin=250 , nb semaine � louer =1 ,nb de chambre a louer =1 
       int nbReserve=0;
        for (int i=1;i<=nb_chambre;i++)
        {
        Scanner input3 = new Scanner(System.in);
        System.out.println("Quel type voulez-vous choisir ? (simple|double|triple|luxe)");
        String type = input3.nextLine();
        while (VerifTypeC(type) == false) {
            System.out.println("type invalide");
            System.out.println("Quel type voulez-vous choisir ? (simple|double|triple|luxe)");
            type = input3.nextLine();
        }
        Scanner input4 = new Scanner(System.in);
        System.out.println("Voulez vous choisir une chambre avec vue mer/piscine/jardin ");
        String vue = input4.nextLine();
        while (VerifVueC(vue) == false) {
            System.out.println("vue invalide");
            System.out.println("Voulez vous choisir une chambre avec vue mer/piscine/jardin ");
            vue = input4.nextLine();
        }
       
        if(r.rechercher_chambre(type.toLowerCase(), vue.toLowerCase(),nbsem,semdebut)> 0){
        r.reserver(type.toLowerCase(), vue.toLowerCase(), nbsem,semdebut);// appel � la methode rechercher_chambre pour chercher la chambre disponible selon ces criteres et reserver chambre pour reserver cette chambre
        nbReserve++;
        }
        else
        {
           Chambre c=new Chambre();
           if(c.Suggestion(type.toLowerCase(), nbsem, semdebut)){
           vue="aaaa";
            while (VerifVueC(vue) == false) {
            System.out.println("entrer une autre vue");
            vue = input4.nextLine();
        }
         if(r.rechercher_chambre(type.toLowerCase(), vue.toLowerCase(),nbsem,semdebut)> 0){
        r.reserver(type.toLowerCase(), vue.toLowerCase(), nbsem,semdebut);// appel � la methode rechercher_chambre pour chercher la chambre disponible selon ces criteres et reserver chambre pour reserver cette chambre
        nbReserve++;   
           }
           }
          else 
            System.out.println("chambre non dispo");
        }       
    }
     if(nbReserve < nb_chambre)
        {
            for (int j=1;j<=nbReserve;j++)
            r.annuler(r.getNum_R());
            System.out.println("chambres non dispo");
        }
     else 
     { 
         Client e1=new Client(nb1);
         System.out.println("La résérvation a été efféctuée avec succés .");
        SendMail.sendmail(e1,r);
     }     
    }
    
    public static void MenuRestaurant()
    {
        System.out.println("entrer le nom du restaurant Royale/Italiano/Mexicano");
            Scanner s1 = new Scanner(System.in);
            String no = s1.nextLine();
            while (verifNom_R(no) == false) {
                System.out.println("restaurant inexistant SVP essayez de nouveau");
                s1 = new Scanner(System.in);
                no = s1.nextLine();
            }
        System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Effectuer une commande     2- Suivre les stats du restaurant     0- Annuler");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        if (nb == 1) {
            MenuCommande(no);
            MenuRestaurant();
        } else if (nb == 2) {
            MenuStat(no);
            MenuRestaurant();
    }
        else if (nb == 0) {
            Menu();
    }
    }
    
    private static void MenuCommande(String nom) {
        System.out.println(" 1- Effectuer une commande     0- Annuler");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        if (nb == 1) {
            Restaurant R = new Restaurant(nom);
            System.out.println("entrer le numero de reservation");
            Scanner c1 = new Scanner(System.in);
            int n = c1.nextInt();
            while (verifNum_R(n) == false) {
                System.out.println("numero de reservation inexistant SVP essayez de nouveau");
                c1 = new Scanner(System.in);
                n = c1.nextInt();
            }
            System.out.println("entrer le code du plat");
            Scanner c2 = new Scanner(System.in);
            String co = c2.nextLine();
            while (verifCode_Plat(co, nom) == false) {
                System.out.println("plat inexistant SVP essayez de nouveau");
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
                System.out.println("numero de la journee invalede SVP essayez de nouveau");
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
            System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
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
                    System.out.println("numero de la journee invalede SVP essayez de nouveau");
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
    
	// verifier si la reservation existe ou non
	private static Boolean VerifTypeC(String r) {
        Boolean bo = false;
        if ((r.equalsIgnoreCase("simple")) || (r.equalsIgnoreCase("double")) || (r.equalsIgnoreCase("triple")) || (r.equalsIgnoreCase("luxe"))) {
            bo = true;
        }
        return bo;
    }

    private static Boolean VerifVueC(String r) {
        Boolean bo = false;
        if ((r.equalsIgnoreCase("mer")) || (r.equalsIgnoreCase("piscine")) || (r.equalsIgnoreCase("jardin"))) {
            bo = true;
        }
        return bo;
    }

    private static Boolean VerifNbsem(int nb) {
        if (nb < 1 || nb > 4) {
            return false;
        } else {
            return true;
        }
    }
    
    private static Boolean verifNum_R(int n) {
        Boolean bo = false;
        try {
            File f = new File("src\\Hotellerie\\Files\\Reservation.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while (((readLine = b.readLine()) != null) && (bo == false)) {
                String[] tab = readLine.split("-");
                if (Integer.parseInt(tab[0]) == n) {
                    bo = true;
                }
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bo;
    }

    /* verifier si le plat du code co appartient au restaurant de nom r ou non */
    private static Boolean verifCode_Plat(String co, String r) {
        Boolean bo = false;
        try {
            File f = new File("src\\Hotellerie\\Files\\" + r + ".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while (((readLine = b.readLine()) != null) && (bo == false)) {
                String[] tab = readLine.split("-");
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
        private static boolean verifFormatDate (String date) {
            boolean v=false;
            if (date.length()==10)
            {String jour;
            String mois;
            String annee;
            jour = date.substring(0, 2);
            mois = date.substring(3, 5);
            annee = date.substring(6, 10);
            String sl1;
            String sl2;
            sl1 = date.substring(2,3);
            sl2 = date.substring(5,6);
            int m;
            m=Integer.parseInt(mois);
            int j;
            j=Integer.parseInt(jour);
            int a;
            a=Integer.parseInt(annee);
            
            if((j>0) && (j<=31) && (m>0) && (m<=12) && (a>1929) && (a<=2003) && ("/".equals(sl1)) && ("/".equals(sl2)))
            {
             v=true;
            }
            }          
            return v;
        }
        // verifier si l'adresse mail est valide
        public static boolean isEmailAdress(String email){
            Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
            Matcher m = p.matcher(email.toUpperCase());
            return m.matches();
            }
        
        // verifier la longueur du tel = 8
        public static boolean islength8(int num){
            String chaine = Integer.toString(num);
            return (8==chaine.length());
            }
}

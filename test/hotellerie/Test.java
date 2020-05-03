/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotellerie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author asus
 */
public class Test {

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

    public static void MenuClient() {
        System.out.println("Bonjour ! ");
        System.out.println("Entrer CIN  ou 0 Pour quitter");
        Scanner sc1 = new Scanner(System.in);
        int nb1 = sc1.nextInt();
        if (nb1 == 0) {
            System.out.println("Operation annuler");
        } else {
            if (Client.Verif(nb1)) {
                System.out.println("Bienvenue cher Client");
                Client e = new Client(nb1);
                System.out.println("Voici vos coordonnees");
                e.affiche();
                System.out.println("1- Reserver une chambre     2- Modifier vos cordonnées     0- Annuler");
                int nb2 = sc1.nextInt();
                if (nb2 == 1) {
                    MenuReserver(nb1);
                    MenuClient();
                } else if (nb2 == 2) {
                    MenuModifierClient(nb1, e);
                    MenuClient();
                } else if (nb2 == 0) {
                    MenuClient();
                }
            } else {
                System.out.println("Vous etes un nouveau client");
                Scanner sc4 = new Scanner(System.in);
                System.out.println("Entrer votre nom");
                String nom = sc4.nextLine();
                System.out.println("Entrer votre Prenom");
                String prenom = sc4.nextLine();
                System.out.println("Entrer votre Date de naissance jj/mm/aaaa");
                String date = sc4.nextLine();
                System.out.println("Entrer votre Email");
                String mail = sc4.nextLine();
                System.out.println("Entrer votre Telephone");
                int tel = sc4.nextInt();
                System.out.println("Entrer votre Pays d'habitat");
                Scanner p = new Scanner(System.in);
                String pays = p.nextLine();
                Client e1 = new Client(nb1, nom, prenom, date, mail, tel, pays);
                e1.affiche();
                e1.Ajouter();
                System.out.println("1- Reserver une chambre     2- Modifier vos cordonnées     0- Annuler");
                int nb2 = sc1.nextInt();
                if (nb2 == 1) {
                    MenuReserver(nb1);
                    MenuClient();
                } else if (nb2 == 2) {
                    MenuModifierClient(nb1, e1);
                    MenuClient();
                } else if (nb2 == 0) {
                    MenuClient();
                }
            }
        }
    }

    public static void MenuReserver(int nb1) {
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
        Reservation r = new Reservation(nb1, nbsem, nb_chambre);//initialiser une reservation avec cin=250 , nb semaine � louer =1 ,nb de chambre a louer =1 
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
        Scanner input5 = new Scanner(System.in);
        System.out.println("A quelle semaine voulez-vous debuter votre sejour ?");
        int semdebut = input5.nextInt();
        while (semdebut + nbsem > 5 || semdebut <= 0) {
            System.out.println("saisir un nombre de semaine correcte");
            System.out.println("A quelle semaine voulez-vous debuter votre sejour ?");
            semdebut = input5.nextInt();
        }
        r.reserver(type.toLowerCase(), vue.toLowerCase(), semdebut, nbsem);// appel � la methode rechercher_chambre pour chercher la chambre disponible selon ces criteres et reserver chambre pour reserver cette chambre
        }
       //r.visualiser(r.getNum_R());
    }

    public static void MenuModifierClient(int nb1, Client e) {
        System.out.println("Entrer votre nouveau mail");
        Scanner sc3 = new Scanner(System.in);
        String nb3 = sc3.nextLine();
        System.out.println("Entrer votre nouveau Tel");
        int nb4 = sc3.nextInt();
        System.out.println("Entrer votre nouveau pays d'habitat");
        String nb5 = sc3.nextLine();
        e.Modifier(nb1, nb3, nb4, nb5);
        System.out.println("Modification effectuee avec suscces !");
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

    public static void Menu() {
        System.out.println("Bonjour ! ");
        System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Reserver chambre     2- Commander d'un restaurant de luxe     3- Statistiques des restaurants luxes");
        Scanner sc = new Scanner(System.in);
        int nb = sc.nextInt();
        if (nb == 1) {
            MenuClient();
            Menu();
        } else if (nb == 2) {
            MenuRestaurant();
            Menu();
        } else if (nb == 3) {
            MenuStat();
            Menu();
        } else {
            System.out.println("Operation annulee");
        }
    }

    public static void main(String[] args) {
        Menu();
    }

    //à developper à partir du main
    private static void MenuRestaurant() {
        System.out.println("entrer le nom du restaurant Royale/Italiano/Mexicano");
        Scanner c5 = new Scanner(System.in);
        String nom = c5.nextLine();
        while (verifNom_R(nom) == false) {
            System.out.println("restaurant inexistant SVP essayez de nouveau");
            c5 = new Scanner(System.in);
            nom = c5.nextLine();
        }
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

    }

    //à developper à partir du main
    private static void MenuStat() {
        System.out.println("entrer le nom du restaurant Royale/Italiano/Mexicano");
            Scanner s1 = new Scanner(System.in);
            String no = s1.nextLine();
            while (verifNom_R(no) == false) {
                System.out.println("restaurant inexistant SVP essayez de nouveau");
                s1 = new Scanner(System.in);
                no = s1.nextLine();
            }
            Restaurant R = new Restaurant(no);
            System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
            System.out.println(" 1- Visualiser la recette d'une journee");
            System.out.println(" 2- Visualiser la recette de la semaine courante");
            System.out.println(" 3- Visualiser la  fréquence de demande de chaque plat pendant la semaine courante");
            Scanner a = new Scanner(System.in);
            int i = a.nextInt();
            while ((i != 1) && (i != 2) && (i != 3)) {
                System.out.println("Veuillez entrez un chiffre parmis 1, 2, 3 ");
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
            }
            if (i == 2) {
                R.recette_semaine();
            }
            if (i == 3) {
                R.frequence();
            }
    }

}
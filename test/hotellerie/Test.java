/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotellerie;

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
            }
            else
                    {
                        System.out.println("Vous etes un nouveau client");
                        Scanner sc4=new Scanner(System.in);
                        System.out.println("Entrer votre nom");
                        String nom=sc4.nextLine();
                        System.out.println("Entrer votre Prenom");
                        String prenom=sc4.nextLine();
                        System.out.println("Entrer votre Date de naissance jj/mm/aaaa");
                        String date=sc4.nextLine();
                        System.out.println("Entrer votre Email");
                        String mail=sc4.nextLine();
                        System.out.println("Entrer votre Telephone");
                        int tel=sc4.nextInt();
                        System.out.println("Entrer votre Pays d'habitat");
                        Scanner p=new Scanner(System.in);
                        String pays=p.nextLine();
                        Client e1=new Client(nb1,nom,prenom,date,mail,tel,pays);
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
    

    public static void Menu() {
        System.out.println("Bonjour ! ");
        System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
        System.out.println(" 1- Reserver chambre     2- Commander d'un restaurant de luxe     3- Statistiques des restaurants luxes");
        Scanner sc = new Scanner(System.in);
        int nb=sc.nextInt();
        if(nb==1)
            MenuClient();
        else if(nb==2)
            MenuRestaurant();
        else if(nb==3)
            MenuStat();
        else 
         System.out.println("Operation annulee");
    }

    public static void main(String[] args) {
     Menu();
    }
 
    //à developper à partir du main
    private static void MenuRestaurant() {
    }
 
        //à developper à partir du main
    private static void MenuStat() {
    }

}

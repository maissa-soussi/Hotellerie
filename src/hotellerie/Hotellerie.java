/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotellerie;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class Hotellerie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                System.out.println("Bonjour ! ");
                System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
                System.out.println(" 1- Reserver chambre     2- Commander d'un restau de luxe");
                Scanner sc=new Scanner(System.in);
		int nb=sc.nextInt();            //lire un entier de l'entrée standard
		//sc.nextLine(); 
                
                if (nb==1)
                {
                    System.out.println("Entrer CIN  ou 0 Pour quitter");
                    Scanner sc1=new Scanner(System.in);
		    int nb1=sc1.nextInt();
                    if (nb1==0)
                    {
                        System.out.println("Operation annuler");
                    }
                    if (Client.Verif(nb1))
                    {
                        System.out.println("Bienvenue cher Client");
                        Client e=new Client(nb1);
                        System.out.println("Voici vos coordonnées");
                        e.affiche();
                        System.out.println("1- Reserver une chambre     2- Modifier vos cordonnées     0- Annuler");
                        Scanner sc2=new Scanner(System.in);
		        int nb2=sc2.nextInt();
                        if (nb2==1)
                        {
                            //ici raaafet le cin c'est dans nb1
                        }
                        if (nb2==2)
                        {   System.out.println("Entrer votre nouveau mail");
                            Scanner sc3=new Scanner(System.in);
		            String nb3=sc3.nextLine();
                            System.out.println("Entrer votre nouveau Tel");
		            int nb4=sc3.nextInt();
                            System.out.println("Entrer votre nouveau pays d'habitat");
		            String nb5=sc3.nextLine();
                            e.Modifier(nb1, nb3, nb4, nb5);
                            System.out.println("Modification effectuée avec susccés !");
                        }
                        if (nb2==0)
                        {
                            System.out.println("Operation annuler");
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
                        String pays=sc4.nextLine();
                        sc4.nextLine();
                        Client e1=new Client(nb1,nom,prenom,date,mail,tel,pays);
                        System.out.println("Maintenant on commence la procedure de reservation");
                        
                        //raafet recopie le code eli 5demtou l fou9 hounii 
                        
                    }
                    
                }
                
                if (nb==2)
                {
                    // ici farouuk le cin c est dans nb1
                }
                
        
    }
    
}

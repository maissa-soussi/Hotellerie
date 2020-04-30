package hotellerie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
public class Hotellerie {
    // verifier si la reservation existe ou non
    private static Boolean verifNum_R(int n)
    {
        Boolean bo=false;
        try {
            File f = new File("src\\Hotellerie\\Files\\Reservation.txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while (((readLine = b.readLine()) != null) && (bo==false)) {
                String[] tab=readLine.split("-");
                if(Integer.parseInt(tab[0])==n){
                  bo=true;
            }
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bo;
    }
    /* verifier si le plat du code co appartient au restaurant de nom r ou non */
    private static Boolean verifCode_Plat(String co,String r)
    {
        Boolean bo=false;
        try {
            File f = new File("src\\Hotellerie\\Files\\"+r+".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while (((readLine = b.readLine()) != null) && (bo==false)) {
                String[] tab=readLine.split("-");
                if(tab[0].equals(co)){
                  bo=true;
            }
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bo;
    }
    // verifier si le restaurant du nom r existe ou non
    private static Boolean verifNom_R(String r)
    {
        Boolean bo=false;
        if(("Royale".equals(r)) || ("Mexicano".equals(r)) || ("Italiano".equals(r)))
            bo=true;
        return bo;
    }
    //verifier si le numero de journee est valide ou non
    private static Boolean verifDate_C(int d)
    {
        Boolean bo=false;
        if((d<=7) && (d>=1))
            bo=true;
        return bo;
    }
    public static void main(String[] args) {
                System.out.println("Bonjour ! ");
                System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
                System.out.println(" 1- Reserver chambre     2- Commander d'un restau de luxe     3- Statistiques des restaurants luxes");
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
                    else
                    {
                    if (Client.Verif(nb1))
                    {
                       System.out.println("Bienvenue cher Client");
                        Client e=new Client(nb1);
                        System.out.println("Voici vos coordonnees");
                        e.affiche();
                        System.out.println("1- Reserver une chambre     2- Modifier vos cordonnées     0- Annuler");
                        Scanner sc2=new Scanner(System.in);
		        int nb2=sc2.nextInt();
                        if (nb2==1)
                        {  Scanner input1=new Scanner(System.in);
                        System.out.println("Combien de semaine voulez-vous allouez?");
                        int nbsem=input1.nextInt();
                        Scanner input2=new Scanner(System.in);
                        System.out.println("Combien de chambre voulez-vous reservez?");
                        int nb_chambre=input2.nextInt();
                        Reservation r=new Reservation(nb1,nbsem,nb_chambre);//initialiser une reservation avec cin=250 , nb semaine � louer =1 ,nb de chambre a louer =1 
                        Scanner input3=new Scanner(System.in);
                        System.out.println("Quel type voulez-vous choisir ?");
                        String type=input3.nextLine();
                        Scanner input4=new Scanner(System.in);
                        System.out.println("Voulez vous choisir une chambre avec vue mer/piscine/jardin ");
                        String vue=input4.nextLine();
                        Scanner input5=new Scanner(System.in);
                        System.out.println("A quelle semaine voulez-vous debuter votre sejour ?");
                        int semdebut= input5.nextInt();
                        r.reserver(type, vue, semdebut, nbsem);// appel � la methode rechercher_chambre pour chercher la chambre disponible selon ces criteres et reserver chambre pour reserver cette chambre
                        r.visualiser(r.getNum_R());// visualiser les details de cette reservation 
                        //r.cloturer(nb1);  //cloturer et afficher les details des reservations du client cin=250
                       // r.annuler(r.getNum_R()-1);
                        
                        
                        
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
                            System.out.println("Modification effectuee avec suscces !");
                        }
                        if (nb2==0)
                        {
                            System.out.println("Operation annulee");
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
                       
                        Scanner input5=new Scanner(System.in);
                        System.out.println("Combien de semaine voulez-vous allouer?");
                        int nbsem=input5.nextInt();
                        Scanner input6=new Scanner(System.in);
                        System.out.println("Combien de chambre voulez-vous reserver?");
                        int nb_chambre=input6.nextInt();
                        Reservation r=new Reservation(nb1,nbsem,nb_chambre);//initialiser une reservation avec cin=250 , nb semaine � louer =1 ,nb de chambre a louer =1 
                        Scanner input7=new Scanner(System.in);
                        System.out.println("Quel type voulez-vous choisir ?");
                        String type=input7.nextLine();
                        Scanner input8=new Scanner(System.in);
                        System.out.println("Voulez vous choisir une chambre avec vue mer/piscine/jardin ");
                        String vue=input8.nextLine();
                        Scanner input9=new Scanner(System.in);
                        System.out.println("A quelle semaine voulez-vous debuter votre sejour ?");
                        int semdebut= input9.nextInt();
                        r.reserver(type, vue, semdebut, nbsem);// appel � la methode rechercher_chambre pour chercher la chambre disponible selon ces criteres et reserver chambre pour reserver cette chambre
                        r.visualiser(r.getNum_R());// visualiser les details de cette reservation
                        r.cloturer(nb1);  //cloturer et afficher les details des reservations du client cin=250*/ */
                       // r.annuler(r.getNum_R()-1);
                        //raafet recopie le code eli 5demtou l fou9 hounii 
                        
                        
                    }
                    
                }}
                
                if (nb==2)
                {
                    System.out.println("entrer le nom du restaurant Royale/Italiano/Mexicano");
                    Scanner c5=new Scanner(System.in);
		    String nom=c5.nextLine();
                    while(verifNom_R(nom)==false)
                    {
                        System.out.println("restaurant inexistant SVP essayez de nouveau");
                        c5=new Scanner(System.in);
		        nom=c5.nextLine();
                    }
                    Restaurant R=new Restaurant(nom);
                    System.out.println("entrer le numero de reservation");
                    Scanner c1=new Scanner(System.in);
		    int n=c1.nextInt();
                    while(verifNum_R(n)==false)
                    {
                        System.out.println("numero de reservation inexistant SVP essayez de nouveau");
                        c1=new Scanner(System.in);
		        n=c1.nextInt();
                    }
                    System.out.println("entrer le code du plat");
                    Scanner c2=new Scanner(System.in);
		    String co=c2.nextLine();
                    while(verifCode_Plat(co, nom)==false)
                    {
                        System.out.println("plat inexistant SVP essayez de nouveau");
                        c2=new Scanner(System.in);
		        co=c2.nextLine();
                    }
                    System.out.println("entrer le nombre de plats");
                    Scanner c3=new Scanner(System.in);
		    int nbp=c3.nextInt();                    
                    System.out.println("entrer le numero de la journee de la commande");
                    Scanner c4=new Scanner(System.in);
		    int d=c4.nextInt();
                    while(verifDate_C(d)==false)
                    {
                        System.out.println("numero de la journee invalede SVP essayez de nouveau");
                        c4=new Scanner(System.in);
		        d=c4.nextInt();
                    }
                    R.effectuer(n, co, nbp, d);
                }
                if (nb==3)
                {
                    System.out.println("entrer le nom du restaurant Royale/Italiano/Mexicano");
                    Scanner s1=new Scanner(System.in);
		    String no=s1.nextLine();
                    while(verifNom_R(no)==false)
                    {
                        System.out.println("restaurant inexistant SVP essayez de nouveau");
                        s1=new Scanner(System.in);
		        no=s1.nextLine();
                    }
                    Restaurant R=new Restaurant(no);
                    System.out.println("Veuillez entrez le chiffre qui correspond à votre choix ");
                    System.out.println(" 1- Visualiser la recette d'une journee");
                    System.out.println(" 2- Visualiser la recette de la semaine courante");
                    System.out.println(" 3- Visualiser la  fréquence de demande de chaque plat pendant la semaine courante");
                    Scanner a=new Scanner(System.in);
		    int i=a.nextInt();
                    while((i!=1) && (i!=2) && (i!=3))
                    {
                        System.out.println("Veuillez entrez un chiffre parmis 1, 2, 3 ");
                        a=new Scanner(System.in);
		        i=a.nextInt();
                    }
                    if(i==1)
                    {
                        System.out.println("entrez le numero de la journee");
                        Scanner b=new Scanner(System.in);
		        int j=b.nextInt();
                        while(verifDate_C(j)==false)
                        {
                            System.out.println("numero de la journee invalede SVP essayez de nouveau");
                            b=new Scanner(System.in);
		            j=b.nextInt();
                    }
                        R.recette_journee(j);
                    }
                    if(i==2)
                    {
                        R.recette_semaine();
                    }
                    if(i==3)
                    {
                        R.frequence();
                    }
                }
        
    }
    
}

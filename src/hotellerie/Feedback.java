/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotellerie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author asus
 */
public class Feedback {

    public static void obtenirFeedback() {
        System.out.println("Comment jugez-vous l’amabilité du personnel d'accueil ? (entre 0 et 5 )");
        Scanner sc1 = new Scanner(System.in);
        int nb1 = sc1.nextInt();
        while (nb1 < 0 || nb1 > 5) {
            System.out.println("entrer une note entre 0 et 5 ");
            nb1 = sc1.nextInt();
        }
        System.out.println("Comment jugez-vous le niveau de confort de votre chambres ?(entre 0 et 5 )");
        int nb2 = sc1.nextInt();
        while (nb2 < 0 || nb2 > 5) {
            System.out.println("entrer une note entre 0 et 5 ");
            nb2 = sc1.nextInt();
        }
        System.out.println("Que pensez-vous de la qualité de service du Restaurant principale ? (entre 0 et 5 )");
        int nb3 = sc1.nextInt();
        while (nb3 < 0 || nb3 > 5) {
            System.out.println("entrer une note entre 0 et 5 ");
            nb3 = sc1.nextInt();
        }
        System.out.println("Comment jugez-vous l'animation de l'hotel ? (entre 0 et 5 )");
        int nb4 = sc1.nextInt();
        while (nb4 < 0 || nb4 > 5) {
            System.out.println("entrer une note entre 0 et 5 ");
            nb4 = sc1.nextInt();
        }
        System.out.println("Donner une note de satisfaction concernant votre séjour ?(entre 0 et 5 )");
        int nb5 = sc1.nextInt();
        while (nb5 < 0 || nb5 > 5) {
            System.out.println("entrer une note entre 0 et 5 ");
            nb5 = sc1.nextInt();
        }
        int[] nb = {nb1, nb2, nb3, nb4, nb5};
        ajouterFeedback(nb);

    }

    public static void ajouterFeedback(int[] nb) {
        try {
            File fichier1 = new File("src\\Feedback.txt");
            FileReader fichier = new FileReader("src\\Hotellerie\\Files\\Feedback.txt");
            BufferedReader br = new BufferedReader(fichier);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fichier1, true));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null && i < 5) {
                String[] detail = line.split("*");
                detail[0] = Integer.parseInt(detail[0]) + nb[i] + "";
                detail[1] = Integer.parseInt(detail[1]) + 1 + "";
                i++;
                bufferedWriter.write(detail[0] + "*" + detail[1]);
                bufferedWriter.write("\r\n");
            }
            bufferedWriter.close();
            br.close();
            Files.move(Paths.get("src\\Feedback.txt"), Paths.get("src\\Hotellerie\\Files\\Feedback.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.getMessage();
        }

    }

    public static void resultatsFeedback() {
        try {
            FileReader fichier = new FileReader("src\\Hotellerie\\Files\\Feedback.txt");
            BufferedReader br = new BufferedReader(fichier);
            String line;
            String[] questions = {"Comment jugez-vous l’amabilité du personnel d'accueil ?", "Comment jugez-vous le niveau de confort de votre chambres ?", "Que pensez-vous de la qualité de service du Restaurant principale ?", "Comment jugez-vous l'animation de l'hotel ?", "Donner une note de satisfaction concernant votre séjour ?"};
            int i = 0;
            while ((line = br.readLine()) != null && i < 5) {
                String[] detail = line.split("*");
                float r = (float) (Integer.parseInt(detail[0])) / Integer.parseInt(detail[1]);
                DecimalFormat df = new DecimalFormat("0.00");
                //System.out.println(questions[i]+" : "+df.format(r));
                if ((1 <= r) && (r < 2)) {
                    System.out.println(questions[i] + " : * ");
                } else if ((2 <= r) && (r < 3)) {
                    System.out.println(questions[i] + " : ** ");
                } else if ((3 <= r) && (r < 4)) {
                    System.out.println(questions[i] + " : *** ");
                } else if ((4 <= r) && (r < 5)) {
                    System.out.println(questions[i] + " : **** ");
                } else {
                    System.out.println(questions[i] + " : ***** ");
                }
                i++;
            }
            br.close();
            Files.move(Paths.get("src\\Feedback.txt"), Paths.get("src\\Hotellerie\\Files\\Feedback.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.getMessage();
        }
    }

}

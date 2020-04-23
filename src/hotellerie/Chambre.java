/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotellerie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author asus
 */
public class Chambre {

    private int Num;
    private String Type;
    private String Vue;
    private boolean Res1;
    private boolean Res2;
    private boolean Res3;
    private boolean Res4;

    public int GetNum() {
        return Num;
    }

    public String GetType() {
        return Type;
    }

    public String GetVue() {
        return Vue;
    }

    public boolean GetRes1() {
        return Res1;
    }

    public boolean GetRes2() {
        return Res2;
    }

    public boolean GetRes3() {
        return Res3;
    }

    public boolean GetRes4() {
        return Res4;
    }

    public void SetRes1(boolean a) {
        Res1 = a;
    }

    public void SetRes2(boolean a) {
        Res2 = a;
    }

    public void SetRes3(boolean a) {
        Res3 = a;
    }

    public void SetRes4(boolean a) {
        Res4 = a;
    }

    public Chambre() {

    }
//Construire un objet Chambre à partir de son numeroC : convertir la ligne de la chambre en une instance chambre
    public Chambre(int numC) {
        try {
            DecimalFormat nf = new DecimalFormat("000");
            BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Chambre.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(nf.format(numC))) {
                    break;
                }
            }
            System.out.println(line);
            br.close();
            String[] update = line.split("-");
            Num = Integer.parseInt(update[0]);
            Type = update[1];
            Vue = update[2];
            Res1 = StringToBoolean(update[0]);
            Res2 = StringToBoolean(update[4]);
            Res3 = StringToBoolean(update[5]);
            Res4 = StringToBoolean(update[6]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//afficher une chambre donnée par son NumC
    public void AfficherChambre(int numC) {
        try {
            DecimalFormat nf = new DecimalFormat("000");
            BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Chambre.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(nf.format(numC))) {
                    System.out.println(line);
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//afficher toutes les chambres de l'hotels
    public void AfficherChambres() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Chambre.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* public void lireChambre() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Chambre.txt"));
            String line;
//while ((line = br.readLine()) != null) {
            // process the line.
//}
            line = br.readLine();
            br.close();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
// reserve une chambre donné par son numero et numero de semaine et stocke la modification sur le fichier chambre
    public void ReserverChambre(int numC, int numS) {
        try {
            File fichier1 = new File("src\\Chambre.txt");
            FileReader fichier = new FileReader("src\\Hotellerie\\Files\\Chambre.txt");
            BufferedReader br = new BufferedReader(fichier);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fichier1, true));
            String line;
            boolean verif = false;
            DecimalFormat nf = new DecimalFormat("000");
            while ((line = br.readLine()) != null) {
                if (line.contains(nf.format(numC))) {
                    if (Verifier(line, numS) == true) {
                        line = ChangerChamp(line, numS);
                        //StringBuffer sb = new StringBuffer();
                        //line=line.replaceFirst(line,ChangerChamp(line,1));
                        // sb.append(line + "\n");
                        // StringBuilder str=new StringBuilder();
                        // str.append(line);
                        // str.append("\n");
                        //line=line.replaceAll(line,ChangerChamp(line, 1));
                        //bufferedWriter.write(str.toString());
                        bufferedWriter.write(line);
                        bufferedWriter.write("\r\n");
                    } else {
                        System.out.println("erreur");
                        bufferedWriter.write(line);
                        bufferedWriter.write("\r\n");
                    }
                } else {
                    bufferedWriter.write(line);
                    bufferedWriter.write("\r\n");
                }
            }
            bufferedWriter.close();
            br.close();

            Files.move(Paths.get("src\\Chambre.txt"), Paths.get("src\\Hotellerie\\Files\\Chambre.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
 //fonction utilisé pour changer le champs Res'num'(Res1 ou Res2....) de la chambre qui sera reservé durant cette semaine
    public String ChangerChamp(String line, int num) {
        String[] update = line.split("-");
        update[num + 2] = "1";
        return update[0] + "-" + update[1] + "-" + update[2] + "-" + update[3] + "-" + update[4] + "-" + update[5] + "-" + update[6];
    }
  //fonction qui permet de verifier sur une ligne(une chambre) si elle est disponible durant la semaine donnée
    public boolean Verifier(String line, int num) {
        String[] update = line.split("-");
        boolean test;
        if (num < 1 || num > 4) {
            test = false;
        } else {
            if (update[num + 2].compareTo("1") >= 0) {
                test = false;
            } else {
                test = true;
            }
        }
        return test;
    }

   //fonction utilisé pour convertir (0,1) du fichier texte en type boolean(true,false)
    private Boolean StringToBoolean(String ch) {
        int a = Integer.parseInt(ch);
        if (a == 1) {
            return true;
        } else {
            return false;
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotellerie;

/**
 *
 * @author ASUS
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author ASUS
 */
public class Client {
    private long Cin;
    private String Nom;
    private String Prenom;
    private String Date_N;
    private String Email;
    private long Tel;
    private String Pays;

    // constructeur en cas d'un nouvel ajout
    public Client(long Cin, String Nom, String Prenom, String Date_N, String Email, long Tel, String Pays) {
        this.Cin = Cin;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Date_N = Date_N;
        this.Email = Email;
        this.Tel = Tel;
        this.Pays = Pays;
    }
    
    //constructeur en cas d'un client existant 
    public Client(long Cin) {
        this.Cin = Cin;
        boolean res=false;
        String[] tab = null;
        try 
        {
                BufferedReader br = new BufferedReader(new FileReader("src\\hotellerie\\Files\\Client.txt"));
                String client;
                    while (((client=br.readLine())!=null) && (res==false))
                    {
                        tab=client.split("-");
                        res=(tab[0].equals(String.valueOf(Cin)));
                    }
                    
                br.close();
                
        }
        catch(IOException e)
                { 
                    System.out.println(e.getMessage());
                }
        
                this.Nom = tab[1];
                this.Prenom = tab[2];
                this.Date_N = tab[3];
                this.Email = tab[4];
                this.Tel = Long.parseLong(tab[5]);
                this.Pays = tab[6];
    }
    // fonction d'affichage d'un client
    public void affiche()
    {
        System.out.println("Cin: "+Cin+"\n"+"Nom: "+Nom+"\n"+"Prenom: "+Prenom+"\n"+"Date de naissance: "+Date_N+"\n"+"Email: "+Email+"\n"+"Tel"+Tel+"\n"+"Pays"+Pays);
    }
    
    //ajout d'un nouveau client
    public void Ajouter()
        {   

            try 
                {   
                    String nvclient=Cin+"-"+Nom+"-"+Prenom+"-"+Date_N+"-"+Email+"-"+Tel+"-"+Pays;
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\hotellerie\\Files\\Client.txt", true));
                    bufferedWriter.newLine();
                    bufferedWriter.write(nvclient);
                    bufferedWriter.close();
                }
            catch (IOException e)
                { 
                    System.out.println(e.getMessage());
                }

        }
    // Verif si un client existe deja dans le fichier
    public static Boolean Verif(long cin)
    {  boolean res=false;
        try 
        {
                BufferedReader br = new BufferedReader(new FileReader("src\\hotellerie\\Files\\Client.txt"));
                String client;
                    while (((client=br.readLine())!=null) && (res==false))
                    {
                        String[] tab=client.split("-");
                        res=(tab[0].equals(String.valueOf(cin)));
                    }
                br.close();
        }
        catch(IOException e)
                { 
                    System.out.println(e.getMessage());
                }

        return res;
    }
    
    // modif des informations d'un client existant
    public void Modifier(long cin, String Email, long Tel, String Pays )
        {   Boolean res=false;
            String nvclient="";
            try 
        {
                BufferedReader br = new BufferedReader(new FileReader("src\\hotellerie\\Files\\Client.txt"));
                String client;
                
                File FileTemp=new File("Client.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileTemp, true));
                
                String[] tab = null;
                
                // recherche du client à partir du fichier client
                    while (((client=br.readLine())!=null) && (res==false))
                    {   
                        tab=client.split("-");
                        res=(tab[0].equals(String.valueOf(cin)));
                        if (res==false)
                        {
                            bufferedWriter.write(client);
                            bufferedWriter.newLine();
                        }
                    }
                    // faire les modifications nécessaires
                    if (!Email.equals(tab[4]))
                    {
                        tab[4]=Email;
                    }
                    
                    if (!Pays.equals(tab[6]))
                    {
                        tab[6]=Pays;
                    }
                    
                    if (Tel != Integer.parseInt(tab[5]))
                    {
                        tab[5]=Long.toString(Tel);
                    }
                    // regrouper les nouvelles informations du client
                    for (int i=0;i<tab.length;i++)
                        nvclient=nvclient+tab[i]+"-";
                    
                    // enlever le dernier caractere - de la chaine 
                     nvclient=nvclient.substring(0, nvclient.length() - 1);
                    
                    // ajouter les données modifiées à leurs places 
                    bufferedWriter.write(nvclient);
                    bufferedWriter.newLine();
                    while (client!=null)
                    {
                        bufferedWriter.write(client);
                        bufferedWriter.newLine();
                        client=br.readLine();
                    }
                    
                    
                    bufferedWriter.close();
                    br.close();
              // remplacer l'ancien fichier client par le nouveau           
            Path temp = Files.move(Paths.get("Client.txt"),Paths.get("src\\hotellerie\\Files\\Client.txt"),StandardCopyOption.REPLACE_EXISTING);
        }
        catch(IOException e)
                { System.out.println(e.getMessage());
                }
        }

    // Getters & Setters
    public long getCin() {
        return Cin;
    }

    public void setCin(long Cin) {
        this.Cin = Cin;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }

    public String getDate_N() {
        return Date_N;
    }

    public void setDate_N(String Date_N) {
        this.Date_N = Date_N;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public long getTel() {
        return Tel;
    }

    public void setTel(long Tel) {
        this.Tel = Tel;
    }

    public String getPays() {
        return Pays;
    }

    public void setPays(String Pays) {
        this.Pays = Pays;
    }





}

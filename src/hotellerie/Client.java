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

    public Client(long Cin, String Nom, String Prenom, String Date_N, String Email, long Tel, String Pays) {
        this.Cin = Cin;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Date_N = Date_N;
        this.Email = Email;
        this.Tel = Tel;
        this.Pays = Pays;
    }


    public void Ajouter()
        {   

            try 
                {   String nvclient=Cin+"-"+Nom+"-"+Prenom+"-"+Date_N+"-"+Email+"-"+Tel+"-"+Pays;
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\hotellerie\\Files\\Client.txt", true));
                    bufferedWriter.newLine();
                    bufferedWriter.write(nvclient);
                    bufferedWriter.close();
                }
            catch (IOException e)
                { System.out.println(e.getMessage());
                }

        }

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
                { System.out.println(e.getMessage());
                }

        return res;
    }
    public void Modifier()
        {
        }

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

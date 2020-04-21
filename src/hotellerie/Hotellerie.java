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
public class Hotellerie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("hello");
        Client e;
        e = new Client(17484274,"Soussi","Meriem","06/10/1993","meriem.soussi@gmail.com",58588626,"Tunisie");
        e.Ajouter();
        boolean a=Client.Verif(17000274);
        System.out.println(a);
    }
        
    
}

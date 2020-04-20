/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotellerie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

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

public int GetNum()
{
   return Num; 
}
public String GetType()
{
    return Type;
}
public String GetVue()
{
    return Vue;
}
public boolean GetRes1()
 { 
    return Res1; 
 }
public boolean GetRes2()
 { 
    return Res2; 
 }
public boolean GetRes3()
 { 
    return Res3; 
 }
public boolean GetRes4()
 { 
    return Res4; 
 }
public void SetRes1(boolean a)
{
    Res1=a;
}
public void SetRes2(boolean a)
{
    Res2=a;
}
public void SetRes3(boolean a)
{
    Res3=a;
}
public void SetRes4(boolean a)
{
    Res4=a;
}
public void lireChambre()
{
   try { 
    BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Chambre.txt"));
String line;
//while ((line = br.readLine()) != null) {
   // process the line.
//}
line=br.readLine();
br.close();
System.out.println(line);
}
catch (IOException e) {
                e.printStackTrace();
            }  
}
public void ReserverChambre(int numC,int numSemaine)
{
   try { 
    BufferedReader br = new BufferedReader(new FileReader("src\\Hotellerie\\Files\\Chambre.txt"));
String line;
boolean verif=false;
DecimalFormat nf = new DecimalFormat("000");
while ((line = br.readLine()) != null && verif==false ) {
   if(line.contains(nf.format(numC)))
   {
       System.out.println(line);
       verif=true;
   }
}
br.close();
}
catch (IOException e) {
                e.printStackTrace();
            }  
}
    

}
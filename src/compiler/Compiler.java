/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author ayman
 */
public class Compiler {
    public static int swidth= Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int sheight= Toolkit.getDefaultToolkit().getScreenSize().height;
    public static void main(String[] args) {
       try{
       parseFrame pf = new parseFrame();
       pf.setVisible(true);
       }
       catch(Exception e){System.out.println("Error occured");}
       
    }
    
    
}

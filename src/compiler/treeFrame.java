/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author ayman
 */
public class treeFrame extends JFrame{
    public JScrollPane tsp;
    public treePanel tp;
    public treeFrame(syntaxTree tree){
        tp=new treePanel(tree);
        tp.setPreferredSize(new Dimension(Compiler.swidth*2, Compiler.sheight*2));
        tsp=new JScrollPane(tp);
        this.setTitle("Compiler");
        this.setResizable(true);
        this.setBounds(0, 0, Compiler.swidth*2, Compiler.sheight*2);
        this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container con = this.getContentPane();
        con.add(tsp,BorderLayout.CENTER);
   }
}

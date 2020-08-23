/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.*;


/**
 *
 * @author ayman
 */
public class parseFrame extends JFrame {
    public parser p = new parser(this);
    public boolean dontDraw=false;
    public JTextArea input = new JTextArea();
    public JButton compile = new JButton("Compile");
    public parseFrame(){
        this.setTitle("Compiler");
        this.setResizable(false);
        this.setBounds(0, 0, Compiler.swidth, Compiler.sheight);
        this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container con = this.getContentPane();
        con.add(input,BorderLayout.CENTER);
        con.add(compile,BorderLayout.SOUTH);
        compile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                p.init();
                try{
                syntaxTree tree =p.program();
                if(!dontDraw){
                    treeFrame tf=new treeFrame(tree);
                    tf.setVisible(true);
                }
                }
                catch(NullPointerException e){
                    p.foundStatements+="Error - Unexpected token\n";
                    JOptionPane.showMessageDialog(null,"Unexpected token error","Error", JOptionPane.ERROR_MESSAGE);
                }               
                try{
                File o=new File("parser_output.txt");
                o.createNewFile();
                BufferedWriter bw= new BufferedWriter(new FileWriter(o));
                String[] stmts = p.foundStatements.split("\n");
                    for (int i = 0; i < stmts.length; i++) {
                        bw.write(stmts[i]);
                        bw.newLine();
                    }
                bw.flush();
                }
                catch(Exception e){}
            }
        });
   }
    
}

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
public class treePanel extends JPanel {
    public syntaxTree tree;

    public treePanel(syntaxTree tree) {
        this.tree=tree;
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLACK);
        tree.paint(g);
    }
}

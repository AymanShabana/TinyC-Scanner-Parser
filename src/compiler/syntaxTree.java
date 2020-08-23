/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.awt.*;

/**
 *
 * @author ayman
 */
public class syntaxTree {
    public syntaxTree sibling,leftChild,rightChild,thirdChild;
    public boolean isRect;
    public float xlevel,ylevel;
    public static int width ,height,full,half,quart,str1,str2;
    public TokenType type;
    public String typeval,parameter;

    public syntaxTree(TokenType type,String typeval,String parameter,int xlevel,int ylevel) {
        this.type=type;
        this.typeval=typeval;
        this.parameter=parameter;
        this.xlevel=xlevel;
        this.ylevel=ylevel;
        width=(int)(Compiler.swidth/11.3);
        height=(int)(Compiler.sheight/12);
        full=(int)(Compiler.swidth/25.6);
        half=(int)(full/2);
        quart=(int)(half/2);
        str1=(int)(Compiler.swidth/192);
        str2=(int)(Compiler.sheight/54);
        if(this.type == TokenType.ID || this.type==TokenType.NUM || this.type==TokenType.PLUS ||this.type==TokenType.MINUS
                ||this.type==TokenType.DIV||this.type==TokenType.MULT||this.type==TokenType.EQUALS||this.type==TokenType.LESSTHAN){
            this.isRect=false;
        }
        else{
            this.isRect=true;
        }
    }
    public void lower(){
        this.ylevel++;
        if(this.leftChild != null){
            this.leftChild.lower();
        }
        if(this.rightChild != null){
            this.rightChild.lower();
        }
        if(this.thirdChild != null){
            this.thirdChild.lower();
        }
        if(this.sibling != null){
            this.sibling.lower();
        }
    }
    public boolean collision(){
        for (int i = 0; i < parser.tlist.size(); i++) {
            if((this.xlevel==parser.tlist.get(i).xlevel) && (this.ylevel==parser.tlist.get(i).ylevel) && !(this.equals(parser.tlist.get(i)))){
                return true;
            }
        }
        return false;
    }
    public void rectify(){
        for (int i = 0; i < parser.tlist.size(); i++) {
            if(parser.tlist.get(i).collision()){
                //parser.tlist.get(i).xlevel+=0.5;
                parser.tlist.get(i).shift();
            }
        }
    }
    public void shift(){
        this.xlevel+=0.5;
        if(this.leftChild != null){
            this.leftChild.shift();
        }
        if(this.rightChild != null){
            this.rightChild.shift();
        }
        if(this.thirdChild != null){
            this.thirdChild.shift();
        }
        if(this.sibling != null){
            this.sibling.shift();
        }
        
    }
    public void paint(Graphics g)
    {
        rectify();
        if(isRect){
            g.drawRect((int)(xlevel*width),(int)((ylevel)*height), full, half);
            g.drawString(typeval+"\n"+parameter, (int)((xlevel*width)+str1), (int)(((ylevel)*height)+str2));
        }
        else{
            g.drawOval((int)(xlevel*width), (int)(ylevel*height), full, half);
            g.drawString(typeval+"\n"+parameter, (int)((xlevel*width)+str1), (int)((ylevel*height)+str2));            
        }
        if(this.leftChild != null){
            g.drawLine((int)((xlevel*width)+half), (int)((ylevel*height)+half), (int)((this.leftChild.xlevel*width)+half), (int)(this.leftChild.ylevel*height));
            this.leftChild.paint(g);
        }
        if(this.rightChild != null){
            g.drawLine((int)((xlevel*width)+half), (int)((ylevel*height)+half), (int)((this.rightChild.xlevel*width)+half), (int)(this.rightChild.ylevel*height));
            this.rightChild.paint(g);
        }
        if(this.thirdChild != null){
            g.drawLine((int)((xlevel*width)+half), (int)((ylevel*height)+half), (int)((this.thirdChild.xlevel*width)+half), (int)(this.thirdChild.ylevel*height));
            this.thirdChild.paint(g);
        }
        if(this.sibling != null){
            g.drawLine((int)((xlevel*width)+full), (int)((ylevel*height)+quart), (int)(this.sibling.xlevel*width), (int)((this.sibling.ylevel*height)+quart));
            this.sibling.paint(g);
        }
    }
}

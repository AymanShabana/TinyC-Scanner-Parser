/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ayman
 */
public class parser {
    public parseFrame refr;
    public String foundStatements="";
    public static ArrayList<syntaxTree> tlist=new ArrayList<syntaxTree>();
    public scanner s;
    public boolean properExit=false;
    public Token currentToken;
    public parser(parseFrame refr) {
        this.refr=refr;
        s= new scanner(refr);
    }
    public void init(){
       try{
           s.readFile();
           s.scan();
           currentToken=s.getToken();
       }
       catch(Exception e){}
    }
    public void match(String str){
        if(currentToken != null)
        {
            if(currentToken.val.equalsIgnoreCase(str)){
                currentToken=s.getToken();
            }
            else{
                foundStatements+="Error - token does not match\n";
                JOptionPane.showMessageDialog(null,"Match error","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            properExit=true;
        }
    }
    public syntaxTree program(){
        int level=0;
        foundStatements+="PROGRAM FOUND\n";        
        syntaxTree temp= stmtSequence(level,level);
        if(!properExit && currentToken != null){
            foundStatements+="Error - Unexpected token\n";
            JOptionPane.showMessageDialog(null,"Unexpected token error","Error", JOptionPane.ERROR_MESSAGE);
            refr.dontDraw=true;
        }
        return temp;
    }
    public syntaxTree stmtSequence(int xlevel,int ylevel){
        foundStatements+="STMTSEQUENCE FOUND\n";        
        syntaxTree t0 = statement(xlevel,ylevel);
        syntaxTree t1=t0;
        if(!s.isDone){
            while(currentToken != null &&currentToken.val.equals(";")){
                match(";");
                t1.sibling=statement(xlevel+1,ylevel);
                t1=t1.sibling;
            }
        }
        return t0;
    }
    public syntaxTree statement(int xlevel,int ylevel){
        switch(currentToken.type){
            case IF:
                return ifstmt( xlevel, ylevel);
            case REPEAT:
                return repeatstmt(xlevel, ylevel);
            case ID:
                return assignstmt(xlevel, ylevel);
            case READ:
                return readstmt(xlevel, ylevel);
            case WRITE:
                return writestmt(xlevel, ylevel);
        }
        return null;
    }
    public syntaxTree ifstmt(int xlevel,int ylevel){
        foundStatements+="STATEMENT FOUND\n";        
        foundStatements+="IF STATEMENT FOUND\n";
        match("if");
        foundStatements+="if\n";
        syntaxTree temp= new syntaxTree(TokenType.IF, "if","" , xlevel, ylevel);
        if(temp.collision()){
                    temp.xlevel+=0.5;
                }
        tlist.add(temp);
        temp.leftChild=exp(xlevel, ylevel+1);
        match("then");
        foundStatements+="then\n";
        temp.rightChild=stmtSequence(xlevel+1, ylevel+1);
        if(currentToken != null && currentToken.val.equalsIgnoreCase("else")){
            match("else");
            foundStatements+="else\n";
            temp.thirdChild=stmtSequence(xlevel+2, ylevel+1);
        }
        match("end");
        foundStatements+="end\n";        
        return temp;
    }
    public syntaxTree repeatstmt(int xlevel,int ylevel){
        foundStatements+="STATEMENT FOUND\n";        
        foundStatements+="REPEAT STATEMENT FOUND\n";
        match("repeat");
        foundStatements+="repeat\n";
        syntaxTree temp=new syntaxTree(TokenType.REPEAT, "repeat", "", xlevel, ylevel);
        if(temp.collision()){
                    temp.xlevel+=0.5;
                }
        tlist.add(temp);
        temp.leftChild=stmtSequence(xlevel, ylevel+1);
        match("until");
        foundStatements+="until\n";
        temp.rightChild=exp(xlevel+1, ylevel+1);
        return temp;
    }
    public syntaxTree assignstmt(int xlevel,int ylevel){
        syntaxTree temp=new syntaxTree(TokenType.ASSIGN, "assign", "{"+currentToken.val+"}", xlevel, ylevel);
        foundStatements+="STATEMENT FOUND\n";        
        foundStatements+="ASSIGN STATEMENT FOUND\n";
        if(temp.collision()){
                    temp.xlevel+=0.5;
                }
        tlist.add(temp);
        match(currentToken.val);
        match(":=");
        temp.leftChild=exp(xlevel, ylevel+1);
        return temp;
    }
    public syntaxTree readstmt(int xlevel,int ylevel){
        match("read");
        syntaxTree temp=new syntaxTree(TokenType.READ, "read","{"+currentToken.val+"}" , xlevel, ylevel);
        foundStatements+="STATEMENT FOUND\n";        
        foundStatements+="READ STATEMENT FOUND\n";
        if(temp.collision()){
                    temp.xlevel+=0.5;
                }
        tlist.add(temp);
        if(currentToken.type != TokenType.ID){
            return null;
        }
        match(currentToken.val);
        return temp;
    }
    public syntaxTree writestmt(int xlevel,int ylevel){
        match("write");
        syntaxTree temp=new syntaxTree(TokenType.WRITE, "write", "", xlevel, ylevel);
        foundStatements+="STATEMENT FOUND\n";        
        foundStatements+="WRITE STATEMENT FOUND\n";
        if(temp.collision()){
                    temp.xlevel+=0.5;
                }
        tlist.add(temp);
        temp.leftChild=exp(xlevel, ylevel+1);
        return temp;
    }
    public syntaxTree exp(int xlevel,int ylevel){
        foundStatements+="exp found\n";
        syntaxTree temp,newtemp;
        temp=simpleexp(xlevel, ylevel);
        if(currentToken != null){
        if(currentToken.val.equalsIgnoreCase("=")||currentToken.val.equalsIgnoreCase("<")){
            newtemp=new syntaxTree(currentToken.type, "op", "{"+currentToken.val+"}", xlevel, ylevel);
            if(newtemp.collision()){
                    newtemp.xlevel+=0.5;
                }
            tlist.add(newtemp);
            match(currentToken.val);
            newtemp.leftChild=temp;
            newtemp.leftChild.ylevel+=1;
            newtemp.rightChild=simpleexp( xlevel+1, ylevel+1);
            temp=newtemp;
        }
        }
        return temp;
    }
    public syntaxTree simpleexp(int xlevel,int ylevel){ //possible error at 1+2+3
        syntaxTree temp,newtemp;
        temp=term(xlevel, ylevel);
        if(currentToken != null){
        while(currentToken.val.equalsIgnoreCase("+")||currentToken.val.equalsIgnoreCase("-")){
            newtemp=new syntaxTree(currentToken.type, "op", "{"+currentToken.val+"}", xlevel, ylevel);
            if(newtemp.collision()){
                    newtemp.xlevel+=0.5;
                }
            tlist.add(newtemp);
            match(currentToken.val);
            newtemp.leftChild=temp;
            newtemp.leftChild.lower();
            newtemp.rightChild=term(xlevel+1, ylevel+1);
            temp=newtemp;
        }
        }
        return temp;
    }
    public syntaxTree term(int xlevel,int ylevel){
        syntaxTree temp,newtemp;        
        temp=factor(xlevel, ylevel);
        if(currentToken != null){
        while(currentToken.val.equalsIgnoreCase("*")||currentToken.val.equalsIgnoreCase("/")){
            newtemp=new syntaxTree(currentToken.type, "op", "{"+currentToken.val+"}", xlevel, ylevel);
            if(newtemp.collision()){
                    newtemp.xlevel+=0.5;
                }
            tlist.add(newtemp);
            match(currentToken.val);
            newtemp.leftChild=temp;
            newtemp.leftChild.lower();
            newtemp.rightChild=factor( xlevel+1, ylevel+1);
            temp=newtemp;        
        }
        }
        return temp;
    }
    public syntaxTree factor(int xlevel,int ylevel){
        switch(currentToken.type){
            case PARLEFT:
                match("(");
                syntaxTree temp= exp(xlevel, ylevel);
                match(")");
                return temp;
            case NUM:
                syntaxTree temp2=new syntaxTree(TokenType.NUM, "const", "{"+currentToken.val+"}", xlevel, ylevel);
                if(temp2.collision()){
                    temp2.xlevel+=0.5;
                }
                tlist.add(temp2);
                match(currentToken.val);
                return temp2;
            case ID:
                syntaxTree temp3=new syntaxTree(TokenType.ID, "id", "{"+currentToken.val+"}", xlevel, ylevel);
                if(temp3.collision()){
                    temp3.xlevel+=0.5;
                }
                tlist.add(temp3);
                match(currentToken.val);
                return temp3;
        }
        return null;
    }
}

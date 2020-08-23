/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

/**
 *
 * @author ayman
 */
enum TokenType{IF,THEN,ELSE,END,REPEAT,UNTIL,READ,WRITE,PLUS,MINUS,MULT,DIV,EQUALS,LESSTHAN,PARLEFT,PARRIGHT,SEMI,ASSIGN,NUM,ID}
public class Token {
    public TokenType type;
    public String val;
    public int numVal;
    public int location;

    public Token(TokenType tt,String s) {
        this.type=tt;
        this.val=s;
        if(this.type==TokenType.NUM){
            this.numVal=Integer.parseInt(this.val);
        }
    }
    public void setLocation(int i){
        this.location=i;
    }
    public String display(){
        return this.val+" \n: "+this.type+"\n";
    }
    
}

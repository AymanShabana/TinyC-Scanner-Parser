/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ayman
 */
public class scanner {
    public String input="";
    public parseFrame reference;
    public int state =1;
    public char[] chars;
    public int charIndex=0;
    public Token toGet;
    public ArrayList<Token> literal=new ArrayList<Token>();
    public ArrayList<Token> tokens=new ArrayList<Token>();
    public ArrayList<Token> identifier=new ArrayList<Token>();
    public boolean isDone=false;
//    public static Token getToken(){
//        
//    }

    public scanner(parseFrame refr) {
        this.reference=refr;
    }
    
    public void readFile(){
        input= reference.input.getText();
        }
    public void scan(){
        chars = input.toCharArray();
    }
    public Token getToken(){
        String stringVal="";
        state=1;
        char next;
        while(state!=6){//DONE State
            if(charIndex<chars.length)
            {
                next=chars[charIndex];
            }
            else{
                next=' ';
                isDone=true;
                break;
            }
        if(state==1)//START State
        {
            if(Character.isWhitespace(next)){
                charIndex++;
                continue;
            }
            else if(next=='{'){
                charIndex++;
                state=2;//INCOMMENT State
            }
            else if(Character.isDigit(next)){
                stringVal+=next;
                charIndex++;
                state=3;//INNUM State
            }
            else if(Character.isLetter(next)){
                stringVal+=next;
                charIndex++;
                state=4;//INID State
            }
            else if(next==':'){
                stringVal+=next;
                charIndex++;
                state=5;//INASSIGN State
            }
            else{
                if(next=='+'){
                    charIndex++;
                    toGet=new Token(TokenType.PLUS,"+");
                    tokens.add(toGet);
                    return toGet;
                }
                else if(next=='-'){
                    charIndex++;
                    toGet=new Token(TokenType.MINUS,"-");
                    tokens.add(toGet);
                    return toGet;
                }
                else if(next=='*'){
                    charIndex++;
                    toGet=new Token(TokenType.MULT,"*");
                    tokens.add(toGet);
                    return toGet;
                }
                else if(next=='/'){
                    charIndex++;
                    toGet=new Token(TokenType.DIV,"/");
                    tokens.add(toGet);
                    return toGet;
                }
                else if(next=='='){
                    charIndex++;
                    toGet=new Token(TokenType.EQUALS,"=");
                    tokens.add(toGet);
                    return toGet;
                }
                else if(next=='<'){
                    charIndex++;
                    toGet=new Token(TokenType.LESSTHAN,"<");
                    tokens.add(toGet);
                    return toGet;
                }
                else if(next=='('){
                    charIndex++;
                    toGet=new Token(TokenType.PARLEFT,"(");
                    tokens.add(toGet);
                    return toGet;
                }
                else if(next==')'){
                    charIndex++;
                    toGet=new Token(TokenType.PARRIGHT,")");
                    tokens.add(toGet);
                    return toGet;
                }
                else if(next==';'){
                    charIndex++;
                    toGet=new Token(TokenType.SEMI,";");
                    tokens.add(toGet);
                    return toGet;
                }
                else{
                    state=6;//DONE
                }                
            }
        }
        else if(state==2){//INCOMMENT
            if(next=='}'){
                charIndex++;
                state=1;
            }
            else{
                charIndex++;
            }
        }
        else if(state==3){//INNUM
            if(Character.isDigit(next)){
                stringVal+=next;
                charIndex++;
            }
            else{
                toGet=new Token(TokenType.NUM,stringVal);
                literal.add(toGet);
                tokens.add(toGet);
                toGet.setLocation(literal.indexOf(toGet));
                return toGet;
            }
        }
        else if(state==4){//ID
            if(Character.isLetter(next)){
                stringVal+=next;
                charIndex++;
            }
            else{
                if(stringVal.equalsIgnoreCase("if")){
                    toGet=new Token(TokenType.IF,stringVal);
                    tokens.add(toGet);
                    return toGet;
                }
                else if(stringVal.equalsIgnoreCase("then")){
                    toGet=new Token(TokenType.THEN,stringVal);
                    tokens.add(toGet);
                    return toGet;
                }
                else if(stringVal.equalsIgnoreCase("else")){
                    toGet=new Token(TokenType.ELSE,stringVal);
                    tokens.add(toGet);
                    return toGet;
                }
                else if(stringVal.equalsIgnoreCase("end")){
                    toGet=new Token(TokenType.END,stringVal);
                    tokens.add(toGet);
                    return toGet;
                }
                else if(stringVal.equalsIgnoreCase("repeat")){
                    toGet=new Token(TokenType.REPEAT,stringVal);
                    tokens.add(toGet);
                    return toGet;
                }
                else if(stringVal.equalsIgnoreCase("until")){
                    toGet=new Token(TokenType.UNTIL,stringVal);
                    tokens.add(toGet);
                    return toGet;
                }
                else if(stringVal.equalsIgnoreCase("read")){
                    toGet=new Token(TokenType.READ,stringVal);
                    tokens.add(toGet);
                    return toGet;
                }
                else if(stringVal.equalsIgnoreCase("write")){
                    toGet=new Token(TokenType.WRITE,stringVal);
                    tokens.add(toGet);
                    return toGet;
                }
                else{
                    toGet=new Token(TokenType.ID,stringVal);
                    if(!identifier.contains(toGet)){
                    identifier.add(toGet);
                    }
                    toGet.setLocation(identifier.indexOf(toGet));
                    tokens.add(toGet);
                    return toGet;
                }
            }
        }
        else if(state==5){//INASSIGN
            if(next=='='){
                stringVal+=next;
                charIndex++;
                toGet=new Token(TokenType.ASSIGN,stringVal);
                tokens.add(toGet);
                return toGet;
            }
            else{
                state=6;
            }
        }
        }
        return null;
    }
    }
    


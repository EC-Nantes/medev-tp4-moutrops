/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.projet.jeudedame;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author vadimdigonnet
 */
public class Plateau {
    ArrayList<Pion> maListePionBlanc = new ArrayList<>();
    ArrayList<Pion> maListePionNoir = new ArrayList<>();

    
    
    public void creerPlateau(){
        for (int i = 0; i<=10;i++){
            for(int j=0;j<=4;j++){
                if (i%2==0 && j%2==0){
                    maListePionBlanc.add(new Pion(new Point2D(i,j),"blanc",this));
                }else if(i%2==1 && j%2==1){
                    maListePionBlanc.add(new Pion(new Point2D(i,j),"blanc",this));
                }        
                
            }
        }
        for (int i = 0; i<=10;i++){
            for(int j=6;j<=9;j++){
                if (i%2==0 && j%2==0){
                    maListePionNoir.add(new Pion(new Point2D(i,j),"blanc"));
                }else if(i%2==1 && j%2==1){
                    maListePionNoir.add(new Pion(new Point2D(i,j),"blanc"));
                }        
                
            }
        }
    }
    
    public void affichePlateau(){
        String[][] plateau = new String[10][10];
        Point2D a =new Point2D();
        for (int i=0;i<50;i++){
            for( int j=0; j<50;j++){
                plateau[i][j]=". ";
            }
        }
        for (Pion p : maListePionBlanc){
            int x =p.getPos().getX();
            int y =p.getPos().getY();
            plateau[x][y] = "B ";    
        }
        for (Pion p : maListePionNoir){
            int x =p.getPos().getX();
            int y =p.getPos().getY();
            plateau[x][y] = "N ";
        }
    }
}

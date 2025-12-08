/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.projet.jeudedame;

import java.util.ArrayList;

/**
 *
 * @author vadimdigonnet
 */
public class Plateau {
    ArrayList<Pion> maListePionBlanc = new ArrayList<>();
    ArrayList<Pion> maListePionNoir = new ArrayList<>();
 
    
    
    public void creerPlateau() {
        maListePionBlanc.clear();
        maListePionNoir.clear();

        // Pions blancs en bas (y = 6..9)
        for (int y = 6; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if ((x + y) % 2 == 1) {
                    maListePionBlanc.add(new Pion(new Point2D(x, y), "blanc", this));
                }
            }
        }

        // Pions noirs en haut (y = 0..3)
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 10; x++) {
                if ((x + y) % 2 == 1) {
                    maListePionNoir.add(new Pion(new Point2D(x, y), "noir", this));
                }
            }
        }
    }
    
    public void affichePlateau(){
        String[][] plateau = new String[50][50];
        Point2D a = new Point2D();
        for (int i=0;i<50;i++){
            for( int j=0; j<50;j++){
                plateau[i][j]=". ";
            }
        }
        for (Pion p : maListePionBlanc){
            int x =p.getPosition().getX();
            int y =p.getPosition().getY();
            plateau[x][y] = "B ";    
        }
        for (Pion p : maListePionNoir){
            int x =p.getPosition().getX();
            int y =p.getPosition().getY();
            plateau[x][y] = "N ";
        }
    }

    public ArrayList<Pion> getMaListePionBlanc() {
        return maListePionBlanc;
    }

    public void setMaListePionBlanc(ArrayList<Pion> maListePionBlanc) {
        this.maListePionBlanc = maListePionBlanc;
    }

    public ArrayList<Pion> getMaListePionNoir() {
        return maListePionNoir;
    }

    public void setMaListePionNoir(ArrayList<Pion> maListePionNoir) {
        this.maListePionNoir = maListePionNoir;
    }
    
        public boolean estDansLePlateau(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }
    
        public Pion getPion(Point2D pos) {
        for (Pion p : maListePionBlanc) {
            if (p.getPosition().getX() == pos.getX()
                    && p.getPosition().getY() == pos.getY()) {
                return p;
            }
        }
        for (Pion p : maListePionNoir) {
            if (p.getPosition().getX() == pos.getX()
                    && p.getPosition().getY() == pos.getY()) {
                return p;
            }
        }
        return null;
    }

    public void enleverPion(Pion pion) {
        if ("blanc".equals(pion.getCouleur())) {
            maListePionBlanc.remove(pion);
        } else if ("noir".equals(pion.getCouleur())) {
            maListePionNoir.remove(pion);
        }
    }

   public boolean caseVide(int x, int y) {
    if (!estDansLePlateau(x, y)) {
        return false;
    }
    
    // Vérifie qu'aucun pion blanc n'est sur cette case
    for (Pion p : getMaListePionBlanc()) {
        if (p.getPosition().getX() == x && p.getPosition().getY() == y) {
            return false;
        }
    }

    // Vérifie qu'aucun pion noir n'est sur cette case
    for (Pion p : getMaListePionNoir()) {
        if (p.getPosition().getX() == x && p.getPosition().getY() == y) {
            return false;
        }
    }
    return true; 
}

   public void vider() {
    getMaListePionBlanc().clear();
    getMaListePionNoir().clear();
}

}

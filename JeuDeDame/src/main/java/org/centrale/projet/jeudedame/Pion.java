/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.projet.jeudedame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Max
 */
public class Pion {
    Point2D position;
    String couleur;
    Plateau plateau;

    public Pion(Point2D position, String couleur,Plateau plateau) {
        this.position = position;
        this.couleur = couleur;
        this.plateau = plateau;
    }

    public Pion() {
    }
    
    
    
    
    public boolean deplace(int dx, int dy) {
    int newX = this.position.getX() + dx;
    int newY = this.position.getY() + dy;

    // Vérifier si la case est dans le plateau
    if (!plateau.estDansLePlateau(newX, newY)) {
        return false;
    }

    // Vérifier que la case est vide
    if (!plateau.caseVide(newX, newY)) {
        return false;
    }

    // Déplacement possible
    this.position.setX(newX);
    this.position.setY(newY);

    return true;
}





    public boolean doitManger() {
    List<Pion> maListePionTotal;

    if ("Blanc".equals(couleur)) {
        maListePionTotal = plateau.getMaListePionNoir();
    } else if ("Noir".equals(couleur)) {
        maListePionTotal = plateau.getMaListePionBlanc();
    } else {
        return false; // couleur inconnue
    }

    // Parcourir tous les pions adverses
    for (Pion pionAdv : maListePionTotal) {
        int dx = pionAdv.getPosition().getX() - this.position.getX();
        int dy = pionAdv.getPosition().getY() - this.position.getY();

        // Il faut que le pion adverse soit sur une des 4 diagonales adjacentes
        if (Math.abs(dx) == 1 && Math.abs(dy) == 1) {

            if (peutSauterParDessus(pionAdv)) {
                return true; 
            }
        }
    }

    return false; // aucune prise trouvée
}


    private boolean peutSauterParDessus(Pion pionAdv) {
        int dx = pionAdv.getPosition().getX() - this.position.getX();
        int dy = pionAdv.getPosition().getY() - this.position.getY();

        // Case derrière le pion adverse
        int xDerriere = pionAdv.getPosition().getX() + dx;
        int yDerriere = pionAdv.getPosition().getY() + dy;

        // À adapter à ta représentation du plateau :
        return plateau.estDansLePlateau(xDerriere, yDerriere)
                && plateau.caseVide(xDerriere, yDerriere);
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D pos) {
        this.position = pos;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

}

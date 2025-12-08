/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.centrale.projet.jeudedame;

import java.util.ArrayList;
import java.util.List;

public class JeuDeDame {

    private Plateau plateau;
    private String joueurCourant; // "Blanc" ou "Noir"

    public JeuDeDame() {
        this.plateau = new Plateau();
        demarrerNouvellePartie();
    }

    public void demarrerNouvellePartie() {
        plateau.vider();

        // Damier 10x10 : lignes 0..9, colonnes 0..9
        // Pions noirs en haut : lignes 0 à 3
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 10; x++) {
                if ((x + y) % 2 == 1) { // cases foncées
                    Pion p = new Pion(new Point2D(x, y), "Noir", plateau);
                    plateau.getMaListePionNoir().add(p);
                }
            }
        }

        // Pions blancs en bas : lignes 6 à 9
        for (int y = 6; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if ((x + y) % 2 == 1) {
                    Pion p = new Pion(new Point2D(x, y), "Blanc", plateau);
                    plateau.getMaListePionBlanc().add(p);
                }
            }
        }

        joueurCourant = "Blanc"; // Blanc commence
    }

    // ---------- Gestion des joueurs ----------
    public String getJoueurCourant() {
        return joueurCourant;
    }

    private void changerJoueur() {
        if ("Blanc".equals(joueurCourant)) {
            joueurCourant = "Noir";
        } else {
            joueurCourant = "Blanc";
        }
    }

    // ---------- Pions qui doivent manger ----------
    /**
     * Retourne la liste des pions du joueur courant qui ont au moins une prise
     * possible.
     */
    public List<Pion> getPionsQuiDoiventManger() {
        List<Pion> resultat = new ArrayList<>();

        List<Pion> listePions;
        if ("Blanc".equals(joueurCourant)) {
            listePions = plateau.getMaListePionBlanc();
        } else {
            listePions = plateau.getMaListePionNoir();
        }

        for (Pion p : listePions) {
            if (!p.doitManger().isEmpty()) {
                resultat.add(p);
            }
        }

        return resultat;
    }

    // ---------- Jouer un coup ----------
    /**
     * Joue un coup si possible.
     *
     * @param origine position de départ du pion
     * @param destination position d'arrivée
     * @return true si le coup est légal et joué, false sinon.
     */
    public boolean jouerCoup(Point2D origine, Point2D destination) {
        Pion pion = plateau.getPion(origine);
        if (pion == null) {
            return false;
        }
        if (!pion.getCouleur().equals(joueurCourant)) {
            return false;
        }

        // Vérifier les pions obligés de manger
        List<Pion> pionsObliges = getPionsQuiDoiventManger();
        boolean Manger = !pionsObliges.isEmpty();

        // Vérifier si ce déplacement est une prise ou un simple déplacement
        int dx = destination.getX() - origine.getX();
        int dy = destination.getY() - origine.getY();

        boolean mouvementSimple = Math.abs(dx) == 1 && Math.abs(dy) == 1;

        boolean mouvementPrise = Math.abs(dx) == 2 && Math.abs(dy) == 2;

        // S'il y a obligation de manger, on refuse les mouvements simples
        if (Manger && !mouvementPrise) {
            return false;
        }

        // Destination doit être dans le plateau et vide
        if (!plateau.estDansLePlateau(dx, dy) || !plateau.caseVide(dx, dy)) {
            return false;
        }

        if (mouvementSimple) {
            // Autoriser seulement si aucune prise obligatoire
            if (!Manger && pion.deplace(dx, dy)) {
                pion.deplace(dx, dy);
                changerJoueur();
                return true;
            } else {
                return false;
            }
        } else if (mouvementPrise) {
            // Case intermédiaire (pion à manger)
            int xInter = (origine.getX() + destination.getX()) / 2;
            int yInter = (origine.getY() + destination.getY()) / 2;
            Point2D posInter = new Point2D(xInter, yInter);

            Pion pionAdverse = plateau.getPion(posInter);
            if (pionAdverse == null) {
                return false;
            }
            if (pionAdverse.getCouleur().equals(joueurCourant)) {
                return false;
            }

            // On peut aussi ajouter un appel à pion.deplacementPriseValide(...)
            // si tu veux séparer logique prise / déplacement
            // On déplace le pion
            pion.deplace(dx, dy);
            // On retire le pion mangé
            plateau.enleverPion(pionAdverse);

            // Vérifier si ce pion peut encore manger (enchaînement de prises)
            if (!pion.doitManger().isEmpty()) {
                // Même joueur rejoue avec ce pion
                return true;
            } else {
                changerJoueur();
                return true;
            }
        }

        return false;
    }

    // ---------- Fin de partie ----------
    /**
     * Fin de partie si un des joueurs n'a plus de pions ou ne peut plus jouer.
     */
    public boolean estFinDePartie() {
        boolean blancSansPion = plateau.getMaListePionBlanc().isEmpty();
        boolean noirSansPion = plateau.getMaListePionNoir().isEmpty();

        if (blancSansPion || noirSansPion) {
            return true;
        }

        // Tu peux ajouter ici : vérifier si aucun coup possible pour un joueur.
        return false;
    }

    public Plateau getPlateau() {
        return plateau;
    }
}

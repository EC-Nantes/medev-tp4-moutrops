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
        if (p.doitManger()) {   // ⭐ maintenant doitManger() renvoie boolean
            resultat.add(p);
        }
    }

    return resultat;
}


    public boolean jouerCoup(Point2D origine, Point2D destination) {
    Pion pion = plateau.getPion(origine);
    System.out.println("Test pion = " + pion);
    
    // 1) Il faut un pion sur la case d'origine
    if (pion == null) {
        System.out.println("Pas de pion sur la case d'origine");
        return false;
    }

    // 2) Le pion doit être de la couleur du joueur courant
    if (!pion.getCouleur().equals(joueurCourant)) {
        System.out.println("Ce n'est pas le tour de cette couleur");
        return false;
    }

    // 3) Obligation de manger ?
    List<Pion> pionsObliges = getPionsQuiDoiventManger();
    boolean doitManger = !pionsObliges.isEmpty();
    System.out.println("il doit = " + doitManger);

    int dx = destination.getX() - origine.getX();
    int dy = destination.getY() - origine.getY();

    boolean mouvementSimple = Math.abs(dx) == 1 && Math.abs(dy) == 1;
    boolean mouvementPrise  = Math.abs(dx) == 2 && Math.abs(dy) == 2;

    // 4) S'il doit manger, on refuse les mouvements simples
    if (doitManger && !mouvementPrise) {
        System.out.println("Un pion doit manger, déplacement simple interdit");
        return false;
    }

    // 5) Destination doit être dans le plateau et vide
    if (!plateau.estDansLePlateau(destination.getX(), destination.getY())
            || !plateau.caseVide(destination.getX(), destination.getY())) {
        System.out.println("Destination hors plateau ou occupée");
        return false;
    }

    // 6) Mouvement simple (sans prise)
    if (mouvementSimple) {
        if (!doitManger) {
            // deplace(dx, dy) est un déplacement RELATIF
            boolean ok = pion.deplace(dx, dy);
            if (ok) {
                changerJoueur();
                System.out.println("Déplacement simple effectué");
                return true;
            } else {
                System.out.println("Déplacement simple refusé par deplace()");
                return false;
            }
        } else {
            return false;
        }
    }

    // 7) Mouvement de prise
    if (mouvementPrise) {
        int xInter = (origine.getX() + destination.getX()) / 2;
        int yInter = (origine.getY() + destination.getY()) / 2;
        Point2D posInter = new Point2D(xInter, yInter);

        Pion pionAdverse = plateau.getPion(posInter);
        if (pionAdverse == null) {
            System.out.println("Aucun pion à manger sur la case intermédiaire");
            return false;
        }
        if (pionAdverse.getCouleur().equals(joueurCourant)) {
            System.out.println("Le pion intermédiaire est de la même couleur");
            return false;
        }

        // Déplacement du pion (relatif)
        boolean ok = pion.deplace(dx, dy);
        if (!ok) {
            System.out.println("Le déplacement de prise a échoué");
            return false;
        }

        // Retirer le pion mangé
        plateau.enleverPion(pionAdverse);

        // 8) Enchaînement de prises possible ?
        if (pion.doitManger()) {   // ⬅️ ici l'ancienne version faisait .isEmpty()
            System.out.println("Le même pion peut encore manger");
            // même joueur rejoue ce pion
            return true;
        } else {
            changerJoueur();
            return true;
        }
    }

    // 9) Sinon, mouvement illégal
    System.out.println("Mouvement ni simple ni de prise");
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

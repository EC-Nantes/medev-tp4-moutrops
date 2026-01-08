/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package org.centrale.projet.jeudedame;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Max
 */
public class JeuDeDameTest {

    public JeuDeDameTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    @org.junit.jupiter.api.Test
    public void testDemarrerNouvellePartie() {
        System.out.println("demarrerNouvellePartie");
        JeuDeDame instance = new JeuDeDame(); // le constructeur appelle déjà demarrerNouvellePartie()

        Plateau plateau = instance.getPlateau();

        // 40 pions en dames internationales (20 par camp)
        assertEquals(20, plateau.getMaListePionBlanc().size(), "Nombre de pions blancs incorrect");
        assertEquals(20, plateau.getMaListePionNoir().size(), "Nombre de pions noirs incorrect");

        // Le joueur courant doit être Blanc
        assertEquals("Blanc", instance.getJoueurCourant());

        // Vérifier que tous les pions sont bien sur le plateau 10x10
        for (Pion p : plateau.getMaListePionBlanc()) {
            assertTrue(plateau.estDansLePlateau(p.getPosition().getX(), p.getPosition().getY()));
        }
        for (Pion p : plateau.getMaListePionNoir()) {
            assertTrue(plateau.estDansLePlateau(p.getPosition().getX(), p.getPosition().getY()));
        }
    }

    @org.junit.jupiter.api.Test
    public void testGetPionsQuiDoiventManger() {
        System.out.println("getPionsQuiDoiventManger");
        JeuDeDame instance = new JeuDeDame();

        List<Pion> result = instance.getPionsQuiDoiventManger();

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Au début de la partie, aucun pion ne devrait être obligé de manger");
    }

    /**
     * Test of jouerCoup method, of class JeuDeDame.
     */
    @org.junit.jupiter.api.Test
    public void testJouerCoup() {
        System.out.println("jouerCoup");
        //Coup Simple
        JeuDeDame jeu = new JeuDeDame();
        Plateau plateau = jeu.getPlateau();

        // On prend un pion blanc de départ
        Point2D origine = new Point2D(1, 6);
        Pion pion = plateau.getPion(origine);
        assertNotNull(pion);
        assertEquals("Blanc", pion.getCouleur());

        Point2D destination = new Point2D(0, 5);

        boolean ok = jeu.jouerCoup(origine, destination);

        assertTrue(ok, "Le coup simple devrait être accepté");
        assertNull(plateau.getPion(origine), "L'ancienne case doit être vide");
        assertEquals(pion, plateau.getPion(destination), "Le pion doit être en destination");
        assertEquals("Noir", jeu.getJoueurCourant(), "Le tour doit passer aux Noirs");
    }

    /**
     * Test of estFinDePartie method, of class JeuDeDame.
     */
    @org.junit.jupiter.api.Test
    public void testEstFinDePartie() {
        System.out.println("estFinDePartie");
        JeuDeDame instance = new JeuDeDame();

        assertFalse(instance.estFinDePartie(),
                "La partie ne devrait pas être finie au début");

        instance.getPlateau().getMaListePionBlanc().clear();

        assertTrue(instance.estFinDePartie(),
                "La partie devrait être finie si les blancs n'ont plus de pions");
    }

}

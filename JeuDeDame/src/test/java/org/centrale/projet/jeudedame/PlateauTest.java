/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package org.centrale.projet.jeudedame;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author agach
 */
public class PlateauTest {
    
    public PlateauTest() {
    }
    
    Plateau p;
    @BeforeEach
    public void init() {
        p=new Plateau();
        p.creerPlateau();
    }


    @Test
    public void testCreerPlateau() {
        System.out.println("creerPlateau");
        assertEquals(20,p.getMaListePionBlanc().size());
    }


    /**
     * Test of estDansLePlateau method, of class Plateau.
     */
    @Test
    public void testEstDansLePlateau() {
        System.out.println("estDansLePlateau");
        assertTrue(p.estDansLePlateau(0, 0));
        assertTrue(p.estDansLePlateau(9, 9));
        assertFalse(p.estDansLePlateau(-1, 5));
        assertFalse(p.estDansLePlateau(3, 10));
    }

    /**
     * Test of enleverPion method, of class Plateau.
     */
    @Test
    public void testEnleverPion() {
        System.out.println("enleverPion");
        Pion pb = p.getMaListePionBlanc().get(0);
        p.enleverPion(pb);
        assertFalse(p.getMaListePionBlanc().contains(pb));
    }

    /**
     * Test of caseVide method, of class Plateau.
     */
    @Test
    public void testCaseVide() {
        assertTrue(p.caseVide(5, 5));
        Pion pb = p.getMaListePionBlanc().get(0);
        assertFalse(p.caseVide(pb.getPosition().getX(), pb.getPosition().getY()));
    }

    /**
     * Test of vider method, of class Plateau.
     */
    @Test
    public void testVider() {
        p.vider();
        assertEquals(0, p.getMaListePionBlanc().size());
        assertEquals(0, p.getMaListePionNoir().size());
    }
    
}

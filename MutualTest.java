package junit;

import static org.junit.Assert.*;
import org.junit.Test;

public class MutualTest {

    @Test
    public void testAddTitular() {
        Mutual m = new Mutual();
        m.addTitular(1); // suma 400
        assertEquals(800.0, m.getCredito(), 0.001);
        assertEquals(1, m.getTitulares());
    }

    @Test
    public void testDeleteTitular() {
        Mutual m = new Mutual();
        m.addTitular(1);      // +400 → 800
        m.deleteTitular(1);   // -400 → 400
        assertEquals(400.0, m.getCredito(), 0.001);
        assertEquals(0, m.getTitulares());
    }

    @Test
    public void testAddBeneficiario() {
        Mutual m = new Mutual();
        m.addBeneficiario(2);  // +400 consumo
        assertEquals(400.0, m.getConsumo(), 0.001);
        assertEquals(2, m.getBeneficiarios());
    }

    @Test
    public void testDeleteBeneficiario() {
        Mutual m = new Mutual();
        m.addBeneficiario(2);  // +400
        m.deleteBeneficiario(1); // -200
        assertEquals(200.0, m.getConsumo(), 0.001);
        assertEquals(1, m.getBeneficiarios());
    }

    @Test
    public void testGetTitulares() {
        Mutual m = new Mutual();
        assertEquals(0, m.getTitulares());
        m.addTitular(2);
        assertEquals(1, m.getTitulares());
    }

    @Test
    public void testGetBeneficiarios() {
        Mutual m = new Mutual();
        assertEquals(0, m.getBeneficiarios());
        m.addBeneficiario(3);
        assertEquals(3, m.getBeneficiarios());
    }

    @Test
    public void testGetCredito() {
        Mutual m = new Mutual();
        assertEquals(400.0, m.getCredito(), 0.001);
    }

    @Test
    public void testGetConsumo() {
        Mutual m = new Mutual();
        assertEquals(0.0, m.getConsumo(), 0.001);
        m.addBeneficiario(1);
        assertEquals(200.0, m.getConsumo(), 0.001);
    }

    @Test
    public void testGetEstado() {
        Mutual m = new Mutual();
        assertEquals(3, m.getEstado());  // Estado inicial: Ganancia
        m.addBeneficiario(5); // consumo = 1000, debería pasar a estado 1 (Pérdida)
        assertEquals(1, m.getEstado());
    }

    @Test
    public void testEstado() {
        Mutual m = new Mutual();        // crédito inicial = 400
        m.addTitular(1);                // +400 → crédito = 800
        m.addBeneficiario(3);           // +600 → consumo = 600

        // pc = 100 - (600 * 100 / 800) = 25 → estado = 3 (Ganancia)
        assertEquals(3, m.getEstado());

        m.addBeneficiario(1);           // +200 → consumo = 800
        // pc = 100 - (800 * 100 / 800) = 0 → estado = 2 (Pasivo)
        assertEquals(2, m.getEstado());

        m.addTitular(0);                // +200 → crédito = 1000
        // pc = 100 - (800 * 100 / 1000) = 20 → estado = 0 (Balanceado)
        assertEquals(0, m.getEstado());

        // Probamos también estado pasivo forzado:
        m.addBeneficiario(1);           // consumo = 1000
        // pc = 100 - (1000 * 100 / 1000) = 0 → estado = 2
        assertEquals(2, m.getEstado());

        m.deleteTitular(2);             // -500 → crédito = 500
        // pc = 100 - (1000 * 100 / 500) = -100 → estado = 1 (Pérdida)
        assertEquals(1, m.getEstado());

        m.addTitular(1);                // +400 → crédito = 900
        m.deleteBeneficiario(1);        // consumo = 800
        // pc = 100 - (800 * 100 / 900) ≈ 11.1 → estado = 0 (Balanceado)
        assertEquals(0, m.getEstado());

        m.deleteTitular(1);             // -400 → crédito = 500
        // pc = 100 - (800 * 100 / 500) = -60 → estado = 1
        assertEquals(1, m.getEstado());

        m.addTitular(2);                // +500 → crédito = 1000
        m.deleteBeneficiario(1);        // consumo = 600
        // pc = 100 - (600 * 100 / 1000) = 40 → estado = 3 (Ganancia)
        assertEquals(3, m.getEstado());
    }
}

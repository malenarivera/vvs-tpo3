package vvs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class MutualTest {

	@Test
	void testAddTitular() {
	    Mutual m = new Mutual();
        m.addTitular(1); // Categoria 1 suma 400
        assertEquals(1, m.getTitulares());
        assertEquals(1, m.getTitulares());
	}
	

	@Test
	void testDeleteTitular() {
		fail("Not yet implemented");
	}

	@Test
	void testAddBeneficiario() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteBeneficiario() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTitulares() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBeneficiarios() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCredito() {
		fail("Not yet implemented");
	}

	@Test
	void testGetConsumo() {
		fail("Not yet implemented");
	}

	@Test
	void testGetEstado() {
		fail("Not yet implemented");
	}

}

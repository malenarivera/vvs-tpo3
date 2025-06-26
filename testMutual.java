package vvs;

public class testMutual {
    public static void main(String[] args) {
        Mutual m = new Mutual();

        // Estado inicial:
        // crédito = 400, consumo = 0, estado = 3 (Ganancia)
        System.out.println("Estado inicial");
        print(m);

        // Acción 1: addTitular(2)
        // montoCategoria(2) = 500
        // crédito += 500 → 400 + 500 = 900
        m.addTitular(2);
        System.out.println("Después de addTitular(2)"); 
        print(m);

        // Acción 2: addBeneficiario(1)
        // consumo += 200 → 0 + 200 = 200
        m.addBeneficiario(1);
        System.out.println("Después de addBeneficiario(1)");
        print(m);

        // Acción 3: deleteBeneficiario(1)
        // consumo -= 200 → 200 - 200 = 0
        m.deleteBeneficiario(1);
        System.out.println("Después de deleteBeneficiario(1)");
        print(m);

        // Acción 4: deleteTitular(2)
        // montoCategoria(2) = 500
        // crédito -= 500 → 900 - 500 = 400
        m.deleteTitular(2);
        System.out.println("Después de deleteTitular(2)");
        print(m);
    }

    public static void print(Mutual m) {
        System.out.println("  Titulares: " + m.getTitulares());
        System.out.println("  Beneficiarios: " + m.getBeneficiarios());
        System.out.println("  Crédito: " + m.getCredito());
        System.out.println("  Consumo: " + m.getConsumo());
        System.out.println("  Estado: " + m.getEstado());
        System.out.println("------------------------------");
    }
}




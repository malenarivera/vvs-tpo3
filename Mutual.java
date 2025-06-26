package vvs;

public class Mutual {
    private double credito;
    private double consumo;
    private int titulares;
    private int beneficiarios;
    private int estado;

    public Mutual() {
        this.credito = 400;
        this.consumo = 0;
        this.beneficiarios = 0;
        this.titulares = 0;
 this.estado = 3;

    }

    public void addTitular(int categoria) {
        this.credito += montoCategoria(categoria);
        this.titulares++;
        cambiarEstado();
    }

    public void deleteTitular(int categoria) {
        this.credito -= montoCategoria(categoria);
        this.titulares--;
        cambiarEstado();
    }

    public void addBeneficiario(int cantidad) {
        this.consumo += cantidad * 200;
        this.beneficiarios += cantidad;
        cambiarEstado();
    }

    public void deleteBeneficiario(int cantidad) {
        this.consumo -= cantidad * 200;
        this.beneficiarios -= cantidad;
        cambiarEstado();
    }

    private int montoCategoria(int categoria) {
        if (categoria == 0)
            return 200;
        else if (categoria == 1)
            return 400;
        else
            return 500;
    }

    private void cambiarEstado() {
        double pc = 100 - (consumo * 100 / credito);
        if (pc > 20)
            estado = 3; // Ganancia
        else if (pc > 0)
            estado = 0; // Balanceado
        else if (pc > -20)
            estado = 2; // Pasivo
        else
            estado = 1; // Pérdida
    }

    // Getters
    public int getTitulares() {
        return titulares;
    }

    public int getBeneficiarios() {
        return beneficiarios;
    }

    public double getCredito() {
        return credito;
    }

    public double getConsumo() {
        return consumo;
    }

    public int getEstado() {
        return estado;
    }
}



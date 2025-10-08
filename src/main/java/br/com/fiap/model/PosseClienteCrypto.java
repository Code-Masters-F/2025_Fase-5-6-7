package br.com.fiap.model;

public class PosseClienteCrypto {

    private Carteira carteira;
    private Criptomoeda criptomoeda;
    private double quantidade;
    private int id;

    public PosseClienteCrypto(Carteira carteira, Criptomoeda criptomoeda, double quantidade, int id) {
        this.carteira = carteira;
        this.criptomoeda = criptomoeda;
        this.quantidade = quantidade;
        this.id = id;
    }

    // Para ser APAGADO quando houver conexão com o banco de dados
    private static int proximoId = 1;
    public PosseClienteCrypto(Criptomoeda criptomoeda) {
        this.criptomoeda = criptomoeda;
        this.quantidade = 0.0;
        this.id = proximoId++;
    }

    public PosseClienteCrypto() {

    }

    public Criptomoeda getCrypto() {
        return criptomoeda;
    }

    public void setCrypto(Criptomoeda novaCriptomoeda) {
        this.criptomoeda = novaCriptomoeda;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }


    public int getId() {
        return id;
    }

    public Carteira getCarteira() {
        return this.carteira;
    }

    public void setCarteira(Carteira novaCarteira) {
        this.carteira = novaCarteira;
    }


    @Override
    public String toString() {
        return "PosseClienteCrypto{" +
                "crypto=" + criptomoeda +
                ", quantidade=" + quantidade +
                ", preço=" +  +
                '}';
    }
}

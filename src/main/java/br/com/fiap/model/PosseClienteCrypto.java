package br.com.fiap.model;

public class PosseClienteCrypto {

    private Carteira carteira;
    private Crypto crypto;
    private double quantidade;
    private int id;

    public PosseClienteCrypto(Carteira carteira, Crypto crypto, double quantidade, int id) {
        this.carteira = carteira;
        this.crypto = crypto;
        this.quantidade = quantidade;
        this.id = id;
    }

    // Para ser APAGADO quando houver conexão com o banco de dados
    private static int proximoId = 1;
    public PosseClienteCrypto(Crypto crypto) {
        this.crypto = crypto;
        this.quantidade = 0.0;
        this.id = proximoId++;
    }

    public PosseClienteCrypto() {

    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto novaCrypto) {
        this.crypto = novaCrypto;
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

    /**
     * @return retorna quanto vale a quantidade que o cliente possui no momento, em R$
     */
    public double precificar() {
        return quantidade * Main.getValorUnitarioCrypto(crypto.getId());
    }

    @Override
    public String toString() {
        return "PosseClienteCrypto{" +
                "crypto=" + crypto +
                ", quantidade=" + quantidade +
                ", preço=" +  +
                '}';
    }
}

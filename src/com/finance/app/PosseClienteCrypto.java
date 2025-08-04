package com.finance.app;

public class PosseClienteCrypto {

    private Crypto crypto;
    private double quantidade;
    private int id;

    public PosseClienteCrypto(Crypto crypto, double quantidade, int id) {
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

    public Crypto getCrypto() {
        return crypto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public int getId() {
        return id;
    }

    protected void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
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

package br.com.fiap.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Carteira {

    private ContaInterna dono;
    private int id;
    Set<PosseClienteCrypto> listaDeCryptos = new HashSet<>();

    public Carteira(ContaInterna dono, int id) {
        this.dono = dono;
        this.id = id;
    }

    public Carteira() {
    }

    public ContaInterna getContaCliente() {
        return dono;
    }

    public void setContaCliente(ContaInterna novoDono) {
        this.dono = novoDono; 
    }

    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    /**
     * Busca na lista de cryptos uma moeda pelo seu id
     * @param idCrypto id da Crypto cadastrada na main
     * @return retorna a criptomoeda caso o usuário a possua (em forma de
     * PosseClienteCrypto que alem da moeda possui a quantidade), ou nulo
     * caso contrario
     */
    protected PosseClienteCrypto buscarCryptoEmPosse(int idCrypto) {
        for (PosseClienteCrypto posseCrypto : listaDeCryptos) {
            if (posseCrypto.getCrypto().getId() == idCrypto) {
                return posseCrypto;
            }
        }
        return null;
    }


    public Map<Integer, Double> getCriptos() {
        Map<Integer, Double> mapaCriptos = new HashMap<>();
        for (PosseClienteCrypto posse : listaDeCryptos) {
            mapaCriptos.put(posse.getCrypto().getId(), posse.getQuantidade());
        }
        return mapaCriptos;
    }

}

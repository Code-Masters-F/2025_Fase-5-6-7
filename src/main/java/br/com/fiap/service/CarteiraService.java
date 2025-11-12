package br.com.fiap.service;

import br.com.fiap.dao.PosseDao;
import br.com.fiap.model.PosseClienteCrypto;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CarteiraService {

    public static void consultarCarteira(Scanner scanner) {
        System.out.print("Digite o ID da conta que deseja consultar a carteira: ");
        int contaId;
        try {
            contaId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        try {
            PosseDao posseDao = new PosseDao();
            List<PosseClienteCrypto> posses = posseDao.listarPossesPorCarteira(contaId);

            if (posses.isEmpty()) {
                System.out.println("Nenhuma posse encontrada para esta conta.");
                return;
            }

            System.out.println("\n--- Carteira ---");
            for (PosseClienteCrypto p : posses) {
                System.out.printf("- %s (%s) | ID Criptomoeda: %d | Quantidade: %.8f%n",
                        p.getCrypto().getNome(),
                        p.getCrypto().getSigla(),
                        p.getCrypto().getId(),
                        p.getQuantidade());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar carteira: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

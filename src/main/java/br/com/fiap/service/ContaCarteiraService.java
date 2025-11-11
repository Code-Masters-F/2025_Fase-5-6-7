package br.com.fiap.service;

import br.com.fiap.dao.*;
import br.com.fiap.model.*;
import br.com.fiap.utils.ContaExternaUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Service para operações relacionadas a Contas e Carteiras
 */
public class ContaCarteiraService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public static void adicionarSaldoConta(Scanner scanner) {
        System.out.println("\n=== Adicionar Saldo à Conta ===");

        System.out.print("Digite o ID da conta interna: ");
        int idConta;
        try {
            idConta = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        System.out.print("Digite o valor a adicionar: R$ ");
        double valor;
        try {
            valor = Double.parseDouble(scanner.nextLine().trim());
            if (valor <= 0) {
                System.err.println("Valor deve ser maior que zero!");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido!");
            return;
        }

        try {
            ContaInternaDao contaDao = new ContaInternaDao();

            // Verifica se a conta existe antes
            ContaInterna conta = ContaInternaDao.buscarContaInternaPorId(idConta);
            if (conta == null) {
                System.err.println("Conta não encontrada com ID: " + idConta);
                return;
            }


            try (var conn = br.com.fiap.factory.ConnectionFactory.getConnection()) {
                contaDao.adicionarSaldo(conn, idConta, BigDecimal.valueOf(valor));
                System.out.println("\n✓ Saldo adicionado com sucesso!");
                System.out.printf("Valor adicionado: R$ %,.2f%n", valor);
                System.out.println("Conta ID: " + idConta);
            }

        } catch (SQLException e) {
            System.err.println("\n✗ Erro ao adicionar saldo: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
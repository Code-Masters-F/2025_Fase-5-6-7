package br.com.fiap.service;

import br.com.fiap.dao.ContaExternaDao;
import br.com.fiap.dao.ContaInternaDao;
import br.com.fiap.model.ContaExterna;
import br.com.fiap.model.ContaInterna;
import br.com.fiap.model.TipoTransacaoFiat;
import br.com.fiap.model.TransacaoConta;
import br.com.fiap.utils.ContaExternaUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ContaExternaService {

    public static void enviarTransferenciaContaExternaParaInterna(Scanner scanner) {
        System.out.println("\n=== Transferência de Conta Externa para Conta Interna ===");

        System.out.print("Digite o id da Conta Externa cadastrada: ");
        int idContaExterna = Integer.parseInt(scanner.nextLine().trim());

        ContaExterna contaExterna = null;
        try {
            contaExterna = ContaExternaDao.buscarContaExternaPorId(idContaExterna);

            if (contaExterna == null) {
                System.err.println("Conta externa não encontrada/não cadastrada. Verifique número/agência/id.");
                return;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao procurar Conta Externa no Banco: " + e.getMessage());
        }

        System.out.print("Digite o id da Conta Interna Cadastrada que deseja realizar a transferencia: ");
        int idContaInterna = Integer.parseInt(scanner.nextLine().trim());

        ContaInterna contaInterna = null;
        try {
            contaInterna = ContaInternaDao.buscarContaInternaPorId(idContaInterna);

            if (contaInterna == null) {
                System.err.println("Conta interna não encontrada/não cadastrada. Verifique número/agência/id.");
                return;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao procurar Conta Interna no Banco: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.print("Digite o valor que deseja transferir: ");
        BigDecimal valor = null;
        try {
            valor = new BigDecimal(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter valor para BigDecimal: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        TransacaoConta transacao = null;
        try {
            transacao = new TransacaoConta();
            transacao.setContaInterna(contaInterna);
            transacao.setContaExterna(contaExterna);
            transacao.setValor(valor);
            transacao.setTipo(TipoTransacaoFiat.DEPOSITO);
            transacao.setDataHora(LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            TransacaoContaService service = new TransacaoContaService();
            service.registrarTransacao(transacao);
            System.out.println("Transferência realizada com sucesso!");
        } catch(SQLException e) {
            System.err.println("Erro ao registrar transação: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void cadastrarContaExterna(Scanner scanner) {
        int idCliente;
        while (true) {
            System.out.print("Digite o id do cliente: ");
            String idClienteInput = scanner.nextLine().trim();

            if (!idClienteInput.matches("\\d+")) {
                System.err.println("ID inválido. Use apenas dígitos.");
                continue;
            }
            try {
                idCliente = Integer.parseInt(idClienteInput);
                break;
            } catch (NumberFormatException e) {
                System.err.println("ID fora do intervalo de int.");
            }
        }

        int numeroConta;
        while (true) {
            System.out.print("Insira o número da conta do Banco Externo (5 a 9 dígitos, opcional -DV): ");
            String numeroContaInput = scanner.nextLine().trim();

            if (!numeroContaInput.matches("\\d{5,9}(-[0-9X])?")) {
                System.err.println("Número da conta inválido. Ex: 123456 ou 123456-7.");
                continue;
            }
            String parteNumerica = numeroContaInput.split("-")[0];
            try {
                numeroConta = Integer.parseInt(parteNumerica);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Número da conta fora do intervalo de int.");
            }
        }

        int numeroAgencia;
        while (true) {
            System.out.print("Insira o número da agência do Banco Externo (4 a 5 dígitos): ");
            String agenciaInput = scanner.nextLine().trim();
            if (!agenciaInput.matches("\\d{4,5}")) {
                System.err.println("Número da agência inválido. Deve conter 4 ou 5 dígitos.");
                continue;
            }
            try {
                numeroAgencia = Integer.parseInt(agenciaInput);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Agência fora do intervalo de int.");
            }
        }

        ContaExternaUtils.listarBancosExternosDisponiveis();

        int codigoBancoExterno;
        while (true) {
            System.out.print("Insira o código do Banco Externo (3 dígitos): ");
            String codigoBancoInput = scanner.nextLine().trim();
            if (!codigoBancoInput.matches("\\d{3}")) {
                System.err.println("Código do banco inválido. Use 3 dígitos (ex: 001, 237, 341).");
                continue;
            }
            try {
                codigoBancoExterno = Integer.parseInt(codigoBancoInput);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Código do banco fora do intervalo de int.");
            }
        }

        String nomeBancoExterno;
        while (true) {
            System.out.print("Insira o nome do Banco Externo: ");
            nomeBancoExterno = scanner.nextLine().trim();
            if (nomeBancoExterno.isEmpty()) {
                System.err.println("Nome do banco não pode ser vazio.");
                continue;
            }
            break;
        }

        try {
            ContaExternaDao contaExternaDao = new ContaExternaDao();
            contaExternaDao.inserirContaExterna(
                    idCliente,
                    numeroConta,
                    numeroAgencia,
                    codigoBancoExterno,
                    nomeBancoExterno
            );
            System.out.println("Conta externa cadastrada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar conta externa: " + e.getMessage());
        }
    }
}

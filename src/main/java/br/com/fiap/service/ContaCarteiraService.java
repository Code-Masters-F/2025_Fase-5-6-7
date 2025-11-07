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

    /**
     * Consulta dados de uma conta interna por ID do cliente
     */
    public static void consultarContaInterna(Scanner scanner) {
        System.out.println("\n=== Consultar Conta Interna ===");
        System.out.print("Digite o ID do cliente: ");

        int idCliente;
        try {
            idCliente = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        try {
            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.consultarClientePorId(idCliente);

            if (cliente == null) {
                System.err.println("Cliente não encontrado com ID: " + idCliente);
                return;
            }

            ContaInternaDao contaDao = new ContaInternaDao();
            ContaInterna conta = contaDao.buscarContaInternaPorCliente(cliente);

            if (conta == null) {
                System.err.println("Este cliente não possui conta interna cadastrada.");
                return;
            }

            System.out.println("\n--- Dados da Conta Interna ---");
            System.out.println("ID da Conta: " + conta.getId());
            System.out.println("Número da Conta: " + conta.getNumeroConta());
            System.out.println("Agência: " + conta.getNumeroAgencia());
            System.out.printf("Saldo: R$ %,.2f%n", conta.getSaldo());
            System.out.println("\n--- Dados do Titular ---");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Email: " + cliente.getEmail());

        } catch (SQLException e) {
            System.err.println("Erro ao consultar conta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Consulta a carteira de criptomoedas de uma conta
     */
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

    /**
     * Realiza transferência entre duas contas internas
     */
    public static void transferirEntreContasInternas(Scanner scanner) {
        System.out.println("\n=== Transferir Entre Contas Internas ===");

        // Conta origem
        System.out.print("Digite o ID da conta ORIGEM: ");
        int idOrigem;
        try {
            idOrigem = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        // Conta destino
        System.out.print("Digite o ID da conta DESTINO: ");
        int idDestino;
        try {
            idDestino = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        // Validação básica
        if (idOrigem == idDestino) {
            System.err.println("Conta de origem e destino não podem ser iguais!");
            return;
        }

        // Valor
        System.out.print("Digite o valor da transferência: R$ ");
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
            contaDao.transferirParaContaInterna(idOrigem, idDestino, valor);
            System.out.println("\n✓ Transferência realizada com sucesso!");
            System.out.printf("Valor transferido: R$ %,.2f%n", valor);
            System.out.println("De: Conta ID " + idOrigem);
            System.out.println("Para: Conta ID " + idDestino);

        } catch (SQLException e) {
            System.err.println("\n✗ Erro ao realizar transferência: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adiciona saldo a uma conta interna
     */
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

            // Adiciona o saldo (precisa de uma conexão)
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

    /**
     * Consulta o histórico de transações de uma conta
     */
    public static void consultarHistoricoTransacoes(Scanner scanner) {
        System.out.println("\n=== Consultar Histórico de Transações ===");

        // Solicita ID da conta
        System.out.print("Digite o ID da conta interna: ");
        int idConta;
        try {
            idConta = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        // Solicita data inicial
        System.out.println("\nData INICIAL do período:");
        LocalDate dataInicio = lerData(scanner);

        // Solicita data final
        System.out.println("\nData FINAL do período:");
        LocalDate dataFim = lerData(scanner);

        // Validação
        if (dataInicio.isAfter(dataFim)) {
            System.err.println("A data inicial não pode ser posterior à data final!");
            return;
        }

        try {
            // Converte para Instant
            Instant from = dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant to = dataFim.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

            // Consulta o histórico
            HistoricoTransacaoDao dao = new HistoricoTransacaoDao();
            List<HistoricoTransacao> historico = dao.consultarHistoricoPorConta(idConta, from, to, 0, 100);

            // Exibe resultados
            if (historico.isEmpty()) {
                System.out.println("\nNenhuma transação encontrada no período.");
                return;
            }

            System.out.println("\n" + "=".repeat(120));
            System.out.printf("%-10s | %-20s | %-10s | %-15s | %-15s | %-30s%n",
                    "ID", "Data/Hora", "Tipo", "Valor", "Conta Destino", "Nome Contraparte");
            System.out.println("=".repeat(120));

            BigDecimal totalSaques = BigDecimal.ZERO;
            BigDecimal totalDepositos = BigDecimal.ZERO;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

            for (HistoricoTransacao t : historico) {
                String dataHoraStr = formatter.format(t.dataHora());
                String tipoStr = t.tipo().name();
                String valorStr = String.format("R$ %,.2f", t.valor());

                // Soma totais
                if (t.tipo() == TipoTransacaoFiat.SAQUE) {
                    totalSaques = totalSaques.add(t.valor());
                } else {
                    totalDepositos = totalDepositos.add(t.valor());
                }

                System.out.printf("%-10d | %-20s | %-10s | %15s | %15d | %-30s%n",
                        t.idTransacao(),
                        dataHoraStr,
                        tipoStr,
                        valorStr,
                        t.idContaContraparte(),
                        t.nomeContraparte());
            }

            System.out.println("=".repeat(120));
            System.out.println("\n--- RESUMO DO PERÍODO ---");
            System.out.printf("Total de transações: %d%n", historico.size());
            System.out.printf("Total em SAQUES:     R$ %,.2f%n", totalSaques);
            System.out.printf("Total em DEPÓSITOS:  R$ %,.2f%n", totalDepositos);
            System.out.printf("SALDO DO PERÍODO:    R$ %,.2f%n", totalDepositos.subtract(totalSaques));
            System.out.println();

        } catch (SQLException e) {
            System.err.println("Erro ao consultar histórico: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Cadastra uma conta externa
     */
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

    /**
     * Método auxiliar para ler datas
     */
    private static LocalDate lerData(Scanner scanner) {
        while (true) {
            System.out.print("Digite a data (dd/MM/yyyy): ");
            String entrada = scanner.nextLine();
            try {
                return LocalDate.parse(entrada, FORMATTER);
            } catch (Exception e) {
                System.out.println("Formato de data inválido. Utilize dd/MM/yyyy.");
            }
        }
    }
}
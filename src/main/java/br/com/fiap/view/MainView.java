package br.com.fiap.view;


import br.com.fiap.dao.*;
import br.com.fiap.model.*;
import br.com.fiap.dao.ClienteDao;
import br.com.fiap.service.TransacaoContaService;
import br.com.fiap.utils.ContaExternaUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class MainView {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static String lerCPF(Scanner scanner) {
        while (true) {
            System.out.print("Digite o CPF (11 dígitos): ");
            String cpf = scanner.nextLine().replaceAll("\\D", "");
            if (cpf.length() == 11) return cpf;
            System.out.println("CPF inválido. Deve conter 11 dígitos numéricos, tente novamente.");
        }
    }

    private static String lerEmail(Scanner scanner) {
        String email;
        while (true) {
            System.out.print("Digite o e-mail: ");
            email = scanner.nextLine();
            if (EMAIL_PATTERN.matcher(email).matches()) {
                return email;
            }
            System.out.println("E-mail inválido. Formato correto: exemplo@dominio.com");
        }
    }

    private static LocalDate lerData(Scanner scanner) {
        while (true) {
            System.out.print("Digite a data (dd/MM/yyyy): ");
            String entrada = scanner.nextLine();
            try {
                return LocalDate.parse(entrada, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Utilize dd/MM/yyyy.");
            }
        }
    }

    public static void main(String[] args) {
        String opcao;
        Scanner scanner = new Scanner(System.in);

        do {
            exibirMenu();
            opcao = scanner.nextLine();
            System.out.println();
            try {
                switch (opcao) {
                    case "1": cadastrarCliente(scanner); break;
                    case "2": cadastrarContaExterna(scanner); break;
                    case "3": cadastrarCrypto(scanner); break;
                    case "4": exibirClientesCadastrados(); break;
                    case "5": consultarCliente(scanner); break;
                    case "6": atualizarCliente(scanner); break;
                    case "7": deletarCliente(scanner); break;
                    case "8": consultarCarteira(scanner); break;
                    case "9": enviarTransferenciaContaInternaParaExterna(scanner); break;
                    case "10": enviarTransferenciaContaExternaParaInterna(scanner); break;
                    case "11": adicionarSaldo(scanner); break;
                    case "12": listarCriptoativos(); break;
                    case "13": comprarCrypto(scanner); break;
                    case "14": venderCrypto(scanner); break;
                    case "15": consultarHistoricoTransacoes(scanner); break;
                    case "0": System.out.println("Saindo do sistema..."); break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Algo deu errado! Tente novamente.");
            }

        } while (!opcao.equals("0"));
    }

    private static void cadastrarContaExterna(Scanner scanner) {
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

    private static void exibirMenu() {
        System.out.println("\n======= MENU =======");
        System.out.println("1  - Cadastrar novo cliente");
        System.out.println("2  - Cadastrar conta externa");
        System.out.println("3 - Cadastrar nova criptomoeda");
        System.out.println("4  - Exibir clientes cadastrados");
        System.out.println("5  - Consultar cliente");
        System.out.println("6  - Atualizar cliente");
        System.out.println("7  - Deletar cliente");
        System.out.println("8 - Listar criptomoedas");
        System.out.println("9 - Comprar criptomoeda");
        System.out.println("10 - Vender criptomoeda");
        System.out.println("11 - Consultar carteira");
        System.out.println("12  - Adicionar saldo");
        System.out.println("13  - Enviar Transferência de Conta Interna Para Conta Externa");
        System.out.println("14 - Enviar Transferência de Conta Externa para Conta Interna");
        System.out.println("15 - Consultar histórico de transações");
        System.out.println("0  - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarCliente(Scanner scanner) {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine().trim();
        String cpf = lerCPF(scanner);
        String email = lerEmail(scanner);
        LocalDate dataNascimento = lerData(scanner);

        int numeroConta;
        while (true) {
            System.out.print("Digite o numero da conta (5 até 9 dígitos): ");
            String contaInput = scanner.nextLine().trim();

            String numeroContaStr;
            if (contaInput.matches("\\d{5,9}-[0-9X]")) {
                numeroContaStr = contaInput.substring(0, contaInput.indexOf('-'));
            } else if (contaInput.matches("\\d{5,9}")) {
                numeroContaStr = contaInput;
            } else {
                System.err.println("Número da conta inválido. Use 5 a 9 dígitos (ex: 123456) ou 5 a 9 dígitos + hífen + DV (ex: 123456-7).");
                continue;
            }
            numeroConta = Integer.parseInt(numeroContaStr);
            break;
        }

        int agencia;
        while (true) {
            System.out.print("Digite a agencia (até 5 dígitos): ");
            String agenciaInput = scanner.nextLine().trim();
            if (!agenciaInput.matches("\\d{4,5}")) {
                System.err.println("Número da agência inválido. Deve conter 4 ou 5 dígitos.");
                continue;
            }
             agencia = Integer.parseInt(agenciaInput);
            break;
        }

        try {
            Cliente cliente = new Cliente(cpf, nome, email, dataNascimento);

            ClienteDao clienteDao = new ClienteDao();
            if (clienteDao.existeClientePorCpfOuEmail(cpf, email)) {
                System.err.println("Já existe cliente com esse CPF ou e-mail");
                return;
            }

            int idCliente = clienteDao.inserirCliente(cliente);
            cliente.setId(idCliente);

            ContaInternaDao contaInternaDao = new ContaInternaDao();
            int idConta = contaInternaDao.inserirContaInterna(idCliente, numeroConta, agencia);

            CarteiraDao daoCarteira = new CarteiraDao();
            daoCarteira.inserirCarteira(idConta);

            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar o usuário: " + e.getMessage());
        }
    }

    private static void exibirClientesCadastrados() {
        try {
            ClienteDao daoCliente = new ClienteDao();

            Map<Cliente, Integer> todosClientesCadastrados = daoCliente.listarContaInternaClientesCadastrados();
            for(Map.Entry<Cliente, Integer> c : todosClientesCadastrados.entrySet()) {
                System.out.print("ID do cliente: " + c.getKey().getId());
                System.out.print(" | ID da conta: " + c.getValue());
                System.out.print(" | Nome: " + c.getKey().getNome());
                System.out.print(" | Email: " + c.getKey().getEmail());
                System.out.print(" | CPF: " + c.getKey().getCpf());
                System.out.print(" | Data de Nascimento: " + c.getKey().getDataNascimento().format(FORMATTER));
                System.out.print(" | Idade: " + c.getKey().getIdade() + System.lineSeparator());
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void atualizarCliente (Scanner scanner) {
        System.out.print("Digite o ID do cliente que deseja atualizar: ");
        int idCliete = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Digite o NOVO nome do cliente: ");
        String novoNome = scanner.nextLine().trim();
        System.out.println("NOVO e-mail");
        String novoEmail = lerEmail(scanner);

        try {
            ClienteDao daoCliente = new ClienteDao();
            daoCliente.atualizarClientePorId(idCliete, novoNome, novoEmail);
            System.out.println("Cliente atualizado com sucesso!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deletarCliente (Scanner scanner) {
        System.out.print("Digite o ID do cliente que deseja DELETAR: ");
        int idCliente = Integer.parseInt(scanner.nextLine().trim());

        try {
            ClienteDao daoCliente = new ClienteDao();
            daoCliente.deletarClientePorId(idCliente);
            System.out.println("Cliente deletado!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void cadastrarCrypto(Scanner scanner) {
        System.out.print("Digite o nome do criptoativo: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Digite a sigla do criptoativo: ");
        String sigla = scanner.nextLine().trim();

        LocalDate dataLancamento = lerData(scanner);

        Criptomoeda criptomoeda = new Criptomoeda(nome, sigla, dataLancamento);

        try {
            CryptoDao cryptoDao = new CryptoDao();
            cryptoDao.inserirCrypto(criptomoeda);
            System.out.println("Criptoativo cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("[SQLException] ao cadastrar Criptoativo");
            System.err.println("Class   : " + e.getClass().getName());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Code    : " + e.getErrorCode());
            System.err.println("Message : " + e.getMessage());
            Throwable t = e.getCause();
            while (t != null) {
                System.err.println("Cause  : " + t.getClass().getName() + " - " + t.getMessage());
                t = t.getCause();
            }
        } catch (Exception e) {
            System.err.println("[Exception] ao cadastrar Criptoativo: " + e.getClass().getName() + " - " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Cause   : " + e.getCause().getClass().getName() + " - " + e.getCause().getMessage());
            }
        }
    }

    private static void consultarCliente(Scanner scanner) {
        System.out.print("Digite o ID do cliente que deseja consultar: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        try {
            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.consultarClientePorId(id);

            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            ContaInternaDao contaDaoCliente = new ContaInternaDao();
            ContaInterna conta = contaDaoCliente.buscarContaInternaPorCliente(cliente);

            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Idade: " + cliente.getIdade());

            if (conta != null) {
                System.out.println("ID da conta: " + conta.getId());
                System.out.println("Número da Conta: " + conta.getNumeroConta());
                System.out.println("Agência: " + conta.getNumeroAgencia());
                System.out.printf("Saldo: R$ %.2f%n", conta.getSaldo());
            } else {
                System.out.println("Este cliente ainda não possui conta cadastrada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar cliente: " + e.getMessage());
        }
    }

    private static void consultarCarteira(Scanner scanner) {
        System.out.print("Digite o ID da conta que deseja consultar a carteira: ");
        int contaId = Integer.parseInt(scanner.nextLine().trim());

        try {
            PosseDao posseDao = new PosseDao();
            List<PosseClienteCrypto> posses = posseDao.listarPossesPorCarteira(contaId);

            if (posses.isEmpty()) {
                System.out.println("Nenhuma posse encontrada para esta conta.");
                return;
            }

            System.out.println("\n--- Carteira ---");
            for (PosseClienteCrypto p : posses) {
                System.out.printf("- %s (%s) | ID Crypto: %d | Quantidade: %.8f%n",
                        p.getCrypto().getNome(),
                        p.getCrypto().getSigla(),
                        p.getCrypto().getId(),
                        p.getQuantidade());

            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar carteira: " + e.getMessage());
        }

    }

    private static void enviarTransferenciaContaInternaParaExterna(Scanner scanner) throws SQLException {
        System.out.println("\n=== Transferência de Conta Interna para Conta Externa ===");

        System.out.print("Número da conta interna de origem: ");
        String numeroContaInterna = scanner.nextLine().trim();

        System.out.print("Agência da conta interna: ");
        String agenciaInterna = scanner.nextLine().trim();

        ContaInterna contaOrigem = ContaInternaDao.buscarContaInternaPorNumeroEAgencia(numeroContaInterna, agenciaInterna);

        if (contaOrigem == null) {
            System.err.println("Conta interna não encontrada. Verifique número/agência.");
            return;
        }

        System.out.print("Número da conta externa de destino: ");
        String numeroContaExterna = scanner.nextLine().trim();

        System.out.print("Agência da conta externa: ");
        String agenciaExterna = scanner.nextLine().trim();

        ContaExterna contaDestino = ContaExternaDao.buscarContaExternaPorNumeroEAgencia(numeroContaExterna, agenciaExterna);

        if (contaDestino == null) {
            System.err.println("Conta externa não encontrada. Verifique número/agência.");
            return;
        }

        System.out.print("Valor da transferência: ");
        double valor;
        try {
            valor = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido! Digite apenas números com ponto decimal (ex: 100.50");
            e.printStackTrace();
            return;
        }

        TransacaoConta transacao;
        try {
            transacao = new TransacaoConta(
                    contaDestino,
                    contaOrigem,
                    valor,
                    TipoTransacaoFiat.SAQUE,
                    LocalDateTime.now()
            );

            TransacaoContaService service = new TransacaoContaService();
            service.registrarTransacao(transacao);
            System.out.println("Transferência realizada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao registrar transação: " + e.getMessage());
            e.printStackTrace();

        } catch(Exception e) {
            System.err.println("Erro ao criar objeto Transação: " + e.getMessage());
            e.printStackTrace();
        }


    }

    private static void enviarTransferenciaContaExternaParaInterna(Scanner scanner) {

    }


    private static void adicionarSaldo(Scanner scanner) {

    }

    private static void listarCriptoativos() {
        System.out.println("\n--- Cryptos cadastradas ---");
        try {
            CryptoDao cryptoDao = new CryptoDao();
            List<Criptomoeda> criptomoedas = cryptoDao.listarCryptos();

            if (criptomoedas.isEmpty()) {
                System.out.println("Nenhuma Crypto encontrada.");
            } else {
                for (Criptomoeda criptomoeda : criptomoedas) {
                    System.out.printf(
                            "- %-15s %-5s - ID: %3d - Lançamento: %s%n",
                            criptomoeda.getNome(),
                            criptomoeda.getSigla(),
                            criptomoeda.getId(),
                            criptomoeda.getDataLancamento()
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar cryptos: " + e.getMessage());
        }
    }

    private static void comprarCrypto(Scanner scanner) {
        System.out.print("Digite o id do cliente que deseja comprar: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite o id do criptoativo que deseja comprar: ");
        int idCrypto = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Digite a quantidade: ");
        double quantidade = Double.parseDouble(scanner.nextLine().trim());

        try {
            CarteiraDao carteiraDao = new CarteiraDao();
            CarteiraDao.CompraResult result = carteiraDao.comprarCrypto(idCliente, idCrypto, quantidade);

            TransacaoCryptoDao txDao = new TransacaoCryptoDao();
            txDao.inserirTransacaoCrypto(
                    result.contaId(),
                    idCrypto,
                    "COMPRA",
                    quantidade,
                    result.precoUnitario(),
                    StatusOperacao.CONCLUIDA
            );

            System.out.printf(
                    "Compra realizada!\nConta: %d | Crypto: %d | Qtde: %.8f | Preço: %.8f | Total: %.8f%n",
                    result.contaId(), idCrypto, quantidade, result.precoUnitario(), result.total()
            );
        } catch (SQLException e) {
            System.err.println("Erro na compra: " + e.getMessage());
        }
    }

    private static void venderCrypto(Scanner scanner) {
        System.out.print("Digite o id do cliente que deseja vender: ");
        int idCliente = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Digite o id do criptoativo: ");
        int idCrypto = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Digite a quantidade de criptoativos que deseja vender: ");
        double quantidade = Double.parseDouble(scanner.nextLine().trim());

        try {
            CarteiraDao carteiraDao = new CarteiraDao();
            CarteiraDao.VendaResult result = carteiraDao.venderCrypto(idCliente, idCrypto, quantidade);

            TransacaoCryptoDao txDao = new TransacaoCryptoDao();
            txDao.inserirTransacaoCrypto(result.contaId(), idCrypto, "VENDA", quantidade, result.precoUnitario(), StatusOperacao.CONCLUIDA);

            System.out.printf(
                    "Venda realizada!\nConta: %d | Crypto: %d | Qtde: %.8f | Preço: %.8f | Crédito: %.8f%n",
                    result.contaId(), idCrypto, quantidade, result.precoUnitario(), result.totalCreditado()
            );
        } catch (SQLException e) {
            System.err.println("Erro na venda: " + e.getMessage());
        }
    }

    private static void consultarHistoricoTransacoes(Scanner scanner) {
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
}

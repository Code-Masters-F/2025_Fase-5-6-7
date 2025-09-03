package br.com.fiap.view;


import br.com.fiap.dao.*;
import br.com.fiap.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

// ================ ATENÇÃO =============
// Essa main está só para teste de funcionamento da lógica de sistema. A main de verdade
// ficará em view e utilizará a logica do CRUD que ainda será implementado pelo Victor

public class MainView {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print("Digite a data (dd/MM/yyyy): ");
            String entrada = scanner.nextLine();
            try {
                return LocalDate.parse(entrada, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Utilize dd/MM/yyyy.");
            }
        }
    }

    // Para simular uma API que pega o valor unitário de uma criptomoeda em reais
    // (o sistema precisa dessa informação e ainda não estudamos meios para pegar dados reais)
    public static double getValorUnitarioCrypto(int idCrypto) {
        return idCrypto * 107.43;
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
                    case "2": cadastrarCrypto(scanner); break;
                    case "3": consultarCliente(scanner); break;
                    case "4": consultarCarteira(scanner); break;
                    case "5": enviarTransferenciaContaInterna(scanner); break;
                    case "6": adicionarSaldo(scanner); break;
                    case "7": listarCriptoativos(); break;
                    case "8": comprarCrypto(scanner); break;
                    case "9": venderCrypto(scanner); break;
//                    case "10": listarTransacoesContas(); break;
//                    case "11": listarTransacoesCryptos(); break;
                    case "0": System.out.println("Saindo do sistema..."); break;
                    default: System.out.println("Opção inválida!");
                }
            }catch (Exception e) {
                System.out.println("Algo deu errado! Tente novamente.");
            }

        }while (!opcao.equals("0"));
    }

    private static void exibirMenu() {
        System.out.println("\n======= MENU =======");
        System.out.println("1 - Cadastrar novo cliente");
        System.out.println("2 - Cadastrar novo criptoativo");
        System.out.println("3 - Consultar cliente");
        System.out.println("4 - Consultar carteira");
        System.out.println("5 - Fazer tansferencia para uma conta externa");
        System.out.println("6 - Fazer tansferencia para uma conta interna");
        System.out.println("7 - Adicionar saldo (somente via transferencia, de sua conta externa para esta corretora)");
        System.out.println("8 - Listar criptoativos");
        System.out.println("9 - Comprar criptoativo");
        System.out.println("10 - Vender criptoativo");
        System.out.println("11 - Consultar transações de contas no sistema");
        System.out.println("12 - Consultar transações de criptoativos no sistema");
        System.out.println("0 - Sair");
        System.out.print("Escolha a opção desejada: ");
    }

    private static void cadastrarCliente(Scanner scanner) {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine().trim();
        String cpf = lerCPF(scanner);
        String email = lerEmail(scanner);
        LocalDate dataNascimento = lerData(scanner);

        System.out.print("Digite o numero da conta: ");
        int numeroConta = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Digite a agencia: ");
        int agencia = Integer.parseInt(scanner.nextLine().trim());

        try {
            Cliente cliente = new Cliente(cpf, nome, email, dataNascimento);

            ClienteDao clienteDao = new ClienteDao();

            if (clienteDao.existeClientePorCpfOuEmail(cpf, email)) {
                System.err.println("Já existe cliente com esse CPF ou e-mail");
                return;
            }

            int idCliente = clienteDao.inserirCliente(cliente);
            cliente.setId(idCliente);

            ContaClienteDao contaClienteDao = new ContaClienteDao();
            contaClienteDao.inserirConta(idCliente, numeroConta, agencia);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar o usuário: " + e.getMessage());
        }
    }

    private static void cadastrarCrypto(Scanner scanner) {
        System.out.print("Digite o nome do criptoativo: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Digite a sigla do criptoativo: ");
        String sigla = scanner.nextLine().trim();

        LocalDate dataLancamento = lerData(scanner);

        Crypto crypto = new Crypto(nome, sigla, dataLancamento);

        try {
            CryptoDao cryptoDao = new CryptoDao();
            cryptoDao.inserirCrypto(crypto);
            System.out.println("Criptoativo cadastrado com sucesso!");
        } catch (RuntimeException | SQLException e) {
            System.err.println("Erro ao cadastrar Criptoativo: " + e.getMessage());
        }
    }

    private static void consultarCliente(Scanner scanner) throws SQLException {
        System.out.print("Digite o ID do cliente que deseja consultar: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        try {
            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.consultarClientePorId(id);

            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            ContaClienteDao contaDaoCliente = new ContaClienteDao();
            ContaCliente conta = contaDaoCliente.buscarContaPorClienteId(id);

            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Idade: " + cliente.getIdade());

            if (conta != null) {
                System.out.println("Número da Conta: " + conta.getNumeroConta());
                System.out.println("Agência: " + conta.getAgencia());
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
        int carteiraId = Integer.parseInt(scanner.nextLine().trim());

        try {
            PosseDao posseDao = new PosseDao();
            List<PosseClienteCrypto> posses = posseDao.listarPossesPorCarteira(carteiraId);

            if (posses.isEmpty()) {
                System.out.println("Nenhuma posse encontrada para esta carteira.");
                return;
            }

            System.out.println("\n--- Carteira ---");
            for (PosseClienteCrypto p : posses) {
                System.out.printf("\"- %s (%s) | ID Crypto: %d | Quantidade: %.8f%n\", " +
                        p.getCrypto().getNome(), p.getCrypto().getSigla(),
                        p.getCrypto().getId(), p.getQuantidade());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar carteira: " + e.getMessage());
        }

    }

    private static void enviarTransferenciaContaInterna(Scanner scanner) throws SQLException {
        System.out.print("Digite o número do id do cliente que realizará a transferência: ");
        int idClienteOrigem = Integer.parseInt(scanner.nextLine().trim());
        Cliente clienteOrigem = new ClienteDao().consultarClientePorId(idClienteOrigem);

        System.out.print("Digite o número do id do cliente que receberá a transferência: ");
        int idClienteDestino = Integer.parseInt(scanner.nextLine().trim());
        Cliente clienteDestino = new ClienteDao().consultarClientePorId(idClienteDestino);

        if(clienteOrigem == null || clienteDestino == null) {
            System.out.println("Cliente destino e/ou cliente origem não encontrado no sistema.");
            return;
        }

        System.out.print("Digite o valor da transferencia: ");
        double valorTransferencia = Double.parseDouble(scanner.nextLine().trim());

        if (valorTransferencia <= 0) {
            System.out.println("Valor inválido.");
            return;
        }

        int numeroContaOrigem = clienteOrigem.getContaCliente().getNumeroConta();
        int numeroAgenciaOrigem = clienteOrigem.getContaCliente().getAgencia();

        int numeroContaDestino = clienteDestino.getContaCliente().getNumeroConta();
        int numeroAgenciaDestino = clienteDestino.getContaCliente().getAgencia();

        TransacaoContaDao transacaoDao = new TransacaoContaDao();
        int idOrigem = transacaoDao.buscarConta(numeroContaOrigem, numeroAgenciaOrigem);
        int idDestino = transacaoDao.buscarConta(numeroContaDestino, numeroAgenciaDestino);

        if (idOrigem == -1 || idDestino == -1) {
            System.out.println("Conta origem e/ou conta destino não encontrada(s).");
            return;
        }

        ContaClienteDao contaDao = new ContaClienteDao();
        contaDao.transferirParaContaInterna(idOrigem, idDestino, valorTransferencia);
        System.out.println("Transferencia realizada com sucesso!");
    }

    // supondo que não temos caixa eletrônico só é possível adicionar saldo via transferencia externa
    private static void adicionarSaldo(Scanner scanner) {
        System.out.print("Digite o numero do id do cliente que deseja adicionar saldo: ");
        int idCliente = Integer.parseInt(scanner.nextLine().trim());

        try {
            Cliente cliente = new ClienteDao().consultarClientePorId(idCliente);

            if (cliente == null) {
                System.out.println("\nCliente não encontrado no sistema.");
                return;
            }
        } catch (SQLException e) {
            System.err.println("Não foi possível consultar o Cliente: " + e.getMessage());
        }

        System.out.print("Digite o número da conta que enviou a transferência: ");
        int numeroConta = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Digite a agência da conta que enviou a transferência: ");
        int agencia = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Digite o valor da transferência: ");
        double valorTransferencia = Double.parseDouble(scanner.nextLine().trim());

        try {
            ContaClienteDao contaDao = new ContaClienteDao();
            contaDao.atualizarSaldo(numeroConta, agencia, valorTransferencia);

            System.out.println("Saldo adicionado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar saldo: " + e.getMessage());
        }
    }

    private static void listarCriptoativos() throws SQLException {
        System.out.println("\n--- Cryptos cadastradas ---");
        try {
            CryptoDao cryptoDao = new CryptoDao();
            List<Crypto> cryptos = cryptoDao.listarCryptos();

            if (cryptos.isEmpty()) {
                System.out.println("Nenhuma Crypto encontrada.");
            } else {
                for (Crypto crypto : cryptos) {
                    System.out.println("- " + crypto.getNome() +
                            " (" + crypto.getSigla() + ") - ID: " + crypto.getId() +
                            " - Lançamento: " + crypto.getDataLancamento());
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

//    private static void listarTransacoesContas(){
//        for (Map.Entry<Integer, TransacaoConta> transacao : todasTransacoesConta.entrySet()) {
//            System.out.println("----- ID da transação: " + transacao.getKey());
//
//            System.out.println("Conta origem: " + transacao.getValue().getNumeroContaOrigem() +
//                    " | Agência origem: " + transacao.getValue().getAgenciaOrigem());
//
//            System.out.println("Conta destino: " + transacao.getValue().getNumeroContaDestino() +
//                    " | Agência destino: " + transacao.getValue().getAgenciaDestino());
//
//            System.out.println(System.lineSeparator());
//        }
//    }

//    private static void listarTransacoesCryptos() {
//        for (Map.Entry<Integer, TransacaoCrypto> transacao : todasTransacoesCrypto.entrySet()) {
//            System.out.println("----- ID da transação: " + transacao.getKey());
//
//            System.out.println("Numero da conta: " + transacao.getValue().getContaCliente().getNumeroConta() +
//                    " | Agência: " + transacao.getValue().getContaCliente().getAgencia());
//
//            System.out.print("Tipo de operação: " + transacao.getValue().getTipoOperacao() +
//                    " | Quantidade: " + transacao.getValue().getQuantidadeCrypto());
//            System.out.printf(" | Valor total: R$ %.2f", transacao.getValue().getValorTotal());
//
//            System.out.println(System.lineSeparator());
//        }
//    }

}

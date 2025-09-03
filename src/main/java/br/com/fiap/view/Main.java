package br.com.fiap.view;


import br.com.fiap.dao.ClienteDao;
import br.com.fiap.dao.ContaClienteDao;
import br.com.fiap.dao.TransacaoContaDao;
import br.com.fiap.model.Cliente;
import br.com.fiap.model.Crypto;
import br.com.fiap.model.TransacaoConta;
import br.com.fiap.model.TransacaoCrypto;

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


public class Main {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private static int proximoClienteId = 1;
    private static int proximoCryptoId = 3;

    private static String lerCPF(Scanner scanner) {
        String cpf;
        while (true) {
            System.out.print("Digite o CPF (11 dígitos): ");
            cpf = scanner.nextLine().replaceAll("\\D", "");
            if (cpf.matches("\\d{11}")) {
                return cpf;
            }
            System.out.println("CPF inválido. Deve conter 11 dígitos numéricos.");
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

    private static String lerData(Scanner scanner) {
        String data;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print("Digite a data (dd/MM/yyyy): ");
            data = scanner.nextLine();
            try {
                LocalDate.parse(data, formatter);
                return data;
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

    // simular banco de dados
    public static List<Crypto> todasCryptosCadastradas = new ArrayList<>();
    public static List<Cliente> todosClientesCadastrados = new ArrayList<>();
    public static Map<Integer, TransacaoConta> todasTransacoesConta = new HashMap<>();
    public static Map<Integer, TransacaoCrypto> todasTransacoesCrypto = new HashMap<>();

    public static void main(String[] args) {

        inicializarDados();

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
                    case "5": enviarTransferenciaContaExterna(scanner); break;
                    case "6": enviarTransferenciaContaInterna(scanner); break;
                    case "7": adicionarSaldo(scanner); break;
                    case "8": listarCriptoativos(); break;
                    case "9": comprarCrypto(scanner); break;
                    case "10": venderCrypto(scanner); break;
                    case "11": listarTransacoesContas(); break;
                    case "12": listarTransacoesCryptos(); break;
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

    private static void inicializarDados() {
        Crypto bitcoin = new Crypto("Bitcoin", "BTC", 1, "01/01/2009");
        Crypto ethereum = new Crypto("Ethereum", "ETH", 2, "30/07/2014");
        todasCryptosCadastradas.add(bitcoin);
        todasCryptosCadastradas.add(ethereum);
        guardarEmTxt("cryptos_cadastradas.txt", bitcoin.toString());
        guardarEmTxt("cryptos_cadastradas.txt", ethereum.toString());
    }

    private static void cadastrarCliente(Scanner scanner) {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        String cpf = lerCPF(scanner);
        String email = lerEmail(scanner);
        String dataNascimento = lerData(scanner);

        Cliente cliente = new Cliente(cpf, nome, email, dataNascimento, proximoClienteId);

        System.out.print("Digite o numero da conta: ");
        int numeroConta = scanner.nextInt();
        System.out.print("Digite a agencia: ");
        int agencia = scanner.nextInt();

        cliente.criarConta(numeroConta, agencia, proximoClienteId++);

        todosClientesCadastrados.add(cliente);

        guardarEmTxt("clientes_cadastrados.txt", cliente.getContaCliente().toString());

        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void cadastrarCrypto(Scanner scanner) {
        System.out.print("Digite o nome do criptoativo: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a sigla do criptoativo: ");
        String sigla = scanner.nextLine();

        String dataLancamento = lerData(scanner);

        Crypto crypto = new Crypto(nome, sigla, proximoCryptoId++, dataLancamento);
        todasCryptosCadastradas.add(crypto);

        guardarEmTxt("cryptos_cadastradas.txt", crypto.toString());

        System.out.println("Criptoativo cadastrado com sucesso!");
    }

    /**
     * Percorre a lista de Cryptos da Main procurando pelo id
     * @param id o id da Crypto que se quer procurar
     * @return retorna a Crypto caso o encontre e null caso contrário
     */
    private static Crypto buscarCrypto(int id) {
        for(Crypto crypto : todasCryptosCadastradas) {
            if (crypto.getId() == id) {
                return crypto;
            }
        }
        return null;
    }

    /**
     * Percorre a lista de clientes da Main procurando pelo id
     * @param id o id do cliente que se quer procurar
     * @return retorna o cliente caso o encontre e null caso contrário
     */
//    private static Cliente buscarCliente(int id) {
//        for(Cliente cliente : todosClientesCadastrados) {
//            if (cliente.getId() == id) {
//                return cliente;
//            }
//        }
//        return null;
//    }

    private static void consultarCliente(Scanner scanner) {
        for(Cliente cliente : todosClientesCadastrados) {
            System.out.println("ID: " + cliente.getId() + " | Nome: " +
                    cliente.getNome());
        }

        System.out.print("Digite o ID do cliente que deseja consultar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Cliente cliente = buscarCliente(id);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Idade: " + cliente.getIdade());
        System.out.println("Número da Conta: " + cliente.getContaCliente().getNumeroConta());
        System.out.println("Agência: " + cliente.getContaCliente().getAgencia());
        System.out.printf("Saldo: R$ %.2f\n", cliente.getContaCliente().getSaldo());
    }

    private static void consultarCarteira(Scanner scanner) {
        System.out.print("Digite o ID da conta que deseja consultar a carteira: ");
        int idConta = Integer.parseInt(scanner.nextLine());
        Cliente cliente = buscarCliente(idConta);

        if(cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        cliente.getContaCliente().getCarteira().verCarteira();
    }

    private static void enviarTransferenciaContaExterna(Scanner scanner) throws SQLException {
        System.out.print("Digite o numero do id do cliente que realizará a transferencia: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        Cliente cliente = new ClienteDao().consultarClientePorId(idCliente);

        if (cliente == null) {
            System.out.println("\nCliente não encontrado no sistema.");
            return;
        }

        System.out.print("Digite o numero da conta que deseja enviar a transferencia: ");
        int numeroContaDestino = scanner.nextInt();

        System.out.print("Digite a agencia que deseja enviar a transferencia: ");
        int numeroAgenciaDestino = scanner.nextInt();

        System.out.print("Digite o valor da transferencia: ");
        double valorTransferencia = Double.parseDouble(scanner.nextLine());

        int numeroContaOrigem = cliente.getContaCliente().getNumeroConta();
        int numeroAgenciaOrigem = cliente.getContaCliente().getAgencia();

        TransacaoContaDao transacaoDao = new TransacaoContaDao();

        int idOrigem = transacaoDao.buscarConta(numeroContaOrigem, numeroAgenciaOrigem);
        if (idOrigem == -1) {
            System.out.println("Conta de ORIGEM do cliente não encontrada (número/agência).");
            return;
        }

        int idDestino = transacaoDao.buscarConta(numeroContaDestino, numeroAgenciaDestino);
        if (idDestino == -1) {
            System.out.println("Conta DESTINO não encontrada (número/agência).");
            return;
        }

        if (idOrigem == idDestino) {
            System.out.println("Operação inválida: origem e destino são a mesma conta.");
            return;
        }

        if (valorTransferencia <= 0) {
            System.out.println("Valor inválido.");
            return;
        }

        ContaClienteDao contaDao = new ContaClienteDao();
        contaDao.transferirParaContaExterna(idOrigem, idDestino, valorTransferencia);
        System.out.println("Transferência realizada com sucesso.");
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

        // registra as transações e manipula ambos os saldos
        clienteOrigem.getContaCliente().transferirMesmoSistema(clienteDestino.getContaCliente(), valorTransferencia);

        guardarEmTxtTransacaoContas();

        System.out.println("Transferencia realizada com sucesso!");
    }

    // supondo que não temos caixa eletrônico só é possível adicionar saldo via transferencia externa
    private static void adicionarSaldo(Scanner scanner) {
        System.out.print("Digite o numero do id do cliente que deseja adicionar saldo: ");
        int idCliente = Integer.parseInt(scanner.nextLine());
        Cliente cliente = buscarCliente(idCliente);

        if(cliente == null) {
            System.out.println("\nCliente não encontrado no sistema.");
            return;
        }

        System.out.print("Digite o numero da conta que enviou a transferencia: ");
        int numeroConta = scanner.nextInt();

        System.out.print("Digite a agencia da conta que enviou a transferencia: ");
        int agencia = scanner.nextInt();

        System.out.print("Digite o valor da transferencia: ");
        double valorTransferencia = Double.parseDouble(scanner.nextLine());

        cliente.getContaCliente().receberTransacaoConta(valorTransferencia, numeroConta, agencia);

        System.out.println("Saldo adicionado com sucesso!");
    }

    private static void listarCriptoativos() {
        System.out.println("\n--- Cryptos cadastradas ---");
        for(Crypto crypto : todasCryptosCadastradas) {
            System.out.println("- " + crypto.getNome() + " - ID: " + crypto.getId());
        }
    }

    private static void comprarCrypto(Scanner scanner) {
        System.out.print("Digite o id do cliente que deseja comprar: ");
        int idCliente = Integer.parseInt(scanner.nextLine());
        Cliente cliente = buscarCliente(idCliente);

        listarCriptoativos();
        System.out.print("Digite o id do criptoativo que deseja comprar: ");
        int idCrypto = Integer.parseInt(scanner.nextLine());
        Crypto crypto = buscarCrypto(idCrypto);

        if (crypto == null || cliente == null) {
            System.out.println("Criptoativo e/ou cliente não encontrado(s).");
            return;
        }

        System.out.print("Digite a quantidade de criptoativos que deseja comprar: ");
        double quantidade = Double.parseDouble(scanner.nextLine());

        cliente.getContaCliente().comprarCrypto(quantidade, idCrypto);

        guardarEmTxtTransacaoCryptos();
    }

    private static void venderCrypto(Scanner scanner) {
        System.out.print("Digite o id do cliente que deseja vender: ");
        int idCliente = Integer.parseInt(scanner.nextLine());
        Cliente cliente = buscarCliente(idCliente);

        listarCriptoativos();
        System.out.print("Digite o id do criptoativo: ");
        int idCrypto = Integer.parseInt(scanner.nextLine());
        Crypto crypto = buscarCrypto(idCrypto);

        if (crypto == null || cliente == null) {
            System.out.println("Criptoativo e/ou cliente não encontrado(s).");
            return;
        }

        System.out.print("Digite a quantidade de criptoativos que deseja vender: ");
        double quantidade = Double.parseDouble(scanner.nextLine());

        cliente.getContaCliente().venderCrypto(quantidade, idCrypto);

        guardarEmTxtTransacaoCryptos();
    }

    private static void listarTransacoesContas(){
        for (Map.Entry<Integer, TransacaoConta> transacao : todasTransacoesConta.entrySet()) {
            System.out.println("----- ID da transação: " + transacao.getKey());

            System.out.println("Conta origem: " + transacao.getValue().getNumeroContaOrigem() +
                    " | Agência origem: " + transacao.getValue().getAgenciaOrigem());

            System.out.println("Conta destino: " + transacao.getValue().getNumeroContaDestino() +
                    " | Agência destino: " + transacao.getValue().getAgenciaDestino());

            System.out.println(System.lineSeparator());
        }
    }

    private static void listarTransacoesCryptos() {
        for (Map.Entry<Integer, TransacaoCrypto> transacao : todasTransacoesCrypto.entrySet()) {
            System.out.println("----- ID da transação: " + transacao.getKey());

            System.out.println("Numero da conta: " + transacao.getValue().getContaCliente().getNumeroConta() +
                    " | Agência: " + transacao.getValue().getContaCliente().getAgencia());

            System.out.print("Tipo de operação: " + transacao.getValue().getTipoOperacao() +
                    " | Quantidade: " + transacao.getValue().getQuantidadeCrypto());
            System.out.printf(" | Valor total: R$ %.2f", transacao.getValue().getValorTotal());

            System.out.println(System.lineSeparator());
        }
    }

    // Deve ser chamado pelas funções que instanciam os objetos, como por exemplo cadastrarCrypto().
    // Deve ser passado o nome do arquivo .txt, mesmo que ainda não exista, e o toString() do objeto
    public static void guardarEmTxt(String nomeArquivo, String conteudo) {
        try (BufferedWriter bufferedW = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            bufferedW.write(conteudo);
            bufferedW.newLine();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void guardarEmTxtTransacaoContas() {
//        TransacaoConta ultimaTransacao = null;
//        for (Map.Entry<Integer, TransacaoConta> entry : todasTransacoesConta.entrySet()) {
//            ultimaTransacao = entry.getValue();
//        }
//        if (ultimaTransacao != null)
//            guardarEmTxt("transacoes_entre_contas.txt", ultimaTransacao.toString());
//    }

    public static void guardarEmTxtTransacaoCryptos() {
        TransacaoCrypto ultimaTransacao = null;
        for (Map.Entry<Integer, TransacaoCrypto> entry : todasTransacoesCrypto.entrySet()) {
            ultimaTransacao = entry.getValue();
        }
        if (ultimaTransacao != null)
            guardarEmTxt("transacoes_cryptos.txt", ultimaTransacao.toString());
    }
}

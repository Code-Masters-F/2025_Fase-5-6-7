package br.com.fiap.view;

import br.com.fiap.dao.*;
import br.com.fiap.model.*;
import br.com.fiap.service.TransacaoContaService;
import br.com.fiap.service.ClienteService;
import br.com.fiap.service.ContaCarteiraService;


import java.math.BigDecimal;
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
                    case "1": opcoesCliente(scanner); break;
                    case "2": submenuContasCarteiras(scanner); break;
                    case "3": cadastrarCriptomoeda(scanner); break;
                    case "4": enviarTransferenciaContaInternaParaExterna(scanner); break;
                    case "5": enviarTransferenciaContaExternaParaInterna(scanner); break;
                    case "6": listarCriptomoedas(); break;
                    case "7": comprarCriptomoeda(scanner); break;
                    case "8": venderCriptomoeda(scanner); break;
                    case "0":
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Algo deu errado! Tente novamente.");
            }

        } while (!opcao.equals("0"));
    }


    private static void exibirMenu() {
        System.out.println("\n======= MENU PRINCIPAL =======");
        System.out.println("1 - Opções relacionadas a Cliente");
        System.out.println("2 - Opções relacionadas a Contas e Carteiras");
        System.out.println("3 - Cadastrar nova criptomoeda");
        System.out.println("4 - Enviar Transferência de Conta Interna para Conta Externa");
        System.out.println("5 - Enviar Transferência de Conta Externa para Conta Interna");
        System.out.println("6 - Listar criptomoedas");
        System.out.println("7 - Comprar criptomoeda");
        System.out.println("8 - Vender criptomoeda");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void opcoesCliente(Scanner scanner) {

        String opcaoCliente = "";

        do {
            try {
                System.out.println(System.lineSeparator() + "=== Opções de cliente ===");
                System.out.println("1 - Cadastrar novo cliente");
                System.out.println("2 - Exibir clientes cadastrados");
                System.out.println("3 - Consultar dados de um cliente");
                System.out.println("4 - Atualizar cliente");
                System.out.println("5 - Deletar cliente");
                System.out.println("0 - Voltar para o menu principal");
                System.out.print("Escolha uma opção: ");
                opcaoCliente = scanner.nextLine();

                switch (opcaoCliente){
                    case "1": ClienteService.cadastrarCliente(scanner); break;
                    case "2": ClienteService.exibirClientesCadastrados(); break;
                    case "3": ClienteService.consultarCliente(scanner); break;
                    case "4": ClienteService.atualizarCliente(scanner); break;
                    case "5": ClienteService.deletarCliente(scanner); break;
                    case "0":
                        System.out.println("Voltando para o menu principal");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }

            } catch (Exception e) {
                System.out.println("Opção inválida");
            }
        }while (!opcaoCliente.equals("0"));
    }

    /*
      Sub-menu para gerenciar contas e carteiras
    */

    private static void submenuContasCarteiras(Scanner scanner) {
        String opcao;
        do {
            exibirSubmenuContasCarteiras();
            opcao = scanner.nextLine();
            System.out.println();
            try {
                switch (opcao) {
                    case "1": ContaCarteiraService.consultarContaInterna(scanner); break;
                    case "2": ContaCarteiraService.consultarCarteira(scanner); break;
                    case "3": ContaCarteiraService.transferirEntreContasInternas(scanner); break;
                    case "4": ContaCarteiraService.adicionarSaldoConta(scanner); break;
                    case "5": ContaCarteiraService.consultarHistoricoTransacoes(scanner); break;
                    case "6": ContaCarteiraService.cadastrarContaExterna(scanner); break;
                    case "0":
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Algo deu errado! Tente novamente.");
                e.printStackTrace();
            }
        } while (!opcao.equals("0"));
    }

    private static void exibirSubmenuContasCarteiras() {
        System.out.println("\n====== GERENCIAR CONTAS E CARTEIRAS ======");
        System.out.println("1 - Consultar conta interna");
        System.out.println("2 - Consultar carteira de criptomoedas");
        System.out.println("3 - Transferir entre contas internas");
        System.out.println("4 - Adicionar saldo à conta");
        System.out.println("5 - Consultar histórico de transações");
        System.out.println("6 - Cadastrar conta externa");
        System.out.println("0 - Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarCriptomoeda(Scanner scanner) {
        System.out.print("Digite o nome do criptoativo: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Digite a sigla do criptoativo: ");
        String sigla = scanner.nextLine().trim();

        LocalDate dataLancamento = lerData(scanner);

        Criptomoeda criptomoeda = new Criptomoeda(nome, sigla, dataLancamento);

        try {
            CryptoDao cryptoDao = new CryptoDao();
            cryptoDao.inserirCriptomoeda(criptomoeda);
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
        BigDecimal valor;
        try {
            valor = new BigDecimal(scanner.nextLine().trim());
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

    private static void listarCriptomoedas() {
        System.out.println("\n--- Criptomoedas cadastradas ---");
        try {
            CryptoDao cryptoDao = new CryptoDao();
            List<Criptomoeda> criptomoedas = cryptoDao.listarCriptomoedas();

            if (criptomoedas.isEmpty()) {
                System.out.println("Nenhuma Criptomoeda encontrada.");
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
            System.out.println("Erro ao consultar Criptomoedas: " + e.getMessage());
        }
    }

    private static void comprarCriptomoeda(Scanner scanner) {
        System.out.print("Digite o id do cliente que deseja comprar: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite o id da criptomoeda que deseja comprar: ");
        int idCrypto = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Digite a quantidade: ");
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
            e.printStackTrace();
        }
    }

    private static void venderCriptomoeda(Scanner scanner) {
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
                    "Venda realizada!\nConta: %d | Criptomoeda: %d | Qtde: %.8f | Preço: %.8f | Crédito: %.8f%n",
                    result.contaId(), idCrypto, quantidade, result.precoUnitario(), result.totalCreditado()
            );
        } catch (SQLException e) {
            System.err.println("Erro na venda: " + e.getMessage());
        }
    }

}

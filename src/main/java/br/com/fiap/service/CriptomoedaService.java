package br.com.fiap.service;

import br.com.fiap.dao.CarteiraDao;
import br.com.fiap.dao.CryptoDao;
import br.com.fiap.dao.TransacaoCryptoDao;
import br.com.fiap.model.Criptomoeda;
import br.com.fiap.model.StatusOperacao;
import br.com.fiap.utils.CondicoesUtils;
import br.com.fiap.view.MenuPrincipal;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CriptomoedaService {

    public static void cadastrarCriptomoeda(Scanner scanner) {
        System.out.print("Digite o nome do criptoativo: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Digite a sigla do criptoativo: ");
        String sigla = scanner.nextLine().trim();

        LocalDate dataLancamento = CondicoesUtils.lerData(scanner);

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

    public static void listarCriptomoedas() {
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

    public static void comprarCriptomoeda(Scanner scanner) {
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

    public static void venderCriptomoeda(Scanner scanner) {
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

    public static void alterarCriptomoeda(Scanner scanner) {
        System.out.print("Digite o ID da criptomoeda que deseja alterar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        try {
            CryptoDao cryptoDao = new CryptoDao();
            Criptomoeda criptoAtual = cryptoDao.consultarCriptomoeda(id);

            if (criptoAtual == null) {
                System.err.println("Criptomoeda não encontrada com o ID: " + id);
                return;
            }

            System.out.println("\n--- Dados atuais ---");
            System.out.println("Nome: " + criptoAtual.getNome());
            System.out.println("Sigla: " + criptoAtual.getSigla());
            System.out.println("Data de Lançamento: " + criptoAtual.getDataLancamento());

            System.out.print("\nDigite o NOVO nome (ou Enter para manter): ");
            String novoNome = scanner.nextLine().trim();
            if (novoNome.isEmpty()) {
                novoNome = criptoAtual.getNome();
            }

            System.out.print("Digite a NOVA sigla (ou Enter para manter): ");
            String novaSigla = scanner.nextLine().trim();
            if (novaSigla.isEmpty()) {
                novaSigla = criptoAtual.getSigla();
            }

            cryptoDao.atualizarCriptomoeda(id, novoNome, novaSigla);
            System.out.println("Criptomoeda atualizada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar criptomoeda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void excluirCriptomoeda(Scanner scanner) {
        System.out.print("Digite o ID da criptomoeda que deseja EXCLUIR: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        try {
            CryptoDao cryptoDao = new CryptoDao();
            Criptomoeda criptomoeda = cryptoDao.consultarCriptomoeda(id);

            if (criptomoeda == null) {
                System.err.println("Criptomoeda não encontrada com o ID: " + id);
                return;
            }

            System.out.println("\n--- Criptomoeda a ser excluída ---");
            System.out.println("Nome: " + criptomoeda.getNome());
            System.out.println("Sigla: " + criptomoeda.getSigla());

            System.out.print("\nTem certeza que deseja excluir? (S/N): ");
            String confirmacao = scanner.nextLine().trim().toUpperCase();

            if (confirmacao.equals("S")) {
                cryptoDao.deletarCriptomoeda(id);
                System.out.println("Criptomoeda excluída com sucesso!");
            } else {
                System.out.println("Exclusão cancelada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir criptomoeda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void exibirCriptomoedaPorId(Scanner scanner) {
        System.out.print("Digite o ID da criptomoeda que deseja consultar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("ID inválido!");
            return;
        }

        try {
            CryptoDao cryptoDao = new CryptoDao();
            Criptomoeda criptomoeda = cryptoDao.consultarCriptomoeda(id);

            if (criptomoeda == null) {
                System.err.println("Criptomoeda não encontrada com o ID: " + id);
                return;
            }

            System.out.println("\n--- Detalhes da Criptomoeda ---");
            System.out.println("ID: " + criptomoeda.getId());
            System.out.println("Nome: " + criptomoeda.getNome());
            System.out.println("Sigla: " + criptomoeda.getSigla());
            System.out.println("Data de Lançamento: " + criptomoeda.getDataLancamento());

        } catch (SQLException e) {
            System.err.println("Erro ao consultar criptomoeda: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

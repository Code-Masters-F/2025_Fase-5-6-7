package br.com.fiap.service;

import br.com.fiap.model.ContaInterna;
import br.com.fiap.utils.CondicoesUtils;

import br.com.fiap.dao.CarteiraDao;
import br.com.fiap.dao.ClienteDao;
import br.com.fiap.dao.ContaInternaDao;
import br.com.fiap.model.Cliente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

public class ClienteService {

    public static void cadastrarCliente(Scanner scanner) {

        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine().trim();
        String cpf = CondicoesUtils.lerCPF(scanner);
        String email = CondicoesUtils.lerEmail(scanner);
        LocalDate dataNascimento = CondicoesUtils.lerData(scanner);

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

            System.out.println(System.lineSeparator() + "Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar o usuário: " + e.getMessage());
        }
    }

    public static void exibirClientesCadastrados() {
        try {
            ClienteDao daoCliente = new ClienteDao();

            Map<Cliente, Integer> todosClientesCadastrados = daoCliente.listarContaInternaClientesCadastrados();
            for(Map.Entry<Cliente, Integer> c : todosClientesCadastrados.entrySet()) {
                System.out.print("ID do cliente: " + c.getKey().getId());
                System.out.print(" | ID da conta: " + c.getValue());
                System.out.print(" | Nome: " + c.getKey().getNome());
                System.out.print(" | Email: " + c.getKey().getEmail());
                System.out.print(" | CPF: " + c.getKey().getCpf());
                System.out.print(" | Data de Nascimento: " + c.getKey().getDataNascimento().format(CondicoesUtils.FORMATTER));
                System.out.print(" | Idade: " + c.getKey().getIdade() + System.lineSeparator());
            }
            System.out.println();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void consultarCliente(Scanner scanner) {
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

            System.out.println(System.lineSeparator() + "Cliente: " + cliente.getNome());
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

    public static void atualizarCliente (Scanner scanner) {
        System.out.print("Digite o ID do cliente que deseja atualizar: ");
        int idCliete = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Digite o NOVO nome do cliente: ");
        String novoNome = scanner.nextLine().trim();
        System.out.println("NOVO e-mail");
        String novoEmail = CondicoesUtils.lerEmail(scanner);

        try {
            ClienteDao daoCliente = new ClienteDao();
            daoCliente.atualizarClientePorId(idCliete, novoNome, novoEmail);
            System.out.println("Cliente atualizado com sucesso!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deletarCliente (Scanner scanner) {
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
}

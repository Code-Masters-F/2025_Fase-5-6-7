package br.com.fiap.view;

import br.com.fiap.service.ClienteService;

import java.util.Scanner;

public class MenuCliente {
    private static final String OPCOES_CLIENTE = """
    ============== OPÇÕES DE CLIENTE ==============

      1 - Cadastrar novo cliente
      2 - Exibir clientes cadastrados
      3 - Consultar dados de um cliente
      4 - Atualizar cliente
      5 - Deletar cliente
      0 - Voltar para o menu principal

    Escolha uma opção: """;


    public static void exibirMenuCliente(Scanner scanner) {
        String opcaoCliente = "";
        do {
            try {
                System.out.print(OPCOES_CLIENTE);
                opcaoCliente = scanner.nextLine().trim();

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
        } while (!opcaoCliente.equals("0"));
    }
}

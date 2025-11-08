package br.com.fiap.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MenuPrincipal {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final String MENU_PRINCIPAL = """
    ================= MENU PRINCIPAL =================
    
      1 - Opções relacionadas a Cliente
      2 - Opções relacionadas a Contas e Carteiras
      3 - Cadastrar nova criptomoeda
      4 - Enviar transferência de Conta Interna para Conta Externa
      5 - Enviar transferência de Conta Externa para Conta Interna
      6 - Listar criptomoedas
      7 - Comprar criptomoeda
      8 - Vender criptomoeda
      0 - Sair

    Escolha uma opção: """;


    public static String lerCPF(Scanner scanner) {
        while (true) {
            System.out.print("Digite o CPF (11 dígitos): ");
            String cpf = scanner.nextLine().replaceAll("\\D", "");
            if (cpf.length() == 11) return cpf;
            System.out.println("CPF inválido. Deve conter 11 dígitos numéricos, tente novamente.");
        }
    }

    public static String lerEmail(Scanner scanner) {
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

    public static LocalDate lerData(Scanner scanner) {
        while (true) {
            System.out.print("Digite a data (dd/MM/yyyy): ");
            String entrada = scanner.nextLine();
            try {
                return LocalDate.parse(entrada, MenuPrincipal.FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Utilize dd/MM/yyyy.");
            }
        }
    }

    public static void exibirMenuPrincipal() {

    }




}

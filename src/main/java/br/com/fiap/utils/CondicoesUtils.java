package br.com.fiap.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CondicoesUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
                return LocalDate.parse(entrada, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Utilize dd/MM/yyyy.");
            }
        }
    }
}

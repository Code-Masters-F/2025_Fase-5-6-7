package br.com.fiap.view;

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
    
      1 - Opções relacionadas a Clientes
      2 - Opções relacionadas a Carteiras
      3 - Opções relacionadas a Contas
      4 - Opções relacionadas a Criptomoedas
      0 - Sair

    Escolha uma opção: """;

}

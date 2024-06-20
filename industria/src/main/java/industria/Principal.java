package industria;

import industria.pessoas.Funcionario;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Principal {

    public static void main(String[] args) {
        
        List<Funcionario> funcionarios = new ArrayList<>();

        // Formatters para data e número
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        // Inserir funcionários na lista
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("18/10/2000", inputFormatter), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.parse("12/05/1990", inputFormatter), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", inputFormatter), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("14/10/1988", inputFormatter), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", inputFormatter), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("19/11/1999", inputFormatter), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("31/03/1993", inputFormatter), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.parse("08/07/1994", inputFormatter), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", inputFormatter), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.parse("02/09/1996", inputFormatter), new BigDecimal("2799.93"), "Gerente"));

        // Remover o funcionário "João"
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // Aumentar salário em 10%
        funcionarios.forEach(funcionario -> {
            BigDecimal salarioAtual = funcionario.getSalario();
            BigDecimal aumento = salarioAtual.multiply(BigDecimal.valueOf(0.10));
            BigDecimal novoSalario = salarioAtual.add(aumento);
            funcionario.setSalario(novoSalario);
        });

        // Agrupar funcionários por função em um Map
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        funcionarios.forEach(funcionario -> funcionariosPorFuncao
                .computeIfAbsent(funcionario.getFuncao(), key -> new ArrayList<>())
                .add(funcionario));

        // Imprimir funcionários agrupados por função
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("________________________________________");
            System.out.println("Função: " + entry.getKey());
            System.out.println("----------------------------------------");
            entry.getValue().forEach(funcionario -> {
                String dataFormatada = funcionario.getDataNascimento().format(outputDateFormatter);
                String salarioFormatado = currencyFormat.format(funcionario.getSalario());
                System.out.println(funcionario.getNome() + " - " + dataFormatada + " - " + salarioFormatado + " - " + funcionario.getFuncao());
            });
            System.out.println();
        }

        // Imprimir funcionários que fazem aniversário no mês 10 e mês 12
        System.out.println("________________________________________");
        System.out.println("Funcionários com Aniversário em Outubro e Dezembro");
        System.out.println("----------------------------------------");
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 || funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(funcionario -> {
                    String dataFormatada = funcionario.getDataNascimento().format(outputDateFormatter);
                    String salarioFormatado = currencyFormat.format(funcionario.getSalario());
                    System.out.println(funcionario.getNome() + " - " + dataFormatada + " - " + salarioFormatado + " - " + funcionario.getFuncao());
                });

        // Imprimir o funcionário com a maior idade
        LocalDate dataAtual = LocalDate.now();
        Funcionario funcionarioMaisVelho = null;
        int idadeMaisVelho = Integer.MIN_VALUE;

        for (Funcionario funcionario : funcionarios) {
            LocalDate dataNascimento = funcionario.getDataNascimento();
            int idade = dataAtual.getYear() - dataNascimento.getYear();

            if (dataNascimento.getDayOfYear() > dataAtual.getDayOfYear()) {
                idade--;
            }

            if (idade > idadeMaisVelho) {
                idadeMaisVelho = idade;
                funcionarioMaisVelho = funcionario;
            }
        }

        if (funcionarioMaisVelho != null) {
            System.out.println("________________________________________");
            System.out.println("Funcionário mais velho:");
            System.out.println("Nome: " + funcionarioMaisVelho.getNome());
            System.out.println("Idade: " + idadeMaisVelho);
            System.out.println();
        } else {
            System.out.println("Não há funcionários na lista.");
        }

        // Imprimir a lista de funcionários por ordem alfabética
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        Collections.sort(funcionariosOrdenados, Comparator.comparing(Funcionario::getNome));

        System.out.println("________________________________________");
        System.out.println("Lista de funcionários por ordem alfabética:");
        System.out.println("----------------------------------------");
        funcionariosOrdenados.forEach(funcionario -> {
            String dataFormatada = funcionario.getDataNascimento().format(outputDateFormatter);
            String salarioFormatado = currencyFormat.format(funcionario.getSalario());
            System.out.println(funcionario.getNome() + " - " + dataFormatada + " - " + salarioFormatado + " - " + funcionario.getFuncao());
        });
        System.out.println();

        // Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        System.out.println("________________________________________");
        System.out.println("Total dos salários dos funcionários: " + currencyFormat.format(totalSalarios));
        System.out.println();

        // Imprimir quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("________________________________________");
        System.out.println("Quantidade de salários mínimos que cada funcionário ganha:");
        System.out.println("----------------------------------------");
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_DOWN);
            System.out.println(funcionario.getNome() + " - " + salariosMinimos + " salários mínimos");
        });
    }
}

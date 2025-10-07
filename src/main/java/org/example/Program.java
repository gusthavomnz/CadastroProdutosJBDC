package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcao;
        do {
            exibirMenu();
            opcao = Integer.parseInt(sc.nextLine());
            switch (opcao) {
                case 0 -> salvarProduto();
                case 1 -> buscarTodosProdutos();
                case 2 -> buscarProdutoPorId();
                case 3 -> atualizarProduto();
                case 4 -> excluirProduto();
                case 5 -> System.exit(0);
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

    }

    private static void exibirMenu() {
        System.out.println("\n### Menu de Operações ###");
        System.out.println("0. Salvar novo produto");
        System.out.println("1. Buscar todos produtos");
        System.out.println("2. Buscar produto por ID");
        System.out.println("3. Atualizar produto");
        System.out.println("4. Excluir produto");
        System.out.println("5. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }

    private static void salvarProduto() {

        System.out.println("\n### Criar Novo Produto ###");

        System.out.println("Nome do produto: ");
        String nome = sc.nextLine();
        System.out.println("Quantidade: ");
        int qtd = sc.nextInt();
        System.out.println("Valor: ");
        Double valor = sc.nextDouble();

        Produto p = new Produto(nome, qtd, valor);
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            produtoDAO.salvar(p);
            System.out.println("Produto Cadastrado!");
        } catch (Exception e) {
            e.getMessage();
        }


    }

    private static void buscarTodosProdutos() {
        System.out.println("\n### Buscar Todos ###");
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            List<Produto> p = produtoDAO.buscarProduto();
            if (p != null) {
                for (Produto produto : p) {
                    System.out.println("Nome: " + produto.nome());
                }
            } else {
                System.out.println("Sem lista de produtos. ");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void buscarProdutoPorId() {
        System.out.println("\n### Buscar Produto por ID ###");
        ProdutoDAO produtoDAO = new ProdutoDAO();
        try {
            System.out.println(produtoDAO.produtoSelecionado(sc));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void atualizarProduto() {
        System.out.println("\n### Atualizar Produto ###");
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.atualizarProduto(sc);
    }

    private static void excluirProduto() {
        System.out.println("\n### Excluir Produto ###");
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.deletarProduto(sc);
    }
}
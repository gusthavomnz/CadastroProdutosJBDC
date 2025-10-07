package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProdutoDAO {


    public void salvar(Produto produto) throws Exception {
        var sql = "insert into produto (nome,quantidade,valor) values (?,?,?)";

        try (Connection conexao = Conexao.obterConexao();
             var statement = conexao.prepareStatement(sql)) {
            statement.setString(1, produto.nome());
            statement.setInt(2, produto.quantidade());
            statement.setDouble(3, produto.valor());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Produto> buscarProduto() throws Exception {

        String consulta = "select * from produto";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conexao = Conexao.obterConexao();
             var statement = conexao.prepareStatement(consulta)) {

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Produto produto = new Produto(rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getInt("quantidade"),
                            rs.getDouble("valor"));

                    produtos.add(produto);
                }
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }

        return produtos;
    }


    public Produto produtoSelecionado(Scanner sc) throws Exception{
        System.out.println("Digite o ID do produto: ");
        long s = sc.nextLong();
        sc.nextLine();
        String consulta = "select * from produto where id = ?";
        try(Connection connection = Conexao.obterConexao();
            PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
            preparedStatement.setLong(1, s);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()){
                long id = rs.getLong("id");
                String nome = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                double valor = rs.getDouble("valor");

                return new Produto(id,nome,quantidade,valor);
            } else {
                System.out.println("Produto não encontrado.");
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return null;
    }

    public void  atualizarProduto(Scanner sc) {
        System.out.println("Qual ID do produto a ser editado?");
        long id = sc.nextLong();

        try(Connection connection = Conexao.obterConexao()) {
            int opcao = -1;
            while (opcao != 0) {
                System.out.println("1- TROCAR NOME: \n +" +
                        "2- TROCAR QUANTIDADE: \n +" +
                        "3- TROCAR VALOR: \n" +
                        "0- SAIR;");
                opcao = sc.nextInt();
                sc.nextLine();
                switch (opcao) {
                    case 1:
                        String sql_nome = "update produto set nome = ? where id = ?";
                        try (PreparedStatement ps = connection.prepareStatement(sql_nome)) {
                            System.out.print("Novo nome: ");
                            String novo_nome = sc.nextLine();
                            ps.setString(1, novo_nome);
                            ps.setLong(2, id);
                            int linhas_afetadas = ps.executeUpdate();
                            System.out.println(linhas_afetadas > 0 ? "Atualizado com sucesso!" : "ID não localizado");
                        }
                        break;

                    case 2:
                        String sql_quantidade = "update produto set quantidade = ? where id = ?";
                        try(PreparedStatement ps = connection.prepareStatement(sql_quantidade)){
                            System.out.print("Insira nova quantidade: ");
                            int qtd = sc.nextInt();
                            ps.setInt(1, qtd);
                            ps.setLong(2,id);
                            int linhas_afetadas = ps.executeUpdate();
                            System.out.println(linhas_afetadas > 0 ? "Atualizado com sucesso!" : "ID não localizado");
                        }
                        break;

                    case 3:
                        String sql_valor = "update produto set valor = ? where id = ?";
                        try(PreparedStatement ps = connection.prepareStatement(sql_valor)){
                            System.out.println("Insira novo valor:");
                            double novo_valor = sc.nextDouble();
                            ps.setDouble(1, novo_valor);
                            ps.setLong(2,id);
                            int linhas_afetadas = ps.executeUpdate();
                            System.out.println(linhas_afetadas > 0 ? "Atualizado com sucesso!" : "ID não localizado");
                        }
                        break;

                    case 0:
                        System.out.println("Sistema finalizado!");
                        break;
                    default:
                        System.out.println("INSIRA UMA OPCAO VALIDA! ");
                        break;
                }
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deletarProduto(Scanner sc) {
        System.out.println("Digite id do produto a ser deletado: ");
        long id = sc.nextLong();
        sc.nextLine();
        String sql_delete = "delete from produto where id = ?";
        try(Connection connection = Conexao.obterConexao();
            PreparedStatement ps = connection.prepareStatement(sql_delete)){
            ps.setLong(1,id);
            int linhas_afetadas = ps.executeUpdate();
            System.out.println(linhas_afetadas > 0 ? "Atualizado com sucesso!" : "ID não localizado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

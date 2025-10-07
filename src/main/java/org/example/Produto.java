package org.example;

public record Produto(Long id, String nome, Integer quantidade, Double valor) {

    public Produto(String nome, Integer quantidade, Double valor) {
        this(null, nome, quantidade, valor);
    }


}

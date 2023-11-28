package com.projeto.sprint.projetosprint.util;

public class PilhaObj<T> {

    // 01) Atributos
    private T[] pilha;
    private int topo;

    // 02) Construtor
    public PilhaObj(int capacidade) {
        this.pilha = (T[]) new Object[capacidade];
        this.topo = -1;
    }

    // 03) MÃ©todo isEmpty
    public Boolean isEmpty() {
        return topo == -1;
    }

    // 04) MÃ©todo isFull
    public Boolean isFull() {
        return topo == pilha.length - 1;
    }

    // 05) MÃ©todo push
    public void push(T info) {
        if (!isFull()) {
            pilha[++topo] = info;
            return;
        }

        throw new IllegalStateException("Pilha cheia");
    }

    // 06) MÉTODO POP
    public T pop() {
        return pilha[topo--];
    }

    // 07) MÉTODO PEEK
    public T peek() {
        Integer numero = topo;
        if (!isEmpty()) {
            return pilha[topo];
        }

        return (T) numero;


    }

    // 08) MÃ©todo exibe

    //Getters & Setters (manter)
    public int getTopo() {
        return topo;
    }
}
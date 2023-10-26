package com.projeto.sprint.projetosprint.util;

import java.util.List;

public class ListaObj <T>{


    private Object[] vetor;
    private int nroElem;

    public ListaObj(int tamanhoLista){
        vetor = (T[]) new Object[tamanhoLista];
        nroElem = 0;
    }

    public void adiciona(T valor){
        vetor[nroElem] = valor;
        nroElem++;
    }

    public void exibe(){
        if(nroElem == 0){
            System.out.println("\nLista está vazia");
        }
        else{
            for (int i = 0; i < nroElem; i++){
                System.out.println(vetor[i]);
            }
        }
    }

    public int busca(T elemento){
        for (int i = 0; i < nroElem; i++){
            if (vetor[i].equals(elemento)){
                return i;
            }
        }
        return  -1;
    }

    public boolean removerPeloIndice(int indice){
        if(indice < 0 || indice > nroElem){
            return  false;
        }
        nroElem--;

        for (int i = indice; i < nroElem; i++){
            vetor[i] = vetor[i + 1];
        }

        return true;
    }

    public boolean removeElemento(T elemento){
        return removerPeloIndice(busca(elemento));
    }

    public int getTamanho(){return nroElem;}

    public T getElemento(int indice){
        if(indice >= 0 || indice < vetor.length){
            return (T) vetor[indice];
        }
        System.out.println("Indice Inválido");
        return null;
    }

    public void limpa(){
        for (Object item : vetor){
            item = null;
        }
        nroElem = 0;
        System.out.println("O vetor está limpo");
    }

}

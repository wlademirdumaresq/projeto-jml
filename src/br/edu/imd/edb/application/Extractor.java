package br.edu.imd.edb.application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Extractor {
    private /*@ spec_public @*/ Map<String, Character> letra = new HashMap<>();
    private /*@ spec_public @*/ Map<Character, String> codigo = new HashMap<>();
    private /*@ spec_public @*/ String mensagem, mapa, traducao;

    /*@
      @	requires mensagem != null && mapa != null && traducao != null; 
      @ assignable this.mensagem, this.mapa, this.traducao, letra, codigo;
      @ ensures this.mensagem == mensagem;
      @ ensures this.mapa == mapa;
      @ ensures this.traducao == traducao;
      @*/
    public Extractor(String mensagem, String mapa, String traducao) throws IOException {
        this.mensagem = mensagem;
        this.mapa = mapa;
        this.traducao = traducao;
        recuperandoTabela();
        descodificandoTexto();
    }

    /*@
      @	requires mapa != null; 
      @ assignable this.mensagem, this.mapa, this.traducao, letra, codigo;
      @ ensures this.mensagem == null;
      @ ensures this.mapa == mapa;
      @ ensures this.traducao == null;
      @*/
    public Extractor(String mapa) throws IOException {
        this.mensagem = null;
        this.mapa = mapa;
        this.traducao = null;
        recuperandoTabela();
    }
    
    public /*@ pure @*/ Map<Character, String> getCodigo() {
        return codigo;
    }

    /*@
      @ requires mapa != null;
      @ assignable letra, codigo;
      @*/
    public void recuperandoTabela() throws IOException {
        FileInputStream dicionario = new FileInputStream(mapa);
        byte linha[] = Files.readAllBytes(Paths.get(this.mapa));
        String arquivo = new String(linha, "UTF8");
        String []codigos = arquivo.split(String.valueOf((char)-1));

        for (int i = 0; i < codigos.length; ) {
            letra.put(codigos[i+1], codigos[i].charAt(0));
            codigo.put(codigos[i].charAt(0), codigos[i+1]);
            i+=2;
        }

        dicionario.close();
    }

    /*@
      @ requires mensagem != null && traducao != null;
      @*/
    public /*@ pure @*/ void descodificandoTexto() throws IOException {
        FileInputStream fs = new FileInputStream(mensagem);

        byte[] bytes = Files.readAllBytes(Paths.get(this.mensagem));
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            str.append(new StringBuilder(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(" ", "0")).reverse());
        }

        String comparador = "";
        FileWriter novo = new FileWriter(traducao);

        for (int j = 0; j < str.length(); j++) {
            comparador += str.charAt(j);
            if (codigo.containsValue(comparador)) {
                if (comparador.equals(codigo.get((char) 258))) {

                    novo.close();

                } else {
                    novo.write(letra.get(comparador));
                    comparador = "";
                }

            }
        }
        
        fs.close();
    }
}







package br.edu.imd.edb.application;

import br.edu.imd.edb.heap.Heap;
import br.edu.imd.edb.tree.Node;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static br.edu.imd.edb.util.TBinary.LBinary;

public class Compressor {

    private /*@ spec_public @*/ int tamanhoAntigo;
    private /*@ spec_public @*/ int tamanhoTabela;
    private /*@ spec_public @*/ int tamanhoCodificado;
    private /*@ spec_public @*/ Heap heap = new Heap();
    private /*@ spec_public @*/ Map<Integer, Integer> map = new HashMap<>();
    private /*@ spec_public @*/ boolean chave = true;
    private /*@ spec_public @*/ Node tree = new Node();
    private /*@ spec_public @*/ Map<Character, String> binari = new HashMap<>();
    private /*@ spec_public @*/ String texto, mensagem, dicionario;


    /*@
      @	requires texto != null && mensagem != null && dicionario != null;
      @ assignable this.texto, this.mensagem, this.dicionario, tamanhoAntigo, tamanhoTabela,tamanhoCodificado,heap,map,tree,binari,chave;
      @ ensures this.texto == texto;
      @ ensures this.mensagem == mensagem;
      @ ensures this.dicionario == dicionario;
      @*/
    public Compressor(String texto, String mensagem, String dicionario) throws IOException {
        this.texto = texto;
        this.mensagem = mensagem;
        this.dicionario = dicionario;
        criarDicionario();
        criandoArvore();
        criandoTabela();
        codificandoTexto();
    }

    /*@ pure;
      @ ensures \result == binari;
      @ */
    public Map<Character, String> getBinari() {
        return binari;
    }

    /*@ pure;
      @ ensures \result == tree;
      @ */
    public Node getTree() {
        return tree;
    }

    /*@ pure;
      @ ensures \result == map;
      @ */
    public Map<Integer, Integer> getMap() {
        return map;
    }


    /*@
      @ requires texto != null;
      @ assignable linha[], leitor;
      @*/
    public void criarDicionario() {

        try {
            FileInputStream arq = new FileInputStream(texto);

            BufferedInputStream leitor = new BufferedInputStream(arq);

            byte linha[] = Files.readAllBytes(Paths.get(texto));
            tamanhoAntigo = linha.length;
            String arquivo = new String(linha, "UTF8");

            for (int i = 0; i < arquivo.length(); i++) {
                if (map.containsKey((int) arquivo.charAt(i))) {
                    int value = (map.get((int) arquivo.charAt(i))) + 1;
                    map.put((int) arquivo.charAt(i), value);
                } else {
                    map.put((int) arquivo.charAt(i), 1);
                }
            }

            leitor.close();

        } catch (IOException e) {
            System.out.println("parametro invalido" + e.getMessage());
        }

        map.put(258, 1);

    }

    /*@
      @ requires map != null;
      @ assignable left, right, tree;
      @*/

    public void criandoArvore() {
        for (Integer i : map.keySet()) {
            Node no = new Node(i, map.get(i));
            heap.insert(no);
        }

        do {
            if (heap.getSize() == 1) {
                chave = false;
                break;
            }
            Node left = heap.peek();
            heap.remove();
            Node right = heap.peek();
            heap.remove();
            tree = new Node(left.getValue().getQuantitie() + right.getValue().getQuantitie(), left, right);
            heap.insert(tree);

        } while (heap.getSize() > 1);

    }


    /*@
     @ requires tree != null;
     @ requires chave != null;
     @ requires dicionario != null;
     @ assignable tamanhoTabela, tabelaCod;
     @*/
    public void criandoTabela() throws IOException {
        String bit[] = LBinary(tree, chave);

        FileWriter tabelaCod = new FileWriter(dicionario);
        tamanhoTabela = 0;
        for (int i = 0; i < bit.length; ) {
            tabelaCod.write((char) Integer.parseInt(bit[i]) + String.valueOf((char) -1) + bit[i + 1] + String.valueOf((char) -1));
            binari.put((char) Integer.parseInt(bit[i]), bit[i + 1]);
            i += 2;
        }
        tabelaCod.close();
        FileInputStream tabela = new FileInputStream(dicionario);
        tamanhoTabela = Files.readAllBytes(Paths.get(this.dicionario)).length;
        tabela.close();

    }


    /*@
     @ requires mensagem != null;
     @ requires texto != null;
     @ assignable contador, arquivo, texto[], bitSet, bits, b, arq, multiplicacao, tamanhoCodificado;
     @*/
    public void codificandoTexto() throws IOException {
        FileOutputStream b = new FileOutputStream(mensagem);
        FileInputStream arq = new FileInputStream(texto);

        int contador = 0;

        BitSet bitSet = new BitSet();
        String bits = "";


        byte texto[] = Files.readAllBytes(Paths.get(this.texto));
        String arquivo = new String(texto, "UTF8");

        for (int i = 0; i < arquivo.length(); i++) {

            if (binari.containsKey(arquivo.charAt(i))) {
                for (int j = 0; j < binari.get(arquivo.charAt(i)).length(); j++) {
                    if (binari.get(arquivo.charAt(i)).charAt(j) == '1') {
                        bits += "1";
                        bitSet.set(contador);
                    } else {
                        bits += "0";
                        bitSet.set(contador, false);
                    }
                    contador += 1;
                }
            }

        }

        for (int j = 0; j < binari.get((char) 258).length(); j++) {
            if (binari.get((char) 258).charAt(j) == '1') {

                bitSet.set(contador);
            } else {

                bitSet.set(contador, false);
            }
            contador += 1;
        }
        int multiplicacao = 0;
        if (contador % 8 == 0) {

            b.write(bitSet.toByteArray());
            b.close();
            arq.close();
        } else {
            multiplicacao = (int) ((1 - (((float) contador / 8) - (contador / 8))) * 8);
            for (int i = 0; i < multiplicacao; i++) {
                bitSet.set(contador);
                contador += 1;

            }
            b.write(bitSet.toByteArray());
            b.close();
            arq.close();
        }

        tamanhoCodificado = (bits.length()) / 8;
        System.out.println(100.0 - (((float) (tamanhoCodificado + tamanhoTabela) * 100) / tamanhoAntigo) + "% compactação");
    }

}

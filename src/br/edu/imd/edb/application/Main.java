package br.edu.imd.edb.application;

import java.io.IOException;

public class Main {
	//compress projeto/arquivosAntigos/teste1.txt  projeto/arquivosBinarios/teste1.edz  projeto/chaves/teste1.edt
	//extract projeto/arquivosBinarios/teste1.edz projeto/chaves/teste1.edt projeto/arquivosNovos/teste1.txt

    public static void main(String[] args) throws IOException {

        if (args[0].equals("compress")) {
            new Compressor(args[1], args[2], args[3]);


        } else if (args[0].equals("extract")) {
            new Extractor(args[1], args[2], args[3]);

        }

    }
}

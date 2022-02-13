package br.edu.imd.edb.application;

import java.io.IOException;

public class Main {
	//compress arquivosAntigos/teste1.txt  arquivosBinarios/teste1.edz  chaves/teste1.edt
	//extract arquivosBinarios/teste1.edz chaves/teste1.edt arquivosNovos/teste1.txt

    public static void main(String[] args) throws IOException {

        if (args[0].equals("compress")) {
            new Compressor(args[1], args[2], args[3]);


        } else if (args[0].equals("extract")) {
            new Extractor(args[1], args[2], args[3]);

        }

    }
}

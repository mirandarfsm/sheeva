package br.com.sheeva.service;

import java.io.IOException;
import java.util.List;

import br.com.sheeva.dominio.Arquivo;
import br.com.sheeva.dominio.Versao;

public interface VersaoService {

	void salvarArquivos(Versao versao, List<Arquivo> arquivos) throws IOException;
}

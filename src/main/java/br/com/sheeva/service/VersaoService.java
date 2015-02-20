package br.com.sheeva.service;

import java.io.InputStream;
import java.util.List;

import br.com.sheeva.dominio.Versao;

public interface VersaoService {

	void salvarArquivos(Versao versao, List<InputStream> arquivos);
}

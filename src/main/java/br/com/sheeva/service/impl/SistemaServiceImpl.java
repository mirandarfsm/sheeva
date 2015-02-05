package br.com.sheeva.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.SistemaDao;
import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.service.SistemaService;

@Service("sistemaService")
public class SistemaServiceImpl implements SistemaService {

	@Autowired
	private SistemaDao sistemaDao;

	public void salvar(Sistema sistema) {
		sistemaDao.save(sistema);
	}

	public void remover(Integer id) {
		sistemaDao.remove(id);
	}

	public List<Sistema> listarTodos() {
		return sistemaDao.getAll();
	}

	public Sistema buscarPeloId(Integer id) {
		return sistemaDao.getById(id);
	}

	public Sistema buscarPeloNome(String nome) {
		return sistemaDao.getByNome(nome);
	}

	public void salvarArquivos(List<UploadedFile> arquivos, Sistema sistema){
		zip(arquivos, sistema.getArquivo());
	}

	private void zip(List<UploadedFile> arquivos, String zipFile) {
		byte[] buffer = new byte[1024];
		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (UploadedFile file : arquivos) {

				ZipEntry ze = new ZipEntry(file.getFileName());
				zos.putNextEntry(ze);

				InputStream in = file.getInputstream();

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			// remember close it
			zos.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}

package br.com.pe.urbana.importacao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;

import br.com.pe.urbana.entidade.EntidadeUsuario;

public class ArquivoDocs {

	public String createFile(List<EntidadeUsuario> usuarios) throws Exception {

		File file = new File("src/br/com/pe/urbana/importacao/docs.xml");
		FlatFile<Record> flatFile = Texgit.createFlatFile(file);
		StringBuilder fileString = new StringBuilder();

		List<Record> docsUsuarios = createUsuarios(flatFile, usuarios);
		for (Record record : docsUsuarios) {
			flatFile.addRecord(record);
		}
		
		fileString.append(StringUtils.join(flatFile.write(), "\r\n"));

		return fileString.toString();
	}

	private List<Record> createUsuarios(FlatFile<Record> flatFile, List<EntidadeUsuario> usuarios) throws Exception {

		List<Record> list = new ArrayList<>();
		
		for (EntidadeUsuario usuario : usuarios) {
			
			Record body = flatFile.createRecord("Body");
		
			body.setValue("id", usuario.getId());
			body.setValue("tipoDocumento", 89);
			body.setValue("numeroDocumento", usuario.getCpf());
			
			list.add(body);
			
		}

		return list;
	}

}

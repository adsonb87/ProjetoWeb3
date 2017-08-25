package br.com.pe.urbana.importacao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;

import br.com.pe.urbana.entidade.EntidadeUsuario;

public class ArquivoUsers {

	public String createFile(List<EntidadeUsuario> usuarios) throws Exception {

		File file = new File("src/br/com/pe/urbana/importacao/user.xml");
		FlatFile<Record> flatFile = Texgit.createFlatFile(file);
		StringBuilder fileString = new StringBuilder();

		List<Record> userUsuarios = createUsuarios(flatFile, usuarios);
		for (Record record : userUsuarios) {
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
			body.setValue("nome", usuario.getNome());
			body.setValue("dataNascimento", usuario.getDataNascimento());
			body.setValue("nomeMae", usuario.getNomeMae());
			
			list.add(body);
			
		}

		return list;
	}

}

package br.com.sheeva.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.autenticacao.AuthenticationContext;
import br.com.sheeva.service.UsuarioService;
import br.com.sheeva.utils.Mensagem;

@Service("alterarSenhaBean")
@Scope("view")
public class AlterarSenhaBean {


	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AuthenticationContext authenticationContext;
	
	private String novaSenha;
	private String senhaAtual = "";

	public AlterarSenhaBean(){

	}
	
	public void alterarSenha(){
		if (senhaAtual == null || senhaAtual == "") {
			Mensagem.msgErro("O campo SENHA ATUAL é obrigatório.");
			return;
		}
		if (novaSenha == null || novaSenha == "") {
			Mensagem.msgErro("O campo NOVA SENHA é obrigatório.");
			return;
		}
		if (!usuarioService.mudarSenha(authenticationContext.getUsuarioLogado().getLogin(), senhaAtual, novaSenha)) {
			Mensagem.msgErro("A senha atual inserida está incorreta!");
			return;
		}
		Mensagem.msgInformacao("Senha Alterada com sucesso");
		
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
	
	
}

package br.fpu.guilherme.utils;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface IErrorService {
	public boolean addErrorsView(RedirectAttributes redirectAttributes, Errors errors);
	public boolean addErrorsViewModel(RedirectAttributes redirectAttributes, Errors errors,Model model);
	/**
	 * @param acao
	 *            (ex:Cadastro de = ({ Matricula }) )
	 * @param name
	 *            (ex:disciplinas - Obs*Mesmo nome do list)
	 * @param entidade
	 *            (list da pesquisa)
	 */
	public boolean entityEmpty(String acao, String name, List<?> entidade, Model model,
			RedirectAttributes redirectAttributes);
	
	public boolean fieldsEmptyOrNull( Object entidade, Model model,
			RedirectAttributes redirectAttributes);
	
	public boolean entityEmptyOrNull( String fieldName, List<?> entidade, Model model,
			RedirectAttributes redirectAttributes);
	
	boolean entityEmpty(String msg1, String msg2, String acao, String name, List<?> entidade, Model model,
			RedirectAttributes redirectAttributes);
	

}

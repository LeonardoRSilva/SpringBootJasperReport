package br.fpu.guilherme.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hamcrest.core.IsNull;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.fpu.guilherme.utils.IErrorService;

@Service
@Transactional
public class ErrorService implements IErrorService {
	private List<ObjectError> erros;
	private List<String> mensagens;

	@Override
	public boolean addErrorsView(RedirectAttributes redirectAttributes, Errors errors) {

		if (errors.hasErrors()) {
			erros = errors.getAllErrors();
			mensagens = new ArrayList<String>();

			for (ObjectError objectError : erros) {

				// String fieldName= errors instanceof FieldError ?
				// ((FieldError)erros).getField() : null;

				// mensagens +=
				// ((FieldError)objectError).getField()+"-"+objectError.getDefaultMessage()+"\n";
				mensagens.add(String.format(" %s : %s", ((FieldError) objectError).getField(),
						objectError.getDefaultMessage()));
				redirectAttributes.addFlashAttribute(((FieldError) objectError).getField().toUpperCase(), "error");

			}
			redirectAttributes.addFlashAttribute("statusError", "unsuccess");
			redirectAttributes.addFlashAttribute("message", mensagens);

			return true;
		}
		return false;
	}

	@Override
	public boolean entityEmpty(String acao, String name, List<?> entidade, Model model,
			RedirectAttributes redirectAttributes) {
		if (!entidade.isEmpty()) {
			model.addAttribute(name, entidade);
			return false;
		} else {

			redirectAttributes.addFlashAttribute("statusError", "unsuccess");
			redirectAttributes.addFlashAttribute("message", " Não exitem cadastrados " + name + "  para "
					+ "realizar o Cadastro de " + acao + ". Cadastre " + name);

		}
		return true;
	}

	@Override
	public boolean entityEmpty(String msg1, String msg2, String acao, String name, List<?> entidade, Model model,
			RedirectAttributes redirectAttributes) {
		if (!entidade.isEmpty()) {
			model.addAttribute(name, entidade);
			return false;
		} else {

			redirectAttributes.addFlashAttribute("statusError", "unsuccess");
			redirectAttributes.addFlashAttribute("message", " Não exitem cadastrados " + name + " " + msg2 + "  para "
					+ "realizar o Cadastro de " + acao + ". " + msg1 + " " + name);

		}
		return true;
	}
	
	@Override
	public boolean fieldsEmptyOrNull( Object entidade, Model model,
			RedirectAttributes redirectAttributes) {
	
		
			
			 //* percorre os atributos de uma classe validando se é nulo ou vazio
	        //obj.getClass().getSimpleName() é o nome do BEAN 
			Class theClass = entidade.getClass();
			Field parametros[] = theClass.getDeclaredFields();	
	         
	        List metodosInvalidos = new ArrayList();
	        if (parametros.length == 0) {
	            return false;
	        }
	       
	            for (Method method : entidade.getClass().getMethods()) {
	                //o nome do métodos começa com get e termina com o nome do parametro do banco
	            	System.out.println("entidade.getClass().getMethods(): "+entidade.getClass().getMethods());
	                if (method.getName().startsWith("get") && method.getName().endsWith(method.getName().substring(3, method.getName().length()))) {
	                    try {
	                        System.out.println("Campo2 : " + method.getName());
	                    	
	                    	
	                    	 String valor = (String) method.invoke(entidade);
	                    	 System.out.println("valor : " + (String) method.invoke(entidade));
	                        if (valor == null || valor.isEmpty()) {
	                        	redirectAttributes.addFlashAttribute(method.getName(), "error");
	                			redirectAttributes.addFlashAttribute("statusError", "unsuccess");
	                			redirectAttributes.addFlashAttribute("message",
	                					method.getName().substring(3, method.getName().length()).toLowerCase() + "- Não pode estar em branco");
	                        	return true;
	                        } else {
	                            //System.out.println("Preenchido com: " + valor);
	                            return false;
	                        }
	                       
	                    } catch (Exception ex) {
	                    }
	                }
	            }

	        
	     
		
		return false;
			/*
			Class theClass = entidade.getClass();
			Field fields[] = theClass.getDeclaredFields();	    
			for (int j = 0, m = fields.length; j < m; j++) 
			{		
				System.out.println("Field name: " + fields[j].getName());
				
			}*/
			
			
	}

	@Override
	public boolean entityEmptyOrNull(String fieldName, List<?> entidade, Model model,
			RedirectAttributes redirectAttributes) {

		if (entidade != null) {

			return false;
		} else {

			redirectAttributes.addFlashAttribute(fieldName, "error");
			redirectAttributes.addFlashAttribute("statusError", "unsuccess");
			redirectAttributes.addFlashAttribute("message",
					fieldName.toLowerCase().substring(2, 12) + "- Não pode estar em branco");

		}
		return true;
	}

	@Override
	public boolean addErrorsViewModel(RedirectAttributes redirectAttributes, Errors errors, Model model) {
		if (errors.hasErrors()) {
			erros = errors.getAllErrors();
			mensagens = new ArrayList<String>();

			for (ObjectError objectError : erros) {

				// String fieldName= errors instanceof FieldError ?
				// ((FieldError)erros).getField() : null;

				// mensagens +=
				// ((FieldError)objectError).getField()+"-"+objectError.getDefaultMessage()+"\n";
				mensagens.add(String.format(" %s : %s", ((FieldError) objectError).getField(),
						objectError.getDefaultMessage()));
				model.addAttribute(((FieldError) objectError).getField().toUpperCase(), "error");

			}
			model.addAttribute("statusError", "unsuccess");
			model.addAttribute("message", mensagens);

			return true;
		}
		return false;
	}

}

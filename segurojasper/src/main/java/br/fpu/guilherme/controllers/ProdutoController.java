package br.fpu.guilherme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.fpu.guilherme.entities.ProdutoEntity;
import br.fpu.guilherme.service.ProdutoService;
import br.fpu.guilherme.utils.IErrorService;

@Controller
public class ProdutoController {
	@Autowired
	public ProdutoService produtoService;
	
	
	@Autowired
	public IErrorService errorServiceInterface;


	@RequestMapping(value = { "/produto" }, method = RequestMethod.GET)
	public ModelAndView list(ProdutoEntity produto, Model model) {
		// model.addAttribute("produtos", produtoRepository.findAll());

		
		if (produto.getId() != null||model.containsAttribute("produto")) {
			//model.addAttribute("produto", produtoService.find(produto.getId()));
			model.addAttribute("pgdate", "true");
		} else {
			ModelAndView mv = new ModelAndView("produto");
			String sku = "MGXK-"+System.currentTimeMillis();
			model.addAttribute("numSKU", sku);
			model.addAttribute("produto", new ProdutoEntity());
			model.addAttribute("pgdate", "true");
		}

		ModelAndView mv = new ModelAndView("produto");

		//model.addAttribute("produtos", (List<ProdutoEntity>) produtoService.getAll());
		
		model.addAttribute("pgdate", "true");

		 
		return mv;
	}

	@RequestMapping(value = { "/produto/save" }, method = RequestMethod.POST)
	public String saveProdutoEntity(@ModelAttribute("produto") @Validated ProdutoEntity produto, Errors errors,
			final RedirectAttributes redirectAttributes) {

		if (errorServiceInterface.addErrorsView(redirectAttributes, errors)) {
			return "redirect:/produto";
		}
		ProdutoEntity editProdutoEntity = this.produtoService.findSku(produto.getSku());
		if(editProdutoEntity != null){
			if (produtoService.save(editProdutoEntity) != null) {
				redirectAttributes.addFlashAttribute("statusError", "success");
				redirectAttributes.addFlashAttribute("message", "Produto salvod com sucesso");
			} else {
				redirectAttributes.addFlashAttribute("statusError", "unsuccess");
				redirectAttributes.addFlashAttribute("message", "Produto não salvo.");
			}
			return "redirect:/produto";
			
		}
		if (produtoService.save(produto) != null) {
			redirectAttributes.addFlashAttribute("statusError", "success");
			redirectAttributes.addFlashAttribute("message", "Produto salvo com sucesso");
		} else {
			redirectAttributes.addFlashAttribute("statusError", "unsuccess");
			redirectAttributes.addFlashAttribute("message", "Produto não salvo.");
		}

		return "redirect:/produto";
	}

	@RequestMapping(value = "/produto/{operation}/{produtoId}", method = RequestMethod.GET)
	public String editRemoveProdutoEntity(@PathVariable("operation") String operation,
			@PathVariable("produtoId") Long produtoId, final RedirectAttributes redirectAttributes, Model model) {
		if (operation.equals("delete")) {
			try {
				if (produtoService.delete(produtoId)) {
					redirectAttributes.addFlashAttribute("statusError", "success");
					redirectAttributes.addFlashAttribute("message", "Produto deletado com sucesso");
				} else {
					redirectAttributes.addFlashAttribute("statusError", "unsuccess");
					redirectAttributes.addFlashAttribute("message", "Produto não deletado.");
				}
			} catch (DataIntegrityViolationException e1) {
				redirectAttributes.addFlashAttribute("statusError", "unsuccess");
				redirectAttributes.addFlashAttribute("message", "Não foi possivel deletar "
						+ "o produto pois existem outras tabelas utilizando este produto.");
			}

		} else if (operation.equals("edit")) {
			ProdutoEntity editProdutoEntity = produtoService.find(produtoId);
			if (editProdutoEntity != null) {
				redirectAttributes.addFlashAttribute("produtos",
						(List<ProdutoEntity>) produtoService.getAll());
				redirectAttributes.addFlashAttribute("produto", editProdutoEntity);
				redirectAttributes.addFlashAttribute("numSKU", editProdutoEntity.getSku());
				return "redirect:/produto";
			} else {
				redirectAttributes.addFlashAttribute("statusError", "unsuccess");
				redirectAttributes.addFlashAttribute("message", "Não encontrado");
			}
		}

		return "redirect:/pesqproduto";
	}

	@RequestMapping(value = "/produto/update", method = RequestMethod.POST)
	public String updateProdutoEntity(@ModelAttribute("editProdutoEntity") ProdutoEntity editProdutoEntity,
			final RedirectAttributes redirectAttributes) {
		if (produtoService.edit(editProdutoEntity) != null) {
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
		return "redirect:/produto";
	}

	
}

package br.fpu.guilherme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.fpu.guilherme.entities.Filtro;
import br.fpu.guilherme.service.ProdutoService;

@Controller
public class PesquisaProdutoController {

	@Autowired
	public ProdutoService produtoService;

	

	@RequestMapping("/pesqproduto")
	public String filtro(Model model) {

		return "pesqproduto";
	}

	@RequestMapping("/produtofiltro")
	public String index(Filtro<String, String> filtro, Model model, final RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("pesqproduto");
		// mv.addObject("filtro", new Filtro<Long, String>());
		mv.addObject("filtroProduto", new Filtro<Long, String>());
		if ((filtro.getParametro2() == null) && (filtro.getParametro1() == null)) {
			redirectAttributes.addFlashAttribute("produtos", produtoService.getAll());
		} else if ((filtro.getParametro2() != null || !filtro.getParametro2().toString().isEmpty()
				|| !filtro.getParametro2().toString().equalsIgnoreCase(""))
				&& (filtro.getParametro1() == null || filtro.getParametro1().toString().isEmpty()
						|| filtro.getParametro1().toString().equalsIgnoreCase(""))) {

			// redirectAttributes.addFlashAttribute("produtos",
			// produtoRepository.findAll());
			redirectAttributes.addFlashAttribute("produtos",
					produtoService.findProdutoByNome(String.valueOf(filtro.getParametro2())));

		} else if ((filtro.getParametro2() == null || filtro.getParametro2().toString().isEmpty()
				|| filtro.getParametro2().toString().equalsIgnoreCase(""))
				&& (filtro.getParametro1() != null || !filtro.getParametro1().toString().isEmpty()
						|| !filtro.getParametro1().toString().equalsIgnoreCase(""))) {

			// redirectAttributes.addFlashAttribute("produtos",
			// produtoRepository.findAll());

			redirectAttributes.addFlashAttribute("produtos",
					produtoService.findProdutoBySkuLike(String.valueOf(filtro.getParametro1())));

		} else if ((filtro.getParametro2() != null || !filtro.getParametro2().toString().isEmpty()
				|| !filtro.getParametro2().toString().equalsIgnoreCase(""))
				&& (filtro.getParametro1() != null || !filtro.getParametro1().toString().isEmpty()
						|| !filtro.getParametro1().toString().equalsIgnoreCase(""))) {
			redirectAttributes.addFlashAttribute("produtos", produtoService.getAll());
		}
		return "redirect:/pesqproduto";

	}

}

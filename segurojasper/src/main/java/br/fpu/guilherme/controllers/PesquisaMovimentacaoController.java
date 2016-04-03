package br.fpu.guilherme.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.fpu.guilherme.entities.Filtro;
import br.fpu.guilherme.entities.TipoMovimentacao;
import br.fpu.guilherme.service.MovimentacaoService;

@Controller
public class PesquisaMovimentacaoController {

	@Autowired
	public MovimentacaoService movimentacaoService;

	

	@RequestMapping("/pesqmovimentacao")
	public String filtro(Model model) {
		model.addAttribute("pgdate", "true");
		return "pesqmovimentacao";
	}

	@RequestMapping("/movimentacaofiltro")
	public String index(Filtro<String, String> filtro, Model model, final RedirectAttributes redirectAttributes) throws ParseException {
		ModelAndView mv = new ModelAndView("pesqmovimentacao");
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		// mv.addObject("filtro", new Filtro<Long, String>());
		mv.addObject("filtroMovimentacao", new Filtro<String, String>());
		if ((filtro.getParametro2() == null) && (filtro.getParametro1() == null)) {
			redirectAttributes.addFlashAttribute("movimentacoes", movimentacaoService.getAll());
		} else if ((filtro.getParametro2() != null || !filtro.getParametro2().toString().isEmpty()
				|| !filtro.getParametro2().toString().equalsIgnoreCase(""))
				&& (filtro.getParametro1() == null || filtro.getParametro1().toString().isEmpty()
						|| filtro.getParametro1().toString().equalsIgnoreCase(""))) {

			// redirectAttributes.addFlashAttribute("movimentacaos",
			// movimentacaoRepository.findAll());
			redirectAttributes.addFlashAttribute("movimentacoes",
					movimentacaoService.findMovimentacaoTipoLike(String.valueOf(filtro.getParametro2())));

		} else if ((filtro.getParametro2() == null || filtro.getParametro2().toString().isEmpty()
				|| filtro.getParametro2().toString().equalsIgnoreCase(""))
				&& (filtro.getParametro1() != null || !filtro.getParametro1().toString().isEmpty()
						|| !filtro.getParametro1().toString().equalsIgnoreCase(""))) {

			// redirectAttributes.addFlashAttribute("movimentacaos",
			// movimentacaoRepository.findAll());
			Date inicio = df.parse(filtro.getParametro1());
			Date fim = df.parse(filtro.getParametro3());
			
			redirectAttributes.addFlashAttribute("movimentacoes",
					movimentacaoService.findMovimentacaoBetween(inicio, fim));

		} else if ((filtro.getParametro2() != null || !filtro.getParametro2().toString().isEmpty()
				|| !filtro.getParametro2().toString().equalsIgnoreCase(""))
				&& (filtro.getParametro1() != null || !filtro.getParametro1().toString().isEmpty()
						|| !filtro.getParametro1().toString().equalsIgnoreCase(""))) {
			redirectAttributes.addFlashAttribute("movimentacoes", movimentacaoService.getAll());
		}
		
		return "redirect:/pesqmovimentacao";

	}
	@ModelAttribute
	public List<TipoMovimentacao> tiposMovimentacao() {
		return Arrays.asList(TipoMovimentacao.values());
	}
}

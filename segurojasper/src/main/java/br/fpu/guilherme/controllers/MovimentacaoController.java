package br.fpu.guilherme.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.fpu.guilherme.entities.ItensMovimentacaoEntity;
import br.fpu.guilherme.entities.MovimentacaoEntity;
import br.fpu.guilherme.entities.ProdutoEntity;
import br.fpu.guilherme.entities.TipoItensMovimentacao;
import br.fpu.guilherme.entities.TipoMovimentacao;
import br.fpu.guilherme.service.ItensMovimentacaoService;
import br.fpu.guilherme.service.MovimentacaoService;
import br.fpu.guilherme.service.ProdutoService;
import br.fpu.guilherme.utils.IErrorService;

@Controller
public class MovimentacaoController  {

	@Autowired
	public ItensMovimentacaoService itensMovimentacaoService;
	
	@Autowired
	public MovimentacaoService movimentacaoService;
	
	@Autowired
	public ProdutoService produtoService;
	
	@Autowired
	public IErrorService errorServiceInterface;
	

	List<ItensMovimentacaoEntity> itens = new ArrayList<>();
	


	@RequestMapping(value = { "/movimentacao" }, method = RequestMethod.GET)
	public String list(MovimentacaoEntity movimentacao, Model model, final RedirectAttributes redirectAttributes) {
		



		model.addAttribute("itensMovimentacao",itens);
		if (movimentacao.getId() != null) {
			
			model.addAttribute("movimentacao", movimentacaoService.find(movimentacao.getId()));
			model.addAttribute("pgdate", "true");
			
		} else {

			
			model.addAttribute("movimentacoes", movimentacao.getItens());
			model.addAttribute("produtos", produtoService.getAll());    
			model.addAttribute("pgdate", "true");
			if(itens.isEmpty()){
				model.addAttribute("vtotal", "0.00");
			}else{
				
				BigDecimal valorTotal = new BigDecimal("0.00");
				for (ItensMovimentacaoEntity itensMovimentacaoEntity : itens) {
					valorTotal = valorTotal.add(itensMovimentacaoEntity.getTotalProduto());
				}
				
				model.addAttribute("vtotal",valorTotal);
			}
			
		}
		model.addAttribute("pgdate", "true");
	
		
		
		Date data = new Date();  
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");  
		model.addAttribute("pgdate", "true");
		model.addAttribute("data", formatador.format( data ));  

		return "/movimentacao";
	}

	@RequestMapping(value = { "/itensmovimentacao/add" }, method = RequestMethod.POST)
	public String addItem(@ModelAttribute("tipoItensMovimentacao")   @Validated TipoItensMovimentacao itemMovimentacao, Errors errors,
			final RedirectAttributes redirectAttributes,Model model) {
		

		
		if (errorServiceInterface.addErrorsView(redirectAttributes, errors)) {
			return "redirect:/movimentacao";
		}
			ProdutoEntity produto = produtoService.find(itemMovimentacao.getProduto().getId());
			
			
			ItensMovimentacaoEntity item = new ItensMovimentacaoEntity();
			ItensMovimentacaoEntity itemb = new ItensMovimentacaoEntity();
			
			item.setTipoItensMovimentacao(itemMovimentacao, produto, (itemMovimentacao.getQuantidade().multiply(itemMovimentacao.getProduto().getCustoMedio()).setScale(2, RoundingMode.HALF_EVEN)));
			Boolean contain = false;
			if(itens != null){
			
				for (ItensMovimentacaoEntity itensMovimentacaoEntity : itens) {
					if(itensMovimentacaoEntity.getProduto().getSku().contentEquals(item.getProduto().getSku())){
						 contain = true;
						 itemb = itensMovimentacaoEntity;
						
						 
					}
				}
				
				 if(itemb != null && contain == true){
				  BigDecimal quantidade = itemb.getQuantidade();//acha o index do elemento na lista buscando-o e pega o campo quantidade soma a quantida que veio no parametro
					
					quantidade = quantidade.add(itemMovimentacao.getQuantidade());
					
					
					itemb.setQuantidade(quantidade);
					
					itemb.setTotalProduto((itemb.getQuantidade().multiply(itemMovimentacao.getProduto().getCustoMedio()).setScale(2, RoundingMode.HALF_EVEN)));
					
					
					itens.set(itens.indexOf(itemb),	itemb);
					
					BigDecimal valorTotal = new BigDecimal("0.00");
					for (ItensMovimentacaoEntity itensMovimentacaoEntity : itens) {
						valorTotal = valorTotal.add(itensMovimentacaoEntity.getTotalProduto());
					}
					
					redirectAttributes.addFlashAttribute("vtotal",valorTotal);
					//itens.add(itemb);
				 }
				 
			} 
			
			if(!contain){
				
				itens.add(item);
				
				
				BigDecimal valorTotal = new BigDecimal("0.00");
				for (ItensMovimentacaoEntity itensMovimentacaoEntity : itens) {
					valorTotal = valorTotal.add(itensMovimentacaoEntity.getTotalProduto());
				}
				
				redirectAttributes.addFlashAttribute("vtotal",valorTotal);
		}
			
			redirectAttributes.addFlashAttribute("itensMovimentacao",itens);
			
		

		return "redirect:/movimentacao";
	}

	@RequestMapping(value = { "/itensmovimentacao/remove" }, method = RequestMethod.POST)
	public String removeItem(@ModelAttribute("tipoItensMovimentacao")   @Validated TipoItensMovimentacao itemMovimentacao, Errors errors,
			final RedirectAttributes redirectAttributes,Model model) {
		

		
		if (errorServiceInterface.addErrorsView(redirectAttributes, errors)) {
			return "redirect:/movimentacao";
		}
			ProdutoEntity produto = produtoService.find(itemMovimentacao.getProduto().getId());
			
			
			ItensMovimentacaoEntity item = new ItensMovimentacaoEntity();
			ItensMovimentacaoEntity itemb = new ItensMovimentacaoEntity();
			item.setTipoItensMovimentacao(itemMovimentacao, produto,  (itemMovimentacao.getQuantidade().multiply(itemMovimentacao.getProduto().getCustoMedio())));
			Boolean contain = false;
			if(itens != null){
			
				for (ItensMovimentacaoEntity itensMovimentacaoEntity : itens) {
					if(itensMovimentacaoEntity.getProduto().getSku().contentEquals(item.getProduto().getSku())){
						 contain = true;
						 itemb = itensMovimentacaoEntity;
						
						 
						 
					}
				}
				
				 if(itemb != null && contain == true){
				  BigDecimal quantidade = itemb.getQuantidade();//acha o index do elemento na lista buscando-o e pega o campo quantidade soma a quantida que veio no parametro
					
					if(quantidade.compareTo(itemMovimentacao.getQuantidade())== -1){
						redirectAttributes.addFlashAttribute("statusError", "unsuccess");
						redirectAttributes.addFlashAttribute("message", "A quantidade a remover deve ser menor que a quantidade inserida de produtos.");
						return "redirect:/movimentacao";
					}
					quantidade = quantidade.subtract(itemMovimentacao.getQuantidade());
					
					
					itemb.setQuantidade(quantidade);
					
					
					
					itemb.setTotalProduto((itemb.getQuantidade().multiply(itemMovimentacao.getProduto().getCustoMedio()).setScale(2, RoundingMode.HALF_EVEN)));
					
					itens.set(itens.indexOf(itemb),	itemb);
					
					if(quantidade.compareTo(new BigDecimal("0.00"))== 0){
						
						itens.remove(itens.indexOf(itemb));
					}
					BigDecimal valorTotal = new BigDecimal("0.00");
					for (ItensMovimentacaoEntity itensMovimentacaoEntity : itens) {
						valorTotal = valorTotal.add(itensMovimentacaoEntity.getTotalProduto());
					}
					redirectAttributes.addFlashAttribute("vtotal",valorTotal);
					//itens.add(itemb);
				 }
				 
			} 
			
			if(!contain){
				
				itens.add(item);	
				
				BigDecimal valorTotal = new BigDecimal("0.00");
				for (ItensMovimentacaoEntity itensMovimentacaoEntity : itens) {
					valorTotal = valorTotal.add(itensMovimentacaoEntity.getTotalProduto());
				}
				redirectAttributes.addFlashAttribute("vtotal",valorTotal);
		}
			
			redirectAttributes.addFlashAttribute("itensMovimentacao",itens);
			
		

		return "redirect:/movimentacao";
	}
	
	
	
	@RequestMapping(value = { "/movimentacao/save" }, method = RequestMethod.POST)
	public String saveMovimentacao(@ModelAttribute("movimentacaoEntity") @Validated MovimentacaoEntity movimentacaoEntity, Errors errors,
			final RedirectAttributes redirectAttributes) {

		if (errorServiceInterface.addErrorsView(redirectAttributes, errors)) {
			
			
			return "redirect:/movimentacao";
		}
		if (itens.isEmpty()) {
			redirectAttributes.addFlashAttribute("statusError", "unsuccess");
			redirectAttributes.addFlashAttribute("message", "A movimentação deve ter pelo menos um produto.");
			return "redirect:/movimentacao";
		}else{
			
			movimentacaoEntity.setDataMovimentacao(new Date());
			
			MovimentacaoEntity movimentacaoSave = this.movimentacaoService.save(movimentacaoEntity);
			
			if(movimentacaoSave == null){
				redirectAttributes.addFlashAttribute("statusError", "unsuccess");
				redirectAttributes.addFlashAttribute("message", "Movimentacao não salva.");
			}
			for (ItensMovimentacaoEntity itensMovimentacaoEntity : itens) {
				itensMovimentacaoEntity.setMovimentacao(movimentacaoSave);
				if(itensMovimentacaoService.save(itensMovimentacaoEntity) == null){
					redirectAttributes.addFlashAttribute("statusError", "unsuccess");
					redirectAttributes.addFlashAttribute("message", "Movimentacao não salva. Problema ao adicionar item.");
					movimentacaoService.delete(movimentacaoSave.getId());
				}
			}
		}
		
		
		if (movimentacaoService.save(movimentacaoEntity) != null) {
			redirectAttributes.addFlashAttribute("statusError", "success");
			redirectAttributes.addFlashAttribute("message", "Movimentacao salvo com sucesso");
		} else {
			redirectAttributes.addFlashAttribute("statusError", "unsuccess");
			redirectAttributes.addFlashAttribute("message", "Movimentacao não salvo.");
		}
		
		itens.clear();
		return "redirect:/movimentacao";
	}

	@RequestMapping(value = "/movimentacao/{operation}/{movimentacaoId}", method = RequestMethod.GET)
	public String editRemoveMovimentacao(@PathVariable("operation") String operation,
			@PathVariable("movimentacaoId") Long movimentacaoId, final RedirectAttributes redirectAttributes,
			Model model) {
		if (operation.equals("delete")) {
			try {
				if (movimentacaoService.delete(movimentacaoId)) {
					redirectAttributes.addFlashAttribute("statusError", "success");
					redirectAttributes.addFlashAttribute("message", "Movimentacao deletado com sucesso");
				} else {
					redirectAttributes.addFlashAttribute("statusError", "unsuccess");
					redirectAttributes.addFlashAttribute("message", "Movimentacao não deletado.");
				}
			} catch (DataIntegrityViolationException e1) {
				redirectAttributes.addFlashAttribute("statusError", "unsuccess");
				redirectAttributes.addFlashAttribute("message", "Não foi possivel deletar "
						+ "o movimentacao pois existem outras tabelas utilizando este movimentacao.");
			}

		} else if (operation.equals("edit")) {
			MovimentacaoEntity editMovimentacao = movimentacaoService.find(movimentacaoId);
			if (editMovimentacao != null) {
				redirectAttributes.addFlashAttribute("movimentacaos",
						(List<MovimentacaoEntity>) movimentacaoService.getAll());
				redirectAttributes.addFlashAttribute("movimentacao", editMovimentacao);
				return "redirect:/movimentacao";
			} else {
				redirectAttributes.addFlashAttribute("statusError", "unsuccess");
				redirectAttributes.addFlashAttribute("message", "Não encontrado");
			}
		}

		return "redirect:/pesqmovimentacao";
	}

	@RequestMapping(value = "/movimentacao/update", method = RequestMethod.POST)
	public String updateMovimentacao(@ModelAttribute("editMovimentacao") MovimentacaoEntity editMovimentacao,
			final RedirectAttributes redirectAttributes) {
		if (movimentacaoService.edit(editMovimentacao) != null) {
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
		return "redirect:/movimentacao";
	}
	
	@ModelAttribute
	public List<TipoMovimentacao> tiposMovimentacao() {
		return Arrays.asList(TipoMovimentacao.values());
	}

}

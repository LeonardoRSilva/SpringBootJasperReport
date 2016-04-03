package br.fpu.guilherme.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

@Controller
public class JasperController {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ApplicationContext appContext;

	@RequestMapping("/movimentacoes/relatorio")
	public ModelAndView generatePdfReport() {
		final JasperReportsPdfView view = new JasperReportsPdfView();
		view.setUrl("classpath:Relatorio_movimentacoes.jrxml");
		view.setJdbcDataSource(dataSource);
		view.setApplicationContext(appContext);
		Map<String, Object> params = new HashMap<>();
		return new ModelAndView(view, params);
	}
	
	@RequestMapping("/produto/relatorio")
	public ModelAndView generatePdfReportProduto() {
		final JasperReportsPdfView view = new JasperReportsPdfView();
		view.setUrl("classpath:relatorio_produtos.jrxml");
		view.setJdbcDataSource(dataSource);
		view.setApplicationContext(appContext);
		Map<String, Object> params = new HashMap<>();
		return new ModelAndView(view, params);
	}

	@RequestMapping("/itensmovimentacao/relatorio")
	public ModelAndView generatePdfReport2() {
		String url = "classpath:Relatorio_detalhe_movimentações.jrxml";
		String chave = "movimentacao_id_itens";
		Long key = 7L;
		return generatePdf(url, chave, key);
	}
	@RequestMapping(value = "/movimentacao/relatorio/{id}", method = RequestMethod.GET)
	public ModelAndView generatePdfReportMovimentacao(@PathVariable("id") Long id) {
		String url = "classpath:Relatorio_detalhe_movimentacoes.jrxml";
		String chave = "movimentacao_id_itens";
		Long key = id;
		return generatePdf(url, chave, key);
	}
	public ModelAndView generatePdf(String url, String chave, Long key) {
		final JasperReportsPdfView view = new JasperReportsPdfView();
		view.setUrl(url);
		view.setJdbcDataSource(dataSource);
		view.setApplicationContext(appContext);

		Map<String, Object> params = new HashMap<>();
		params.put(chave, key);
		return new ModelAndView(view, params);
	}

}

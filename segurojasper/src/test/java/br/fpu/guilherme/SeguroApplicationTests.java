package br.fpu.guilherme;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.fpu.guilherme.entities.ProdutoEntity;
import br.fpu.guilherme.service.ProdutoService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SeguroApplication.class)
@WebAppConfiguration
public class SeguroApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeguroApplicationTests.class);
	@Autowired
	public ProdutoService produtoService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void findAllTest() {
		List<ProdutoEntity> produtos = this.produtoService.getAll();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Test FindAll(): " + produtos);
		}
	}
	
	@Test
	public void findProdutoByNomeTest() {
		List<ProdutoEntity> produtos = this.produtoService.findProdutoByNome("a");

		
			LOGGER.info("Test FindName(): " + produtos);
		
	}
	
	@Test
	public void findprodutoBySku() {
		List<ProdutoEntity> produtos = this.produtoService.findProdutoBySkuLike("1459");

		
			LOGGER.info("Test findprodutoBySku(): " + produtos);
		
	}



}

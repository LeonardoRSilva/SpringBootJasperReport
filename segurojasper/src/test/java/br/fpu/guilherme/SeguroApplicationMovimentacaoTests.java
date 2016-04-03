package br.fpu.guilherme;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.fpu.guilherme.entities.MovimentacaoEntity;
import br.fpu.guilherme.service.MovimentacaoService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SeguroApplication.class)
@WebAppConfiguration
public class SeguroApplicationMovimentacaoTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeguroApplicationMovimentacaoTests.class);
	@Autowired
	public MovimentacaoService movimentacaoService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	@Transactional
	public void findAllTest() {
		List<MovimentacaoEntity> movimentacoes = this.movimentacaoService.getAll();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Test FindAll(): " + movimentacoes);
		}
	}
	
	@Test
	@Transactional
	public void findDataMovimentacaoLikeTest() {
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 01, 05);
		
		Date data = cal.getTime();
		List<MovimentacaoEntity> movimentacoes = this.movimentacaoService.findMovimentacaoBetween(data, new Date());

			LOGGER.info("data: " + data);
			LOGGER.info("Test findDataMovimentacaoLikeTest(): " + movimentacoes);
		
	}
	
	@Test
	@Transactional
	public void findMovimentacaoTipoLikeTest() {
		List<MovimentacaoEntity> movimentacoes = this.movimentacaoService.findMovimentacaoTipoLike("Entrada");

		
			LOGGER.info("Test findMovimentacaoTipoLikeTest(): " + movimentacoes);
		
	}



}

package br.com.unicuritiba.lojaonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import br.com.unicuritiba.lojaonline.repositories.produtoRepository;

@Controller
public class indexController {
	@Autowired
	private produtoRepository produtoRepository;
	
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/administrativo/index");
		mv.addObject("listaProduto", produtoRepository.findAll());
		return mv;
	}
}

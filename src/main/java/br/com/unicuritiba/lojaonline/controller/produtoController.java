package br.com.unicuritiba.lojaonline.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.unicuritiba.lojaonline.models.produto;
import br.com.unicuritiba.lojaonline.repositories.produtoRepository;

@Controller
public class produtoController {
	@Autowired
	private produtoRepository produtoRepositories;
	
	
	@GetMapping("/produtos")
	public ModelAndView cadastrarProduto(produto produto) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
		mv.addObject("produto",produto);	
		return mv;
	}
	
	@GetMapping("/produtos/lista")
	public ModelAndView acessarProduto() {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		mv.addObject("listaProduto", produtoRepositories.findAll());
		return mv;
	}
	
	
	@GetMapping("/produtos/lista/editar/{id}")
	public ModelAndView editarProduto(@PathVariable("id")Long id) {
		Optional<produto> produto = produtoRepositories.findById(id);
		return cadastrarProduto(produto.get());
	}
	
	@GetMapping("/produtos/lista/remover/{id}")
	public ModelAndView removerProduto(@PathVariable("id")Long id) {
		Optional<produto> produto = produtoRepositories.findById(id);
		produtoRepositories.delete(produto.get());
		return acessarProduto();
	
	}
	
	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView salvar(produto produtos, BindingResult result) {
		if(result.hasErrors()) {
			return cadastrarProduto(produtos);
		}
		produtoRepositories.saveAndFlush(produtos);
		return cadastrarProduto(new produto());
		
		
	}
}

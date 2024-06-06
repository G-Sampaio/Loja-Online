package br.com.unicuritiba.lojaonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.unicuritiba.lojaonline.models.produto;
import br.com.unicuritiba.lojaonline.repositories.produtoRepository;
import org.springframework.ui.Model;


@Controller
public class compraController {
	@Autowired
	private produtoService produtoService;
	
    @GetMapping("/compra/{id}")
    public String getProdutoById(@PathVariable("id") Long id, Model model) {
        produto Produto = produtoService.findById(id);
        if(Produto == null) {
        	return "administrativo/index";
        }
        model.addAttribute("produto", Produto);
        return "administrativo/compra";
    }
}
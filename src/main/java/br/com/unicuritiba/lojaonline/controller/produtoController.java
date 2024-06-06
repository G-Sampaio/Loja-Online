package br.com.unicuritiba.lojaonline.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import br.com.unicuritiba.lojaonline.models.produto;
import br.com.unicuritiba.lojaonline.repositories.produtoRepository;
import ch.qos.logback.core.model.Model;
import jakarta.persistence.criteria.Path;

@Controller
@ControllerAdvice
public class produtoController {
	
	private static String caminhoimagens = "C:\\Imagens\\";
	@ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(NoHandlerFoundException ex) {
        return new ModelAndView("redirect:/");
    }
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
	
	@GetMapping("/produtos/lista/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagemProduto(@PathVariable("imagem")String imagem) throws IOException {
		File imagemArquivo = new File(caminhoimagens+imagem);
		if(imagem!=null || imagem.trim().length()>0) {

			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}
	
	
	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView salvar(produto produtos, BindingResult result, 
			@RequestParam("file") MultipartFile arquivo){
		
		
		if(result.hasErrors()) {
			return cadastrarProduto(produtos);
		}
		produtoRepositories.saveAndFlush(produtos);
		
		try {
			if(!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				java.nio.file.Path caminho =  Paths.get(caminhoimagens+String.valueOf(produtos.getId())+arquivo.getOriginalFilename());
				Files.write(caminho, bytes);
				
				produtos.setNomeImagem(String.valueOf(produtos.getId())+arquivo.getOriginalFilename());
				produtoRepositories.saveAndFlush(produtos);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return cadastrarProduto(new produto());

	}

	@GetMapping("/compra")
	public String acessarControllerCompra() {
		return"administrativo/compra";
	}
}

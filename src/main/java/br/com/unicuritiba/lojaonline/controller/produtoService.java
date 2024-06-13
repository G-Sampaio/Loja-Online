package br.com.unicuritiba.lojaonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unicuritiba.lojaonline.models.produto;
import br.com.unicuritiba.lojaonline.repositories.produtoRepository;

@Service
public class produtoService {
	@Autowired
    private produtoRepository produtoRepository;

    public List<produto> findAll() {
        return produtoRepository.findAll();
    }

    public produto findById(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }
}

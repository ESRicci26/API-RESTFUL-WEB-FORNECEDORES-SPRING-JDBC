package com.javaricci.ApiRestFulWeb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.javaricci.ApiRestFulWeb.Entity.Fornecedor;
import com.javaricci.ApiRestFulWeb.Service.FornecedorService;


@Controller
@RequestMapping("/fornecedores")
public class WebFornecedorController {

    @Autowired
    private FornecedorService service;

    @GetMapping
    public String listarTodos(Model model) {
        model.addAttribute("fornecedores", service.listarTodos());
        return "fornecedores/listar";
    }

    @GetMapping("/novo")
    public String novoFornecedor(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedores/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Fornecedor fornecedor) {
        service.salvar(fornecedor);
        return "redirect:/fornecedores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("fornecedor", service.buscarPorId(id).orElse(new Fornecedor()));
        return "fornecedores/formulario";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/fornecedores";
    }
}
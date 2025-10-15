package com.javaricci.ApiRestFulWeb.Repository;

import com.javaricci.ApiRestFulWeb.Entity.Fornecedor;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository {
    List<Fornecedor> findAll();
    Optional<Fornecedor> findById(Long id);
    Fornecedor save(Fornecedor fornecedor); // insert ou update
    void deleteById(Long id);
}
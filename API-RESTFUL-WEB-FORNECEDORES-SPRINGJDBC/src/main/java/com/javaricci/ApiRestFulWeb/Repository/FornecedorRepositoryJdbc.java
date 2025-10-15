package com.javaricci.ApiRestFulWeb.Repository;

import com.javaricci.ApiRestFulWeb.Entity.Fornecedor;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class FornecedorRepositoryJdbc implements FornecedorRepository {

    private final JdbcTemplate jdbcTemplate;

    public FornecedorRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Fornecedor> rowMapper = (rs, rowNum) -> {
        Fornecedor f = new Fornecedor();
        f.setId(rs.getLong("id"));
        f.setNome(rs.getString("RAZAOSOCIALFORNECEDOR"));
        f.setCnpj(rs.getString("CNPJFORNECEDOR"));
        f.setEndereco(rs.getString("ENDERECO"));
        f.setBairro(rs.getString("BAIRRO"));
        f.setMunicipio(rs.getString("MUNICIPIO"));
        f.setCep(rs.getString("CEP"));
        return f;
    };

    @Override
    public List<Fornecedor> findAll() {
    	// select todos
        String sql = "SELECT id, RAZAOSOCIALFORNECEDOR, CNPJFORNECEDOR, ENDERECO, BAIRRO, MUNICIPIO, CEP FROM FORNECEDORES";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Fornecedor> findById(Long id) {
    	// select por Id
        String sql = "SELECT id, RAZAOSOCIALFORNECEDOR, CNPJFORNECEDOR, ENDERECO, BAIRRO, MUNICIPIO, CEP FROM FORNECEDORES WHERE id = ?";
        List<Fornecedor> list = jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public Fornecedor save(Fornecedor fornecedor) {
        if (fornecedor.getId() == null) {
            // insert
            String sql = "INSERT INTO FORNECEDORES (RAZAOSOCIALFORNECEDOR, CNPJFORNECEDOR, ENDERECO, BAIRRO, MUNICIPIO, CEP) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, fornecedor.getNome());
                ps.setString(2, fornecedor.getCnpj());
                ps.setString(3, fornecedor.getEndereco());
                ps.setString(4, fornecedor.getBairro());
                ps.setString(5, fornecedor.getMunicipio());
                ps.setString(6, fornecedor.getCep());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                fornecedor.setId(key.longValue());
            }
            return fornecedor;
        } else {
            // update
            String sql = "UPDATE FORNECEDORES SET RAZAOSOCIALFORNECEDOR = ?, CNPJFORNECEDOR = ?, ENDERECO = ?, BAIRRO = ?, MUNICIPIO = ?, CEP = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    fornecedor.getNome(),
                    fornecedor.getCnpj(),
                    fornecedor.getEndereco(),
                    fornecedor.getBairro(),
                    fornecedor.getMunicipio(),
                    fornecedor.getCep(),
                    fornecedor.getId());
            return fornecedor;
        }
    }

    @Override
    public void deleteById(Long id) {
    	// delete
        String sql = "DELETE FROM FORNECEDORES WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

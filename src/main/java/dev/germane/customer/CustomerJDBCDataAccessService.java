package dev.germane.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id = ?
                """;
        return jdbcTemplate
                .query(sql, customerRowMapper, customerId)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer (name, email, age)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge());
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        var sql = """
                SELECT count(id) 
                FROM customer
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsCustomerWithId(Integer customerId) {
        var sql = """
                SELECT count(id) 
                FROM customer
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, customerId);
        return count != null && count > 0;

    }

    @Override
    public void deleteById(Integer customerId) {
        var sql = """
                DELETE 
                FROM customer
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, customerId);
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer.getName() != null) {
            String sql = "UPDATE customer SET name = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    customer.getName(),
                    customer.getId());
        }

        if (customer.getAge() != null) {
            String sql = "UPDATE customer SET age = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    customer.getAge(),
                    customer.getId());
        }

        if (customer.getEmail() != null) {
            String sql = "UPDATE customer SET email = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    customer.getEmail(),
                    customer.getId());
        }
    }
}

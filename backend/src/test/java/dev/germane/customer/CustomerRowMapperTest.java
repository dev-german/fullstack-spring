package dev.germane.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getInt("age")).thenReturn(20);
        when(resultSet.getString("name")).thenReturn("German");
        when(resultSet.getString("email")).thenReturn("german@gmail.com");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("gender")).thenReturn("MALE");
        when(resultSet.getString("profile_image_id")).thenReturn("profile_image_id1234");


        // When
        Customer actual = customerRowMapper.mapRow(resultSet, 1);

        // Then
        Customer expected = new Customer(
                1L,
                "German",
                "german@gmail.com",
                "password",
                20,
                Gender.MALE,
                "profile_image_id1234");

        assertThat(actual).isEqualTo(expected);
    }
}
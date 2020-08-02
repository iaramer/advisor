package ai.adv.portfoliomanager.repository.mapper;

import ai.adv.portfoliomanager.dto.PortfolioDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;

public class PortfolioMapper implements RowMapper {

  @Override
  public PortfolioDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    return PortfolioDto.builder()
        .uuid(rs.getObject("uuid", UUID.class))
        .name(rs.getString("name"))
        .isModel(rs.getBoolean("is_model"))
        .baseCurrency(rs.getString("base_currency"))
        .build();
  }
}

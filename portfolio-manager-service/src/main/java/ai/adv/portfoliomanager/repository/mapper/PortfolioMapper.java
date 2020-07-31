package ai.adv.portfoliomanager.repository.mapper;

import ai.adv.portfoliomanager.dto.PortfolioDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PortfolioMapper implements RowMapper {

  @Override
  public PortfolioDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    return PortfolioDto.builder()
        .id(rs.getInt("id"))
        .name(rs.getString("name"))
        .build();
  }
}

package ai.adv.financialdata.repository.mapper;

import ai.adv.financialdata.dto.StockPriceDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class StockPriceMapper implements RowMapper {

  @Override
  public StockPriceDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    return StockPriceDto.builder()
        .ticker(rs.getString("ticker"))
        .price(rs.getBigDecimal("price"))
        .decimals(rs.getInt("decimals"))
        .lotSize(rs.getInt("lot_size"))
        .build();
  }
}

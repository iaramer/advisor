package ai.adv.financialdata.repository;

import ai.adv.financialdata.dto.StockPriceDto;
import ai.adv.financialdata.repository.mapper.StockPriceMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StockPriceRepository {

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<StockPriceDto> getStockPricesByTickers(List<String> tickers) {
    String sqlQuery = """
    SELECT ticker, price, decimals, lot_size 
    FROM stock_prices 
    where ticker = 'MSFT' 
    """;
    List <StockPriceDto> std = jdbcTemplate.query(sqlQuery, new StockPriceMapper());
    return Collections.emptyList();
  }

  public List<StockPriceDto> saveStockPrices(List<StockPriceDto> stockPriceDtos) {
    String sqlQuery = """
    INSERT INTO stock_prices(ticker, price, decimals, lot_size)
    VALUES (?,?,?,?)
    """;
    List<Object[]> parameters = new ArrayList<>();
    jdbcTemplate.batchUpdate(sqlQuery, parameters);
    return Collections.emptyList();
  }
}

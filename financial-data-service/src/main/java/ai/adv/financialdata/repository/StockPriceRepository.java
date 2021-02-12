package ai.adv.financialdata.repository;

import ai.adv.financialdata.dto.StockPriceDto;
import ai.adv.financialdata.repository.mapper.StockPriceMapper;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
    String rowQuery = """
    SELECT ticker, exchange, price, decimals, lot_size 
    FROM stock_prices 
    WHERE ticker IN (%s) 
    """;
    String inSql = String.join(",", Collections.nCopies(tickers.size(), "?"));
    String sqlQuery = String.format(rowQuery, inSql);

    return jdbcTemplate.query(sqlQuery, tickers.toArray(), new StockPriceMapper());
  }

  public void saveStockPrices(List<StockPriceDto> stockPriceDtos) {
    String sqlQuery = """
    INSERT INTO stock_prices(ticker, exchange, price, decimals, lot_size)
    VALUES (?,?,?,?,?)
    ON CONFLICT (ticker, exchange) 
    DO 
    UPDATE SET price=?, decimals=?, lot_size=?
    """;
    List<Object[]> parameters = stockPriceDtos.stream()
        .map(this::mapToObjectArray)
        .collect(Collectors.toList());

    jdbcTemplate.batchUpdate(sqlQuery, parameters);
  }

  private Object[] mapToObjectArray(StockPriceDto stockPriceDto) {
    String ticker = stockPriceDto.getTicker();
    String exchange = stockPriceDto.getExchange();
    BigDecimal price = stockPriceDto.getPrice();
    int decimals = stockPriceDto.getDecimals();
    int lotSize = stockPriceDto.getLotSize();

    return new Object[] {ticker, exchange, price, decimals, lotSize, price, decimals, lotSize};
  }
}

package ai.adv.portfoliomanager.repository;

import ai.adv.portfoliomanager.dto.PortfolioDto;
import ai.adv.portfoliomanager.repository.mapper.PortfolioMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PortfolioRepository {

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public void create(PortfolioDto portfolioDto) {

    String sqlQuery = "INSERT INTO portfolios(name) VALUES ('Portfolio name')";
    jdbcTemplate.update(sqlQuery);
  }

  public List<PortfolioDto> getAllPortfolios() {

    String sqlQuery = "SELECT id, name FROM portfolios";
    return jdbcTemplate.query(sqlQuery, new PortfolioMapper());
  }
}

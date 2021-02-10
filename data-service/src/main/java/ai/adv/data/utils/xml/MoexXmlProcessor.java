package ai.adv.data.utils.xml;

import ai.adv.data.dto.StockPriceDto;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MoexXmlProcessor {

  public List<StockPriceDto> process(String xmlBody) {
    List<StockPriceDto> stockPrices = new ArrayList<>();
    StockPriceDto stockPriceDto = null;

    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    try {
      XMLEventReader xmlEventReader = xmlInputFactory
          .createXMLEventReader(new ByteArrayInputStream(xmlBody.getBytes()));
      while (xmlEventReader.hasNext()) {
        XMLEvent xmlEvent = xmlEventReader.nextEvent();
        if (xmlEvent.isStartElement()) {
          stockPriceDto = extractStockPrices(xmlEvent);
        }
        if (xmlEvent.isEndElement()) {
          EndElement endElement = xmlEvent.asEndElement();
          String localPart = endElement.getName().getLocalPart();
          Optional.ofNullable(stockPriceDto)
              .filter(s -> localPart.equals("row"))
              .ifPresent(stockPrices::add);
        }
      }

    } catch (XMLStreamException e) {
      log.error(e.getMessage());
    }
    return stockPrices;
  }

  private StockPriceDto extractStockPrices(XMLEvent xmlEvent) {
    StockPriceDto stockPriceDto = new StockPriceDto();
    StartElement startElement = xmlEvent.asStartElement();
    if (startElement.getName().getLocalPart().equals("row")) {
      Optional.of(new QName("SECID"))
          .map(startElement::getAttributeByName)
          .map(Attribute::getValue)
          .ifPresent(stockPriceDto::setTicker);

      Optional.of(new QName("PREVADMITTEDQUOTE"))
          .map(startElement::getAttributeByName)
          .map(Attribute::getValue)
          .map(BigDecimal::new)
          .ifPresent(stockPriceDto::setPrice);

      Optional.of(new QName("DECIMALS"))
          .map(startElement::getAttributeByName)
          .map(Attribute::getValue)
          .map(Integer::parseInt)
          .ifPresent(stockPriceDto::setDecimals);

      Optional.of(new QName("LOTSIZE"))
          .map(startElement::getAttributeByName)
          .map(Attribute::getValue)
          .map(Integer::parseInt)
          .ifPresent(stockPriceDto::setLotSize);
    }
    return stockPriceDto;
  }
}

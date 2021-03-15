# financial-data-service

Service automatically gets data from open APIs. Sources:

* MOEX

WIP:

* NYSE, NASDAQ
* Financial statements (EDGAR)

## Architecture

Integration with other sources:
There's an API provided to obtain the latest prices.

### Kafka

For other purposes (e.g., financial statements) there's an opportunity for integration with Kafka.

### PostreSQL

All data is stored in a database.

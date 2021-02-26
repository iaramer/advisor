# portfolio-manager-service

Service forms investment portfolio and executes re-balancing based on a scheduler:

* Static asset allocation

WIP:

* Dynamic portfolio allocation
* Management based on modern (Markowitz) portfolio theory
* Sending tasks to broker-service

## Architecture

The service is integrated with financial-data-service for getting prices of assets, financial
statements, etc.

### PostreSQL

Portfolios are stored in a database.

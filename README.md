# Demo automated tests with Geb and Spock

Browsers are configured in `GebConfig.spec`

```sh
# run tests with default browser (chrome)
mvn clean test

# run with other configured browser
mvn clean test -Dgeb.env=firefox

# headless chrome
mvn clean test -Dgeb.env=chromeHeadless
```
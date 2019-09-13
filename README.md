# SpringDataTestingEmbeddedMongo
Simple Spring Data project which shows how you can use an embedded Mongo database to write unit tests.

The main feature of the project is the tests:

```
./gradlew clean test
```

However, the project also includes a static main method in ApplicationMain, which you can use to write code against a local mongo instance.

If you wish to do this, be sure to adjust src/main/resources/application.properties to suit your local Mongo setiup.

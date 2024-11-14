## Test Coverage Analysis
### Records
**Records simply store public final data. This cannot exactly be put to a test.**  
That said, `RunStorage.java` does include two extra methods, which are tested and covered.

### JsonReader
Tests with Coverage return a result of 14/16 methods. This is because **two methods have test versions that do not implement web interactions, which replace the corresponding methods that do.**

### Exclusions
In this testing with coverage, the following packages and files are excluded:

| File                                                       | Reason                           |
|------------------------------------------------------------|----------------------------------|
| `WebApiHandler.java`                                       | Web code should not be in tests. |
| `CLI.java`                                                 | View-layer code.                 | 
| `records` <sub><i>(except for `RunStorage.java`)</i></sub> | Simply stores public final data. |
| `application`                                              | View-layer code.                 |                                           
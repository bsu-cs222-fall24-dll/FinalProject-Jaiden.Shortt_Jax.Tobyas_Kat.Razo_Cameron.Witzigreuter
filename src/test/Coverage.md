## Test Coverage Analysis

For analyzing test coverage,
we utilize a modified "Test All in Project" configuration 
that excludes classes and packages
in order to obtain more accurate coverage values.
Below are the details backing and clarifying the changes to the edited configuration.

### RunsListHandler
Tests with Coverage return a result of 2/5 methods. This is because **three methods have test versions that do not implement web interactions.**

| Untested Method                          | Reason                                          |
|------------------------------------------|-------------------------------------------------|
| `getRunsListWithLeaderboardParameters()` | Untested Method utilizes internet-enabled code. |
| `getRunsListAndWrapInLeaderboard()`      | Untested Method utilizes internet-enabled code. |
| `getRunsList()`                          | Untested Method utilizes internet-enabled code. |

### Exclusions
In this testing with coverage, the following packages and files are excluded:

| File                                            | Type                                  | Reason                                                                                                   |
|-------------------------------------------------|---------------------------------------|----------------------------------------------------------------------------------------------------------|
| `webapihandlers`, except `RunsListHandler.java` | <kbd>Package</kbd>, except <kbd>Class | Internet-enabled code should not be utilized in unit tests, except 2/5 methods are not internet-enabled. |
| `CLI.java`                                      | <kbd>Class                            | View-layer code.                                                                                         | 
| `application`, except `application.log`         | <kbd>Package                          | View-layer code.                                                                                         |                                           
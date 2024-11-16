## Test Coverage Analysis

For analyzing test coverage,
we utilize a modified "Test All in Project" configuration 
that excludes classes and packages
in order to obtain more accurate coverage values.
Below are the details backing and clarifying the changes to the edited configuration.

### JsonReader
Tests with Coverage return a result of 13/16 methods. This is because **three methods have test versions that do not implement web interactions, which replace the corresponding methods that do.**

These test methods are verbatim copies of their untested counterparts, _except for the reason they were created._

| Untested Method       | Corresponding Test Method  | Reason                                          |
|-----------------------|----------------------------|-------------------------------------------------|
| `createLeaderboard()` | `test_createLeaderboard()` | Untested Method utilizes internet-enabled code. |
| `createRunList()`     | `test_createRunList()`     | Untested Method utilizes internet-enabled code. |
| `createRun()`         | `test_createRun()`         | Untested Method utilizes internet-enabled code. |

### Exclusions
In this testing with coverage, the following packages and files are excluded:

| File             | Type         | Reason                                                      |
|------------------|--------------|-------------------------------------------------------------|
| `webapihandlers` | <kbd>Package | Internet-enabled code should not be utilized in unit tests. |
| `CLI.java`       | <kbd>Class   | View-layer code.                                            | 
| `application`    | <kbd>Package | View-layer code.                                            |                                           
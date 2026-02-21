# This is the result of DKatalis Take Home Test.

## ATM

## Additional scenario

* Error message will be displayed when executing command wihout login first or after logout.
* Error message will be displayed when the amount is insufficient for withdrawal.
* Error message will be displayed when transfering amount of money to non exists account.

## How to build and run this application ?

* Pre-requisite:
  - Java 11
  - Maven (version 3 when creating this application)
  - Internet connection to download dependencies from remote repository.

### Build

* Build it by executing maven command (the default goal will be package).

```mvn``` or ```mvn package```

Maven will generate the jar file in ```[PROJECT_DIR]/target/banking-1.0-SNAPSHOT.jar```

### Execute

* Execute it by runing java command with jar option.

```java -jar [PROJECT_DIR]/target/banking-1.0-SNAPSHOT.jar```

* The available command are the same as described in ATM Problem description.
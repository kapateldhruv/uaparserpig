# uaparserpig
This is user agent parser for apache pig.

## Credits
Derived from https://github.com/tobie/ua-parser/tree/master/java

## Compiling
This project uses Maven. Create your own jar by running following command.

```
cd uaparserpig
mvn package
```

## Usage

```
REGISTER /home/dhruv/workspace/uaparserpig/target/uaparserpig-0.0.1-SNAPSHOT.jar;
DEFINE useragentparser kapatel.dhruv.uaparserpig.PiguaparserUDF();

A = LOAD 'uaseragents.txt' AS (agent:chararray);
B = FOREACH A GENERATE useragentparser(agent,'USERAGENT_FAMILY');
```

### FUNC(String useragentString, String attributeName)

###### attributeName
Allows you to extract specific property of useragent string.

+ USERAGENT_FAMILY
+ USERAGENT_MAJOR
+ USERAGENT_MINOR
+ OS_FAMILY
+ OS_MAJOR
+ OS_MINOR
+ DEVICE_FAMILY

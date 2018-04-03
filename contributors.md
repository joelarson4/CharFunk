#CharFunk contributors

If you can help, please do.  The source files of interest are:

+`src/charFunk-1.1.3.js` - the actual library
+`src/java/GenCharFunkData.java` - this uses java.lang.Character methods to build up and output CharFunk data into the marked sections of charFunk-1.1.3.js and charFunk-test-data.js
+`src/java/GenDoco.java` - chews up the comments in charFunk-1.1.3.js and outputs documentation markdown into readme.md
+`tests/charFunk-tests.js` - all the qunit tests for CharFunk

Once you've made any changes, you can build everything using

```
npm install
./build.sh
npm run test
```

Note that build tests very comprehensively across all codepoints (524309 assertions!) so it will take a while.

###Some areas needing help:

+ Is there a better way to generate the doco?  I want something that takes JSDoc type comments and turns them into usable markdown.
+ Is isValidName actually working correctly?  I have circumstantial evidence it isn't quite right... but unsure how to improve.
+ Am I wrappering this for AMD & CommonJS properly?  I copied some patterns I've seen elsewhere... unsure if I got it right.

###Miscellaneous notes:

+ Grunt-contrib-qunit includes v1.4.0 qunit, but we need v1.11.0 for throws() support
+ Make sure you are using *JDK* *1.7* *and* *Java* *7*!
+ Be sure you're on node 0.10 at least

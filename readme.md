#CharFunk - a Unicode character utility for JavaScript

CharFunk provides some of the functionality that Java's Character class does.  Many of these things would be difficult to do in JavaScript without very unweildy RegExps or using [XRegExp](http://xregexp.com/).  For example, it lets you test whether unicode characters in the Basic Multilingual Plane (0000 to FFFF) are letters or digits.

Here are some of the things you can do with CharFunk:

```js
//Is this character a digit?
CharFunk.isDigit('A'); //false
CharFunk.isDigit('1'); //true
CharFunk.isDigit('\u0E54'); //true - that's Thai #4 - http://unicodinator.com/#0E54

//Is this character a mirrored character?
CharFunk.isMirrored('A'); //false
CharFunk.isMirrored('('); //true
CharFunk.isMirrored('\u2039'); //true - that's a Single Left-pointing Angle Quotation Mark - http://unicodinator.com/#2039

//Is this string valid JavaScript Identifier?
CharFunk.isValidName('Apple');          //true
CharFunk.isValidName('تفاحة');          //true - that's the Arabic word for apple
CharFunk.isValidName('Apple Dumpling'); //false
CharFunk.isValidName('function');       //true
CharFunk.isValidName('function',true);  //false - when that second argument is set truthy it means we want to avoid reserved keywords

//Replace all the characters that are not letters or digits or question marks with an underscore
CharFunk.replaceMatches('What will come out?',function(ch) {
  return CharFunk.isLetterOrDigit(ch) || ch=='?';
  },'_'); //will return 'What_will_come_out_'

//OK, you could do that with JavaScript regex easily enough, but you could not do this:
CharFunk.replaceMatches('جمهورية مصر العربية',function(ch) {
    return !CharFunk.isLetterOrDigit(ch);
    },'_'); //will return 'جمهورية_مصر_العربية'

//Find the position of last uppercase letter in the string
CharFunk.lastIndexOf('Новые Известия',CharFunk.isUpperCase); //returns 6
```

All you need is to download [charFunk-1.1.3.min.js](https://raw.github.com/joelarson4/CharFunk/master/charFunk-1.1.3.min.js) -- that's it, no other dependencies.

If you want to use in Node, you can simply

    npm install charfunk

and then

    var CharFunk = require('charfunk');

Interested in contributing?  Check out [contributors.md](https://github.com/joelarson4/CharFunk/blob/master/contributors.md) for some details.

Finally, if you are working on anything involving Unicode, you might also find this helpful: [Unicodinator](http://unicodinator.com).

##API


###CharFunk.getDirectionality(ch)
Used to find the directionality, which is handled a bit differently than the other sets of properties since we need a value back, not just true/false.
Returns one of the following:
+ `UNDEFINED`
+ `L`   for LEFT_TO_RIGHT
+ `R`   for RIGHT_TO_LEFT
+ `AL`  for RIGHT_TO_LEFT_ARABIC
+ `EN`  for EUROPEAN_NUMBER
+ `ES`  for EUROPEAN_NUMBER_SEPARATOR
+ `ET`  for EUROPEAN_NUMBER_TERMINATOR
+ `AN`  for ARABIC_NUMBER
+ `CS`  for COMMON_NUMBER_SEPARATOR
+ `NSM` for NONSPACING_MARK
+ `BN`  for BOUNDARY_NEUTRAL
+ `B`   for PARAGRAPH_SEPARATOR
+ `S`   for SEGMENT_SEPARATOR
+ `WS`  for WHITESPACE
+ `ON`  for OTHER_NEUTRALS
+ `LRE` for LEFT_TO_RIGHT_EMBEDDING
+ `LRO` for LEFT_TO_RIGHT_OVERRIDE
+ `RLE` for RIGHT_TO_LEFT_EMBEDDING
+ `RLO` for RIGHT_TO_LEFT_OVERRIDE
+ `PDF` for POP_DIRECTIONAL_FORMAT



`@param {String} string` - a length 1 string

`@returns {String}` a string representing the directionality, as defined above


###CharFunk.getMatches(string,callback)
Returns an array of contiguous matching strings for which the callback returns true, similar to String.match().
`CharFunk.getMatches("test this out",CharFunk.isLetter); // returns ["test","this","out"]`


`@param {String} string` - a string of any length

`@param {Function} callback` - a function to call for each character, which must return true if a match or false if not a match.  This function will be provided three arguments: a char to check, a number for the position, and a number for the string length

`@returns {Array{String}}` 


###CharFunk.isAllLettersOrDigits(string)
Returns true if the string argument is composed of all letters and digits


`@param {String} string` - a string of any length

`@returns {Boolean}` 


###CharFunk.isDigit(ch)
Returns true if provided a length 1 string that is a digit


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.isLetter(ch)
Returns true if provided a length 1 string that is a letter


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.isLetterNumber(ch)
Returns true if provided a length 1 string that is in the Unicode "Nl" category.
Beware -- this is NOT the same thing as isLetterOrDigit()!


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.isLetterOrDigit(ch)
Returns true if provided a length 1 string that is a letter or a digit


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.isLowerCase(ch)
Returns true if provided a length 1 string that is lowercase


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.isMirrored(ch)
Returns true if provided a length 1 string that is a mirrored character


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.isUpperCase(ch)
Returns true if provided a length 1 string that is uppercase


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.isValidFirstForName(ch)
Returns true if provided a length 1 string that is a valid leading character for a JavaScript identifier


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.isValidMidForName(ch)
Returns true if provided a length 1 string that is a valid non-leading character for a ECMAScript identifier


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.isValidName(string,checkReserved)
Returns true if the string is a valid ECMAScript identifier.
This is a bit more restrictive than browsers tend to be, using the actual rules http://www.ecma-international.org/ecma-262/5.1/


`@param {String} string` - a string of any length

`@param {Boolean} checkReserved` - set to true if you wish to get back false if string is a reserved ECMAScript keyword

`@returns {Boolean}` 


###CharFunk.isWhitespace(ch)
Returns true if provided a length 1 string that is a whitespace character


`@param {String} ch` - a length 1 string

`@returns {Boolean}` 


###CharFunk.indexOf(string,callback)
Returns the first index where the character causes a true return from the callback, or -1 if no match


`@param {String} string` - a string of any length

`@param {Function} callback` - a function to call for each character, which must return true if a match or false if not a match.  This function will be provided three arguments: a char to check, a number for the position, and a number for the string length

`@returns {Number}` 


###CharFunk.lastIndexOf(string,callback)
Returns the last index where the character causes a true return from the callback, or -1 if no match


`@param {String} string` - a string of any length

`@param {Function} callback` - a function to call for each character, which must return true if a match or false if not a match.  This function will be provided three arguments: a char to check, a number for the position, and a number for the string length

`@returns {Number}` 


###CharFunk.matchesAll(string,callback)
Returns true if all characters in the provided string result in a true return from the callback.


`@param {String} string` - a string of any length

`@param {Function} callback` - a function to call for each character, which must return true if a match or false if not a match.  This function will be provided three arguments: a char to check, a number for the position, and a number for the string length

`@returns {Boolean}` 


###CharFunk.replaceMatches(string,callback,ch)
Returns a new string with all matched characters replaced, similar to String.replace().
If the callback returns a string, then that will be used as the replacement.
Otherwise, if a ch argument is provided, then that will be used as a replacement.
If the callback does not return a string and the ch is not provided, then matched characters will simply be removed.


`@param {String} string` - a string of any length

`@param {Function} callback` - a function to call for each character, which must return a string as a replacement value, otherwise a true if a match or false if not a match.  This function will be provided three arguments: a char to check, a number for the position, and a number for the string length

`@param {String} ch` - optional, a length 1 string for replacement

`@returns {String}` a new string


###CharFunk.splitOnMatches(string,callback)
Splits the string on all matches, similar to String.split().
`CharFunk.splitOnMatches("test this out",CharFunk.isWhitespace); // returns ["test","this","out"]`


`@param {String} string` - a string of any length

`@param {Function} callback` - a function to call for each character, which must return true if a match or false if not a match.  This function will be provided three arguments: a char to check, a number for the position, and a number for the string length

`@returns {Boolean}` 

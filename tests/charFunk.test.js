"use strict";
let ss; //starting point for looped tests
const LOOP_INCREMENT = 5;
const TEST_DATA = require("./charFunk-test-data");
const CharFunk = require("../src/charFunk-1.1.3");

test("CharFunk basic tests", function() {
  expect(typeof CharFunk == "object").toBe(true);
  expect(typeof CharFunk._GTYPE == "undefined").toBe(true);
  expect(typeof CharFunk._CASE == "undefined").toBe(true);
  expect(typeof CharFunk._DIRECTIONALITY == "undefined").toBe(true);
  expect(typeof CharFunk._MIRRORED == "undefined").toBe(true);
  expect(typeof CharFunk._WHITESPACE == "undefined").toBe(true);
});

// prettier-ignore
test("CharFunk error test", function() {
  expect(function() { CharFunk.isDigit(); }).toThrow();
  expect(function() { CharFunk.isDigit(1); }).toThrow();
  expect(function() { CharFunk.isDigit({}); }).toThrow();
  expect(function() { CharFunk.isDigit([]); }).toThrow();
  expect(function() { CharFunk.isDigit(null); }).toThrow();
  expect(function() { CharFunk.isDigit("string"); }).toThrow();

  expect(function() { CharFunk.isAllLettersOrDigits(); }).toThrow();
  expect(function() { CharFunk.isAllLettersOrDigits(1); }).toThrow();
  expect(function() { CharFunk.isAllLettersOrDigits({}); }).toThrow();
  expect(function() { CharFunk.isAllLettersOrDigits([]); }).toThrow();
  expect(function() { CharFunk.isAllLettersOrDigits(null); }).toThrow();

  expect(CharFunk.isAllLettersOrDigits("")).toBe(true);
});

test("CharFunk isValidFirstForName", function() {
  expect(CharFunk.isValidFirstForName("A")).toBe(true);
  expect(CharFunk.isValidFirstForName("z")).toBe(true);
  expect(CharFunk.isValidFirstForName("_")).toBe(true);
  expect(CharFunk.isValidFirstForName("$")).toBe(true);
  expect(CharFunk.isValidFirstForName(String.fromCharCode(5870))).toBe(true);

  expect(!CharFunk.isValidFirstForName("1")).toBe(true);
  expect(!CharFunk.isValidFirstForName("?")).toBe(true);
  expect(!CharFunk.isValidFirstForName("\u2000")).toBe(true);
});

test("CharFunk isValidMidForName", function() {
  expect(CharFunk.isValidMidForName("A")).toBe(true);
  expect(CharFunk.isValidMidForName("z")).toBe(true);
  expect(CharFunk.isValidMidForName("_")).toBe(true);
  expect(CharFunk.isValidMidForName("$")).toBe(true);
  expect(CharFunk.isValidMidForName(String.fromCharCode(5870))).toBe(true);
  expect(CharFunk.isValidMidForName("1")).toBe(true);

  expect(!CharFunk.isValidMidForName("?")).toBe(true);
  expect(!CharFunk.isValidMidForName("\u2000")).toBe(true);
});

test("CharFunk isValidName", function() {
  let ii, pi, ch, vld, nam, obj;
  expect(CharFunk.isValidName("Apple")).toBe(true);
  expect(CharFunk.isValidName("banana_cherry")).toBe(true);
  expect(CharFunk.isValidName("date1")).toBe(true);
  expect(!CharFunk.isValidName("2elderberry")).toBe(true);
  expect(!CharFunk.isValidName("fig grapefruit")).toBe(true);

  expect(CharFunk.isValidName("function")).toBe(true);
  expect(!CharFunk.isValidName("function, example")).toBe(true);

  //from http://mathiasbynens.be/notes/javascript-identifiers
  expect(CharFunk.isValidName("\u006C\u006F\u006C\u0077\u0061\u0074")).toBe(true);
  expect(CharFunk.isValidName("foo\u200Cbar")).toBe(true);
  expect(CharFunk.isValidName("λ")).toBe(true);
  expect(CharFunk.isValidName("π")).toBe(true);
  expect(CharFunk.isValidName("ಠ_ಠ")).toBe(true);
  expect(CharFunk.isValidName("ლ_ಠ益ಠ_ლ")).toBe(true);
  expect(CharFunk.isValidName("〱〱")).toBe(true);
  expect(CharFunk.isValidName("Ꙭൽↈⴱ")).toBe(true);
});

test("CharFunk indexOf", function() {
  expect(CharFunk.indexOf("This is 1 test", CharFunk.isWhitespace) == 4).toBe(true);
  expect(CharFunk.indexOf("This\u1680is 1 test", CharFunk.isWhitespace) == 4).toBe(true);
  expect(CharFunk.indexOf("Thisis1test", CharFunk.isWhitespace) == -1).toBe(true);
});

test("CharFunk lastIndexOf", function() {
  expect(CharFunk.lastIndexOf("This is 1 test", CharFunk.isWhitespace) == 9).toBe(true);
  expect(CharFunk.lastIndexOf("a b c d\u1680e", CharFunk.isWhitespace) == 7).toBe(true);
  expect(CharFunk.lastIndexOf("Thisis1test", CharFunk.isWhitespace) == -1).toBe(true);
});

test("CharFunk matchesAll", function() {
  var check1 = function(ch) {
      return CharFunk.isWhitespace(ch) || CharFunk.isLetterOrDigit(ch);
    },
    check2 = function(ch) {
      return CharFunk.isMirrored(ch) || CharFunk.isLetterNumber(ch);
    },
    check3 = function(ch, idx, len) {
      if (idx === 0 || idx == len - 1) return true; //accept anything for first and last position
      return CharFunk.isWhitespace(ch) || CharFunk.isLetterOrDigit(ch);
    };
  expect(CharFunk.matchesAll("This is 1 test", check1)).toBe(true);
  expect(!CharFunk.matchesAll("This is 1 test.", check1)).toBe(true);
  expect(!CharFunk.matchesAll("This is 1 test", check2)).toBe(true);
  expect(CharFunk.matchesAll("!This is 1 test!", check3)).toBe(true);
});

test("CharFunk replaceMatches", function() {
  var replace1 = function(ch) {
      return !CharFunk.isLetterOrDigit(ch);
    },
    replace2 = function(ch) {
      return !CharFunk.isLetterOrDigit(ch);
    },
    replace3 = function(ch, idx, len) {
      return "[" + idx + " " + len + "]";
    };
  expect(CharFunk.replaceMatches("This is 1 test!", replace1) == "Thisis1test").toBe(true);
  expect(CharFunk.replaceMatches("This is 1 test!", replace2, "_") == "This_is_1_test_").toBe(true);
  expect(CharFunk.replaceMatches("Test", replace3) == "[0 4][1 4][2 4][3 4]").toBe(true);
});

test("CharFunk getMatches", function() {
  expect(CharFunk.getMatches("This is a sentence", CharFunk.isLetterOrDigit).join(":") == "This:is:a:sentence").toBe(
    true
  );
  expect(CharFunk.getMatches("هذا اختبار", CharFunk.isLetterOrDigit).join(":") == "هذا:اختبار").toBe(true);
  expect(CharFunk.getMatches("This is a sentence", CharFunk.isMirrored).join(":") === "").toBe(true);
  expect(CharFunk.getMatches("Encyclopedia", CharFunk.isLetterOrDigit).join(":") == "Encyclopedia").toBe(true);
});

test("CharFunk splitOnMatches", function() {
  expect(CharFunk.splitOnMatches("This is a sentence", CharFunk.isWhitespace).join(":") == "This:is:a:sentence").toBe(
    true
  );
  expect(CharFunk.splitOnMatches("هذا اختبار", CharFunk.isWhitespace).join(":") == "هذا:اختبار").toBe(true);
  expect(CharFunk.splitOnMatches("Encyclopedia", CharFunk.isWhitespace).join(":") == "Encyclopedia").toBe(true);
});

//Before I was running each inner loop from ii=0 to essentially TEST_DATA.~.length, which is ii=65535.
//This breaks up the tests into 10 seperate batches which only test 1/5th of the full data range.
//Not only does this avoid the timeout, but will catch general problems more quickly (due to failing on the first cycle).

for (ss = 0; ss < LOOP_INCREMENT; ss++) {
  test("CharFunk isDigit #" + (1 + ss), function() {
    var ii, ch;
    for (ii = ss; ii < TEST_DATA.GTYPE.length; ii += LOOP_INCREMENT) {
      ch = String.fromCharCode(ii);
      if (TEST_DATA.GTYPE[ii] == 2) {
        expect(CharFunk.isDigit(ch)).toBe(true);
      } else {
        expect(!CharFunk.isDigit(ch)).toBe(true);
      }
    }
  });

  test("CharFunk isLetter #" + (1 + ss), function() {
    var ii, ch;
    for (ii = ss; ii < TEST_DATA.GTYPE.length; ii += LOOP_INCREMENT) {
      ch = String.fromCharCode(ii);
      if (TEST_DATA.GTYPE[ii] == 1) {
        expect(CharFunk.isLetter(ch)).toBe(true);
      } else {
        expect(!CharFunk.isLetter(ch)).toBe(true);
      }
    }
  });

  test("CharFunk isLetterOrDigit #" + (1 + ss), function() {
    var ii, ch;
    for (ii = ss; ii < TEST_DATA.GTYPE.length; ii += LOOP_INCREMENT) {
      ch = String.fromCharCode(ii);
      if (TEST_DATA.GTYPE[ii] > 2) {
        expect(!CharFunk.isLetterOrDigit(ch)).toBe(true);
      } else {
        expect(CharFunk.isLetterOrDigit(ch)).toBe(true);
      }
    }
  });
  test("CharFunk isLetterNumber #" + (1 + ss), function() {
    var ii, ch;
    for (ii = ss; ii < TEST_DATA.GTYPE.length; ii += LOOP_INCREMENT) {
      ch = String.fromCharCode(ii);
      if (TEST_DATA.GTYPE[ii] == 3) {
        expect(CharFunk.isLetterNumber(ch)).toBe(true);
      } else {
        expect(!CharFunk.isLetterNumber(ch)).toBe(true);
      }
    }
  });

  test("CharFunk isLowerCase #" + (1 + ss), function() {
    var ii, ch;
    for (ii = ss; ii < TEST_DATA.CASE.length; ii += LOOP_INCREMENT) {
      ch = String.fromCharCode(ii);
      if (TEST_DATA.CASE[ii] == 2) {
        expect(CharFunk.isLowerCase(ch)).toBe(true);
      } else {
        expect(!CharFunk.isLowerCase(ch)).toBe(true);
      }
    }
  });

  test("CharFunk isMirrored #" + (1 + ss), function() {
    var ii, ch;
    for (ii = ss; ii < TEST_DATA.MIRRORED.length; ii += LOOP_INCREMENT) {
      ch = String.fromCharCode(ii);
      if (TEST_DATA.MIRRORED[ii] == 1) {
        expect(CharFunk.isMirrored(ch)).toBe(true);
      } else {
        expect(!CharFunk.isMirrored(ch)).toBe(true);
      }
    }
  });

  test("CharFunk isUpperCase #" + (1 + ss), function() {
    var ii, ch;
    for (ii = ss; ii < TEST_DATA.CASE.length; ii += LOOP_INCREMENT) {
      ch = String.fromCharCode(ii);
      if (TEST_DATA.CASE[ii] == 1) {
        expect(CharFunk.isUpperCase(ch)).toBe(true);
      } else {
        expect(!CharFunk.isUpperCase(ch)).toBe(true);
      }
    }
  });

  test("CharFunk isWhitespace #" + (1 + ss), function() {
    var ii, ch;
    for (ii = ss; ii < TEST_DATA.WHITESPACE.length; ii += LOOP_INCREMENT) {
      ch = String.fromCharCode(ii);
      if (TEST_DATA.WHITESPACE[ii] == 1) {
        expect(CharFunk.isWhitespace(ch)).toBe(true);
      } else {
        expect(!CharFunk.isWhitespace(ch)).toBe(true);
      }
    }
  });
}

//The purpose of this class is to generate the constants needed in CharFunk.
//Currently these are just cut and pasted into the source, regeneration is likely unneeded so
//It is not in a package because that would be overkill.

import java.util.*;
import java.io.*;

public class GenCharFunkData {

    public static PrintWriter out;

    public static void main(String[] args) {
        try {
            System.out.println("Updating data...");
            String srcfilnam="../charFunk-1.1.3.js";
            String[] filcon=getFileContents(srcfilnam);
            out=new PrintWriter(new File(srcfilnam));
            out.println(filcon[0]);
            printDataGeneralTypes();
            printDataCase();
            printDataDirectionality();
            printDataMirrored();
            printDataNameMid();
            printDataWhitespace();
            out.println(filcon[1]);
            out.flush();
            out.close();

            System.out.println("Updating test data...");
            out=new PrintWriter(new File("../../tests/charFunk-test-data.js"));
            out.println("module.exports = {");
            printTestsGeneralTypes();
            printTestsCase();
            printTestsDirectionality();
            printTestsMirrored();
            printTestsNameMid();
            printTestsWhitespace();
            out.println("};");
            out.flush();
            out.close();
        }
        catch(Exception exc) {
            exc.printStackTrace();
        }
        System.out.println("Data updates complete");
    }

    public static String[] getFileContents(String filpth) throws Exception {
        FileReader filred=new FileReader(filpth);
        BufferedReader bufred=new BufferedReader(filred);
        StringBuffer bgnbuf=new StringBuffer(); //for the beginning of the file before the data
        StringBuffer endbuf=new StringBuffer(); //end of the file after the data
        String redstr;
        int pos=1; //1 means before data, 2 means within data, 3 means after
        while((redstr=bufred.readLine()) != null) {
            if(redstr.indexOf("//!end data")>-1) pos=3;
            if(pos==1) {
                if(bgnbuf.length()>0) bgnbuf.append('\n');
                bgnbuf.append(redstr);
            }
            if(pos==3) {
                if(endbuf.length()>0) endbuf.append('\n');
                endbuf.append(redstr);
            }
            if(redstr.indexOf("//!start data")>-1) pos=2;
        }
        bufred.close();
        filred.close();
        return new String[] { bgnbuf.toString(), endbuf.toString() };
    }

    //general type: letter, digit or other
    public static void printDataGeneralTypes() {
        out.println("        _GTYPE={");
        (new PropTable(out,"LETTER") { boolean check(char ch) { return Character.isLetter(ch); }}).print(true);
        (new PropTable(out,"DIGIT")  { boolean check(char ch) { return Character.isDigit(ch); }}).print(true);
        (new PropTable(out,"LETTER_NUMBER")  { boolean check(char ch) { return Character.getType(ch)==Character.LETTER_NUMBER; }}).print(true);
        (new PropTable(out,"OTHER")  { boolean check(char ch) { return !Character.isLetter(ch) && !Character.isDigit(ch) && Character.getType(ch)!=Character.LETTER_NUMBER; }}).print(false);
        out.println("        },");
    }

    public static void printTestsGeneralTypes() {
        (new FullTable(out,"GTYPE")  {
            int check(char ch) {
                if(Character.isLetter(ch)) { return 1; }
                else if(Character.isDigit(ch)) { return 2; }
                else if(Character.getType(ch)==Character.LETTER_NUMBER) { return 3; }
                else { return 4; }
            }
        }).print(true);
    }

    //upper vs lower
    public static void printDataCase() {
        out.println("        _CASE={");
        (new PropTable(out,"UPPER") { boolean check(char ch) { return Character.isUpperCase(ch); }}).print(true);
        (new PropTable(out,"LOWER") { boolean check(char ch) { return Character.isLowerCase(ch); }}).print(true);
        (new PropTable(out,"OTHER") { boolean check(char ch) { return !Character.isUpperCase(ch) && !Character.isLowerCase(ch); }}).print(false);
        out.println("        },");
    }

    public static void printTestsCase() {
        (new FullTable(out,"CASE")  {
            int check(char ch) {
                if(Character.isUpperCase(ch)) { return 1; }
                else if(Character.isLowerCase(ch)) { return 2; }
                else { return 3; }
            }
        }).print(true);
    }

    //directionality
    public static void printDataDirectionality() {
        out.println("        _DIRECTIONALITY={");
        (new PropTable(out,"UNDEFINED") { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_UNDEFINED; } }).print(true);
        (new PropTable(out,"L"   ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_LEFT_TO_RIGHT; } }).print(true);
        (new PropTable(out,"R"   ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_RIGHT_TO_LEFT; } }).print(true);
        (new PropTable(out,"AL"  ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC; } }).print(true);
        (new PropTable(out,"EN"  ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_EUROPEAN_NUMBER; } }).print(true);
        (new PropTable(out,"ES"  ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR; } }).print(true);
        (new PropTable(out,"ET"  ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR; } }).print(true);
        (new PropTable(out,"AN"  ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_ARABIC_NUMBER; } }).print(true);
        (new PropTable(out,"CS"  ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR; } }).print(true);
        (new PropTable(out,"NSM" ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_NONSPACING_MARK; } }).print(true);
        (new PropTable(out,"BN"  ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_BOUNDARY_NEUTRAL; } }).print(true);
        (new PropTable(out,"B"   ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR; } }).print(true);
        (new PropTable(out,"S"   ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_SEGMENT_SEPARATOR; } }).print(true);
        (new PropTable(out,"WS"  ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_WHITESPACE; } }).print(true);
        (new PropTable(out,"ON"  ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_OTHER_NEUTRALS; } }).print(true);
        (new PropTable(out,"LRE" ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING; } }).print(true);
        (new PropTable(out,"LRO" ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE; } }).print(true);
        (new PropTable(out,"RLE" ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING; } }).print(true);
        (new PropTable(out,"RLO" ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE; } }).print(true);
        (new PropTable(out,"PDF" ) { boolean check(char ch) { return Character.getDirectionality(ch)==Character.DIRECTIONALITY_POP_DIRECTIONAL_FORMAT; } }).print(false);
        out.println("        },");
    }

    public static void printTestsDirectionality() {
        (new FullTable(out,"DIRECTIONALITY")  {
            int check(char ch) {
                return Character.getDirectionality(ch);
            }
        }).print(true);
    }

    //mirrored
    public static void printDataMirrored() {
        out.println("        _MIRRORED={");
        (new PropTable(out,"YES") { boolean check(char ch) { return Character.isMirrored(ch); }}).print(true);
        (new PropTable(out,"NO")  { boolean check(char ch) { return !Character.isMirrored(ch); }}).print(false);
        out.println("        },");
    }

    public static void printTestsMirrored() {
        (new FullTable(out,"MIRRORED")  {
            int check(char ch) {
                if(Character.isMirrored(ch)) { return 1; }
                else { return 2; }
            }
        }).print(true);
    }

    //allowed in the middle of a JavaScript variable name
    public static void printDataNameMid() {
        out.println("        _NAMEMID={");
        (new PropTable(out,"YES") { boolean check(char ch) { return GenCharFunkData.checkNameMid(ch); } }).print(true);
        (new PropTable(out,"NO")  { boolean check(char ch) { return !GenCharFunkData.checkNameMid(ch); } }).print(false);
        out.println("        },");
    }

    public static void printTestsNameMid() {
        (new FullTable(out,"NAMEMID")  {
            int check(char ch) {
                if(GenCharFunkData.checkNameMid(ch)) { return 1; }
                else { return 2; }
            }
        }).print(true);
    }

    public static boolean checkNameMid(char ch) {
        //From the excellent http://mathiasbynens.be/notes/javascript-identifiers:
        //  An identifier must start with $, _, or any character in the Unicode categories
        //  “Uppercase letter (Lu)”, “Lowercase letter (Ll)”, “Titlecase letter (Lt)”,
        //  “Modifier letter (Lm)”, “Other letter (Lo)”, or “Letter number (Nl)”.
        //  The rest of the string can contain the same characters, plus any U+200C zero
        //  width non-joiner characters, U+200D zero width joiner characters, and characters
        //  in the Unicode categories “Non-spacing mark (Mn)”, “Spacing combining mark (Mc)”,
        //  “Decimal digit number (Nd)”, or “Connector punctuation (Pc)”.

        return ch=='$' || ch=='_'
            || Character.isLetterOrDigit(ch)
            || ch==0x200C
            || ch==0x200D
            || Character.getType(ch)==Character.LETTER_NUMBER
            || Character.getType(ch)==Character.NON_SPACING_MARK
            || Character.getType(ch)==Character.COMBINING_SPACING_MARK
            || Character.getType(ch)==Character.DECIMAL_DIGIT_NUMBER
            || Character.getType(ch)==Character.CONNECTOR_PUNCTUATION
            ;
    }

    //whitespace
    public static void printDataWhitespace() {
        out.println("        _WHITESPACE={");
        (new PropTable(out,"YES") { boolean check(char ch) { return Character.isWhitespace(ch); }}).print(true);
        (new PropTable(out,"NO")  { boolean check(char ch) { return !Character.isWhitespace(ch); }}).print(false);
        out.println("        },");
    }

    public static void printTestsWhitespace() {
        (new FullTable(out,"WHITESPACE")  {
            int check(char ch) {
                if(Character.isWhitespace(ch)) { return 1; }
                else { return 2; }
            }
        }).print(false);
    }

}

//keeps track of a particular property being true/false
class PropTable {

    boolean lastOn=false; //true if the previous item was the type we care about
    int lastOnCi=0; //position of the last true item
    StringBuffer table=new StringBuffer();
    String name;
    PrintWriter out;

    PropTable(PrintWriter out, String nam) {
        this.out=out;
        name=nam;
        build();
    }

    void build() {
        for(int ci=0; ci<65535; ci++) {
            record(ci,check((char)ci));
        }
    }

    //to be overridden by each
    boolean check(char ch) {
        return false;
    }

    void record(int ci, boolean on) {
        if(!lastOn && on) {
            if(table.length()>0) { table.append(','); }
            table.append(ci-lastOnCi);
            lastOnCi=ci;
        }
        lastOn=on;
    }

    void print(boolean cma) {
        out.println("            "+name+":["+table+"]"+( cma ? "," : "" ));
    }
}


//builds full table of properties used in printTests
class FullTable {

    StringBuffer table=new StringBuffer();
    String name;
    PrintWriter out;

    FullTable(PrintWriter out, String nam) {
        this.out=out;
        name=nam;
        build();
    }

    void build() {
        for(int ci=0; ci<65535; ci++) {
            record(ci,check((char)ci));
        }
    }

    //to be overridden by each
    int check(char ch) {
        return 0;
    }

    void record(int ci, int val) {
        if(table.length()>0) { table.append(','); }
        table.append(val);
    }

    void print(boolean cma) {
        out.println("    "+name+":["+table+"]"+( cma ? "," : "" ));
    }
}


//The purpose of this class is create markdown documentation out of the inline comments.
//I tried my damndest to get JSDoc or other alternatives to do what I want but failed...
//And after a few hours I decided I could just write my own thing for this in about 20 minutes.
//If you see a way to do this better, please help!
//It is not in a package because that would be overkill.

import java.util.*;
import java.io.*;

public class GenDoco {

    public static void main(String[] args) {
        try {
            System.out.println("Generating Doco...");
            ArrayList<String> inplin=getFileContents("../charFunk-1.1.3.js");//source javascript lines
            ArrayList<String> inpmkd=getFileContents("../../readme.md");
            ArrayList<String> outmkd=new ArrayList<String>();//output markdown

            for(String lin : inpmkd) {
                outmkd.add(lin);
                if(lin.equals("##API")) break;
                }

            int fncidx=-1;//index of where the function header goes
            boolean nxtfnc=false;//next function we want to go back and replace function placeholder
            boolean ign=true;//ignore header area
            boolean prvats=false; //first at sign encountered in a while

            for(String lin : inplin) {
                lin=lin.trim();
                if(ign) {
                    if(lin.equals("*/")) ign=false; //clear ignore
                    continue;
                    }
                if(nxtfnc && lin.indexOf("function(")>-1) {
                    String fncnam="CharFunk."+lin.substring(0,lin.indexOf("="));
                    String fncarg=lin.substring(lin.indexOf("(")+1,lin.indexOf(")"));
                    outmkd.set(fncidx,"\n\n###"+fncnam+"("+fncarg+")");
                    nxtfnc=false;
                }
                else if(lin.startsWith("/*")) {
                    fncidx=outmkd.size();
                    outmkd.add("placeholder");//replaced above
                }
                else if(lin.startsWith("*/")) {
                    nxtfnc=true;
                }
                else if(lin.startsWith("*")) {
                    if(lin.indexOf(" @")>-1) {
                        if(!prvats) outmkd.add("");
                        int endquo=lin.length();
                        int dshidx=lin.indexOf("-");
                        int crlidx=lin.lastIndexOf("}");
                        if(crlidx>-1) endquo=crlidx+1;
                        if(dshidx>-1) endquo=dshidx;
                        outmkd.add("\n`"+lin.substring(1,endquo).trim()+"` "+lin.substring(endquo).trim());
                    }
                    else {
                        outmkd.add(lin.substring(Math.min(lin.length(),3)));
                    }
                }
                prvats=(lin.indexOf(" @")>-1);//previous line has at sign
            }

            PrintWriter outwri=new PrintWriter(new File("../../readme.md"));
            for(String lin : outmkd) {
                outwri.println(lin);
            }
            outwri.flush();
            outwri.close();
        }
        catch(Exception exc) {
            exc.printStackTrace();
        }
        System.out.println("Doco output complete");
    }

    public static ArrayList<String> getFileContents(String filpth) throws Exception {
        FileReader filred=new FileReader(filpth);
        BufferedReader bufred=new BufferedReader(filred);
        ArrayList<String> fillin=new ArrayList<String>();
        String redstr;
        while((redstr=bufred.readLine()) != null) {
            fillin.add(redstr);
        }
        bufred.close();
        filred.close();
        return fillin;
    }

}


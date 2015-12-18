package com.example.vyomkeshjha.CampApp;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vyomkeshjha on 17/12/15.
 */
public class HTMLparser {
    Document toBeParsed;

    HTMLparser() {
        toBeParsed = SisPrs.academics;
    }

    public static String[] getStudentDetails(Document doc)
    {
        String[] Details = new String[3];
        String StudentNameBuilder="span[id=cc_ProfileTitle_name]";
        Elements SubjectAtIndex = doc.select(StudentNameBuilder);
        String Name = SubjectAtIndex.text();
        Details[0]=Name;

        String SectionBuilder="span[id=cc_ProfileTitle_sectionCode]";
        Elements SectionIndex = doc.select(SectionBuilder);
        String Section = SectionIndex.text();
        Details[1]=Section;
        Log.d("SECTION OF STUDENT:",Details[1]);
        return Details;
    }

    public static String[] extractFromGradeTable(Document document, String tableId,Integer index) {


        String[] DataAtX= new String[15];


        //Set id of any table from any website and the below code will print the contents of the table.
        //Set the extracted data in appropriate data structures and use them for further processing
      //  Elements Table = doc.select("span[id=cc_TermGradeBookSummary_productName_1]");
       // String Content = Table.text();
      //  Log.e("Content", Content);
      //  Log.v("tableData",Table.toString());

        String SubjectNameBuilder="span[id="+"cc_TermGradeBookSummary_productName_"+index.toString()+"]";
        Elements SubjectAtIndex = document.select(SubjectNameBuilder);
        String Name = SubjectAtIndex.text();
        DataAtX[0]=Name;

        String  CreditNameBuilder="span[id="+"cc_TermGradeBookSummary_credit_"+index.toString()+"]";
        Elements CreditdAtIndex = document.select(CreditNameBuilder);
        String Credits = CreditdAtIndex.text();
        DataAtX[1]=Credits;
        String  GradeNameBuilder="span[id="+"cc_TermGradeBookSummary_pfinalResult_"+index.toString()+"]";
        Elements GradeAtIndex = document.select(GradeNameBuilder);
        String Grade = GradeAtIndex.text();
        DataAtX[2]=Grade;
        String op=Name+" "+" "+Credits+" "+" "+Grade;
        //Log.e("Content2", Content2);
        //Log.v("tableData2",SubjectAtIndex.toString());
        // Log.e("elements",Table.toString());
        //You can check for nesting of tds if such structure exists
        //for (Element td : Table) {
        //Log.v("table","\n" + td.text());
        //  }
        return DataAtX;

    }

}
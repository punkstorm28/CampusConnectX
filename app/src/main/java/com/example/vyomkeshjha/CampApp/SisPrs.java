package com.example.vyomkeshjha.CampApp;


        import java.io.IOException;
        import java.lang.annotation.ElementType;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.Stack;

        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.Connection;
        import org.jsoup.nodes.Element;
        import org.jsoup.select.Elements;

        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.TextView;

        import com.example.vyomkeshjha.CampAppX.R;

public class SisPrs extends Activity {
    TextView Text;
    static String idValue="150906009";
    static String DOB="1998-12-15";
    static Document academics;
    Document NameExtractionDoc;
    static String Name;
    private Map<String, String> cookies = new HashMap<String, String>();
    ProgressDialog mProgressDialog;
    String Desc;
    HTMLparser wrapper = new HTMLparser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Locate the Buttons in activity_main.xml

        Button descbutton = (Button) findViewById(R.id.descbutton);
        Button wp2 = (Button) findViewById(R.id.WpBu);
   //  Temporary text view for the HTML
        Text = (TextView)findViewById(R.id.TV);
        // Capture button click
        wp2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("start","getWPEXECbeg");
            new GetWp().execute();
                Log.e("start","getWPEXECend");
            }
        });


        descbutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute LoginToWsis AsyncTask
                new LoginToWsis().execute();
            }
        });

    }

    // LoginToWsis AsyncTask, gets the cookies stored into the hashlist
    public static Document getAcdemics()
    {
        return academics;
    }

    private class LoginToWsis extends AsyncTask<Void, Void, Void> {

        String Cookie;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SisPrs.this);
            mProgressDialog.setTitle("Logging in");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
              try {
            // /*
                Connection.Response res = Jsoup.connect("http://websismit.manipal.edu/websis/control/createAnonSession")
                          .data("idValue", idValue)
                          .data("birthDate_i18n", DOB).data("birthDate", DOB)
                          .method(Connection.Method.POST)
                  .execute();
                  Log.v("done", "connection made");

                  //Store All the Cookies in the hashList
                  cookies.putAll(res.cookies());

                  NameExtractionDoc=res.parse();
                  HTMLparser.getStudentDetails(NameExtractionDoc);
                  //Document SieP = res.parse();
                  Cookie = res.cookie("JSESSIONID");
                  //Log.d("cookie","cookie is :"+Cookie);
                  //Log.i("document",SieP.body().toString());//*/
                 //Document doc =wrapper.get("http://websismit.manipal.edu/websis/");
                  //Log.i("document",doc.body().toString());

              }
              catch (IOException e)
              {
                  Log.e("error","   WebsisConnectFailed");
                  e.printStackTrace();
              }

                Desc = Cookie;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txtdesc = (TextView) findViewById(R.id.desctxt);
            txtdesc.setText(Desc);
            mProgressDialog.dismiss();
        }
    }

    // Background task to retrieve the webpage HTML
    private class GetWp extends AsyncTask<Void, Void, Void> {
        String desc;


        ProgressDialog mProgressDialogX;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialogX = new ProgressDialog(SisPrs.this);
            mProgressDialogX.setTitle("connection");
            mProgressDialogX.setMessage("Loading...");
            mProgressDialogX.setIndeterminate(false);
            mProgressDialogX.show();
        }
        String Data="";

        @Override
        protected Void doInBackground(Void... params) {


                // Connect to the web site
                try {
                    Log.e("COOKIE", "the cookie is " + Desc);
                    Connection.Response getAcademics = Jsoup.connect("http://websismit.manipal.edu/websis/control/StudentAcademicProfile").cookies(cookies)
                            .method(Connection.Method.POST)
                            .execute();
                    academics = getAcademics.parse();

                    //String Body = academics.body().toString();
                    //String Location =academics.location();
                    //String Title = academics.title();
                    //String all = academics.toString();
                    //Log.d("title",Title);
                    //Log.d("location",Location);
                    //Log.w("AcademicProfile",academics.toString());
                    //Log.w("AcademicProfileBody",Body);
                    for(int i=1;i<11;i++)
                    {
                        Data +=  HTMLparser.extractFromGradeTable(academics,"TermGradeBookSummary_table",i)[0]+"\n";
                    }


                }
                catch (IOException e)
                {
                    Log.e("error","Connection Failed at getWebpage");
                    e.printStackTrace();
                }




            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            Text.setText(Data);
            mProgressDialogX.dismiss();
        }
    }
}
package com.example.vyomkeshjha.CampApp;


        import java.io.IOException;
        import java.io.InputStream;

        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.select.Elements;
        import org.jsoup.Connection;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.vyomkeshjha.CampAppX.R;

public class SisPrs extends Activity {

    // URL Address

    ProgressDialog mProgressDialog;
    String Desc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Locate the Buttons in activity_main.xml

        Button descbutton = (Button) findViewById(R.id.descbutton);
        Button wp2 = (Button) findViewById(R.id.WpBu);

        wp2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("start","getWPEXECbeg");
            new GetWp().execute();
                Log.e("start","getWPEXECend");
            }
        });

        // Capture button click
        descbutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Description AsyncTask
                new Description().execute();
            }
        });

    }

    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;
        String Cookie;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SisPrs.this);
            mProgressDialog.setTitle("connector");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
              try {
                  Connection.Response res = Jsoup.connect("http://websismit.manipal.edu/websis/")
                          .data("idValue", "150906009")
                          .data("birthDate_i18n", "1998-12-15").data("birthDate", "1998-12-15")
                          .method(Connection.Method.POST)
                  .execute();
                  Log.v("done","connection made");
                  Document SieP = res.parse();
                  Cookie = res.cookie("JSESSIONID");
                  Log.d("cookie","cookie is :"+Cookie);

              }
              catch (Exception e)
              {
                  Log.e("error","   WebsisConnectFailed");
                  e.printStackTrace();
              }

                Document sis = Jsoup.connect("http://websismit.manipal.edu/websis/control/createAnonSession").data("cookieexists", "true")
                        .data("idValue", "150906009")
                        .data("birthDate_i18n", "1998-12-15").data("birthDate","1998-12-15")
                        .post();
                Log.i("data",sis.title()+"logged");
                Log.i("data",sis.body()+"logged");
                Log.i("data",sis.toString()+"logged");
                Document Details = Jsoup.connect("http://websismit.manipal.edu/websis/control/StudentAcademicProfile").get();
                // Using Elements to get the Meta data
               // Log.d("details",Details.body().toString());
                Desc = Cookie;
            } catch (IOException e) {
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

    // WbPageGetter AsyncTask
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

        @Override
        protected Void doInBackground(Void... params) {

                // Connect to the web site
                try {
                    Log.e("COOK", "the cookie is " + Desc);
                    Connection.Response getAcademics = Jsoup.connect("http://websismit.manipal.edu/websis/control/StudentAcademicProfile").cookie("JSESSIONID",Desc)
                            .method(Connection.Method.GET)
                            .execute();
                    Log.d("AcademicProfile",getAcademics.toString());
                }
                catch (IOException e)
                {
                    Log.e("error","   LooksLikeNullCook");
                    e.printStackTrace();
                }




            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            mProgressDialogX.dismiss();
        }
    }
}
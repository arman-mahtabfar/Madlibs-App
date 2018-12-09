 package com.example.armanmahtabfar.madlibapp;

 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.util.Log;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.TextView;
 import android.content.Intent;

 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.JsonObjectRequest;
 import com.android.volley.toolbox.Volley;

 import org.json.JSONException;
 import org.json.JSONObject;

// Arman is a big gay.
 public class MainActivity extends AppCompatActivity {

     /** Default loggin tag for messages from the main activity. */
     private static final String TAG = "Madlib:Main";

     /** Request queue for our network requests. */
     private static RequestQueue requestQueue;

    public void help(android.view.View v){
        startActivity(new Intent(MainActivity.this, Help.class));
    }

     @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

         final EditText editTextToRemove = (EditText) findViewById(R.id.editText);
         final EditText editTextToAdd = (EditText) findViewById(R.id.editText2);
         final TextView lyricsDisplay = (TextView) findViewById(R.id.textView4);

        // Attach the handler to our UI button called newSong
        Button APICall = findViewById(R.id.newSong);
        APICall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Start API button clicked");
                try {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            "https://talaikis.com/api/quotes/random/",
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(final JSONObject response) {
                                    Log.d(TAG, response.toString());
                                    try {
                                        lyricsDisplay.setText(response.get("quote").toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(final VolleyError error) {
                            Log.w(TAG, error.toString());
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //this is the button that will madlib based on lyrics
        Button madlib = (Button) findViewById(R.id.madlib);
        madlib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toRemove = editTextToRemove.getText().toString();
                String toAdd = editTextToAdd.getText().toString();
                String quote = lyricsDisplay.getText().toString();
                String result = toRemove + toAdd + quote;
                lyricsDisplay.setText(result);
            }
        });
     }
}

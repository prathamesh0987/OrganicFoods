package svrinfotech.com.organicfoods;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import svrinfotech.com.organicfoods.pojo.OrderedProducts;

public class Checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    private String mid,order_id="",customer_id="first_01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_checksum);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mid="cQxKAV39541294414551";
        new SendUserDataToServer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        Random random=new Random();
        int o_id=random.nextInt();
        order_id=String.valueOf(o_id);
        OrderedProducts orderedProducts=(OrderedProducts) getIntent().getSerializableExtra("orderedProduct");
        Toast.makeText(getApplicationContext(),orderedProducts.getQuantity()+" "+orderedProducts.getFinalCost(),Toast.LENGTH_LONG).show();
    }

    private class SendUserDataToServer extends AsyncTask<ArrayList<String>,Void,String > {

        String url="http://www.svrinfotech.net/paytm/generateChecksum.php";
        String verifyUrl="https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        String checksumHash="";

        @Override
        protected String doInBackground(ArrayList<String>... arrayLists) {
            String param =
                            "MID="+mid+
                            "&ORDER_ID="+order_id+
                            "&CUST_ID="+customer_id+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT=100&WEBSITE=WEBSTAGING"+
                            "&CALLBACK_URL="+verifyUrl+
                            "&INDUSTRY_TYPE_ID=Retail";

            /*JSONObject postParams=new JSONObject();

            try {
                postParams.put("param",param);
                postParams.put("ORDER_ID",order_id);
                postParams.put("CUST_ID",customer_id);
                postParams.put("CHANNEL_ID","WAP");
                postParams.put("TXN_AMOUNT","100");
                postParams.put("WEBSITE","WEBSTAGING");
                postParams.put("CALLBACK_URL",verifyUrl);
                postParams.put("INDUSTRY_TYPE_ID","Retail");
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            /*RequestQueue requestQueue= Volley.newRequestQueue(Checksum.this);
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    postParams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                checksumHash = response.getString("CHECKSUMHASH");
                                Toast.makeText(getApplicationContext(),"Checksum Hash : "+checksumHash,Toast.LENGTH_LONG).show();
                                Log.e("Checksum Hash",checksumHash);
                                Log.e("ORDER_ID",response.getString("ORDER_ID"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),
                                    "Error : "+error.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            );
            requestQueue.add(jsonObjectRequest);*/

            JSONParser jsonParser=new JSONParser(Checksum.this);
            JSONObject response=jsonParser.makeHttpRequest(url,"POST",param);
            try {
                checksumHash = response.getString("CHECKSUMHASH");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            PaytmPGService paytmPGService=PaytmPGService.getStagingService();
            HashMap<String, String> paytmMap=new HashMap<>();
            paytmMap.put("MID",mid);
            paytmMap.put("ORDER_ID",order_id);
            paytmMap.put("CUST_ID",customer_id);
            paytmMap.put("CHANNEL_ID","WAP");
            paytmMap.put("TXN_AMOUNT","100");
            paytmMap.put("WEBSITE","WEBSTAGING");
            paytmMap.put("CALLBACK_URL",verifyUrl);
            paytmMap.put("CHECKSUMHASH",checksumHash);
            paytmMap.put("INDUSTRY_TYPE_ID","Retail");

            PaytmOrder paytmOrder=new PaytmOrder(paytmMap);
            paytmPGService.initialize(paytmOrder,null);
            paytmPGService.startPaymentTransaction(Checksum.this,true,true,Checksum.this);
        }
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {
        Log.e("Status : ",inResponse.toString());
    }

    @Override
    public void networkNotAvailable() {

    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {

    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {

    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {

    }

    @Override
    public void onBackPressedCancelTransaction() {

    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

    }
}

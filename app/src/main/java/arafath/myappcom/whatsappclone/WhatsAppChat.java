package arafath.myappcom.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppChat extends AppCompatActivity implements View.OnClickListener{

    private ListView list;
    private ArrayList<String> chatList;
    private ArrayAdapter arrayAdapter;
    private String selectedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app_chat);

        selectedUser = getIntent().getStringExtra("selectedUser");
        setTitle(selectedUser);

        findViewById(R.id.btnSend).setOnClickListener(this);

        list = findViewById(R.id.chatList);
        chatList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,chatList);
        list.setAdapter(arrayAdapter);

        try {
            ParseQuery<ParseObject> firstUser = ParseQuery.getQuery("Chat");
            ParseQuery<ParseObject> secondUser = ParseQuery.getQuery("Chat");

            firstUser.whereEqualTo("waSender", ParseUser.getCurrentUser().getUsername());
            firstUser.whereEqualTo("waReciever", selectedUser);


            secondUser.whereEqualTo("waSender", selectedUser);
            secondUser.whereEqualTo("waReciever", ParseUser.getCurrentUser().getUsername());

            ArrayList<ParseQuery<ParseObject>> allQueries = new ArrayList<>();
            allQueries.add(firstUser);
            allQueries.add(secondUser);

            ParseQuery<ParseObject> myQuery = ParseQuery.or(allQueries);
            myQuery.orderByAscending("createdAt");

            myQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {

                        for (ParseObject obj : objects) {
                            String mes = obj.get("message") + "";

                            if (obj.get("waSender").equals(ParseUser.getCurrentUser().getUsername())) {
                                mes = ParseUser.getCurrentUser().getUsername() + ": " + mes;
                            }

                            if (obj.get("waSender").equals(selectedUser)) {
                                mes = selectedUser + ": " + mes;
                            }
                            chatList.add(mes);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        final EditText editText = findViewById(R.id.chatEdit);

        ParseObject chat = new ParseObject("Chat");
        chat.put("waSender", ParseUser.getCurrentUser().getUsername());
        chat.put("waReciever",selectedUser);
        chat.put("message",editText.getText().toString());
        chat.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    FancyToast.makeText(WhatsAppChat.this,"Message Sent",FancyToast.INFO, Toast.LENGTH_SHORT,true).show();
                    chatList.add(ParseUser.getCurrentUser().getUsername()+": "+editText.getText().toString());
                    arrayAdapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });
    }
}

package arafath.myappcom.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener {

    private EditText userLog, passLog;
    private Button logInlog, signInlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        userLog = findViewById(R.id.userNameLog);
        passLog = findViewById(R.id.passLog);

        logInlog = findViewById(R.id.button);
        signInlog = findViewById(R.id.button2);

        if(ParseUser.getCurrentUser() != null){
            transitionToMainActivity();
        }

        passLog.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(logInlog);
                }
                return false;
            }
        });

        logInlog.setOnClickListener(this);
        signInlog.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button:
                if(userLog.getText().toString().equals("") || passLog.getText().toString().equals("")){
                    FancyToast.makeText(this,"Username, Password required", FancyToast.ERROR, Toast.LENGTH_SHORT,true).show();

                }else{
                    ParseUser.logInInBackground(userLog.getText().toString(), passLog.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(e== null&& user!=null){
                                FancyToast.makeText(LoginActivity.this,userLog.getText().toString() + " successfully Logged In", FancyToast.SUCCESS, Toast.LENGTH_SHORT,true).show();
                                transitionToMainActivity();
                            }else{
                                FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.ERROR, Toast.LENGTH_SHORT, true).show();

                            }
                        }
                    });
                }
                break;

            case R.id.button2:
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void rootLayoutTapped(View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void transitionToMainActivity(){
        Intent intent = new Intent(LoginActivity.this,WhatsAppActivity.class);
        startActivity(intent);
        finish();
    }
}

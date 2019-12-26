package arafath.myappcom.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import arafath.myappcom.whatsappclone.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mailSign, userSign, passSign;
    private Button signInsign, logInsign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");

        mailSign = findViewById(R.id.EmailEditText);
        userSign = findViewById(R.id.usernmaedit);
        passSign = findViewById(R.id.passEdit);

        signInsign = findViewById(R.id.signUpbtm);
        logInsign = findViewById(R.id.logInbtn);

        passSign.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(signInsign);
                }
                return false;
            }
        });

        if(ParseUser.getCurrentUser() != null){
            transitionToMainActivity();
        }

        signInsign.setOnClickListener(this);
        logInsign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.signUpbtm:

                if (mailSign.getText().toString().equals("") || userSign.getText().toString().equals("") || passSign.getText().toString().equals("")) {
                    FancyToast.makeText(this, "Email, username, password required", FancyToast.INFO, Toast.LENGTH_SHORT, true).show();
                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(mailSign.getText().toString());
                    appUser.setUsername(userSign.getText().toString());
                    appUser.setPassword(passSign.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + userSign.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(MainActivity.this, userSign.getText().toString() + " is Signed Up", FancyToast.SUCCESS, Toast.LENGTH_SHORT, true).show();
                                transitionToMainActivity();
                            } else {
                                FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.ERROR, Toast.LENGTH_SHORT, true).show();
                            }
                        }

                    });
                    progressDialog.dismiss();
                }
                break;

            case R.id.logInbtn:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
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
        Intent intent = new Intent(MainActivity.this,WhatsAppActivity.class);
        startActivity(intent);
        finish();
    }
}

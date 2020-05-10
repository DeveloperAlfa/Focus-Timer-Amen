package io.devalfa.focustimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText userName;
    Button submitName;
    LinearLayout knowMore;
    LinearLayout more;
    TextView hide;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.NameET);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        name = sharedPref.getString(getString(R.string.user_name), getString(R.string.enter_name));
        if(!name.matches(getString(R.string.enter_name))) userName.setText(name);
        submitName = findViewById(R.id.SubmitNameButton);
        if(userName.getText().toString().length()!=0)
        {
            submitName.setBackground(getDrawable(R.drawable.button_active));
            submitName.setTextColor(getResources().getColor(R.color.white));
            submitName.setEnabled(true);
        }
        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    userName.setHint("");
                }
                else
                {
                    userName.setHint(R.string.enter_name);
                }
            }
        });
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submitName = findViewById(R.id.SubmitNameButton);
                if(userName.getText().toString().length()!=0)
                {
                    submitName.setBackground(getDrawable(R.drawable.button_active));
                    submitName.setTextColor(getResources().getColor(R.color.white));
                    submitName.setEnabled(true);
                }
                else
                {
                    submitName.setBackground(getDrawable(R.drawable.button_inactive));
                    submitName.setTextColor(getResources().getColor(R.color.gray));
                    submitName.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        knowMore = findViewById(R.id.knowmoreLL);
        knowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more = findViewById(R.id.moreLL);
                if(more.getVisibility()==View.GONE) more.setVisibility(View.VISIBLE);
                else more.setVisibility(View.GONE);
            }
        });
        hide = findViewById(R.id.moreHide);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more = findViewById(R.id.moreLL);
                more.setVisibility(View.GONE);
            }
        });
        submitName = findViewById(R.id.SubmitNameButton);
        submitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = findViewById(R.id.appIcon);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imageView, "appIconTransition");
                Intent in = new Intent(MainActivity.this,ChooseTimer.class);
                in.putExtra("userName", userName.getText().toString());
                startActivity(in,options.toBundle());
                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.user_name), userName.getText().toString());
                editor.apply();
                finish();
            }
        });
    }
}

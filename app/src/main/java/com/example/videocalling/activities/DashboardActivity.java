package com.example.videocalling.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videocalling.R;
import com.example.videocalling.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;



public class DashboardActivity extends AppCompatActivity {

   // ActivityMainBinding binding;

  BottomNavigationView bottomNavigationView;


    FirebaseAuth auth;
    FirebaseAuth mAuth;

    //ProgressDialog dialog;
    FirebaseDatabase database;
    EditText secretCodeBox;
    Button joinBtn, shareBtn, btnLogout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // binding = ActivityMainBinding.inflate(getLayoutInflater());
      setContentView(R.layout.activity_dashboard);
        //setContentView(binding.getRoot());


          mAuth = FirebaseAuth.getInstance();
       btnLogout = findViewById(R.id.btnLogout);

       btnLogout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.signOut();
               Intent intent = new Intent(DashboardActivity.this,Authentication.class);
               startActivity(intent);
               finish();
               Toast.makeText(DashboardActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
           }
       });
//bottomNavigationView = findViewById(R.id.bottomNavigationView2);

//getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

//bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
    //@Override




        auth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        secretCodeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinBtn);
        shareBtn = findViewById(R.id.shareBtn);



        URL serverURL;
        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                         //   .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }



        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (secretCodeBox.getText().toString().isEmpty()) {
                    Toast.makeText(DashboardActivity.this, "Please enter a room id!", Toast.LENGTH_SHORT).show();
                } else {


                   JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(secretCodeBox.getText().toString())

                             .setAudioMuted(true)
                              .setVideoMuted(true)
                            //    .setWelcomePageEnabled(false)
                           .setConfigOverride("requireDisplayName", true)
                           //.setConfigOverride("requireDisplayName", true)
                           .setConfigOverride("reqiureInviteOthers", true)
                           .setFeatureFlag("invite.enabled", true)
                           .setFeatureFlag("add-people.enabled", true)

                           .build();


                   JitsiMeetActivity.launch(DashboardActivity.this,options);
                }



            }
        });
        //
        Button shareButton = findViewById(R.id.shareBtn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text you want to share
                EditText editText = findViewById(R.id.codeBox);
                String text = editText.getText().toString();

                // Create a sharing intent
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, text);

                // Start the sharing activity
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });

    }
}
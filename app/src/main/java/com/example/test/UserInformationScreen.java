package com.example.test;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.databinding.UserInformationScreenBinding;

import org.json.JSONObject;

import java.util.UUID;

public class UserInformationScreen extends AppCompatActivity {

    UserInformationScreenBinding binding;
    UserRepository userRepo = new UserRepository();
    User _user = new User();


    Handler registerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Response response = (Response) msg.obj;
            if (response.returnCode == 200)
            {
              userRepo.updateUser(((WebService)getApplication()).getSrv(), updateUserHandler, _user);
            }

            return true;
        }
    });

    Handler updateUserHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Response response = (Response) msg.obj;
            if (response.returnCode == 200)
            {
                Intent i = new Intent(UserInformationScreen.this, ProgramInformation.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UserInformationScreenBinding.inflate(getLayoutInflater());

        setContentView(R.layout.user_information_screen);
        UUID uuid = UUID.randomUUID();
        String appKey = uuid.toString();
        getSupportActionBar().hide();
        ExperienceLevel[] items = {ExperienceLevel.BEGINNER, ExperienceLevel.AMATEUR, ExperienceLevel.INTERMEDIATE, ExperienceLevel.PROFESSIONAL};
        Spinner experienceLevelInput = findViewById(R.id.experience_level_input);

        EditText fullNameInput = findViewById(R.id.fullname_input);
        EditText heightInput = findViewById(R.id.height_input);
        EditText weightInput = findViewById(R.id.weight_input);
        Button nextButton = findViewById(R.id.go_button);

        ArrayAdapter<ExperienceLevel> experienceLevelAdp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        experienceLevelInput.setAdapter(experienceLevelAdp);

        nextButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("GymDuo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("APPKEY", appKey);
            editor.putString("FULLNAME", fullNameInput.getText().toString());
            editor.apply();

            Object exp = experienceLevelInput.getSelectedItem();
            String fullName = fullNameInput.getText().toString();
            String height = heightInput.getText().toString();
            String weight = weightInput.getText().toString();

            ExperienceLevel expEnum = ExperienceLevel.BEGINNER;
            switch (exp.toString())
            {
                case "BEGINNER":
                    expEnum = ExperienceLevel.BEGINNER;
                    break;

                case "AMATEUR":
                    expEnum = ExperienceLevel.AMATEUR;
                    break;

                case "INTERMEDIATE":
                    expEnum = ExperienceLevel.INTERMEDIATE;
                    break;

                case "PROFESSIONAL":
                    expEnum = ExperienceLevel.PROFESSIONAL;
                    break;

            }

            _user.setFullName(fullName);
            _user.setHeight(Integer.parseInt(height));
            _user.setWeight(Integer.parseInt(weight));
            _user.setAppKey(appKey);
            _user.setExperienceLevel(expEnum);

            userRepo.register(((WebService)getApplication()).getSrv(), registerHandler, appKey);
        });
    }
}

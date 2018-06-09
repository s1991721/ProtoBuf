package com.ljf.protobuf_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tutorial.AddressBookProtos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by mr.lin on 2018/6/8.
 */

public class MainActivity extends Activity {

    private TextView resultTv;
    private Button readFileBt;
    private Button sendToServerBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        AddressBookProtos.Person person = AddressBookProtos.Person
                .newBuilder()
                .setName("Tom")
                .setId(1)
                .setEmail("Email")
                .addPhones(0,
                        AddressBookProtos.Person.PhoneNumber.newBuilder()
                                .setNumber("1234")
                                .setType(AddressBookProtos.Person.PhoneType.HOME)
                                .build())
                .build();

        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addPeople(0, person)
                .addPeople(1, person)
                .build();
        try {
            FileOutputStream fileOutputStream = openFileOutput("proto", MODE_PRIVATE);
            fileOutputStream.write(addressBook.toByteArray());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        resultTv = findViewById(R.id.resultTv);
        readFileBt = findViewById(R.id.readFileBt);
        sendToServerBt = findViewById(R.id.sendToServerBt);

        readFileBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fileInputStream = openFileInput("proto");
                    AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.parseFrom(fileInputStream);
                    resultTv.setText("from local:\n" + addressBook.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sendToServerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://10.2.24.243:8080/test");
                            InputStream is = url.openStream();

                            final AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.parseFrom(is);
                            is.close();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    resultTv.setText("from net:\n" + addressBook.toString());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }


}
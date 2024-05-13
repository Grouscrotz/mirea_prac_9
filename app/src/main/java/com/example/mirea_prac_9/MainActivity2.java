package com.example.mirea_prac_9;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity2 extends AppCompatActivity {
    private EditText NameFile;
    private EditText textFile;
    private TextView textView;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textFile = findViewById(R.id.fileText2);
        NameFile = findViewById(R.id.fileName2);

    }

    public void write(View view){
        String fileName = NameFile.getText().toString();
        String content = textFile.getText().toString();
        try {
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "mirea");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(dir, fileName);
            FileOutputStream fileOutput = new FileOutputStream(file);
            fileOutput.write(content.getBytes());
            fileOutput.close();
            textFile.setText("");

            Toast.makeText(MainActivity2.this, "Файл создан", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(View view) {
        String fileName = NameFile.getText().toString();
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "mirea");
        file = new File(dir, fileName);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удаление")
                .setMessage("Вы уверены, что вы хотите удалить файл?")
                .setPositiveButton("Да", (dialog, which) -> {
                    boolean result = file.delete();
                    if (result) {
                        Toast.makeText(this, "Файл удалён", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
    }





    }






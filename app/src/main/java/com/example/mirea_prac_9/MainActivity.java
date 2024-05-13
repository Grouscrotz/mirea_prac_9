package com.example.mirea_prac_9;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private static final int READ_BLOCK_SIZE = 100;
    private EditText editText;
    private EditText textToFile;
    private String text;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.fileName);
        textToFile = findViewById(R.id.fileText);


        if (savedInstanceState != null && savedInstanceState.containsKey("KEY_STATE")) {
            text = savedInstanceState.getString("KEY_STATE");
        }

    }



    public void writeToFile(View view) {
        String filename = editText.getText().toString();

        try {
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fos);
            outputWriter.write(textToFile.getText().toString());
            outputWriter.close();

            // создаем всплывающее окно c результатом выволнения записи в файл
            Toast.makeText(getBaseContext(), "Запись в файл успешно проведена!",
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readFromFile(View v) {

        try {
            // Название файла
            String filename = editText.getText().toString();

            // TextView для отображения прочитанного из файла
            textView = findViewById(R.id.textView);

            // Объёкт для прочтения фaйла
            FileInputStream fileInputStream = openFileInput(filename);

            InputStreamReader reader = new InputStreamReader(fileInputStream);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;


            // цикл читает данные из файла,
            while ((charRead = reader.read(inputBuffer)) != -1) {
                // конвертируем char в строку
                String rString = String.copyValueOf(inputBuffer, 0, charRead);
                s += rString;
            }
            reader.close();


            // Отображаем на textView прочитанное из файла
            textView.setText(s);
            String text = textView.getText().toString();


            // создаем всплывающее окно c результатом выволнения чтения из файла
            Toast.makeText(getBaseContext(), "Чтение из файла успешно проведено!",
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile() {
        // Считываем имя файла, который хотим удалить
        String filename = editText.getText().toString();

        // Получаем файловый объект для файла из внутреннего хранилища
        File dir = getFilesDir();
        File file = new File(dir, filename);

        // Удаляем файл
        boolean deleted = file.delete();

        Toast.makeText(MainActivity.this, "Удаление файла успешно проведено!",
                Toast.LENGTH_LONG).show();
    }

    public void addTextToFile(View view) {
        try {
            String filename = editText.getText().toString();
            FileOutputStream fos = openFileOutput(filename, MODE_APPEND);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fos);
            outputWriter.write(textToFile.getText().toString());
            outputWriter.close();

            // создаем всплывающее окно c результатом выволнения записи в файл
            Toast.makeText(getBaseContext(), "Добавление текста в файл успешно выполнено!",
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Подтверждение");
        builder.setMessage("Вы уверены, что хотите удалить файл?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFile();
            }
        });

        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Обработчик нажатия на кнопку "Нет" в диалоге: Toast
                Toast.makeText(MainActivity.this,"Ну ладно :(",
                        Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void startAlert(View view) {
        createAlertDialog();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Сохраняем значение строковой переменной
        String textValue = textView.getText().toString();
        outState.putString("KEY_STATE", textValue);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Восстанавливаем сохраненное состояние
        String restoredText = savedInstanceState.getString("KEY_STATE");
        // Используем сохраненное значение для восстановления состояния UI или других компонентов
        textView.setText(restoredText);

    }

    public void nextActivity(View view) {
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);
    }




}
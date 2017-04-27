package codetutor.youtube.com.todoclient;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG=MainActivity.class.getSimpleName();

    private TextView textViewContent,textViewCount;

    private EditText editTextWhere;

    private Button buttonGetAllToDos, buttonGetToDoForPlace;

    private Uri uriAllToDos = Uri.parse("content://todolist.youtube.com.codetutor/TODO_TABLE");
    private Uri uriForSpecificPlace=Uri.parse("content://todolist.youtube.com.codetutor/TODO_TABLE_FROM_PLACE");
    private Uri uriToDoCount=Uri.parse("content://todolist.youtube.com.codetutor/TOTAL_TODOS");

    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewContent=(TextView)findViewById(R.id.textViewContent);
        textViewCount=(TextView)findViewById(R.id.textViewCount);
        editTextWhere=(EditText)findViewById(R.id.editTextWhere);

        buttonGetAllToDos=(Button)findViewById(R.id.buttonGetAllToDos);
        buttonGetToDoForPlace=(Button)findViewById(R.id.buttonGetToDoForPlace);

        buttonGetAllToDos.setOnClickListener(this);
        buttonGetToDoForPlace.setOnClickListener(this);

        contentResolver=getContentResolver();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCount();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonGetAllToDos: setAllToDos(); break;
            case R.id.buttonGetToDoForPlace: setSpecificPlaceToDos(); break;
        }
    }

    private void setAllToDos(){
        Cursor cursor=contentResolver.query(uriAllToDos,null,null,null,null,null);
        StringBuilder stringBuilder=new StringBuilder("");
        if(cursor!=null &cursor.getCount()>0){

            while(cursor.moveToNext()){
                stringBuilder.append(cursor.getLong(0)+", "+cursor.getString(1)+", "+cursor.getString(2)+"\n");
            }
        }
        cursor.close();
        textViewContent.setText(stringBuilder.toString());
    }

    private void setSpecificPlaceToDos(){
        String place=editTextWhere.getText().toString();
        StringBuilder stringBuilder=new StringBuilder("");
        Cursor cursor=contentResolver.query(uriForSpecificPlace,null,null,new String[]{place},null,null);

        if(cursor!=null &cursor.getCount()>0){

            while(cursor.moveToNext()){
                stringBuilder.append(cursor.getLong(0)+", "+cursor.getString(1)+"\n");
            }
        }
        cursor.close();
        textViewContent.setText(stringBuilder.toString());
    }

    private void setCount(){
        Cursor cursor=contentResolver.query(uriToDoCount,null,null,null,null,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            textViewCount.setText("Total ToDos Count: "+cursor.getInt(0));
        }
        cursor.close();
    }
}

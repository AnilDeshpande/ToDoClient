package codetutor.youtube.com.todoclient;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by anildeshpande on 4/27/17.
 */

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    private static String TAG=SecondActivity.class.getSimpleName();

    private Uri CONTENT_URI_1 = Uri.parse("content://todolist.youtube.com.codetutor/TODO_TABLE");

    ContentResolver contentResolver;

    private EditText editTextNewToDoString, editTextPlace, editTextToDoId, editTextNewToDo;
    private Button buttonAddToDo, buttonModifyToDo;
    private TextView textViewToDos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editTextNewToDo=(EditText)findViewById(R.id.editTextNewToDo);
        editTextNewToDoString=(EditText)findViewById(R.id.editTextNewToDoString);
        editTextPlace=(EditText)findViewById(R.id.editTextPlace);
        editTextToDoId=(EditText)findViewById(R.id.editTextToDoId);

        buttonAddToDo=(Button)findViewById(R.id.buttonAddToDo);
        buttonModifyToDo=(Button)findViewById(R.id.buttonModifyToDo);

        buttonAddToDo.setOnClickListener(this);
        buttonModifyToDo.setOnClickListener(this);

        contentResolver=getContentResolver();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.buttonAddToDo: addToDo(); break;
            case R.id.buttonModifyToDo: updateToDo(); break;
            default:break;
        }
    }

    private void addToDo(){
        String todo=editTextNewToDoString.getText().toString();
        String place=editTextPlace.getText().toString();

        ContentValues contentValues=new ContentValues();
        contentValues.put("todo",todo);
        contentValues.put("place",place);

        contentResolver.insert(CONTENT_URI_1,contentValues);
        finish();
    }

    private void updateToDo(){
        ContentValues contentValues=new ContentValues();
        contentValues.put("todo",editTextNewToDo.getText().toString());
        String whereClause= "task_id = ?";
        String [] whereValues=new String []{editTextToDoId.getText().toString()};

        contentResolver.update(CONTENT_URI_1,contentValues,whereClause,whereValues);
        finish();
    }


}

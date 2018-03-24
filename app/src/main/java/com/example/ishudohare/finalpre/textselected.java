package com.example.ishudohare.finalpre;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ishudohare.finalpre.DataUtils.*;

/**

 */
public class textselected extends AppCompatActivity implements View.OnClickListener {
    Button addnote;
 TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.textselectedactivity);

        Bundle bundle = getIntent().getExtras();

//Extract the data…
        String venName = bundle.getString("VENUE_NAME");

//Create the text vi   addnote = (Button) findViewById(R.id.addnotebutton);

        addnote = (Button) findViewById(R.id.addnotebutton);
        tv=(TextView)findViewById(R.id.msgtv);

        tv.setText(venName);
        registerForContextMenu(tv);
        addnote.setOnClickListener(this);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.add(0, v.getId(), 0, "your text is copied");
        TextView yourTextView = (TextView) v;
        yourTextView.setText(tv.getText().toString());

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboard.setText(yourTextView.getText());

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.addnotebutton)
        {startActivity(new Intent(this,MainActivityNotes.class));


    }

}}

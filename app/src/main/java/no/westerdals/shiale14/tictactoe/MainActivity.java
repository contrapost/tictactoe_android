package no.westerdals.shiale14.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Main activity for registration of players.
 * Check if both names of players are received and validates input.
 * Starts next activity, creates and inflates a menu with to items:
 * "Results" that opens applications third activity and "Description" (dialog box)
 * that contains basic information about application.
 *
 * Created by Alexander Shipunov.
 */

public class MainActivity extends AppCompatActivity {

    private EditText inputNameFirstPlayer, inputNameSecondPlayer;
    private Context context;
    public Button btnStart;
    public static ArrayList<String> results = new ArrayList<>();
    public String firstPlayer, secondPlayer;
    public static boolean gameActivityVisited;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        initWidgets();
        initListeners();

        gameActivityVisited = false;
    }

    private void initWidgets() {
        inputNameFirstPlayer = (EditText) findViewById(R.id.editTextFirstPlayer);
        inputNameSecondPlayer = (EditText) findViewById(R.id.editTextSecondPlayer);
        btnStart = (Button) findViewById(R.id.btnStart);
    }

    private void initListeners() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPlayer = inputNameFirstPlayer.getText().toString().trim();
                secondPlayer = inputNameSecondPlayer.getText().toString().trim();
                if (firstPlayer.equals("") || secondPlayer.equals("")) {
                    Toast.makeText(context, "Please type the names of both players!", Toast.LENGTH_SHORT).show();
                } else if (firstPlayer.equals(secondPlayer)) {
                    Toast.makeText(context, "Players should have different names!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, GameActivity.class);
                    intent.putExtra("firstPlayer", firstPlayer);
                    intent.putExtra("secondPlayer", secondPlayer);
                    startActivity(intent);

                    inputNameFirstPlayer.getText().clear();
                    inputNameSecondPlayer.getText().clear();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.resultMenuItem:
                Intent intent = new Intent(context, ResultActivity.class);
                startActivity(intent);
                return true;
            case R.id.descriptionMenuItem:
                DescriptionDialog descriptionDialog = new DescriptionDialog(MainActivity.this);
                descriptionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                descriptionDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

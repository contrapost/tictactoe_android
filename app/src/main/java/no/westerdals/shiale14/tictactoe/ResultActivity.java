package no.westerdals.shiale14.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Displays information about results of previous games by listing
 * them in List View.
 *
 * Offer user two options: play again or go to the first activity.
 *
 * Created by Alexander Shipunov.
 */

public class ResultActivity extends AppCompatActivity {

    private Context context;
    private Button btnPlayAgain, btnMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        context = this;

        initWidgets();
        initListeners();
    }

    private void initWidgets() {
        ListView listView = (ListView) findViewById(R.id.listViewResults);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, MainActivity.results);
        listView.setAdapter(adapter);

        btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
    }

    private void initListeners() {
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.results, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.startGameMenuItem:
                startNewGame();
                return true;
            case R.id.mainMenuItem:
                openMainMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startNewGame() {
        if(MainActivity.gameActivityVisited) {
            Intent intentGame = new Intent(context, GameActivity.class);
            intentGame.putExtra("firstPlayer", getIntent().getStringExtra("firstPlayer"));
            intentGame.putExtra("secondPlayer", getIntent().getStringExtra("secondPlayer"));
            startActivity(intentGame);
        } else {
            Toast.makeText(context, "There are no registered players. Please choose \"Main menu\".", Toast.LENGTH_LONG).show();
        }
    }

    private void openMainMenu() {
        Intent intentMain = new Intent(context, MainActivity.class);
        startActivity(intentMain);
    }
}

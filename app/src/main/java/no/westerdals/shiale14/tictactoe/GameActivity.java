package no.westerdals.shiale14.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;

/**
 * Creates game board, accidentally chooses who will play for X and O,
 * informs users about next move, check and save results, informs users
 * who has won and opens ResultsActivity.
 *
 * Created by Alexander Shipunov.
 */

public class GameActivity extends AppCompatActivity {

    private boolean turnOfXPlayer;
    private ArrayList<Integer> turnsOfXPlayer = new ArrayList<>(5);
    private ArrayList<Integer> turnsOfOPlayer = new ArrayList<>(4);
    private HashSet<ArrayList<Integer>> winCombinations = new HashSet<>(8);
    private int numberOfTurns;
    private Context context;
    private String nameXPlayer, nameOPlayer, winner;
    private Button[] buttons;
    private TextView textXPlayerVsOPlayer, nextMoveText;
    private ButtonListener btnListener = new ButtonListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initWidgets();
        initGame();
        initListeners();

        context = this;
    }

    private void initGame() {
        turnsOfXPlayer.clear();
        turnsOfOPlayer.clear();
        numberOfTurns = 0;
        turnOfXPlayer = true;
        winner = "draw";
        winCombinations = getWinCombinations();
        sortition();
        MainActivity.gameActivityVisited = true;
        notifyAboutNextMove(nameXPlayer);
    }

    private HashSet<ArrayList<Integer>> getWinCombinations() {
        HashSet<ArrayList<Integer>> winCombinations = new HashSet<>(8);

        winCombinations.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
        winCombinations.add(new ArrayList<>(Arrays.asList(4, 5, 6)));
        winCombinations.add(new ArrayList<>(Arrays.asList(7, 8, 9)));
        winCombinations.add(new ArrayList<>(Arrays.asList(1, 4, 7)));
        winCombinations.add(new ArrayList<>(Arrays.asList(2, 5, 8)));
        winCombinations.add(new ArrayList<>(Arrays.asList(3, 6, 9)));
        winCombinations.add(new ArrayList<>(Arrays.asList(1, 5, 9)));
        winCombinations.add(new ArrayList<>(Arrays.asList(3, 5, 7)));

        return winCombinations;
    }

    private void sortition() {
        if (!(new Random().nextBoolean())) {
            nameXPlayer = getIntent().getStringExtra("firstPlayer");
            nameOPlayer = getIntent().getStringExtra("secondPlayer");
        } else {
            nameXPlayer = getIntent().getStringExtra("secondPlayer");
            nameOPlayer = getIntent().getStringExtra("firstPlayer");
        }
        textXPlayerVsOPlayer.setText(String.format("%s (X) vs. %s (O)", nameXPlayer, nameOPlayer));
    }

    private void notifyAboutNextMove(String name) {
        String temp = "Your move, " + name + "!";
        nextMoveText.setText(temp);
    }

    private void initWidgets() {
        buttons = new Button[]{
                (Button) findViewById(R.id.btnOneOne),
                (Button) findViewById(R.id.btnOneTwo),
                (Button) findViewById(R.id.btnOneThree),
                (Button) findViewById(R.id.btnTwoOne),
                (Button) findViewById(R.id.btnTwoTwo),
                (Button) findViewById(R.id.btnTwoThree),
                (Button) findViewById(R.id.btnThreeOne),
                (Button) findViewById(R.id.btnThreeTwo),
                (Button) findViewById(R.id.btnThreeThree)

        };

        textXPlayerVsOPlayer = (TextView) findViewById(R.id.textViewXPlayerVsOPlayer);
        nextMoveText = (TextView) findViewById(R.id.textViewNextMove);
    }

    private void initListeners() {
        for (Button button : buttons)
            button.setOnClickListener(btnListener);
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            int id = v.getId();
            if (btn.getText().toString().equals("")) {
                numberOfTurns++;
                if (turnOfXPlayer) {
                    btn.setText("X");
                    turnOfXPlayer = false;
                    notifyAboutNextMove(nameOPlayer);
                } else {
                    btn.setText("O");
                    turnOfXPlayer = true;
                    notifyAboutNextMove(nameXPlayer);
                }

                switch (id) {
                    case R.id.btnOneOne:
                        if (btn.getText().toString().equals("X")) {
                            turnsOfXPlayer.add(1);
                        } else turnsOfOPlayer.add(1);
                        break;
                    case R.id.btnOneTwo:
                        if (btn.getText().toString().equals("X")) turnsOfXPlayer.add(2);
                        else turnsOfOPlayer.add(2);
                        break;
                    case R.id.btnOneThree:
                        if (btn.getText().toString().equals("X")) turnsOfXPlayer.add(3);
                        else turnsOfOPlayer.add(3);
                        break;
                    case R.id.btnTwoOne:
                        if (btn.getText().toString().equals("X")) turnsOfXPlayer.add(4);
                        else turnsOfOPlayer.add(4);
                        break;
                    case R.id.btnTwoTwo:
                        if (btn.getText().toString().equals("X")) turnsOfXPlayer.add(5);
                        else turnsOfOPlayer.add(5);
                        break;
                    case R.id.btnTwoThree:
                        if (btn.getText().toString().equals("X")) turnsOfXPlayer.add(6);
                        else turnsOfOPlayer.add(6);
                        break;
                    case R.id.btnThreeOne:
                        if (btn.getText().toString().equals("X")) turnsOfXPlayer.add(7);
                        else turnsOfOPlayer.add(7);
                        break;
                    case R.id.btnThreeTwo:
                        if (btn.getText().toString().equals("X")) turnsOfXPlayer.add(8);
                        else turnsOfOPlayer.add(8);
                        break;
                    case R.id.btnThreeThree:
                        if (btn.getText().toString().equals("X")) turnsOfXPlayer.add(9);
                        else turnsOfOPlayer.add(9);
                        break;
                }
            }

            if (numberOfTurns >= 5) {
                checkResults();
            }
        }

        private void checkResults() {

            for (ArrayList<Integer> list : winCombinations) {
                if (turnsOfXPlayer.containsAll(list)) {
                    winner = nameXPlayer;
                    saveResult(winner);
                    break;
                }
                if (turnsOfOPlayer.containsAll(list)) {
                    winner = nameOPlayer;
                    saveResult(nameOPlayer);
                    break;
                }
            }

            if (numberOfTurns == 9 && winner.equals("draw")) {
                saveResult(winner);
            }
        }

        private void saveResult(String winner){
            if(winner.equals("draw")) {
                Toast.makeText(context, "The game ended in a draw", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, winner + " has won!", Toast.LENGTH_SHORT).show();
            }
            String number = "" + (MainActivity.results.size() + 1);
            @SuppressLint("SimpleDateFormat")
            String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            String result = String.format("%s. Match: %s (X) vs. %s (O) \nWinner: %s \nDate: %s",
                    number, nameXPlayer, nameOPlayer, winner, time);
            MainActivity.results.add(result);
            openNextIntent();
        }

        private void openNextIntent() {
            Intent intent = new Intent(context, ResultActivity.class);
            intent.putExtra("firstPlayer", nameXPlayer);
            intent.putExtra("secondPlayer", nameOPlayer);
            startActivity(intent);
            for (Button b : buttons) b.setText("");
            initGame();
        }
    }
}

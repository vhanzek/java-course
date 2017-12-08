package hr.fer.android.jmbag0036482927;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The activity that displays result of the chosen operation or error message
 * if an error has occurred.
 *
 * @author Vjeran
 */
public class DisplayActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        final TextView message = (TextView) findViewById(R.id.tvMessage);
        Button okBtn = (Button) findViewById((R.id.btnOK));
        Button reportBtn = (Button) findViewById(R.id.btnSend);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String operation = getOperationName(extras.getInt("opId"));
            String errorMsg = extras.getString("error");

            if(errorMsg != null){
                String first = extras.getString("first");
                String second = extras.getString("second");
                message.setText(
                   String.format(
                      "While performing %s over entries %s and %s " +
                      "following error occurred: %s.", operation, first, second, errorMsg
                   )
                );
            } else {
                String result = extras.getString("result");
                message.setText(
                   String.format("Result of the %s is %s.", operation, result)
                );
            }
        }

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent(getApplicationContext(), CalculusActivity.class);
                startActivity(returnIntent);
            }
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse("mailto:" + "ana.baotic@infinum.hr")
                );
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.jmbag) + ": dz report");
                emailIntent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());

                startActivity(Intent.createChooser(emailIntent, "Choose email"));
            }
        });
    }

    /**
     * Helper method for getting operation name from ID.
     *
     * @param opId operation ID
     * @return operation name
     */
    private String getOperationName(int opId){
        switch(opId){
            case R.id.rbSum: return "addition";
            case R.id.rbSub: return "subtraction";
            case R.id.rbMul: return "multiplication";
            case R.id.rbDiv: return "division";
            default: return null;
        }
    }

}

package wsu.csc5991.trustcircle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

/**
 * Util class with common methods
 */
public class Util {

    // Shared enum to store the background color
    public enum Shared {
        Data;
        public int backgroundColor;
    }

    // showDialogBox method to display dialog box
    public static void showDialogBox(Context activity, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}

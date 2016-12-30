/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import com.example.android.justjava.R;

import static android.R.attr.name;
import static android.R.id.input;
import static com.example.android.justjava.R.id.chocolate_checkbox;
import static com.example.android.justjava.R.id.whipped_cream_checkbox;
import static com.example.android.justjava.R.string.chocolate;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    // default quanity
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
        //displayPrice(quantity);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        boolean hasWhippedCream = checkWhippedCream(whipped_cream_checkbox);
        boolean hasChocolate = checkWhippedCream(chocolate_checkbox);
        String name = retrieveName();
        int totalPrice = calculatePrice(quantity, hasWhippedCream, hasChocolate);
        createOrderSummary(totalPrice, hasWhippedCream, hasChocolate, name);
    }

    /**
     *
     * @param boxID for id of box to check for
     * @return whether box is checked
     */
    private boolean checkWhippedCream(int boxID) {
        CheckBox checkBox = (CheckBox) findViewById((int) boxID);
        boolean isBoxChecked = ((checkBox)).isChecked();
        return isBoxChecked;
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream == true)
            basePrice += 1;
        if (hasChocolate == true)
            basePrice += 2;

        int price = basePrice * quantity;
        return price;
    }

    /**
     * Retrieves Name of the person for billing Info
     */
    private String retrieveName() {
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        String name = nameEditText.getEditableText().toString();
        if (name.equals("")) {
            name = getResources().getString(R.string.anonymous);
        }
        return name;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method increases the given quantity on the screen.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Context context = getApplicationContext();
            CharSequence text = getResources().getString(R.string.max_cups);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        } else {
            quantity++;
            display(quantity);
        }
    }

    /**
     * This method decreases the given quantity on the screen.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = getResources().getString(R.string.min_cups);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        } else {
            quantity--;
            display(quantity);
        }
    }

    /**
     *
     * @param price as calculated total price
     * @param hasChocolate as whether Chocolate is selected
     * @param hasWhippedCream as whether Whipped Cream is selected
     * @param name as Name of the person for billing
     * @return Order summary
     */
    private void createOrderSummary(int price, boolean hasChocolate, boolean hasWhippedCream, String name) {
//        String message = "Name: " + name;
//        message += "\nAdd whipped cream? " + hasWhippedCream + "\nAdd chocolate? " + hasChocolate;
//        message += "\nQuantity: " + quantity + "\nTotal: ";
//        message += NumberFormat.getCurrencyInstance().format(price) + "\nThank you!";
//        return message;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:sapgit97@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "sapgit97@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.Osubject) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.Oname) + " " + name + "\n" +
                getResources().getString(R.string.OhasWhippedCream) + " " + hasWhippedCream + "\n" +
                getResources().getString(R.string.OhasChocolate) + " " + hasChocolate + "\n" +
                getResources().getString(R.string.Oquantity) + " " + quantity + "\n" +
                getResources().getString(R.string.Ototal) + " " + NumberFormat.getCurrencyInstance().format(price)
                + "\n" + getResources().getString(R.string.Othanks) );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
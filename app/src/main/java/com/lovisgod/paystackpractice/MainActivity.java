package com.lovisgod.paystackpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class MainActivity extends AppCompatActivity {

    private EditText cardNumber, cardCVC, cardYear, cardMonth;
    private Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardCVC = findViewById(R.id.edit_cvc);
        cardNumber = findViewById(R.id.edit_card_number);
        cardYear = findViewById(R.id.edit_expiry_year);
        cardMonth= findViewById(R.id.edit_expiry_month);

        pay = findViewById(R.id.button_perform_transaction);



        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCard();
    }

    private void validateCard(){
        final String card_number = cardNumber.getText().toString().trim();
        final String card_cvc = cardCVC.getText().toString().trim();
        final int card_exp_year = Integer.parseInt( cardYear.getText().toString());
        final int card_exp_month = Integer.parseInt( cardMonth.getText().toString());

        Card card = new Card(card_number, card_exp_month, card_exp_year, card_cvc);
        if (card.isValid()){
            performPayment(card);
        }
    }
        });

    }

    private void performPayment(Card card) {
        final Charge charge = new Charge();
        charge.setCard(card); //sets the card to charge
        charge.setEmail("olifedayo94@gmail.com");
        charge.setAmount(1000);

        PaystackSdk.chargeCard(MainActivity.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                // This is called only after transaction is deemed successful.
                // Retrieve the transaction, and send its reference to your server
                // for verification.
                String paymentReference = transaction.getReference();
                Toast.makeText(MainActivity.this, "Transaction Successful! payment reference: "
                        + paymentReference, Toast.LENGTH_LONG).show();

            }


            @Override
            public void beforeValidate(Transaction transaction) {
                // This is called only before requesting OTP.
                // Save reference so you may send to server. If
                // error occurs with OTP, you should still verify on server.
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //handle error here
                Log.d("TAG", "onError: -> " + error.getMessage()+ " " + transaction.getReference());
            }

        });
    }
}

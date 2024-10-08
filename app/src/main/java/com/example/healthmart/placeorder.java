package com.example.healthmart;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class placeorder extends AppCompatActivity {

    private EditText nameField, addressField, phoneField;
    private Button placeOrderButton;
    private Button datetime;
    private int selectedYear, selectedMonth, selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);

        // Initialize Views
        nameField = findViewById(R.id.editTextName);
        addressField = findViewById(R.id.editTextAddress);
        phoneField = findViewById(R.id.editTextPhone);
        datetime = findViewById(R.id.buttonDatePicker);
        placeOrderButton = findViewById(R.id.buttonPlaceOrder);

        // Initialize date with the current date
        final Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Show DatePickerDialog when clicking the datetime button
        datetime.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    placeorder.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        selectedYear = year;
                        selectedMonth = monthOfYear;
                        selectedDay = dayOfMonth;
                        // Update button text with the selected date
                        datetime.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    },
                    selectedYear, selectedMonth, selectedDay);

            // Show the DatePickerDialog
            datePickerDialog.show();
        });

        // Set listener for Place Order button
        placeOrderButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String address = addressField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();

            // Validate input fields
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(placeorder.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                return; // Exit the method if validation fails
            }

            // Validate phone number (basic validation)
            if (phone.length() < 10) {
                Toast.makeText(placeorder.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                return; // Exit the method if validation fails
            }

            // Get selected delivery date from DatePickerDialog
            Calendar deliveryDate = Calendar.getInstance();
            deliveryDate.set(selectedYear, selectedMonth, selectedDay);

            // Show success message and navigate to BuyMedicine activity
            Toast.makeText(placeorder.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(placeorder.this, BuyMedicine.class);
            startActivity(intent);

            // Add code here to save the order details (name, address, phone, deliveryDate) to Firebase or any backend
        });
    }
}

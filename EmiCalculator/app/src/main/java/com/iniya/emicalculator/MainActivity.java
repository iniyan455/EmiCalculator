package com.iniya.emicalculator;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import static com.iniya.emicalculator.EmiFormula.emi_CalculatorFormula;
import static com.iniya.emicalculator.EmiFormula.emi_CalculatorFormula;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Emi> mArrayList;
    MaterialEditText edtPrincipal, edtRoi, edtDuration;
    RadioButton radioYear, radioMonth;
    Button btnPrincipal, btnTotalPay, btnEmi, btnInterest;
    private static int calculateMonth = 1;
    double emi;
    /**
     * List For EMI Response
     **/
    AlertDialog alertDialog;

    private double mail_principal, mail_rate, mail_duration;
    private List<Emi> emiPerMonthBasedYearlyList = new ArrayList<>();
    private List<Emi> emiMonthly = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        mArrayList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        hideSoftKeyboard();
    }

    private void initViews() {

        radioMonth = (RadioButton) findViewById(R.id.month);
        radioYear = (RadioButton) findViewById(R.id.year);


        btnEmi = (Button) findViewById(R.id.btnemi);

        btnTotalPay = (Button) findViewById(R.id.totalpay);

        btnInterest = (Button) findViewById(R.id.percent);
        //btnInterest=(Button)findViewById(R.id.btnemi);


        btnPrincipal = (Button) findViewById(R.id.btnprincipal);

        radioMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtPrincipal.getText().toString().isEmpty() && !edtRoi.getText().toString().isEmpty() && !edtDuration.getText().toString().isEmpty()) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            radioYear.setChecked(false);
                            radioMonth.setChecked(true);
                            emi_YearlySplittedMonth(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));

                            edtDuration.setText("" + (Integer.parseInt(edtDuration.getText().toString().trim().replace("", "")) * 12));

                        }//public void run() {
                    });
                }
            }
        });

        radioYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtPrincipal.getText().toString().isEmpty() && !edtRoi.getText().toString().isEmpty() && !edtDuration.getText().toString().isEmpty()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            radioYear.setChecked(true);
                            radioMonth.setChecked(false);
                            emi_Yearly(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));

//
//                            if(edtDuration.getText().toString().equals("1")){
//                                edtDuration.setText("" + (Integer.parseInt(edtDuration.getText().toString().trim().replace("", "")) * 12));
//
//                            }else
                            edtDuration.setText("" + (Integer.parseInt(edtDuration.getText().toString().trim().replace("", "")) / 12));


                        }//public void run() {
                    });
                }

            }
        });
        edtPrincipal = (MaterialEditText) findViewById(R.id.principal);

        edtRoi = (MaterialEditText) findViewById(R.id.rate);


        edtDuration = (MaterialEditText) findViewById(R.id.duration);

        edtPrincipal.setText("1000");
        edtRoi.setText("10");
        edtDuration.setText("1");


        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        if (!edtPrincipal.getText().toString().isEmpty() && !edtRoi.getText().toString().isEmpty() && !edtDuration.getText().toString().isEmpty()) {
            if (radioMonth.isChecked())
                emi_Yearly(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));
            else
                emi_YearlySplittedMonth(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));
        }

        edtPrincipal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtPrincipal.getText().toString().isEmpty() && !edtRoi.getText().toString().isEmpty() && !edtDuration.getText().toString().isEmpty()) {
                    if (radioMonth.isChecked())
                        emi_YearlySplittedMonth(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));

                    else
                        emi_Yearly(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));


                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // hideSoftKeyboard();

            }
        });


        edtRoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!edtPrincipal.getText().toString().isEmpty() && !edtRoi.getText().toString().isEmpty() && !edtDuration.getText().toString().isEmpty()) {
                    if (radioMonth.isChecked())
                        emi_YearlySplittedMonth(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));

                    else
                        emi_Yearly(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));


                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //    hideSoftKeyboard();


            }
        });


        edtDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!edtPrincipal.getText().toString().isEmpty() && !edtRoi.getText().toString().isEmpty() && !edtDuration.getText().toString().isEmpty()) {
                    if (radioMonth.isChecked())
                        emi_YearlySplittedMonth(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));

                    else
                        emi_Yearly(Double.parseDouble(edtPrincipal.getText().toString()), Double.parseDouble(edtRoi.getText().toString()), Double.parseDouble(edtDuration.getText().toString()));


                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //hideSoftKeyboard();

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.sendmail:

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                //  dialogBuilder.setCancelable(false);
// ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.sendmail, null);
                dialogBuilder.setView(dialogView);

                final MaterialEditText editText = (MaterialEditText) dialogView.findViewById(R.id.edtmail);

                Button btnSend = (Button) dialogView.findViewById(R.id.btnmail);


                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText.getText().toString().contains(".com")) {
                            sendEmiMail(mArrayList, mail_principal, mail_rate, mail_duration, editText.getText().toString());
                            Toast.makeText(getApplicationContext(), "" + mArrayList.size()
                                    , Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(getApplicationContext(), "Please Give Valid Email", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog = dialogBuilder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void RecyclerviewAdapter() {

        int total_interest = 0;
        int total_payableamount = 0;
        double permonth_emi = 0;
        for (Emi emi : mArrayList) {
            total_interest += emi.getInterest_permonth();
            total_payableamount += emi.getPrincipal_paidamount();
            permonth_emi = emi.getInterest_permonth();

            Log.e("MainActivity",emi.getInterest_permonth()+"total"+emi.getTotal_emiamount());
        }




        btnPrincipal.setText("Principal: " + edtPrincipal.getText().toString());



        btnEmi.setText("Emi: " + new DecimalFormat("##.####").format(emi));

        btnTotalPay.setText("Total Pay: " + new DecimalFormat("##.####").format((total_payableamount + (total_interest / Double.parseDouble(edtPrincipal.getText().toString()) * 100))));
       btnInterest.setText("Interest: " + new DecimalFormat("##.####").format(total_interest / Double.parseDouble(edtPrincipal.getText().toString()) * 100));

       // btnInterest.setText("Interest: " + new DecimalFormat("##.####").format( ((Double.parseDouble(edtPrincipal.getText().toString()) / Double.parseDouble(edtRoi.getText().toString()))/12)+Double.parseDouble(edtPrincipal.getText().toString())));





        EmiAdapter mAdapter = new EmiAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

    }

    public List<Emi> emi_Yearly(double principal, double rate, double duration) {
        mArrayList.clear();
        mail_principal = principal;
        mail_rate = rate;
        mail_duration = duration;

        double time;

        time = duration;

        emi = emi_CalculatorFormula(principal, rate, time);


        for (int i = 1; i <= time; i++) {


            /** To get Interest of  per month and total interest of year ,opening and closing amount **/
            principal = emiInterestCalculateFunction(principal, rate, emi, i);

        }


        RecyclerviewAdapter();
        return mArrayList;

    }

    public List<Emi> emi_YearlySplittedMonth(double principal, double rate, double duration) {

        calculateMonth = 1;
        emiPerMonthBasedYearlyList.clear();
        mArrayList.clear();
        mail_principal = principal;
        mail_rate = rate;
        mail_duration = duration;

        double time;

        time = duration / 12;

        emi = emi_CalculatorFormula(principal, rate, time);

        emiInterestCalculateYearSplitMonthly(principal, rate, emi, time * 12);

        RecyclerviewAdapter();
        return emiPerMonthBasedYearlyList;

    }


    private double emiInterestCalculateYearSplitMonthly(double p, double r, double emivalue, double time) {

        double temp = p;
        double initialamount, interest, actual_amount;


        for (int i = 1; i <= time; i++) {

            initialamount = (temp * (r / 100) / 12);
            interest = emivalue - initialamount;
            actual_amount = temp - interest;


            mArrayList.add(new Emi(Math.round(temp), Math.round(emivalue * 12), Math.round(initialamount), Math.round(interest), Math.round(actual_amount), calculateMonth++, new Date()));

            temp = actual_amount;


        }


        return temp;
    }

    double emiInterestCalculateFunction(double p, double r, double emi, double year) {
        double paymentofyear = 0;
        double temp = p;
        double initialamount, interest, actual_amount;
        double totalinterest = 0;
        double closingbalance;

        for (int i = 0; i < 12; i++) {

            initialamount = (temp * (r / 100) / 12);
            interest = emi - initialamount;
            actual_amount = temp - interest;
            temp = actual_amount;
            closingbalance = temp;

            totalinterest += initialamount;
            paymentofyear = p - closingbalance;
        }
        mArrayList.add(new Emi(Math.round(p), Math.round(emi * 12), Math.round(totalinterest), Math.round(paymentofyear), Math.round(temp), year, new Date()));

        return (temp);
    }


    public void sendEmiMail(List<Emi> emilist, double principal, double rate, double duration, String email) {
        int total_interest = 0;
        int total_payableamount = 0;
        double permonth_emi = 0;
        StringBuilder table = new StringBuilder();
        for (Emi emi : emilist) {

            total_interest += emi.getInterest_permonth();
            total_payableamount += emi.getPrincipal_paidamount();
            permonth_emi = emi.getTotal_emiamount();


        }


        for (Emi anEmilist : emilist) {


            table.append("<tr>").append("<td>").append(anEmilist.getOpening_balance()).append("</td>\n").append("    <td>").append(anEmilist.getTotal_emiamount()).append("</td>\n").append("    <td>").append(anEmilist.getInterest_permonth()).append("</td>\n").append("    \n").append("    <td>").append(anEmilist.getPrincipal_paidamount()).append("</td>\n").append("\t<td>").append(anEmilist.getClosing_balance()).append("</td>").append("</tr>");
        }
        String html = "`<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<style>\n" +
                "\n" +
                "html,body{\n" +
                "\tfont-family:Times New Roman;\n" +
                "\tborder: 1px solid #ddd;\n" +
                "    padding: 15px;\n" +
                "}\n" +
                " #customers {\n" +
                "    font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n" +
                "    border-collapse: collapse;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "#customers td, #customers th {\n" +
                "    border: 1px solid #ddd;\n" +
                "    padding: 8px;\n" +
                "}\n" +
                "\n" +
                "#customers tr:nth-child(even){background-color: #f2f2f2;}\n" +
                "\n" +
                "#customers tr:hover {background-color: #ddd;}\n" +
                "\n" +
                "#customers th {\n" +
                "    padding-top: 12px;\n" +
                "    padding-bottom: 12px;\n" +
                "    text-align: left;\n" +
                "    background-color: #4CAF50;\n" +
                "    color: white;\n" +
                "}\n" +
                ".heading{\n" +
                "text-align: center;\n" +
                "    color: #3F51B5;\n" +
                "}\n" +
                ".flexdiv{\n" +
                "\tdisplay:flex;\n" +
                "\tflex-direction: row;\n" +
                "\tflex-wrap:wrap;\n" +
                "}\n" +
                ".flexdiv div{\n" +
                "flex:1;\n" +
                "padding:20px;\n" +
                "margin:10px;\n" +
                "border:1px solid #ddd;\n" +
                "text-align:center;\n" +
                "}\n" +
                "\n" +
                ".background1{\n" +
                "\tbackground:#e4fffc;\n" +
                "}\n" +
                ".background2{\n" +
                "\tbackground:#b0d7f7;\n" +
                "}\n" +
                ".background3{\n" +
                "\tbackground:#d9c4ff;\n" +
                "}\n" +
                ".background4{\n" +
                "\tbackground:#ecc4d2;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h3 class=\"heading\">EMI CALCULATOR</h3>\n" +
                "<div>\n" +
                "<p>Loan Amount is <b>" + Math.round(principal) + "</b></p>\n" +
                "<p>No. of Year is <b>" + Math.round(duration) + "</b></p>\n" +
                "<p>Rate of Interest [ROI] is <b>" + Math.round(rate) + "</b></p>\n" +
                "</div>\n" +
                "<div class=\"flexdiv\">\n" +
                "\t<div class=\"background1\">\n" +
                "\t<p>Monthly EMI</p>\n" +
                "\t<p style=\"color:#009688;\"><b>" + permonth_emi + "</b></b></p>\n" +
                "\t</div>\n" +
                "\t<div class=\"background2\">\n" +
                "\t<p>Total Interest</p>\n" +
                "\t<p style=\"color:#2196F3;\"><b>" + total_interest + "</b></p>\n" +
                "\t</div>\n" +
                "\t<div class=\"background3\">\n" +
                "\t<p>Payable Amount</p>\n" +
                "\t<p style=\"color:#673AB7;\"><b>" + total_payableamount + "</b></p>\n" +
                "\t</div>\n" +
                "\t<div class=\"background4\">\n" +
                "\t<p>Interest Percentage</p>\n" +
                "\t<p style=\"color:#E91E63;\"><b>" + Double.parseDouble(new DecimalFormat("##.##").format((total_interest / emilist.get(0).getOpening_balance()) * 100)) + "</b></p>\n" +
                "\t</div>\n" +
                "</div>\n" +
                "<div>\n" +
                "\t<table id=\"customers\">\n" +
                "  <tr>\n" +
                "    <th>Opening Balance</th>\n" +
                "    <th>Total EMI Amount</th>\n" +
                "    <th>Interest Per Month\t\t\n" +
                "\t\t\t</th>\n" +
                "    <th>Principal Paid Amount</th>\n" +
                "    <th>Closing Balance</th>\n" +
                "  </tr>\n" + table + "</table>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n`";


        final String username = "iniyan455@gmail.com";
        final String password = "THAVAMANI8";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new GMailAuthenticator(username, password));

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("iniyan455@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Emi  Report");
            message.setText("Dear Sir/Mam ");

            MimeBodyPart messageBodyPart = new MimeBodyPart();


            MimeMultipart multipart = new MimeMultipart();
            BodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(html, "text/html");
            //   htmlPart.setContent(html, true);
            htmlPart.setDisposition(BodyPart.INLINE);

            multipart.addBodyPart(htmlPart);

            //  String file = pdfFileR2.getAbsolutePath();
            //  String fileName = reportName_Pdf + "_" + user_month_name;

            //  FileDataSource source = new FileDataSource(file);
            //   messageBodyPart.setDataHandler(new DataHandler(source));
            //  messageBodyPart.setFileName(fileName);
            //  messageBodyPart.setText("Dear Sir/Mam ");
            multipart.addBodyPart(messageBodyPart);


            message.setContent(html, "text/html");

            //  message.setContent(multipart, "text/pdf");


            Transport.send(message);


            Toast.makeText(getApplicationContext(), "Send Mail", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
            // Toast.makeText(getApplicationContext(), e.toString()., Toast.LENGTH_SHORT).show();

        }


    }

    protected void hideSoftKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

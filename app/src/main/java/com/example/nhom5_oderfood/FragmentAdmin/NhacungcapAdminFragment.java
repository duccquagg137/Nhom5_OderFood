package com.example.nhom5_oderfood.FragmentAdmin;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.example.nhom5_oderfood.Adapter.NhaCungCapAdminAdapter;
import com.example.nhom5_oderfood.DAO.NhaCungCapDAO;
import com.example.nhom5_oderfood.DTO.NhaCungCap;

import com.example.nhom5_oderfood.R;
import com.example.nhom5_oderfood.databinding.FragmentNhacungcapBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NhacungcapAdminFragment extends Fragment {
    NhaCungCapDAO nhaCungCapDAO;
    ArrayList<NhaCungCap> arrayList;
    NhaCungCapAdminAdapter adapter;
    FragmentNhacungcapBinding binding;

    String tenncc,thongtin,lienhe,Email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNhacungcapBinding.inflate(inflater, container, false);
        nhaCungCapDAO = new NhaCungCapDAO(getContext());
        loatData();
        listener();
        return binding.getRoot();
    }
    public void loatData(){
        arrayList = nhaCungCapDAO.getDSNCC();
        adapter = new NhaCungCapAdminAdapter(getContext() , arrayList);
        binding.rcvNccadmin.setAdapter(adapter);
    }

    public void listener(){
        binding.btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogadd();
            }
        });
    }




    @SuppressLint("MissingInflatedId")
    private void dialogadd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogaddncc,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText ten = view.findViewById(R.id.addtenncc);
        TextInputEditText tt = view.findViewById(R.id.addthongtinncc);
        TextInputEditText lhe = view.findViewById(R.id.addlienhencc);
        TextInputEditText email = view.findViewById(R.id.addemailncc);

        Button add = view.findViewById(R.id.btnAdd);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhaCungCap nhaCungCap = new NhaCungCap();

                tenncc= ten.getText().toString();
                thongtin = tt.getText().toString();
                lienhe = lhe.getText().toString();
                Email = email.getText().toString();

                nhaCungCap.setTenNhaCC(tenncc);
                nhaCungCap.setThongTin(thongtin);
                nhaCungCap.setLienHe(lienhe);
                nhaCungCap.setEmail(Email);
                boolean check = nhaCungCapDAO.addNCC(nhaCungCap);
                if (ten.length() == 0 || tt.length() == 0 || lhe.length() == 0 || email.length() == 0){
                    Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (check) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loatData();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }

            }
        });

    }
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean validateEmail(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
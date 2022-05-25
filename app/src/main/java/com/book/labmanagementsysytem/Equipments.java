package com.book.labmanagementsysytem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.HardwarePropertiesManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Equipments extends Fragment {
    private ArrayList<equipmentlistitems> itemArrayList;  //List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private FirebaseFirestore db;
    private ListView listView; // Listview
    LinearLayout spinnerlayout;
    private boolean success = false; // boolean
    EditText edtKeyword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_equipments, container, false);
        if(getActivity()!=null) getActivity().setTitle("Equipments");
        spinnerlayout = v.findViewById(R.id.layoutspinner);
        spinnerlayout.setVisibility(View.GONE);
        edtKeyword = (EditText) v.findViewById(R.id.edtKeyword);
        listView = (ListView) v.findViewById(R.id.list); //Listview Declaration
        edtKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        getproducts();

        ImageButton btnaddproduct = v.findViewById(R.id.btnadd);
        btnaddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(requireActivity(), Addequipment.class);
                startActivity(i);
            }
        });
        return v;
    }


    private void getproducts()
    {
   requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        spinnerlayout.setVisibility(View.VISIBLE);
        //get Database
        db = FirebaseFirestore.getInstance();
        itemArrayList = new ArrayList<equipmentlistitems>();
        db.collection("Equipments").addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot value,
                            @Nullable FirebaseFirestoreException e) {
            if (e != null) {

                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                return;
            }

            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType().equals("added")) {

                }
            }

            for (QueryDocumentSnapshot document : value) {
                itemArrayList.add(new equipmentlistitems(document.getId(), document.getString("Name"),document.getString("Code"),document.getString("Location"),document.getString("Img")));

            }
            myAppAdapter = new MyAppAdapter(itemArrayList, getContext());
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setAdapter(myAppAdapter);
            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            spinnerlayout.setVisibility(View.GONE);

        }
    });

    }

    public class MyAppAdapter extends BaseAdapter        //has a class viewholder which holds
    {
        public class ViewHolder
        {
            TextView textName;
            TextView textCode;
            ImageView imageView;
            Button btncart;


        }

        public List<equipmentlistitems> parkingList;

        public Context context;
        ArrayList<equipmentlistitems> arraylist;

        private MyAppAdapter(List<equipmentlistitems> apps, Context context)
        {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<equipmentlistitems>();
            arraylist.addAll(parkingList);
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) // inflating the layout and initializing widgets
        {

            View rowView = convertView;
            ViewHolder viewHolder= null;


            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_item, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.textName = (TextView) rowView.findViewById(R.id.txtequipmentname);
                viewHolder.textCode = (TextView) rowView.findViewById(R.id.txtproductcode);
                viewHolder.imageView = (ImageView) rowView.findViewById(R.id.itemThumbnail);


                rowView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {



                    }
                });
                rowView.setTag(viewHolder);
            }
            else
            {

                viewHolder = (ViewHolder) convertView.getTag();

            }
            // here setting up names and images
            viewHolder.textName.setText(parkingList.get(position).getName());
            viewHolder.textCode.setText(parkingList.get(position).getCode());
            Glide
                    .with(Equipments.this)
                    .load(parkingList.get(position).getImg())
                    .centerCrop()
                    .placeholder(R.drawable.noimage)
                    .into(viewHolder.imageView);



            return rowView;


        }


    }
}
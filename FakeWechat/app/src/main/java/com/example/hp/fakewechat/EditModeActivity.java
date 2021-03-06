package com.example.hp.fakewechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class EditModeActivity extends AppCompatActivity {

    public class PersonAdapter extends RecyclerView.Adapter<EditModeActivity.PersonAdapter.ViewHolder> {
        private List<Person> mPersonList;

        class ViewHolder extends RecyclerView.ViewHolder {
            View personView;
            ImageView img;
            TextView name;

            public ViewHolder(View view) {
                super(view);
                personView = view;
                img = (ImageView) view.findViewById(R.id.person_image);
                name = (TextView) view.findViewById(R.id.person_name);
            }
        }

        public PersonAdapter(List<Person> personList){
            mPersonList = personList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.person_item,viewGroup,false);
            final EditModeActivity.PersonAdapter.ViewHolder holder = new EditModeActivity.PersonAdapter.ViewHolder(view);
            holder.personView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Person person = mPersonList.get(position);
                    Intent intent = new Intent();
                    intent.setClass(EditModeActivity.this,edit.class);
                    intent.putExtra("person",person);
                    startActivityForResult(intent,1);
                }
            });
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Person person = mPersonList.get(position);
                    Intent intent = new Intent();
                    intent.setClass(EditModeActivity.this,edit.class);
                    intent.putExtra("person",person);
                    startActivityForResult(intent,1);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull EditModeActivity.PersonAdapter.ViewHolder viewHolder, int i) {
            Person person = mPersonList.get(i);
            Bitmap bitmap = BitmapFactory.decodeFile(person.getImg());
            viewHolder.img.setImageBitmap(bitmap);
            viewHolder.name.setText(person.getName());
        }

        @Override
        public int getItemCount() {
            return mPersonList.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mode);
        mydatabaseHelper = new MydatabaseHelper(this,"Person.db",null,1);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_edit);
        refresh();
    }

    private MydatabaseHelper mydatabaseHelper;
    private List<Person> personList = new ArrayList<>();
    private RecyclerView recyclerView;
    private final int Edit = 1;

    private void refresh(){
        personList.clear();
        SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query("Person",null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String img_path = cursor.getString(cursor.getColumnIndex("img"));
                personList.add(new Person(id, name,img_path));
            }while(cursor.moveToNext());
        }
        cursor.close();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        EditModeActivity.PersonAdapter personAdapter = new EditModeActivity.PersonAdapter(personList);
        recyclerView.setAdapter(personAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        refresh();
    }
}

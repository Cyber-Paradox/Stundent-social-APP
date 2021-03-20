package com.example.studentappmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.studentappmessenger.Adapters.AdapterParticipantAdd;
import com.example.studentappmessenger.Models.ModelUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupParticipantAddActivity extends AppCompatActivity {
private RecyclerView usersRv;
private ActionBar actionBar;
private FirebaseAuth firebaseAuth;
private String groupId;
private String myGroupRole;
private ArrayList<ModelUser> userList;
private AdapterParticipantAdd adapterParticipantAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_participant_add);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Add Participants");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseAuth=FirebaseAuth.getInstance();

        usersRv=findViewById(R.id.usersRv);
       groupId= getIntent().getStringExtra("groupId");
       loadGroupInfo();

    }

    private void getAllUsers() {
        userList= new ArrayList<>();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    ModelUser modelUser=ds.getValue(ModelUser.class);
                    if(!firebaseAuth.getUid().equals(modelUser.getUid())){
                        userList.add(modelUser);
                    }
                }
                adapterParticipantAdd=new AdapterParticipantAdd(GroupParticipantAddActivity.this,userList,""+groupId,""+myGroupRole);
                 usersRv.setAdapter(adapterParticipantAdd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadGroupInfo() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Groups");
        final DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Groups");
          ref.orderByChild("groupId").equalTo(groupId).addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()){
                        String groupId=""+ds.child("groupId").getValue();
                        final String groupTitle=""+ds.child("groupTitle").getValue();
                        String groupDescription=""+ds.child("groupDescription").getValue();
                        String groupIcon=""+ds.child("groupIcon").getValue();
                         String createdBy=""+ds.child("createdBy").getValue();
                         String timeStamp=""+ds.child("timeStamp").getValue();
                         actionBar.setTitle("Add Participants");
                         ref1.child(groupId).child("Participants").child(firebaseAuth.getUid())
                                 .addValueEventListener(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                         if(snapshot.exists()){
                                             myGroupRole=""+snapshot.child("role").getValue();
                                             actionBar.setTitle(groupTitle+ "("+myGroupRole+")");
                                                    getAllUsers();
                                         }
                                     }

                                     @Override
                                     public void onCancelled(@NonNull DatabaseError error) {

                                     }
                                 });
                    }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
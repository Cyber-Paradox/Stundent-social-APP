package com.example.studentappmessenger.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.studentappmessenger.Adapters.AdapterGroupChatList;
import com.example.studentappmessenger.GroupCreateActivity;
import com.example.studentappmessenger.MainActivity;
import com.example.studentappmessenger.Models.ModelGroupChatList;
import com.example.studentappmessenger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupChatsFragment extends Fragment {

   private RecyclerView groupsRv;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private FirebaseAuth firebaseAuth;
private ArrayList<ModelGroupChatList>groupChatLists;
private AdapterGroupChatList adapterGroupChatList;
    public GroupChatsFragment() {
        // Required empty public constructor
    }

    public static GroupChatsFragment newInstance(String param1, String param2) {
        GroupChatsFragment fragment = new GroupChatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth= FirebaseAuth.getInstance();
        View view= inflater.inflate(R.layout.fragment_group_chats,container,false);
        groupsRv= view.findViewById(R.id.groupsRv);
        loadGroupChatsList();
return view;
    }

    private void loadGroupChatsList() {
        groupChatLists= new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Groups");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               groupChatLists.clear();
               for (DataSnapshot ds: dataSnapshot.getChildren()){

                  if(ds.child("Participants").child(firebaseAuth.getUid()).exists()){
                      ModelGroupChatList model = ds.getValue(ModelGroupChatList.class);
                      groupChatLists.add(model);
                  }

               }
               adapterGroupChatList= new AdapterGroupChatList(getActivity(),groupChatLists);
             groupsRv.setAdapter(adapterGroupChatList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void searchGroupChatsList(final String query) {
        groupChatLists= new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Groups");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupChatLists.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    if(!ds.child("Participants").child(firebaseAuth.getUid()).exists()){
                        if(ds.child("groupTitle").toString().toLowerCase().contains(query.toLowerCase())){
                            ModelGroupChatList model = ds.getValue(ModelGroupChatList.class);
                            groupChatLists.add(model);
                        }

                    }

                }
                adapterGroupChatList= new AdapterGroupChatList(getActivity(),groupChatLists);
                groupsRv.setAdapter(adapterGroupChatList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);

        menu.findItem(R.id.action_add_post).setVisible(false);
        menu.findItem(R.id.action_add_participant).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_groupinfo).setVisible(false);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!TextUtils.isEmpty(s.trim())){
                    searchGroupChatsList(s);
                }
                else {
                    loadGroupChatsList();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!TextUtils.isEmpty(s.trim())){
                    searchGroupChatsList(s);
                }
                else {
                    loadGroupChatsList();
                }

                return false;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();
        }
     
        else if (id==R.id.action_create_group){
            startActivity(new Intent(getActivity(), GroupCreateActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkUserStatus() {
        FirebaseUser user= firebaseAuth.getCurrentUser();
            if (user==null){
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        }

}
package com.example.mikkel.workoutplanner.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mikkel.workoutplanner.Interfaces.onPositiveClick;
import com.example.mikkel.workoutplanner.MainActivity;
import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.adapters.TabsAdapter;
import com.example.mikkel.workoutplanner.data.Database.Plan;
import com.example.mikkel.workoutplanner.data.StateData.PlansFragmentState;
import com.example.mikkel.workoutplanner.dialogs.DialogNewPlan;
import com.example.mikkel.workoutplanner.singletons.DataManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Fragment_Plans extends NavigationFragment implements onPositiveClick
{
    private View view;
    private TabLayout tabsLayout;
    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;
    private PlansFragmentState state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plans,container,false);

        return view;
    }

    @Override
    protected void onCreateNavigation() {
        super.onCreateNavigation();
        setToolbarTitle("Workout plans");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageButton imageButton = view.findViewById(R.id.workplanAddButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onNewPlanClick();
            }
        });

        tabsAdapter = new TabsAdapter(getChildFragmentManager());

        viewPager = view.findViewById(R.id.PlansViewPager);
        viewPager.setAdapter(tabsAdapter);

        tabsLayout = view.findViewById(R.id.WorkoutPlanTabs);
        tabsLayout.setupWithViewPager(viewPager);

        if(DataManager.getInstance().getState(PlansFragmentState.class) == null)
            state = DataManager.getInstance().addState(new PlansFragmentState());
        else
            state = DataManager.getInstance().getState(PlansFragmentState.class);

        tabsLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                state.setSelectedTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    private void syncPlans()
    {
        tabsAdapter.clear();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot data = dataSnapshot.child(DataManager.getInstance().get_user().getUid());
                for (DataSnapshot d : data.getChildren())
                {
                    Plan plan = d.getValue(Plan.class);
                    tabsAdapter.addItem(new Fragment_Exercises(),plan.getName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void onNewPlanClick()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        DialogNewPlan dialog = new DialogNewPlan();
        dialog.setListener(this);
        dialog.show(getActivity().getFragmentManager(), "NewPlanDialog");
    }


    @Override
    public void onResume() {
        super.onResume();
        TabLayout.Tab tab = tabsLayout.getTabAt(state.getSelectedTab());
        if(tab != null)
            tab.select();
    }

    @Override
    public void onPositiveClicked(Object data) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        Plan plan = new Plan();
        plan.setName((String)data);
        database.child(DataManager.PlansDataName).child(DataManager.getInstance().get_user().getUid()).push().setValue(plan);
        syncPlans();
    }
}

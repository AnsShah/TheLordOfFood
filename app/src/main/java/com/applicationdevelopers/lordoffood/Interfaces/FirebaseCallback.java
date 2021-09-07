package com.applicationdevelopers.lordoffood.Interfaces;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import androidx.annotation.NonNull;

/**
 * Designed & Developed By Syed Ans Ali
 * Date: 03/07/2021
 */
public interface FirebaseCallback {
    void onDataChange(@NonNull DataSnapshot dataSnapshot);
    void onCancelled(@NonNull DatabaseError databaseError);
}

package com.example.firstapplication.helper;

import com.example.firstapplication.model.UserModel;

import io.realm.Realm;

public class RealmHelper {
    private Realm mRealm;

    public RealmHelper () {
        mRealm  = Realm.getDefaultInstance();
    }

    public void saveUser(UserModel userModel) {
        mRealm.beginTransaction();
        mRealm.insert(userModel);
        mRealm.commitTransaction();
    }

    public void deleteUser() {
        mRealm.delete(UserModel.class);
    }

}

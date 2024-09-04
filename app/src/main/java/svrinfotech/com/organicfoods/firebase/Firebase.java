package svrinfotech.com.organicfoods.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {

    public static FirebaseAuth getFirebaseAuth() {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        return firebaseAuth;
    }

    public static DatabaseReference getUserReference() {
        DatabaseReference userReference=FirebaseDatabase.getInstance().getReference().child("user");
        return userReference;
    }
}

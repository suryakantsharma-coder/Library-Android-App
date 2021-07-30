package com.smart.neet_jeefullpakage;


import com.google.firebase.database.IgnoreExtraProperties;

import androidx.annotation.Keep;

@Keep
@IgnoreExtraProperties
public class DO {

     String student_Name;
     String student_Unique_ID;

    public DO() {
    }

    public DO(String student_Name, String student_Unique_ID) {
        this.student_Name = student_Name;
        this.student_Unique_ID = student_Unique_ID;
    }

    public String getStudent_Name() {
        return student_Name;
    }

    public void setStudent_Name(String student_Name) {
        this.student_Name = student_Name;
    }

    public String getStudent_Unique_ID() {
        return student_Unique_ID;
    }

    public void setStudent_Unique_ID(String student_Unique_ID) {
        this.student_Unique_ID = student_Unique_ID;
    }
}

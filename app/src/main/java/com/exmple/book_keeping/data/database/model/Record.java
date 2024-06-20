package com.exmple.book_keeping.data.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// Record.java
@Entity(tableName = "records")
public class Record implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String type; // "income" or "expense"
    public String category;
    public double amount;
    public String date;
    public String note;
}

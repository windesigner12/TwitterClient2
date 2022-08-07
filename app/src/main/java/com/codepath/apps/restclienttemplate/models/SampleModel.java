package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the Room guide for more details:
 * https://github.com/codepath/android_guides/wiki/Room-Guide
 *
 */
@Entity
public class SampleModel {

	@PrimaryKey(autoGenerate = true)
	Long id;

	// Define table fields
	@ColumnInfo
	private String name;

	@ColumnInfo
	public String body;

	@ColumnInfo
	public String createdAt;


	//public User user;

	public SampleModel() {
		super();
	}

	// Parse model from JSON
	public SampleModel(JSONObject object) throws JSONException {
		super();

		try {
			this.name = object.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}


		this.body = object.getString("text"); ;


		this.createdAt = object.getString("created_at");

		//this.user = User.fromJson(object.getJSONObject("user"));
	}

	 //Getters
	public String getName() {
		return name;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
}

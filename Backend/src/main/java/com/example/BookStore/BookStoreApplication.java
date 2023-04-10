package com.example.BookStore;

import com.example.BookStore.Repository.DBConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class BookStoreApplication {
	public  static DBConnection dbConnection ;

	static void set_all_dbConnection(){
		 dbConnection = new DBConnection();
		 dbConnection.connect();


	}

	public static void main(String[] args) {
		System.out.println("Hello World");
		set_all_dbConnection();
		SpringApplication.run(BookStoreApplication.class, args);
	}

}

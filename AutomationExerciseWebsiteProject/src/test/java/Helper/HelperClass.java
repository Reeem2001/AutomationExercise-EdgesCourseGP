/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

/**
 *
 * @author Reem
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;



public class HelperClass {
    private static final String TestPrjRoot = "src/test/java/";
    private static final String TestDataFolder = "TestData/";
    
    public static String ReadFromFile(String fileName, String Key) throws FileNotFoundException
    {
        FileReader reader = new FileReader(TestPrjRoot+TestDataFolder+fileName);
        JsonElement e1 = JsonParser.parseReader(reader);
        return e1.getAsJsonObject().get(Key).getAsString();
    }

    /**
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static User[] ReadUsers (String fileName) throws FileNotFoundException
    {
        FileReader reader = new FileReader(TestPrjRoot+TestDataFolder+fileName);
        User[] ListOfUserData = new Gson().fromJson(reader, User[].class);
        return ListOfUserData;
    }
    
    public static RegisteredUser[] ReadRegisteredUsers (String fileName) throws FileNotFoundException
    {
        FileReader reader = new FileReader(TestPrjRoot+TestDataFolder+fileName);
        RegisteredUser[] ListOfRegisteredUser = new Gson().fromJson(reader, RegisteredUser[].class);
        return ListOfRegisteredUser;
    }
    
     public static Reviews[] ReadReviews (String fileName) throws FileNotFoundException
    {
        FileReader reader = new FileReader(TestPrjRoot+TestDataFolder+fileName);
        Reviews[] ListOfReviews = new Gson().fromJson(reader, Reviews[].class);
        return ListOfReviews;
    }
     
     public static FullUserData[] ReadFullUser (String fileName) throws FileNotFoundException
    {
        FileReader reader = new FileReader(TestPrjRoot+TestDataFolder+fileName);
        FullUserData[] ListOfUserData = new Gson().fromJson(reader, FullUserData[].class);
        return ListOfUserData;
    }
     
      public static ContactUsForm[] ReadContactUsFormData (String fileName) throws FileNotFoundException
    {
        FileReader reader = new FileReader(TestPrjRoot+TestDataFolder+fileName);
        ContactUsForm[] ListOfData = new Gson().fromJson(reader, ContactUsForm[].class);
        return ListOfData;
    }
      
      
    public static Product[] ReadProducts(String fileName) throws FileNotFoundException {
        FileReader reader = new FileReader(TestPrjRoot + TestDataFolder + fileName);
        Product[] listOfProducts = new Gson().fromJson(reader, Product[].class);
        return listOfProducts;
    }
  
     
     
    
}

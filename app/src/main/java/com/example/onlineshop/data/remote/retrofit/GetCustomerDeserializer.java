package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.BillingAddress;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.ShippingAddress;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


import java.lang.reflect.Type;

public class GetCustomerDeserializer implements JsonDeserializer<Customer> {

    @Override
    public Customer deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        Customer customer;

        JsonObject bodyObject = json.getAsJsonObject();
        JsonObject customerObject = bodyObject.getAsJsonObject("customer");
        int id = customerObject.get("id").getAsInt();
        String created_at = customerObject.get("created_at").getAsString();
            String email = customerObject.get("email").getAsString();
            String first_name = customerObject.get("first_name").getAsString();
            String last_name = customerObject.get("last_name").getAsString();
            String username = customerObject.get("username").getAsString();
            String password = "";
        int last_order_id = customerObject.get("last_order_id").getAsInt();
        String last_order_date = customerObject.get("last_order_date").getAsString();
        int orders_count = customerObject.get("orders_count").getAsInt();
        int total_spent = customerObject.get("total_spent").getAsInt();
        String avatar_url = customerObject.get("avatar_url").getAsString();

        JsonObject billing_address = bodyObject.getAsJsonObject("billing_address");
        String first_name_billing_address = billing_address.get("first_name").getAsString();
        String last_name_billing_address = billing_address.get("last_name").getAsString();
        String company_billing_address = billing_address.get("company").getAsString();
        String address_1_billing_address = billing_address.get("address_1").getAsString();
        String address_2_billing_address = billing_address.get("address_2").getAsString();
        String city_billing_address = billing_address.get("city").getAsString();
        String state_billing_address = billing_address.get("state").getAsString();
        String postcode_billing_address = billing_address.get("postcode").getAsString();
        String country_billing_address = billing_address.get("country").getAsString();
        String email_billing_address = billing_address.get("email").getAsString();
        String phone_billing_address = billing_address.get("phone").getAsString();

        BillingAddress[] billingAddresses = new BillingAddress[1];
        billingAddresses[0] = new BillingAddress(first_name_billing_address,last_name_billing_address,
                company_billing_address,address_1_billing_address,address_2_billing_address,
                city_billing_address,state_billing_address,postcode_billing_address,country_billing_address,
                email_billing_address,phone_billing_address);

        JsonObject shipping_address = bodyObject.getAsJsonObject("shipping_address");
        String first_name_shipping_address = shipping_address.get("first_name").getAsString();
        String last_name_shipping_address = shipping_address.get("last_name").getAsString();
        String company_shipping_address = shipping_address.get("company").getAsString();
        String address_1_shipping_address = shipping_address.get("address_1").getAsString();
        String address_2_shipping_address = shipping_address.get("address_2").getAsString();
        String city_shipping_address = shipping_address.get("city").getAsString();
        String state_shipping_address = shipping_address.get("state").getAsString();
        String postcode_shipping_address = shipping_address.get("postcode").getAsString();
        String country_shipping_address = shipping_address.get("country").getAsString();

        ShippingAddress[] shippingAddresses = new ShippingAddress[1];
        shippingAddresses[0] = new ShippingAddress(first_name_shipping_address,last_name_shipping_address,
                company_shipping_address,address_1_shipping_address,address_2_shipping_address,
                city_shipping_address,state_shipping_address,postcode_shipping_address,country_shipping_address);


            customer = new Customer(id,created_at,email,first_name,last_name,username,password,last_order_id,
                    last_order_date,orders_count,total_spent,avatar_url,billingAddresses,shippingAddresses);


        return customer;
    }
}

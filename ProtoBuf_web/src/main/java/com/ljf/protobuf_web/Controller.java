package com.ljf.protobuf_web;

import com.example.tutorial.AddressBookProtos;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mr.lin on 2018/6/8.
 */
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AddressBookProtos.Person person = AddressBookProtos.Person
                .newBuilder()
                .setName("Tom")
                .setId(1)
                .setEmail("Email")
                .addPhones(0,
                        AddressBookProtos.Person.PhoneNumber.newBuilder()
                                .setNumber("1234")
                                .setType(AddressBookProtos.Person.PhoneType.HOME)
                                .build())
                .build();

        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addPeople(0, person)
                .addPeople(1, person)
                .build();

        OutputStream outputStream = resp.getOutputStream();
        outputStream.write(addressBook.toByteArray());
        outputStream.flush();
        outputStream.close();
    }

}

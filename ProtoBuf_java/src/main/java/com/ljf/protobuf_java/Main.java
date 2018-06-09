package com.ljf.protobuf_java;

import com.example.tutorial.AddressBookProtos;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by mr.lin on 2018/6/6.
 */
public class Main {

    public static void main(String[] args) {

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

        System.out.println(addressBook.toByteString());

        try {

            AddressBookProtos.AddressBook addressBook1 = AddressBookProtos.AddressBook.parseFrom(addressBook.toByteArray());

            System.out.println(addressBook1.toString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }

}

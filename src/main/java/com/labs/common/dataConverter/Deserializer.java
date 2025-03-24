package com.labs.common.dataConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Deserializer {
    public static <T>T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);
        @SuppressWarnings("unchecked")
        T deserialized = (T) in.readObject();
        return deserialized;
    } 
}

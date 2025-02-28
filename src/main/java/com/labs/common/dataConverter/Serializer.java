package com.labs.common.dataConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer {
    public static <T extends Serializable>byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bos.toByteArray();
        return bytes;
    }
}

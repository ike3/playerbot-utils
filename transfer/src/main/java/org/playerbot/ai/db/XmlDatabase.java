package org.playerbot.ai.db;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.playerbot.ai.entity.Character;

public class XmlDatabase implements Database {
    private String data;

    @Override
    public Character select(String characterName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Character character) throws DbException {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Character.class);
            StringWriter writer = new StringWriter();
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(character, writer);
            data = writer.toString();
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public String getData() {
        return data;
    }

    @Override
    public void close() throws DbException {
    }

    @Override
    public void delete(String characterName) throws DbException {
        throw new UnsupportedOperationException();
    }
}

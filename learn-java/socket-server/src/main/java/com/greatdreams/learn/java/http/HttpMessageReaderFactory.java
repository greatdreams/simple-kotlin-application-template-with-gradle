package com.greatdreams.learn.java.http;

import com.greatdreams.learn.java.IMessageReader;
import com.greatdreams.learn.java.IMessageReaderFactory;

public class HttpMessageReaderFactory implements IMessageReaderFactory {

    public HttpMessageReaderFactory() {
    }

    @Override
    public IMessageReader createMessageReader() {
        return new HttpMessageReader();
    }
}
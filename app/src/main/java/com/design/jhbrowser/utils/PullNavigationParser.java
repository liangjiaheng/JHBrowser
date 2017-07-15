package com.design.jhbrowser.utils;

import android.util.Xml;


import com.design.jhbrowser.bean.Navigation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamL on 2017/5/2.
 */

public class PullNavigationParser implements NavigationParser {
    @Override
    public List<Navigation> parse(InputStream is) throws Exception {

        List<Navigation> navigations = null;
        Navigation navigation = null;

        XmlPullParser parser = Xml.newPullParser();//由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "utf-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    navigations = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("navigation")) {
                        navigation = new Navigation();
                    } else if (parser.getName().equals("id")) {
                        eventType = parser.next();
                        navigation.setId(Integer.parseInt(parser.getText()));
                    } else if (parser.getName().equals("url")) {
                        eventType = parser.next();
                        navigation.setUrl(parser.getText());
                    } else if (parser.getName().equals("title")) {
                        eventType = parser.next();
                        navigation.setTitle(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("navigation")) {
                        navigations.add(navigation);
                        navigation = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return navigations;
    }

    @Override
    public String serialize(List<Navigation> navigations) throws Exception {

        XmlSerializer serializer = Xml.newSerializer();//由android.util.Xml创建一个XmlSerizlizer实例
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);
        serializer.startDocument("utf-8", true);
        serializer.startTag("", "navigations");
        for (Navigation navigation : navigations) {
            serializer.startTag("", "navigation");
            serializer.attribute("", "id", navigation.getId() + "");

            serializer.startTag("", "url");
            serializer.text(navigation.getUrl());
            serializer.endTag("", "url");

            serializer.startTag("", "title");
            serializer.text(navigation.getTitle());
            serializer.endTag("", "title");

            serializer.endTag("", "navigation");
        }

        serializer.endTag("", "navigations");
        serializer.endDocument();

        return writer.toString();
    }
}

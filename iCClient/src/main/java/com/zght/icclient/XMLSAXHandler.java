package com.zght.icclient;

import java.io.InputStream;  
import java.io.StringWriter;  
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Map;
import java.util.List;  
  
import javax.xml.parsers.SAXParser;  
import javax.xml.parsers.SAXParserFactory;  
import javax.xml.transform.OutputKeys;  
import javax.xml.transform.Result;  
import javax.xml.transform.Transformer;  
import javax.xml.transform.TransformerFactory;  
import javax.xml.transform.sax.SAXTransformerFactory;  
import javax.xml.transform.sax.TransformerHandler;  
import javax.xml.transform.stream.StreamResult;  
  
import org.xml.sax.Attributes;  
import org.xml.sax.SAXException;  
import org.xml.sax.helpers.AttributesImpl;  
import org.xml.sax.helpers.DefaultHandler;  


public class XMLSAXHandler extends DefaultHandler{
	
	//
	//private ArrayList<ReturnData> mList;
	//
	private Map<String,String> mList;
	//
	private String Name, Value;
	private StringBuilder SB;  
	//
	/** 
     * MySaxHandler的构造方法
     *  
     * @param list  返回集合
     */  
    public XMLSAXHandler(Map<String,String> list){  
        this.mList = list;  
    }
	
	
	/** 
     * 当SAX解析器，解析到xml文档开始的时候，会调用的方法
     */  
    @Override  
    public void startDocument() throws SAXException {  
        super.startDocument();    
    }
    
    /** 
     * 当sax解析器解析到某个元素开始的时候，会调用的方法
     * ch记录了这个属性值的内容
     * 
     * characters 方法有可能执行多次
     * 正确的做法：用一个Stringbuilder 在characters方法中拼接数据，在endElement方法中填充数据
     */  
    @Override  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {  
        super.characters(ch, start, length);  
        SB.append(new String(ch, start, length) );//ƴ��
    }
	
    /** 
     * sax解析到某个元素   开始   的时候回调用的方法
     * localname记录了元素的属性的名字
     */  
    @Override  
    public void startElement(String uri, String localName, String qName,  
            Attributes attributes) throws SAXException {  
        super.startElement(uri, localName, qName, attributes);  
        //记录localName
        Name = localName;
        Value = "";
        SB = new StringBuilder();
    }
    
    /** sax解析到某个元素的  结束  时候回调用的方法
     *localname记录了元素的属性的名字
     */  
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
        super.endElement(uri, localName, qName);  
        //Element开始和结束的名字相同
        if( Name.equalsIgnoreCase( localName ) )
        {
	        Value = SB.toString(); 
	        //增加
	        //mItem = new ReturnData();
	        //mItem.SetName(Name);
	        //mItem.SetValue(Value);
	        mList.put(Name,Value); //将对象加入到list中
        }
    }
    
    
}

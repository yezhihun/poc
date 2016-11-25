package com.yingu.poc.common.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

/**
 * 
 * 
 *
 * Description:
 *
 * @author tianye
 * @version V1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2016年9月8日下午3:29:23    tianye       V1.0
 * </pre>
 */
@SuppressWarnings("unchecked")
public class JaxbUtil {
	/**
	 * JavaBean转换成xml 默认编码UTF-8
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertToXml(Object obj) {
		return convertToXml(obj, "UTF-8");
	}

	/**
	 * JavaBean转换成xml
	 * 
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String convertToXml(Object obj, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * xml转换成JavaBean
	 * 
	 * @param xml
	 * @param c
	 * @return
	 */
	public static <T> T converyToJavaBean(String xml, Class<T> c) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}

	/**
	 * 
	 * @Author tianye
	 * @Description: XML -> Java Object, 特别支持对Root Element是Collection的情形.
	 * @return List<T>
	 * @throws
	 */
	public static <T> List<T> converyToJavaBean(String rootName, String xml, Class<T> c) {
		List<T> list = new ArrayList<T>();
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			String title = xml.substring(0, xml.indexOf(">") + 1);
			String body = xml.substring(xml.indexOf(">") + 1);
			if (body.contains("<" + rootName)) {
				if(body.length()>(body.indexOf(">") + 1)){
					String children = body.substring(body.indexOf(">") + 1,
							body.lastIndexOf("<"));
					String splitStr = children.substring(0,
							children.indexOf(">") + 1);
					String[] str = children.split(splitStr);
					for (String s : str) {
						if (!s.isEmpty()) {
							StringReader reader = new StringReader(title + splitStr
									+ s);
							list.add((T) unmarshaller.unmarshal(reader));
						}
					}
				}
			} else {
				StringReader reader = new StringReader(xml);
				list.add((T) unmarshaller.unmarshal(reader));
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @Author tianye
	 * @Description: Java Object->Xml, 特别支持对Root Element是Collection的情形.
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static <T> String convertToXml(Collection<T> root, String rootName,
			Class<T> c) {
		try {
			JAXBContext context = JAXBContext.newInstance(c,
					CollectionWrapper.class);
			Marshaller marshaller = context.createMarshaller();
			CollectionWrapper<T> wrapper = new CollectionWrapper<T>();
			wrapper.collection = root;

			JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(
					new QName(rootName), CollectionWrapper.class, wrapper);

			StringWriter writer = new StringWriter();
			marshaller.marshal(wrapperElement, writer);

			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 封装Root Element 是 Collection的情况.
	 * 
	 * @param <T>
	 */
	public static class CollectionWrapper<T> {
		@XmlAnyElement
		protected Collection<T> collection;
	}
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOM4JXMLdemo {
	//全局变量
	Document document =null;
	
	// 获取document对象，为全局document赋值
	public void getDocument() {
		SAXReader saxreader = new SAXReader();
		try {
			document = saxreader.read(new File("infomation.xml"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	// 显示收藏信息（品牌、型号）
	public void showPhoneInfo() {
		// 获取根节点
		Element root = document.getRootElement();
		// 获取根节点下面的子节点，
		for(Iterator itbrand = root.elementIterator();itbrand.hasNext();) {
			// 遍历迭代器中的每个brand节点
			Element brand = (Element)itbrand.next();
			// 显示品牌：brand的name属性
			System.out.println("品牌："+brand.attributeValue("name"));
			// 获取品牌下的手机型号
			for(Iterator ittype = brand.elementIterator();ittype.hasNext();) {
				Element type = (Element)ittype.next();
				System.out.println("\t型号："+type.attributeValue("name"));
			}
		}
	}
	
	public void add() {
		// 获取XML节点
		Element root = document.getRootElement();
		// 为根节点添加一个brand元素，name属性赋值为三星
		Element brand = root.addElement("Brand");
		brand.addAttribute("name", "三星");
		// 为brand元素添加type元素，type元素name属性赋值为galxy fold
		Element type = brand.addElement("type");
		type.addAttribute("name", "Galxy");
		this.saveXML("new.xml");
	}
	
	
	// 保存xml
	public void saveXML(String path) {
		
		// 创建一个XMLwriter对象（等同于dom解析中的转换器）
		OutputFormat formate = OutputFormat.createPrettyPrint();
		// formate.setEncoding("BGK");
		try {
			XMLWriter writer = new XMLWriter(new FileWriter(path),formate);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	// 修改手机收藏信息
	public void update() {
		// 获取xml的根元素
		Element root = document.getRootElement();
		int id =0;
		for(Iterator itbrands = root.elementIterator();itbrands.hasNext();) {
			Element brand = (Element)itbrands.next();
			// 为brand设置id属性
			id++;
			brand.addAttribute("id", id+"");
		}
		// 保存xml
		this.saveXML("new.xml");
	}
	
	// 删除手机收藏信息
	public void delete() {
		// 获取xml的根元素
		Element root = document.getRootElement();
		// 获取所有brand元素，遍历
		for(Iterator itbrands = root.elementIterator();itbrands.hasNext();) {
			Element brand = (Element)itbrands.next();
			// 找到品牌名为华为的元素
			if (brand.attributeValue("name").equals("华为")) {
				// 由父节点删除
				brand.getParent().remove(brand);
			}
		}
		// 保存xml
		this.saveXML("new.xml");
				
	}
	public static void main(String[] args) {
		DOM4JXMLdemo dom4jxml = new DOM4JXMLdemo();
		dom4jxml.getDocument();
		dom4jxml.add();
		dom4jxml.update();
		// dom4jxml.showPhoneInfo();
		System.out.print("hello");
	}
	
}

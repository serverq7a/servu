package org.mistex.game.world.content.clanchat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Michael | Chex
 */
public class ClanFile {
	
	private File file;
	
	public ClanFile(String owner) {
		file = new File("./Data/newClans/" + MistexUtility.capitalize(owner.toLowerCase()) + ".xml");
		if (!file.exists()) {
			createClan(owner);
		}
	}
	
	public void createClan(String name) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();

			Element clan = doc.createElement("Clan");
			doc.appendChild(clan);

			clan.appendChild(doc.createElement("ClanData"));
			clan.appendChild(doc.createElement("Friends"));
			clan.appendChild(doc.createElement("Recruits"));
			clan.appendChild(doc.createElement("Corporals"));
			clan.appendChild(doc.createElement("Sergeants"));
			clan.appendChild(doc.createElement("Lieutenants"));
			clan.appendChild(doc.createElement("Captains"));
			clan.appendChild(doc.createElement("Generals"));
			
			insertElement(doc, "ClanData", "name", name);
			insertElement(doc, "ClanData", "owner", name);
			insertElement(doc, "ClanData", "lootshare", "0");
			insertElement(doc, "ClanData", "whoCanEnter", "0");
			insertElement(doc, "ClanData", "whoCanTalk", "0");
			insertElement(doc, "ClanData", "whoCanKick", "0");
			doc.getDocumentElement().normalize();
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			Client c = (Client) World.getPlayer(name);
			if (c != null) {
				for (long asLong : World.getPlayer(name).friends) {
					if (asLong <= 0)
						continue;
					changeElement("Friends", "name", MistexUtility.longToPlayerName2(asLong).replaceAll("_", " "), false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void insertElement(Document document, String parent, String child, String content) {
		try {
			NodeList nodeList = document.getElementsByTagName(parent);
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node nNode = nodeList.item(index);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Element contentElement = document.createElement(child);
					contentElement.appendChild(document.createTextNode(content));
					eElement.appendChild(contentElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getSetting(String child) {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setIgnoringComments(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(file);

			NodeList parentElements = doc.getElementsByTagName("ClanData");
			for (int index = 0; index < parentElements.getLength(); index++) {
				Node currentElement = parentElements.item(index);
				if (currentElement.getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) currentElement;
					NodeList children = childElement.getElementsByTagName(child);
					for (int i = 0; i < children.getLength(); i++) {
						Element nameElement = (Element) children.item(i);
						if (nameElement.getNodeName().equalsIgnoreCase(child)) {
							return Integer.parseInt(nameElement.getTextContent());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public String getStringSetting(String child) {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setIgnoringComments(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(file);

			NodeList parentElements = doc.getElementsByTagName("ClanData");
			for (int index = 0; index < parentElements.getLength(); index++) {
				Node currentElement = parentElements.item(index);
				if (currentElement.getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) currentElement;
					NodeList children = childElement.getElementsByTagName(child);
					for (int i = 0; i < children.getLength(); i++) {
						Element nameElement = (Element) children.item(i);
						if (nameElement.getNodeName().equalsIgnoreCase(child)) {
							return nameElement.getTextContent();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<String> retrieveRanks(String parent) {
		List<String> list = new ArrayList<String>();
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setIgnoringComments(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(file);

			NodeList parentElements = doc.getElementsByTagName(parent);
			for (int index = 0; index < parentElements.getLength(); index++) {
				Node currentElement = parentElements.item(index);
				if (currentElement.getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) currentElement;
					NodeList children = childElement.getElementsByTagName("name");
					for (int i = 0; i < children.getLength(); i++) {
						Element nameElement = (Element) children.item(i);
						list.add(nameElement.getTextContent());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public void changeElement(String parent, String child, String content, boolean remove) {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setIgnoringComments(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(file);

			NodeList rankList = doc.getElementsByTagName(parent);
			for (int index = 0; index < rankList.getLength(); index++) {
				Node curRank = rankList.item(index);
				if (curRank.getNodeType() == Node.ELEMENT_NODE) {
					Element rankType = (Element) curRank;
					NodeList names = rankType.getElementsByTagName(child);
					for (int i = 0; i < names.getLength(); i++) {
						Element nameElement = (Element) names.item(i);
						if (nameElement.getTextContent().equalsIgnoreCase(content)) {
							rankType.removeChild(names.item(i));
						}
					}
				}
			}
			
			if (!remove)
				insertElement(doc, parent, child, content);
			
			XPath xp = XPathFactory.newInstance().newXPath();
			NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);

			for (int i = 0; i < nl.getLength(); ++i) {
				Node node = nl.item(i);
				node.getParentNode().removeChild(node);
			}

			doc.getDocumentElement().normalize();
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}